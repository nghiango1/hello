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
a lot of nix-shell on top each other

## Nix-build

Now I want to build some thing using this nix as my build system, but this is
for create a shell, which mean we nessed our environment over and over. I belive
we want to use `nix-build` instead. Which turn us to use `default.nix`.

After some try and error (and look through almost not thing to help me start
with `nix-build`), I tested the `nix-build` to sucessfully run my example program.
But it store my code into nix store, which isn't ideal for developing
