# Static testcase generator

## Introduction

I dealling with create some testcase for Computer sience practice problem,
but i found out that I working on a Window environment which only intended
for C# development. So I thought that it could be the time for some static
javasript.

## Functionality

The first ideal is just for generate and display input, output. Then by
some more line of code, I found out that I actually do a lot more with a
complete friendly Web interface:

- Clear indicator UI for Input and Output
- Drop down form for generate testcase multiple problem
- Copy button (input, output) for small testcase
- Download button (input, output) for bigger testcase

## Retrospecter

### New things

I added lodash into the project just for `_.range()`, which is quite overkill.
Other than that, this does have some reference to some really least/less used
provided javascript function (I used Firefox and their MDN document for function
reference)

- `parseInt(string, radix)` for convert Base10 to Base2
- How to create a blob and download it (saved to file)

    ```javascript
        const outputText = document.getElementById('input').textContent;
        const blob = new Blob([outputText], { type: 'text/plain' });
        const link = document.createElement('a');
        link.href = URL.createObjectURL(blob);
        link.download = 'input.txt';
        link.click();
    ```

- This is how we can dynamicly load new javascript file (for XSS?)

    ```javascript
        document.getElementById('gname').value = paramValue;
        const script = document.createElement('script');
        script.src = `./${paramValue}`;
        document.head.appendChild(script);
        script.onload = function () {
            //
            // generate();
        };
    ```

- Originally without dynamic load, I have to use defer on script tag so js
script ran after page is load

    ```html
    <script defer="" src="./index.js"></script>
    ```

- Form summit (with default `GET`) can restart the page when correctly change
parameter value, but stay still if there is no URL change. I add `onclick` handler
to the button so it can regenerate the testcase using script file

    ```html
    <input type="submit" id="generate" value="Generate">`
    ```

### "Old" things

Retrospecter thing that I simply need to use more, I believe this will help me
atually remember them

- ``${line1}\n${line2}`` format string in Javascript
- Array to String join can be really easy

    ```javascript
    input.split("").reverse().join("")
    ```

- Or using this when I can't remember the context

    ```javascript
    let line2 = input.reduce((x, e) => {return x +" "+ e;});
    let testCases = input.reduce((x, e) => {return x +"\n"+ e;});
    ```

More advance thing:

- `Math.random()` for random in range [0, 1]