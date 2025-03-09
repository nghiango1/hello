{ pkgs ? import <nixpkgs> {} }:

let
  inherit (pkgs) bundlerEnv;
  gems = bundlerEnv {
    name = "blog-gems";
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
