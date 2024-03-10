# List

> Using project template

## Notes

List is a nice datastructure for testing language capability on OOP, here I what I can observe
- Javascript have class, have extend for some level of inheritance
- But Interface isn't supported directly in the language
- JSdoc is just a mean to document javascript code, the syntax is just not great for the code read ability, and typescript-language-server currently do not provided static type check for jsdoc class template (@implements)

This can be solve by:
- Using the language feature as intended: Create a class, and using extends (inheritence)
- When we have complex class: Create a Typescipt .d.ts file for seperated type define, can only be use to create a object type, not as a interface. Using @type{import("./<type>.d.ts").Class} for LSP to work properly.

Jsdocs generated Document:
- Class type with jsdoc doesn't work well with LSP: Most of the syntax for generate documenet from jsdoc doesn't show up in LSP, while also take up a lot of code readability. So if we don't really rely on JSdoc for document generation, just forcus on a fair level of type instead of enforcing Jsdoc.
- Jsdoc default is quite bad in UI, but it can be better with plugin
