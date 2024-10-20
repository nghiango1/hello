# README

This README would normally document whatever steps are necessary to get the
application up and running.

## Ruby version:

3.2.5

## System dependencies
None

> Incomming

## Configuration
None

> Incomming

## Database creation
None

> Incomming

## Database initialization
None

> Incomming

## How to run the test suite
None

> Incomming

## Services (job queues, cache servers, search engines, etc.)
None

> Incomming

## Deployment instructions

Start the server
```sh
bin/rails server
```

## Next step

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

Create new Models (Object that representing real world application target).
- Generate new Models
    ```sh
    rails generate model Article title:string body:text
    ```

- This result in generating theses output (create new files):
    ```
      invoke  active_record
      create    db/migrate/20241020145009_create_articles.rb
      create    app/models/article.rb
      invoke    test_unit
      create      test/models/article_test.rb
      create      test/fixtures/articles.yml
    ```

- Then we update the database to have our Models represent in regional table format
    ```sh
    rails db:migrate
    ```

- We then can use `rails console` to create and interact with this Models directly (instead of using SQL Database or running in-app ruby script)
    ```sh
    rails console
    ```

- Try with these command
    ```ruby
    # Loading development environment (Rails 7.2.1.1)
    article = Article.new(title: "Hello Rails", body: "I am on Rails!")
    article.save
    Article.find(1)
    Article.all
    exit
    ```

- Using the Model into our View (Pages) require "import" them into an variable in `./app/controllers/articles_controller.rb`
    ```ruby
    class ArticlesController < ApplicationController
      def index
        @articles = Article.all
      end
    end
    ```

- Then update the page `html.erb` files to use the variable. Here we loop though it
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
