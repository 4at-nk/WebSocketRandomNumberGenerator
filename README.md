# WebSocketRandomNumberGenerator

## Run locally:
```
mvn jetty:run -f pom.xml
```

## Run via Docker:
### Build image
```
docker build -f Dockerfile -t ws_random_generator .
```
### Run container
```
docker run -p 8080:8080 -t ws_random_generator
```

## Request URL
connect to
```
ws://host:8080/random-number-generator/generate
```
and make any request

## Response example
```
{
    "number" : 53253552259355382664331909106680115348
}
```