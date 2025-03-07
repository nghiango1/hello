{ pkgs ? import <nixpkgs> { } }:

pkgs.mkShell{
  packages = with pkgs; [ cmake];
  shellHook = ''
    rm -rf build
    mkdir build
    cd build/
    cmake ..
  '';
}

