# Install environment
Use node version manager is better, find `nvm` and install node using it.

Install eslint, create new project
- `nvim init`: Create project.json
- `npm init @eslint/config`: Create eslint, follow all instruction, recommend main use in node instead in browser for quick dev. Adding both is the right way to go.
- Additional rules: Indent should use 4 space, and console.log() should not get us warrning
    ```
    rules: {
        'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
        indent: ['error', 4],
    },
    ```

DAP
- Install [mason] js-debug-adapter, config file with nvim in github
- Use launch only, option 1
