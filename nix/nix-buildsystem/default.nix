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
