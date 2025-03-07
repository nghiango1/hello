{
  pkgs ? import <nixpkgs> {},
}:

let
  fs = pkgs.lib.fileset;

  binName = "example";
in

pkgs.stdenv.mkDerivation {
  name = "example";
  src = fs.toSource {
    root = ./.;
    fileset = ./.;
  };
  nativeBuildInputs = [ pkgs.cmake ];
}
