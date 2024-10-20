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
