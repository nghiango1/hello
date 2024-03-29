package evaluator

import (
	"fmt"
	"interingo/ast"
	"interingo/object"
)

var (
	NULL  = &object.Null{}
	TRUE  = &object.Boolean{Value: true}
	FALSE = &object.Boolean{Value: false}
)

func nativeBoolToBooleanObject(input bool) *object.Boolean {
	if input {
		return TRUE
	}
	return FALSE
}

func newError(format string, a ...interface{}) *object.Error {
	return &object.Error{Message: fmt.Sprintf(format, a...)}
}

func isError(obj object.Object) bool {
	if obj != nil {
		if obj.Type() == object.ERROR_OBJ {
			return true
		}
	}
	return false
}

func Eval(node ast.Node, env *object.Environment) object.Object {
	switch node := node.(type) {
	case *ast.Program:
		return evalProgram(node.Statements, env)
	case *ast.ExpressionStatement:
		return Eval(node.Expression, env)
	case *ast.Boolean:
		return evalBooleanLiteral(node)
	case *ast.IntegerLiteral:
		return evalIntegerLiteral(node)
	case *ast.Identifier:
		return evalIdentifier(node, env)
	case *ast.CallExpression:
		return evalCallExpression(node, env)
	case *ast.PrefixExpression:
		right := Eval(node.Right, env)
		if isError(right) {
			return right
		}
		return evalPrefixExpression(node.Operator, right)
	case *ast.InfixExpression:
		right := Eval(node.Right, env)
		if isError(right) {
			return right
		}
		left := Eval(node.Left, env)
		if isError(left) {
			return left
		}
		return evalInfixExpression(node.Operator, left, right)
	case *ast.BlockStatement:
		return evalBlockStatement(node, env)
	case *ast.IfExpression:
		return evalIfStatement(node, env)
	case *ast.FunctionLiteral:
		return evalFunctionLiteral(node, env)
	case *ast.LetStatement:
		val := Eval(node.Value, env)
		if isError(val) {
			return val
		}
		env.Set(node.Name.Value, val)
	case *ast.ReturnStatement:
		return evalReturnStatement(node, env)
	}
	return nil
}

func evalProgram(stmts []ast.Statement, env *object.Environment) object.Object {
	var result object.Object
	for _, stmt := range stmts {
		result = Eval(stmt, env)
		switch result := result.(type) {
		case *object.ReturnValue:
			return result.Value
		case *object.Error:
			return result
		}
	}
	return result
}

func evalBlockStatement(block *ast.BlockStatement, env *object.Environment) object.Object {
	var result object.Object
	for _, statement := range block.Statements {
		result = Eval(statement, env)
		if result != nil {
			rt := result.Type()
			if rt == object.RETURN_VALUE_OBJ || rt == object.ERROR_OBJ {
				return result
			}
		}
	}
	return result
}

func evalBooleanLiteral(node ast.Node) object.Object {
	boolLiteral, _ := node.(*ast.Boolean)
	return nativeBoolToBooleanObject(boolLiteral.Value)
}

func evalIntegerLiteral(node ast.Node) object.Object {
	intLiteral, _ := node.(*ast.IntegerLiteral)
	intObj := &object.Integer{
		Value: intLiteral.Value,
	}
	return intObj
}

func evalIdentifier(node ast.Node, env *object.Environment) object.Object {
	identLiteral, _ := node.(*ast.Identifier)
	name := identLiteral.Value
	value, ok := env.Get(name)
	if !ok {
		return newError("identifier not found: %s", name)
	}
	return value
}

func evalFunctionLiteral(fl *ast.FunctionLiteral, env *object.Environment) *object.Function{
	params := fl.Parameters
	body := fl.Body
	return &object.Function{Parameters: params, Body: body, Env: env}
}

func evalFunctionObject(fo *object.Function, args []ast.Expression) object.Object {
	numOfFuncParam := len(fo.Parameters)
	numOfArgs := len(args)
	if numOfArgs != numOfFuncParam {
		return newError("Function take %d agrument but %d are given", numOfArgs, numOfFuncParam)
	}

	encloseEnv := object.NewEnclosedEnvironment(fo.Env)
	for i := 0; i < numOfFuncParam; i++ {
		argValue := Eval(args[i], fo.Env)
		if isError(argValue) {
			return argValue
		}
		encloseEnv.Set(fo.Parameters[i].Value, argValue)
	}

	result := Eval(fo.Body, encloseEnv)
	if resultValue, ok := result.(*object.ReturnValue); ok {
		return resultValue.Value
	}
	return result
}

func evalCallExpression(node ast.Node, env *object.Environment) object.Object {
	callExpression, _ := node.(*ast.CallExpression)

	result := Eval(callExpression.Function, env)

	if isError(result) {
		return result
	}

	functionObject, ok := result.(*object.Function)
	if !ok {
		return newError("%s is not callable", result.Type())
	}

	return evalFunctionObject(functionObject, callExpression.Arguments)
}

func evalMinusOperatorExpression(right object.Object) object.Object {
	if right.Type() != object.INTEGER_OBJ {
		return newError("unknown operator: -%s", right.Type())
	}
	value := right.(*object.Integer).Value
	return &object.Integer{Value: -value}
}

func evalBangOperatorExpression(right object.Object) object.Object {
	switch right {
	case TRUE:
		return FALSE
	case FALSE:
		return TRUE
	case NULL:
		return TRUE
	default:
		return FALSE
	}
}

func evalPrefixExpression(operator string, right object.Object) object.Object {
	switch operator {
	case "-":
		return evalMinusOperatorExpression(right)
	case "!":
		return evalBangOperatorExpression(right)
	default:
		return newError("unknown operator: %s%s", operator, right.Type())
	}
}

func evalIntegerInfixExpression(operator string, left object.Object, right object.Object) object.Object {
	leftVal := left.(*object.Integer).Value
	rightVal := right.(*object.Integer).Value
	switch operator {
	case "+":
		return &object.Integer{Value: leftVal + rightVal}
	case "-":
		return &object.Integer{Value: leftVal - rightVal}
	case "*":
		return &object.Integer{Value: leftVal * rightVal}
	case "/":
		if rightVal == 0 {
			return newError("divide by zero: %d %s %d", leftVal, operator, rightVal)
		}
		return &object.Integer{Value: leftVal / rightVal}
	case "==":
		return nativeBoolToBooleanObject(leftVal == rightVal)
	case "!=":
		return nativeBoolToBooleanObject(leftVal != rightVal)
	case ">":
		return nativeBoolToBooleanObject(leftVal > rightVal)
	case "<":
		return nativeBoolToBooleanObject(leftVal < rightVal)
	default:
		return newError("unknown operator: %s %s %s", left.Type(), operator, right.Type())
	}
}

func evalBooleanInfixExpression(operator string, left object.Object, right object.Object) object.Object {
	leftVal := left.(*object.Boolean).Value
	rightVal := right.(*object.Boolean).Value
	switch operator {
	case "==":
		return nativeBoolToBooleanObject(leftVal == rightVal)
	case "!=":
		return nativeBoolToBooleanObject(leftVal != rightVal)
	default:
		return newError("unknown operator: %s %s %s", left.Type(), operator, right.Type())
	}
}

func evalInfixExpression(operator string, left object.Object, right object.Object) object.Object {
	switch {
	case left.Type() == object.INTEGER_OBJ && right.Type() == object.INTEGER_OBJ:
		return evalIntegerInfixExpression(operator, left, right)
	case left.Type() == object.BOOLEAN_OBJ && right.Type() == object.BOOLEAN_OBJ:
		return evalBooleanInfixExpression(operator, left, right)
	case left.Type() != right.Type():
		return newError("type mismatch: %s %s %s", left.Type(), operator, right.Type())
	default:
		return newError("unknown operator: %s %s %s", left.Type(), operator, right.Type())
	}
}

func evalIfStatement(ie *ast.IfExpression, env *object.Environment) object.Object {
	condition := Eval(ie.Condition, env)
	errObj, ok := condition.(*object.Error)
	if ok {
		return errObj
	}
	if isTruthy(condition) {
		return Eval(ie.Consequence, env)
	} else if ie.Alternative != nil {
		return Eval(ie.Alternative, env)
	} else {
		return NULL
	}
}

func isTruthy(obj object.Object) bool {
	switch obj {
	case NULL:
		return false
	case TRUE:
		return true
	case FALSE:
		return false
	default:
		return true
	}
}

func evalReturnStatement(rs *ast.ReturnStatement, env *object.Environment) object.Object {
	val := Eval(rs.ReturnValue, env)
	return &object.ReturnValue{Value: val}
}
