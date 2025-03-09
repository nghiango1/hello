# README

This README would normally document whatever steps are necessary to get the
application up and running.

## Ruby version

3.2.5

## Dependencies

### System dependancy

Require:

- Ruby 3.2.5: Used through rvm is suggeted, Ubuntu/Debian default ruby package doesn't match

  ```sh
  rvm install 3.2.5
  ```

- SQLite3: Will need to change to Postgresql soon

  ```sh
  sudo apt install sqlite3
  ```

### Ruby dependancy

We can install develepent specific dependancy into local: To diffirentiate with deployment bundle install, we have grouped them to `:development`

```rb
group :development do
  gem "rubocop"
  gem "solargraph", "~> 0.47.2", require: false

  gem "ruby-lsp", "~> 0.19.0", require: false
  gem "ruby-lsp-rails", "~> 0.3.6"
  gem "ruby-lsp-rspec", "~> 0.1.10", require: false
end
```

To set up development environment, config bundle with

```sh
bundle config set --local with 'development'
```

Then call `install`

```sh
bundle install
```

## Configuration

None

> Incomming

## Database creation

Currently we use SQLite3. So there no need for create DB other than calling migration

```sh
rails db:migrate
```

## Database initialization

None, no need to populate any account yet

## How to run the test suite

None, no test yet

## Services (job queues, cache servers, search engines, etc.)

None, what is this

## Deployment instructions

Start the server

```sh
bin/rails server
```

## The next step - MVC web design

### Routing rails application

> We can inspect what routes are mapped by running the `bin/rails routes` command

Create new routes handler

- Update routes configuration using DSL `./config/routes.rb`

  ```ruby
  Rails.application.routes.draw do
    # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html
    get "/articles", to: "articles#index"
  ```

- Generate new sites index page

  ```sh
  rails generate controller Articles index --skip-routes
  ```

- Look at `./app/controllers/articles_controller.rb` and `./app/views/articles/index.html.erb` and try to change their content

  ```html
  <h1>Articles#index</h1>
  <p>Hello world</p>
  <p>Find me in app/views/articles/index.html.erb</p>
  ```

- Change roots page of the Web application need update `./config/routes.rb`. Here I use the articles page we just created

  ```ruby
    # Defines the root path route ("/")
    root "articles#index"
  end
  ```

### Create new Models

> Object that representing real world application target

Generate new Models

```sh
rails generate model Article title:string body:text
```

This result in generating theses output (create new files):

```
  invoke  active_record
  create    db/migrate/20241020145009_create_articles.rb
  create    app/models/article.rb
  invoke    test_unit
  create      test/models/article_test.rb
  create      test/fixtures/articles.yml
```

Then we update the database to have our Models represent in regional table format

```sh
rails db:migrate
```

We then can use `rails console` to create and interact with this Models directly (instead of using SQL Database or running in-app ruby script)

```sh
rails console
```

Try with these command

```ruby
# Loading development environment (Rails 7.2.1.1)
article = Article.new(title: "Hello Rails", body: "I am on Rails!")
article.save
Article.find(1)
Article.all
exit
```

### Create new View

Displaying created Model into our Pages require "import" them into an variable in `./app/controllers/articles_controller.rb`

```ruby
class ArticlesController < ApplicationController
  def index
    @articles = Article.all
  end
end
```

Then update the page `html.erb` files to use the variable. Here we loop though it

```erb
<p>Lists of created articles</p>
<ul>
  <% @articles.each do |article| %>
    <li>
      <%= article.title %>
    </li>
  <% end %>
</ul>
```

> **`<% %>` and `<%= %>`**: The `<% %>` tag means "evaluate the enclosed Ruby code." The `<%= %>` tag means "evaluate the enclosed Ruby code, and output the value it returns." Anything you could write in a regular Ruby program can go inside these ERB tags, though it's usually best to keep the contents of ERB tags short, for readability.

## Haml - New template engine for Rails

Haml is a markup language that's used to cleanly and simply describe the HTML of any web document without the use of inline code. Haml functions as a replacement for inline page templating systems such as PHP, ASP, and ERB, the templating language used in most Ruby on Rails applications

Haml can be used in three ways:

- as a command-line tool,
- as a plugin for Ruby on Rails,
- and as a standalone Ruby module.

The first step for all of these is to install the Haml gem:

```sh
gem install haml
```

Then, to use Haml with Rails, add the following line to the `Gemfile`, here I use a specific version:

```rb
# HAML
# gem "haml"
gem "hamlit", "~> 2.15.0"
```

Once it’s installed, all view files with the ".html.haml" extension will be compiled using Haml. Bellow is some walk through using haml

### HTML Tag in HAML

A basic element should look like this.

- `%tagname` example: `%h1`, `%p`
- `{}` following hash (dictionary) define in ruby
- The Contents doesn't have any restriction

```haml
%tagname{:attr1 => 'value1', :attr2 => 'value2'} Contents
```

We can also use `(attr1='value1' attr2='value2')` inreplace with ruby hash `{}` for a more familar style with HTML

```haml
%tagname(attr1='value1' attr2='value2') Contents
```

Special case for id and class attribute, we can use this syntax that similar to the CSS that styles the document

```haml
%tagname#id.class
```

Incase of `<div>` tag, any tag without a name will defaults to a div.

- Example:

  ```haml
  #foo Hello!
  ```

- Become:

  ```html
  <div id="foo">Hello!</div>
  ```

Indentation: To represent the HTML structure, haml use indentation

- Example:

  ```haml
  %ul
    %li Salt
    %li Pepper
  ```

- Become:

  ```html
  <ul>
    <li>Salt</li>
    <li>Pepper</li>
  </ul>
  ```

For embedding Ruby:

- An equals sign, =, will output the result of the code.

  ```haml
  %h1 = "aa"
  ```

- A hyphen, -, will run the code but not output the result. So you can even use control statements like if and while:

  ```haml
  %p
    Date/Time:
    - now = DateTime.now
    %strong= now
    - if now > DateTime.parse("December 31, 2006")
      = "Happy new " + "year!"
  ```

> For loop in haml can remove `end`, haml can self interpreter that
> raise Hamlit::HamlSyntaxError.new(%q[You don't need to use "- end" in Haml. Un-indent to close a block:

## Bundle - Gemfile update

Referrer: [https://bundler.io/v2.5/man/gemfile.5.html](https://bundler.io/v2.5/man/gemfile.5.html)

There is some change to gemfile, I put some info in here:

- Groups: Each gem MAY specify membership in one or more groups. Any gem that does not specify membership in any group is placed in the default group.
- Require As: Each gem MAY specify files that should be used when autorequiring via Bundler.require. You may pass an array with multiple files or true if the file you want required has the same name as gem or false to prevent any file from being autorequired.

```sh
group :development do
  gem "rubocop"
  gem "solargraph", "~> 0.47.2", require: false # rubocop:todo Gemfile/MissingFeatureCategory

  gem "ruby-lsp", "~> 0.19.0", require: false
  gem "ruby-lsp-rails", "~> 0.3.6"
  gem "ruby-lsp-rspec", "~> 0.1.10", require: false
end
```

## CRUD - Create Read Update Delete

4 Main methods to interact with a business object.

### Setup proper route to handle each CRUD request

To make your life easier, instead of create all the nesseary path/route handle, we can use this syntax `resources` in `config/route.rb`. Rails will handle create the default path instead.

```rb
Rails.application.routes.draw do
  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html
  # get "/articles", to: "articles#index" - Remove
  # get "/articles/new", to: "articles#new" - Remove
  # post "/articles/new", to: "articles#create" - Remove
  # get "/articles/:id", to: "articles#show" - Remove

  # [...]

  resources :articles
end
```

New route will be added will look like this. Now we only need to provide `articles_controller.rb` to handle user request

```
→ rails routes
            Prefix Verb   URI Pattern                  Controller#Action
rails_health_check GET    /up(.:format)                rails/health#show
pwa_service_worker GET    /service-worker(.:format)    rails/pwa#service_worker
      pwa_manifest GET    /manifest(.:format)          rails/pwa#manifest
              root GET    /                            articles#index
          articles GET    /articles(.:format)          articles#index
                   POST   /articles(.:format)          articles#create
       new_article GET    /articles/new(.:format)      articles#new
      edit_article GET    /articles/:id/edit(.:format) articles#edit
           article GET    /articles/:id(.:format)      articles#show
                   PATCH  /articles/:id(.:format)      articles#update
                   PUT    /articles/:id(.:format)      articles#update
                   DELETE /articles/:id(.:format)      articles#destroy
[...]
```

We walk though each route handle and providing their coresponse defination in `articles_controller.rb`: `articles#index`, `articles#create`, `articles#new`, `articles#edit`, `articles#show`, `articles#update`, `articles#update`, `articles#destroy` (We already finish `index` at this point)

### Read

We update `articles#index` and `articles#show` controler. This is equivalent to the deleted route that we replace

- Config in `route.rb`

  ```rb
  Rails.application.routes.draw do
    # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html
    get "/articles", to: "articles#index" - Remove
    get "/articles/:id", to: "articles#show" - Remove
  ```

- After replace by `resources :article` in `route.rb`. We focus on:

  ```
  → rails routes
              Prefix Verb   URI Pattern                  Controller#Action
  [...]
            articles GET    /articles(.:format)          articles#index
  [...]
             article GET    /articles/:id(.:format)      articles#show
  ```

It closely tied to GET request, where we want to get (and display) the data of a specific model (link to all avaiable one and each one detail infomations)

### Create

We update `articles#new` and `articles#create` controler. This is equivalent to these page route

```
→ rails routes
            Prefix Verb   URI Pattern                  Controller#Action
  [...]
                   POST   /articles(.:format)          articles#create
       new_article GET    /articles/new(.:format)      articles#new
  [...]
```

To quickly create form that match with the object definition, we can use `form_with`. Here I use HAML instead of `erb` in the document. Most of the embedded ruby start with `=`, which indicate they output will be display in the html return by the server.

```haml
%h1 New Article

= form_with model: @article do |form|
  %div
    = form.label :title
    %br
    = form.text_field :title
  %div
    = form.label :body
    %br
    = form.text_area :body
  %div
    = form.submit
```

The form submit data can be access using `params` in (any?) articles post handler

- The post handler will be `articles#create`

  ```
                     POST   /articles(.:format)          articles#create
  ```

- The `params` data will be send into create method in articles_controler look like this

  ```rb
  {"authenticity_token"=>"RXTeCQ4UBBgVFAwA0sWjTYoeSEjt7R24M4zRJQxiyVg8z5kowj9q2q6sGa_JHEg5YzoGYiu1Vuue9E_Ub_lJPQ", "article"=>{"title"=>"test 2", "body"=>"test 3"}, "commit"=>"Create Article", "controller"=>"articles", "action"=>"create"}
  ```

- It will be sent to debug output from the `rails server` command. Here is how it look like

  ```
    Parameters: {"authenticity_token"=>"[FILTERED]", "article"=>{"title"=>"test 2", "body"=>"test 3"}, "commit"=>"Create Article"}
  ```

- We can do filter on post `params` value using this syntax (provided by rails) before push it into creation/save method

  ```rb
  article_params = params.require(:article).permit(:title, :body)

  @article = Article.new(article_params)

  @article.save
  ```

- For futher validating the content of each value, we can add defination into `app/models/article.rb` (which should be empty when created)

  ```rb
  # **Commented document:** Article contant for blog pages infomation
  class Article < ApplicationRecord
    validates :title, presence: true
    validates :body, presence: true, length: { minimum: 10 }
  end
  ```

### Update

We update `articles#update` and `articles#edit` controler. This is equivalent to these page route

```
→ rails routes
            Prefix  Verb   URI Pattern                  Controller#Action
  [...]
       edit_article GET    /articles/:id/edit(.:format) articles#edit
            article GET    /articles/:id(.:format)      articles#show
                    PATCH  /articles/:id(.:format)      articles#update
                    PUT    /articles/:id(.:format)      articles#update
  [...]
```

> We mix with `article#show` in there. It is unrelated and share the same prefix. Just focus into Controller#Action

Again, we want to have a form to get user input to change the article data. This come with `_partial_` share View, which normally have name start with `_` (underscore) character.

- Base on the form that we wroted in `./app/views/articles/new.html.haml` View

  ```haml
  %h1 New Article

  = form_with model: @article do |form|
    %div
      = form.label :title
      %br
      = form.text_field :title
    %div
      = form.label :body
      %br
      = form.text_area :body
    %div
      = form.submit
  ```

- We create a new file `./app/views/articles/_form.html.haml`, which is `_partial_` share View we just mention about

  ```haml
  = form_with model: @article do |form|
    %div
      = form.label :title
      %br
      = form.text_field :title
      - ref_article.errors.full_messages_for(:title).each do |message|
        %div#error_title.red.top-2= message
    %div
      = form.label :body
      %br
      = form.text_area :body
      - ref_article.errors.full_messages_for(:body).each do |message|
        %div#error_body.blue.top-2= message
    %div
      = form.submit
  ```

  > There is some addition, along with the old form code, which get the return errors from server and showed to the user when the form is summited and handle. Something to notes about:
  >
  > - I try out some id and special class there, it have no use at the moment till CSS/JS is implement, we show each of error message into their own div.
  > - I also have `ref_article` as a ruby param variable used in the form embedded code. This need to be provided by the caller
  >   (I later drop all of these extra thing from the code, as it just for tested purpose)

- Now we can call `_partial_` share View to create a full page View, start with GET edit_articles page `./app/views/articles/edit.html.haml`. There is alot of name scheme here where we referrence the Controller and View related unit here. They quite seem to be all over the place so better to remember them directly

  ```haml
  %h1 Edit Article

  = render "form", ref_article: @article
  ```

  > Here we push arg value `@article` for `ref_article` param. As we can see the scope of variables can be shown in the example code (`@article` instance scope variable can be use directly, while local scope variable need to be passed through params system), both of the tatic are to be consider when passing the data for render in nested `_partial_` View

- The errors use full_message_for, which some how we can get the document in Nvim with `<K>`.

  ```
  = .full_messages_for

  (from gem activemodel-7.2.1.1)
  === Implementation from Errors
  ------------------------------------------------------------------------
    full_messages_for(attribute)

  ------------------------------------------------------------------------

  Returns all the full error messages for a given attribute in an array.

    class Person
      validates_presence_of :name, :email
      validates_length_of :name, in: 5..30
    end

    person = Person.create()
    person.errors.full_messages_for(:name)
    # => ["Name is too short (minimum is 5 characters)", "Name can't be blank"]
  ```

- Now, where could these error message came from. In my belived, it setup in model validate. We can check [README#Create](README#Create) where I set up those validate

  ```rb
  # Article for blog
  class Article < ApplicationRecord
    validates :title, presence: true
    validates :body, presence: true, length: { minimum: 10 }
  end
  ```

- Finally, I replace the controller handler View for `articles#new` with the _partial_ View i just created

  ```haml
  %h1 Edit Article

  = render "form"
  ```

### Destroy

Add detroy handler into article controler

```rb

  def destroy
    @article = Article.find(params[:id])
    @article.destroy

    redirect_to root_path, status: :see_other
  end

```

Then place a link to that endpoint into our `show` view. Oh way, is failed as
there is no deestroy_article_path link at all. Just like update doesn't have
any GET enpoint, destroy need to be specialy call through other way

- `rail routes` on DELETE and destroy endpoint

```
                                         PATCH  /articles/:id(.:format)                  articles#update
                                         PUT    /articles/:id(.:format)                  articles#update
                                         DELETE /articles/:id(.:format)                  articles#destroy
        turbo_recede_historical_location GET    /recede_historical_location(.:format)
```

- This doesn't work

```rb
%h1= @article.title
%p= @article.body

%ul
  %li= link_to "Edit", edit_article_path(@article)
  %li= link_to "Delete", destroy_article_path(@article)
```

- This is how to do it through `rails`: we use the data option to set the
  `data-turbo-method` and `data-turbo-confirm` HTML attributes of the "Destroy"
  link. Both of these attributes hook into Turbo, which is included by default
  in fresh Rails applications.

  - `data-turbo-method="delete"` will cause the link to make a `DELETE` request
    instead of a `GET` request.
  - `data-turbo-confirm="Are you sure?"` will cause a confirmation dialog to
    appear when the link is clicked. If the user cancels the dialog, the request
    will be aborted.

```rb
%h1= @article.title
%p= @article.body

%ul
  %li= link_to "Edit", edit_article_path(@article)
  %li= link_to "Delete", article_path(@article), data: { turbo_method: :delete, turbo_confirm: "Are you sure?" }
```

After trying with the DELETE request, it seem to me that I can only use turbo
method to actually delete the article. Feel bad.

Some how I skip over `link_to`, which is a helper renders a link with its first
argument as the link's text and its second argument as the link's destination.
That doesn't help much? well, if we instead pass a model object as the second
argument, `link_to` will call the appropriate path helper to convert the object
to a path

- Original code

```erb
      <a href="<%= article_path(article) %>">
        <%= article.title %>
      </a>
```

- Shorten code

```erb
    <li>
      <%= link_to article.title, article %>
    </li>
```

- Or in `haml`

```haml
%ul
  - @articles.each do |article|
    %li
      %a(href="/articles/#{article.id}")= article.title
```

- Shorten code

```haml
%ul
  - @articles.each do |article|
    %li= link_to article.title, article
```

- Incase of nested child, consider the calling is an `<a>` element

```haml
%ul
  - @articles.each do |article|
    %li
      = link_to article.title, article
      %a(href="/articles/#{article.id}")= article.title
```

## NIXOS

Adding new shell.nix into the project, call nix-shell and it automatically catch
and prepare the correct environment for you

```sh
nix-shell
```

Now using the bundle install for running deploy/dev server

```sh
bundle config set --local with 'development'
bundle install
bundle exec rails server
```

I also catched two thing in the setup development process

- `bundle` use a different gem installed path (which need us to use bundle exec
  as it actually a wrapper for bundle which will setup correct RUNTIME variable)
- Some native package build process need some help for system library, which
  I need to add libyaml into `shell.nix`'s `buildInputs`
