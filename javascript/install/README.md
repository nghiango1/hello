# Install javascript environment

Use node version manager is better, find `nvm` and install node using it.

Create new project
- `nvim init`: Create project.json
- Create `index.js` (or any file name that you config)

Install eslint and config code formating with LSP error on javascript formating
- `npm init @eslint/config`: Create eslint, follow all instruction, recommend main use in node instead in browser for quick dev. Adding both is the right way to go.
- Additional rules: Indent should use 4 space, and console.log() should not get us warrning
    ```
    rules: {
        'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
        indent: ['error', 4],
    },
    ```

Install javascript LSP - Only option is typescript-lsp 
- Install typescript LSP using Mason (neovim)
    ◍ typescript-language-server tsserver, tsserver
- Enable typescript type check in javascript file with this in the top of the file
    ```js
    // @ts-check
    ```
- Or even better, set up `jsconfig.json`, which is config file for typescipt-lsp for javascript project, which persitance on all project file. Set checkJs flag to `true` to Enable type checking on JavaScript files.
    ```json
    {
        "checkJs": true,
    }
    ```

Adding `jsdoc` eslint enforce (which will get fix when it not work anymore)
- Install `jsdoc`
    ```sh
    npm install --save-dev eslint-plugin-jsdoc
    ```
- Force eslint to require `jsdoc` in file by adding these comment on top of the file
    ```js
    /* eslint "require-jsdoc": ["error", {
        "require": {
            "FunctionDeclaration": true,
            "MethodDefinition": true,
            "ClassDeclaration": true,
            "ArrowFunctionExpression": true,
            "FunctionExpression": true
        }
    }] */
    ```
- Simmilar, to make sure this is enfored on project. Set this config
    ```json
    "rules": {
        "jsdoc/require-description": "warn",
        "valid-jsdoc": "error"
    },
    ```

DAP
- Install [mason] js-debug-adapter, config file with nvim in github
- Use launch only (option 1)
