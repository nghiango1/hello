# Install javascript environment

Use node version manager is better, find `nvm` and install node using it.

Create new project
- `nvim init`: Create project.json
- Create `index.js` (or any file name that you config)

Install eslint and config code formating with LSP error on javascript formating
- `npm init @eslint/config`: Create eslint, follow all instruction, recommend main use in node instead in browser for quick dev. Adding both is the right way to go.
- Additional rules: Indent should use 4 space, and console.log() should not get us warning
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
- Or even better, set up `jsconfig.json`, which is config file for typescipt-lsp for javascript project, which persitance on all project file. Set `checkJs` flag to `true` to Enable type checking on JavaScript files.
    ```json
    "compilerOptions": {
        "checkJs": true,
    }
    ```
- ES6 and we have module support directly inside javascript, which is great
    - To have LSP support module import, export with ES6 JS module, config
        ```json
        "compilerOptions": {
            "moduleResolution": "classic",
        }
        ```
        This make sure we have `./src/index.mjs` a like for import to work
    - Readmore at [developer.mozilla.org](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Modules)
    - Use special extension for module javascript file `*.mjs` so LSP can work perfectly


Adding `jsdoc` eslint enforce style guide have two option: Plugin base (recommend), or Native base
- Option 1 (Recommend): Install `jsdoc` plugin for eslint
    ```sh
    npm install --save-dev eslint-plugin-jsdoc
    ```

    and add it to eslint config, with specify so it could work well with typescript-language-server LSP (typescript define type)

    ```json
    "extends": [
        "plugin:jsdoc/recommended-typescript-flavor-error"
    ],
    ```

- Option 2 (Optional): Enable `eslint` built-in support for `jsdoc` in file by adding these comment on top of the file (this is said to be deprecated eslint document and may not work in future update)
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

    To make sure this is enfored on project. Set this in eslint config file (this is said to be deprecated eslint document and may not work in future update)

    ```json
    "rules": {
        "jsdoc/require-description": "warn",
        "valid-jsdoc": "error"
    },
    ```

DAP (neovim)
- Install [mason] js-debug-adapter, using my nvim config in github
- Use launch only (option 1)
