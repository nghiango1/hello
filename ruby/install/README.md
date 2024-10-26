# Install

Ruby in ubuntu have version 3.0, which isn't recommended for Rails edge version (>3.1). To handle this, I just use `rvm` (https://rvm.io/rvm/install)

```sh
gpg --keyserver keyserver.ubuntu.com --recv-keys 409B6B1796C275462A1703113804BB82D39DC0E3 7D2BAF1CF37B13E2069D6956105BD0E739499BDB
curl -sSL https://get.rvm.io | bash
```

Then we can update PATH and start installing Ruby + Rails, here I use version 3.2.5
```sh
. ~/.profile
rvm install 3.2.5
gem install rails
ruby --version #ruby 3.0.2p107 (2021-07-07 revision 0db68f0233) [x86_64-linux-gnu]
rail --version #Rails 7.2.1.1
```

## Sorlargraph - LSP for ruby

> Just install it through Mason, work perfect

## Sorbet - LSP static type checker support 

> Doesn't seem good enough ;/, error every where

Document: [https://sorbet.org/docs/adopting](https://sorbet.org/docs/adopting)

Create a project with bundle
```sh
bundle init
```

Then edit the created `Gemfile` and add Sorbet
```rb
gem 'sorbet', :group => :development
gem 'sorbet-runtime'
gem 'tapioca', require: false, :group => [:development, :test]
```

Install gem
```sh
bundle install
```

> Alternatively we can use the sorbet-static-and-runtime gem to install both sorbet and sorbet-runtime and keep them synchronized at the same version
> ```rb
> gem 'sorbet-static-and-runtime'
> gem 'tapioca', require: false, :group => [:development, :test]
> ```

Now we can init Sorbet for the project
```sh
bundle exec tapioca init
```
