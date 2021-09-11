# ARG can be overriden from CLI with --build-arg A=B
ARG alpine_version=3.14

FROM golang:1.17.1-alpine$alpine_version AS builder
WORKDIR /app
# Example of mount build instead of full copy, with mounted cache.
RUN --mount=target=. --mount=type=cache,target=/.cache/go-build go mod download
RUN --mount=target=. go build -o /mygoapp
# Copy is fine, but can take more time than mounting volumes.
#COPY go.mod .
#COPY go.sum .
#COPY app.go .
#RUN --mount=target=~/.cache/go-build,type=cache go mod download
#RUN go build -o /mygoapp


FROM alpine:$alpine_version
RUN mkdir /home/golang
RUN adduser golang -D /home/golang
WORKDIR /home/golang
USER golang
EXPOSE 8080
COPY --from=builder --chown=golang /mygoapp ./
CMD ./mygoapp
