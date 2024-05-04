# `c#` language

This contain notes to install `dotnet` for linux c# development, which mainly used for my niche [project](https://github.com/nghiango1/RightClickMoveMode/)

## Notes

Try to follow official documentation, remember to use `dotnet-install.sh` with the right version you want to install. Here is all I need to do when start a full setup almost from ground up

### Build Environment

#### Setup dotnet cli

Install the right version of .dotnet (I use dotnet v6.0)
```sh
/dotnet-install.sh --version 6.0.421
```

Setup PATH, this can be done by adding there line to your `~/.profile` file
```sh
# set PATH so it includes .dotnet
if [ -d "$HOME/.dotnet" ] ; then
    PATH="$HOME/.dotnet/:$PATH"
fi
```

Source the `.profile` file for immediate effect in current shell
```sh
source ~/.profile
```

Check installation complete with these command
```sh
which dotnet
# Should return /home/<username>/.dotnet//dotnet
dotnet --version
# Should return 5.0.408 (or install version)
```

#### Install the game (STEAM installer)

For a remoted experience, steam doesn't work for me (via SSH -X, GUI remote etc). Here is some problem I have to deal with to get the game installed on the remote server
- Can't login to Steam over `ssh -X`: Can be deal with vcenter web remote, NoMachine remote.
- Steam can't be render (after login): Use other device Android app (mobile) and use remote install functionality

```sh
sudo apt install steam
# Manually remote and install Stardew Valley
```

#### Install SMAPI

Get the latest SMAPI installer from [SMAPI.io](SMAPI.io), in my current writing, latest version is 3.18.6
```sh
wget https://github.com/Pathoschild/SMAPI/releases/download/3.18.6/SMAPI-3.18.6-installer.zip
unzip SMAPI-3.18.6-installer.zip
cd 'SMAPI 3.18.6 installer/'
./'install on Linux.sh'
```

#### `libssl` problem - Side note

Another problem with `dotnet build`, which we need to install SSL 1.1.1. This is because SSL 1.1.1 reach end of life on 12/09/2023, and .NET 5.0 also already reach end of life as well
```
$ dotnet build
No usable version of libssl was found
Aborted (core dumped)
```

Install there on a live system may been a wrong move, so it better to working on a VM, or Container environment instead of your own PC. Here is a set of command for build and install OpenSSL 1.1.1 from source
```sh
wget https://www.openssl.org/source/openssl-1.1.1c.tar.gz
tar -xzvf openssl-1.1.1c.tar.gz
cd openssl-1.1.1c
./config
make
sudo make install
export LD_LIBRARY_PATH="/usr/local/lib"
```

Last line is a environment variable, we ant to add theres line to your `~/.profile` file and make it permanent or `dotnet build` will stop working in other/new `bash` session
```sh
# ssl  
export LD_LIBRARY_PATH="/usr/local/lib"
```

#### Build the mod from source

Clone the RightClickMoveMode repo
```sh
git clone https://github.com/nghiango1/RightClickMoveMode.git 
cd RightClickMoveMode
```

Build
```sh
dotnet build
```

Get the built `zip` files in `bin/Debug/net5.0` directory and extract to the mods folder
```sh
cd bin/Debug/net5.0
unzip 'MouseMoveMode 1.2.8-b.zip' -d '~/.steam/steam/steamapps/common/Stardew Valley/Mods'/
```

Debug the mod: Currently I have no ideal how to debug a dll file in my current setup, so just play the game and experiencing if it working or not

### Personal IDE

This doesn't that needed, normally you don't want to go extra long for just an IDE. Try use VSCode

> My setup is quite low on resource at the moments, I can't even think about open VScode and Steam/Stardrew Valley at the same time at all.

> This is un-finish, but edit text doesn't need that much work anyway

#### Setup neovim

Install `gcc` for compiling Treesitter language parsing
```sh
sudo apt install gcc
```

Clone my config file 
```sh
cd ~/.config/
git clone https://github.com/nghiango1/nvim.git
```

Or, You can start with [kichstart](https://github.com/nvim-lua/kickstart.nvim), It better to fork it and use your own repo
```sh
cd ~/.config/
git clone https://github.com/nvim-lua/kickstart.nvim.git
```

Install neovim (unstable)
```sh
sudo add-apt-repository ppa:neovim-ppa/unstable 
sudo apt-get update
sudo apt-get install neovim
```

Update alternative, chosing `nvim` as default for `vi` command
```sh
sudo update-alternatives --config vi
```

Start neovim, waiting till all Lazy package manager and Treesitter finish install
```sh
vi
```

#### Setup LSP, DAP for `c#`

##### LSP

We use `omnisharp` LSP for c#, which require NET v6.0. We already install NET v5.0, so run install script will override the `~/.dotnet` directory. We can prevent that by renaming
```sh
# Rename the ~/.dotnet directory
mv ~/.dotnet ~/.dotnet5
# Install NET v6.0 will be in newly created ~/.dotnet directory
./dotnet-install.sh --version 6.0.418
# Rename the ~/.dotnet directory to new distinct
mv ~/.dotnet ~/.dotnet6
# Rename the ~/.dotnet5 directory back to  default
mv ~/.dotnet5 ~/.dotnet
# (Soft) Link new dotnet executable file version 6 to PATH as dotnet6
ln -s ~/.dotnet6/dotnet ~/.local/bin/dotnet6
```

If you not have `~/.local/bin` in you PATH yet, add this to your `~/.profile` file
```sh
# set PATH so it includes ~/.local/bin
if [ -d "$HOME/.local/bin" ] ; then
    PATH="$HOME/.local/bin/:$PATH"
fi
```

To make it effect immediately, source `~/.profile` file
```sh
source ~/.profile
```

Check installation complete with these command
```sh
which dotnet6
# Should return /home/<username>/.local/bin/dotnet6
dotnet6 --version
# Should return 6.0.418 (or install version)
```

> This just install, i still can't make LSP working. VSCode automated omnisharp LSP install seem fine. Tho
