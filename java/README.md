# Java

Suck a pain to deal with, when the language is for job, not for hobbyist project.

## DAP

Reuse from vscode extension

## LSP

### LSP start-up command

Java have LSP, a great LSP know to man provided by [Eclipse Foundation](https://www.eclipse.org/org/foundation/) who help my poor soul to get out from Oracle Java in my main job. Tool chain of java could alway be like this when you try to look at it tho.

```lua
  local cmd = {
    -- 💀
    'java',
    '-Declipse.application=org.eclipse.jdt.ls.core.id1',
    '-Dosgi.bundles.defaultStartLevel=4',
    '-Declipse.product=org.eclipse.jdt.ls.core.product',
    '-Dosgi.sharedConfiguration.area=/nix/store/f4bck5zlwi9lw98pwcaxfb87n0qwix6k-jdt-language-server-1.26.0/share/config',
    --              yes, i use nixos ^^^^^^^^^^
    '-Dosgi.sharedConfiguration.area.readOnly=true',
    '-Dosgi.checkConfiguration=true',
    '-Dosgi.configuration.cascaded=true',
    '-Dlog.protocol=true',
    '-Dlog.level=ALL',
    '-javaagent:' .. path.java_agent,
    '-Xms1g',
    '--add-modules=ALL-SYSTEM',
    '--add-opens',
    'java.base/java.util=ALL-UNNAMED',
    '--add-opens',
    'java.base/java.lang=ALL-UNNAMED',

    -- 💀
    '-jar',
    path.launcher_jar,

    -- 💀
    '-data',
    data_dir,
  }
```

This is the command I have to reconstruct in lua to form a command to run `jdt-language-server` from `eclipse.jdt.ls`

Also, the checker for project root rely on java project manager like maven or gradle or (simply) `.git`. So to make sure the LSP doesn't "malfunction", create a empty `.git` directory inside each one will help a lot.

```lua
local root_files = {
  '.git',
  'mvnw',
  'gradlew',
  'pom.xml',
  'build.gradle',
}
```

### LSP Build-Cache

LSP will build the project! Some time you want to delete these built file to either:
- Have LSP running/analysing update the code change that some how get loss/error
- Having the LSP acutally running (Yes, the LSP just won't working because of it built-cache gone wrong)

To rebuilt the code that LSP are using, we need to delete built-cache in the cache directory. Normaly it will be the `data_dir` provided to the LSP via IDE client configuration. Here is the path from my neovim config:
```lua
      local function get_jdtls_paths()
        if cache_vars.paths then
          return cache_vars.paths
        end

        local path = {}

        path.data_dir = vim.fn.stdpath('cache') .. '/nvim-jdtls'
```

I can check it in runtime via command
```vim
:lua print(vim.fn.stdpath('cache') .. '/nvim-jdtls')
```

Which is in `~/.cache/nvim/nvim-jdtls` directory
