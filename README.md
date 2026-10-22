# Quarkus greetings service

The service exposes two endpoints:

```text
GET  /hello       -> world!
POST /greetings   -> echoes message, with a UTC timestamp and execution time
```

## Try the endpoints

Use `curl` for a single request:

```sh
curl -i http://localhost:8080/hello
curl -i -X POST http://localhost:8080/greetings \
  -H 'Content-Type: application/json' \
  -d '{"message":"Hello from the Quarkus!"}'
```

The POST response includes `message`, `timestamp` (UTC), and
`executionTimeNanos` (time spent preparing the response; typically very small).

### Install hey on Ubuntu

`hey` sends multiple HTTP requests and reports response time statistics. On
Ubuntu, install Go and then install `hey`:

```sh
sudo apt update
sudo apt install -y golang-go
go install github.com/rakyll/hey@latest
export PATH="$(go env GOPATH)/bin:$PATH"
```

Send 10 POST requests, one at a time, and review the mean and percentile times:

```sh
hey -n 10 -c 1 \
  -m POST \
  -H 'Content-Type: application/json' \
  -d '{"message":"Hello from the Quarkus!"}' \
  http://localhost:8080/greetings
```

## Run as a JVM application

With Maven and the selected JDK installed:

```sh
mvn quarkus:dev
mvn package
java -jar target/quarkus-app/quarkus-run.jar
```

Quarkus produces an optimized `fast-jar` layout under `target/quarkus-app/`.
Run the `quarkus-run.jar` launcher; the directory alongside it contains the
application and runtime dependencies.

## Build and run a native executable

Install GraalVM Native Image for your platform, then:

```sh
mvn package -Dnative
./target/quarkus-fun-1.0.0-SNAPSHOT-runner
```

Alternatively, build the native executable in a container (Docker or Podman):

```sh
mvn package -Dnative -Dquarkus.native.container-build=true
```

The native executable is platform-specific. GraalVM currently releases on the
JDK 25 line, so `.mise.toml` selects the latest Oracle GraalVM 25 rather than
JDK 27.
