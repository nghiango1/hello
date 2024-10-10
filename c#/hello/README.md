# C# Hello world

## Prerequire

To create and build dotnet project, download nessesary tools before hand. Follow top level README.md to get ready

## Create new project

We can't compile code without first create a project (or it just me who don't know how to create standalone C# project)
- We can look at available template with this command
    ```sh
    dotnet new --list
    ```

- Expected output look like this
    ```
    Template Name                                 Short Name      Language    Tags
    --------------------------------------------  --------------  ----------  --------------------------
    ASP.NET Core Empty                            web             [C#],F#     Web/Empty
    ASP.NET Core gRPC Service                     grpc            [C#]        Web/gRPC
    ASP.NET Core Web API                          webapi          [C#],F#     Web/WebAPI
    ASP.NET Core Web App                          webapp,razor    [C#]        Web/MVC/Razor Pages
    ASP.NET Core Web App (Model-View-Controller)  mvc             [C#],F#     Web/MVC
    ASP.NET Core with Angular                     angular         [C#]        Web/MVC/SPA
    ASP.NET Core with React.js                    react           [C#]        Web/MVC/SPA
    Blazor Server App                             blazorserver    [C#]        Web/Blazor
    Blazor WebAssembly App                        blazorwasm      [C#]        Web/Blazor/WebAssembly/PWA
    Class Library                                 classlib        [C#],F#,VB  Common/Library
    Console App                                   console         [C#],F#,VB  Common/Console
    ...
    ```

- For our `Hello world` application, we only need console support so let use it
    ```sh
    dotnet new console
    ```

Now the created project should already have our needed Hello world source. But some how we build fail.

## Write your first Helloworld C# program

Starting with .NET SDK 6.0.300, the console template has a --use-program-main option. Use it to create a console project that doesn't use top-level statements and has a Main method.

```sh
dotnet new console --use-program-main
```

Then the generated Program.cs is as follows:
```c_sharp
namespace MyProject;

class Program
{
    static void Main(string[] args)
    {
        Console.WriteLine("Hello, World!");
    }
}
```

And we will build it normally

## Build and run the code

Build is quite simple
```sh
dotnet build .
```

Without proper environment (as I using Linux), I can't run the program directly. We can use `dotnet` tool to do so, but it also rebuild the program though
```sh
dotnet run .
```

To actually build a file that can be run directly in Linux, we have to add `--runtime linux-x64` flag. This flag also require one of either flag '--self-contained' or '--no-self-contained' options (required when '--runtime' is used).
```sh
dotnet build . --runtime linux-x64 --self-contained
```

> Now the build file for linux-x64 should be isolate in `bin/Debug/net6.0/linux-x64/`.

We can also create release build by using `dotnet publish -c release` instead of `dotnet build .`. The flag for linux is the same
```sh
dotnet publish -c release --runtime linux-x64 --self-contained
```

> Now the build release file for linux-x64 should be isolate in `bin/release/net6.0/linux-x64/`.
