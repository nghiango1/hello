# Sample

Try out the dev environement with my javascript setup: Jsdoc, Typescript LSP. I want to deal with some small problem instead wroking with this setup directly in a full (commit) project yet. It have strict linting rule, which close to airbnb javasript styling guide, to make sure i'm not missing some thing

## Notes

- I try to make interface work (List/ LinkedList) but fail in [problem 2](src/2-add-two-number.mjs) and seperated `../list/`. This have it own `jsdoc` comment tag that isn't supported by Typescript LSP.
- Map typing not working. Can't assign the template type in [problem 3](src/3-longest-substring-without-repeating-characters.mjs).
