package main

import (
	"embed"
	"io/fs"
	"log"
	"net/http"
)

//go:embed all:dist
var fsEmbed embed.FS

// EmbedFolder attempts to retrieve a file from the embedded filesystem
func EmbedFolder(fsEmbed embed.FS, targetPath string) (http.FileSystem, error) {
	fsys, err := fs.Sub(fsEmbed, targetPath)
	if err != nil {
		log.Println("Failed to access folder:", err)
		return nil, err
	}
	return http.FS(fsys), nil
}

func main() {
	// Attempt to read the embedded file '_aaa.html'
	fsys, err := EmbedFolder(fsEmbed, "dist")
	if err != nil {
		log.Println("Could not access the embedded folder:", err)
		return
	}

	file, err := fsys.Open("_aaa.html")
	if err != nil {
		// If the file can't be found, it confirms the behavior we expect
		log.Println("Successfully verified that the file cannot be embedded:", err)
		return
	}
	defer file.Close()

	log.Println("OK")
	// If successfully opened, we have a flaw since it should not exist
	info, err := file.Stat()
	if err != nil {
		log.Println("The file has been embedded unexpectedly:", info.Name())
	}
}
