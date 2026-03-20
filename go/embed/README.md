# Embed HTTP File server

Using Go to:

- Handle embed FS: This mean file will be distribuited within the build binary
- HTTP Fileserver use embed files: Validate `/<name>.html` -> `/<name>` route

## Notes

The go provided HTTP Fileserver library doesn't seem to handle route:

`/_aaa.html` -> `/_aaa`

But still support

`/` -> `/index.html`

## Usage

```sh
make embed-run
make http-run
```
