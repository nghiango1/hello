# `c` lang
I like `c` when it time to know how thing work

## Feature

- `hypervisor/`: How VM acutally work, I don't know, but there is `vmx` thing that support creating a VM on hardware? I'd love to try to make my own VM manage program from eh.. almost nothing

- `ProjectTemplate/`: A `c` hello world project with `Make`, I'd copy this template when I start a new one

- `lrucache/`: Basic data structure

- `search/`: Contain **two crystal ball** problem, which is a special O(sqrt n) problem

- `sort/`: Quicksort (single file, project) and Radixsort (single file) implement

- `structure/`: Creating a structure with some function that directly tie to them in `c`. I like to sample this with List and LinkedList data structure, which is exactly what I did.

- `variable/`: Some indepth security to know about pointer and array variable in c (initialling, type casting, overbound set, ...)

## How to use

### Build the code

For standalone code, build the code using VIM make command. Here is the configuartion needed (for quick fix list also)

```vimrc
" Setup compiler
" c
set makeprg=gcc\ -fdiagnostics-plain-output\ -g\ -I.\ -o\ dist/%:r\ %
"                                      this error a lot --^^^^
" quick fix list error parse
set errorformat=%f:%l:%c:%t:%m,%f:%l:%c:%m,%f:%m,%m
```

> I manually create `dist` folder so that it will be `.gitignore`.

For anything that have a project structure:
- `src/` contain the source code
- `dist/` should contain the build code
- `Makefile` for `make` CLI, you can build executable by using
    ```sh
    make
    ```

### Debug (with DAP)

Debug the code using DAP, eh, quick note:
- Install `nvim-dap` (`kickstart` repo could be a good start)
- Install `nvim-dap-ui` (you won't want to use REPL only to debug)
- Install `codelldb` and put it into path (DAP server for handling `rust`, `c`, `c++`)
- Config `nvim-dap`: setup keybind
- Config `nvim-dap`: setup DAP server to handle `c` file. It should be place in `nvim/after/plugin` folder
    ```lua
    local dap = require('dap')

    dap.adapters.codelldb = {
        type = 'server',
        port = "${port}",
        executable = {
            command = 'codelldb',
            args = { "--port", "${port}" },
        }
    }

    dap.adapters.c = dap.adapters.codelldb
    dap.configurations.c = {
        {
            name = "Launch DAP default",
            type = "c",
            request = "launch",
            program = "${workspaceFolder}/dist/${fileBasenameNoExtension}",
            cwd = '${workspaceFolder}',
            stopOnEntry = false,
        },
    }
    ```

### Debug with `gdb`

> Just don't, please get good with IDA to better your life
