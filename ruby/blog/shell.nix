{ pkgs ? import <nixpkgs> {} }:

let
  inherit (pkgs) bundlerEnv stdenv;
  gems = bundlerEnv {
    name = "PROJECTNAME-gems";
    gemdir = ./.;
    groups = [ "default" "production" "development" "test"];
    ruby = pkgs.bundler.ruby;
  };
in
pkgs.mkShell {
  buildInputs = with pkgs; (gems.buildInputs ++ [
    bundler
    libyaml
  ]);
}
