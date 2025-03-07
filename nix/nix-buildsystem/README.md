# Using nix to build

After readding about `nix-shell`, and knowing `nix` support as a build system.
I want to follow and using nix to build a simple C++ project

Original: <https://nix.dev/tutorials/first-steps/declarative-shell>

## Nix-shell

We start with a `nix-shell -p <anypackagehere> --run 'cmmand to run here'`
to a script file

```sh
#!/usr/bin/env nix-shell
#! nix-shell -i bash --pure
#! nix-shell -p bash cacert curl jq python3Packages.xmljson
#! nix-shell -I nixpkgs=https://github.com/NixOS/nixpkgs/archive/2a601aafdc5605a5133a2ca506a34a3a73377247.tar.gz

curl https://github.com/NixOS/nixpkgs/releases.atom | xml2json | jq .
```

But, we have our prefered usage for `nix-shell`, which using `shell.nix` within
the directory to fire up a new session. We skip over some hoop to create a
shell runner inside our `shell.nix`

- We can see that nixpkgs use a tarball that pull directly from github. This
  can be not ideal as we will rebuild a lot of dependance package using tarball
  definition instead of reuse our own nixOS already pulled + built package
- Other than that, we using:

  - `let .. in`: which look like variable declare
  - `pkgs.mkShellNoCC`: is a specialized stdenv.mkDerivation that removes some
    repetition when using it with nix-shell. Don't understand that yet but it is
    [documented here](https://nixos.org/manual/nixpkgs/stable/#sec-pkgs-mkShell).
    We also found that `{ pkgs ? import <nixpkgs> {} }:` is usually use, which
    I assume that it will import our default nix repo

```nix
let
  nixpkgs = fetchTarball "https://github.com/NixOS/nixpkgs/tarball/nixos-24.05";
  pkgs = import nixpkgs { config = {}; overlays = []; };
in

pkgs.mkShellNoCC {
  packages = with pkgs; [
    cowsay
    lolcat
  ];

  GREETING = "Hello, Nix!";

  shellHook = ''
    echo $GREETING | cowsay | lolcat
  '';
}
```

to use current system package instead

```nix
{ pkgs ? import <nixpkgs> {} }:

let
  # nixpkgs = fetchTarball "https://github.com/NixOS/nixpkgs/tarball/nixos-24.05";
  # pkgs = import nixpkgs { config = {}; overlays = []; };
in

pkgs.mkShellNoCC {
  packages = with pkgs; [
    cowsay
    lolcat
  ];

  GREETING = "Hello, Nix!";

  shellHook = ''
    echo $GREETING | cowsay | lolcat
  '';
}
```

I do try to turn it into build script using shellHook, it turn out i will nested
a lot of nix-shell on top each other. And `make` doesn't work inside shellHook.
The path doesn't seem to match and header file can't be found.

## Nix-build

Now create a shell isn't enough with nix-build. I belive that we want to use
`nix-build` instead. Which turn us to use `default.nix` file instead.

After some try and error (and look through almost not thing to help me start
with `nix-build`), I tested the `nix-build` to sucessfully run my example program.
But it store my code into nix store, which isn't ideal for developing

- No `stdenv`, `lib`, ... or anything like that. I belive they are to import
  thing, but don't know where them came from (it tell me they are missing)
- `fs` is needed for import a local build source, added to `src`. Similar to
  `gitFetch` or thing like that when we try to fetch source from internet, which
  I can't find any where using serach engine, the tutorial doc does have it own
  article though <https://nix.dev/tutorials/working-with-local-files>

Also, the tutorial use some thing to pin the Nixpkgs dependency, so maybe it is
where the `stdenv, lib` is defined. I just replace it with import `pkgs` and it
work so I skip the process

> It drain a lot of mobile data to pull some thing so I have to stop it. Now,
> if I can build the package, how exactly I can create and import gcov for
> test coverage over the source, and cross-compile tho

```cpp
{
  pkgs ? import <nixpkgs> {},
}:

let
  fs = pkgs.lib.fileset;
in

pkgs.stdenv.mkDerivation {
  name = "example";
  src = fs.toSource {
    root = ./.;
    fileset = ./.;
  };
  nativeBuildInputs = [ pkgs.cmake ];
}
```

It also seem that `cmake` is supported by default, so there no need for further
custom the build step. I want to take a look into the process though

```sh
nix-build
```
