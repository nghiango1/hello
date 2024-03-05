const mapBuiltin = new Map()

mapBuiltin.set('key', 'value')

const value = mapBuiltin.get('key')
if (value !== undefined) {
    console.log(`Variable map have key = 'key' with value = '${value}' of type = '${typeof value}'`)
}

const toString = mapBuiltin.get('toString')
if (toString === undefined) {
    console.log(`Variable map not have key = 'toString'`)
}

console.log('\n\n')
// Object it self is a hashmap implement, but with some defined property
// which may make it unideal to be use as normal map operation, where some
// unwanted key inside our map cause error
const obj = {}

obj['key'] = 'value'

console.log(`Object obj have key='key' with value = '${obj.key}' of type '${typeof obj['key']}'`)
console.log(`Object obj have key='toString' with value = '${obj.toString.toString()}' of type '${typeof obj['toString']}'`)
