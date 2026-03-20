all:
	@echo "Usage:"
	@echo "  make http-run"
	@echo "  make embed-run"

.PHONY: http-run
http-run:
	rm -rf cmd/http/dist
	mkdir -p cmd/http/dist
	echo "<h1>Hello word<h1>" >> cmd/http/dist/index.html
	echo "<h1>Hello aaa<h1>" >> cmd/http/dist/_aaa.html
	go run cmd/http/main.go

.PHONY: embed-run
embed-run:
	rm -rf cmd/embed/dist
	mkdir -p cmd/embed/dist
	echo "<h1>Hello word<h1>" >> cmd/embed/dist/index.html
	echo "<h1>Hello aaa<h1>" >> cmd/embed/dist/_aaa.html
	go run cmd/embed/main.go
