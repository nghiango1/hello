package main

import (
	"embed"
	"io/fs"
	"log"
	"net/http"
)

//go:embed all:dist
var embedDist embed.FS

func main() {
	// Create a file server handler serving /tmp directory
	fsys, err := fs.Sub(embedDist, "dist")
	if err != nil {
		log.Fatal("Sub", err)
	}
	httpFs := http.FS(fsys)
	fs := http.FileServer(httpFs)

	// Register handler for root path
	http.Handle("/", fs)

	// Start HTTP server on port 8080
	log.Println("Serving /dist on http://:3000")
	err = http.ListenAndServe(":3000", nil)
	if err != nil {
		log.Fatal(err)
	}
}
