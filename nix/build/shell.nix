{ pkgs ? import <nixpkgs> { } }:

let
  # nixpkgs = fetchTarball "https://github.com/NixOS/nixpkgs/tarball/nixos-24.05";
  # pkgs = import nixpkgs { config = {}; overlays = []; };

in pkgs.mkShellNoCC {
  packages = with pkgs; [ cmake cowsay lolcat ];

  GREETING = "Hello, Nix!";

  shellHook = ''
    rm -rf build
    mkdir build
    cd build/
    pwd
    cmake ..
    echo $GREETING | cowsay | lolcat
    exit
  '';
}

