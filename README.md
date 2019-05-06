# java-framework-compare

Project using the same application and tests to compare java frameworks.
Currently supports:
* TomEE
* SpringBoot
* Quarkus

# Requirements

A JDK 8+ installed
Maven 3.5+

## Tests

To run all the tests just execute:
```
mvn clean install
```

# Run the applications

## Start PostgreSQL

```
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --name postgres-for-frameworks -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=test -p 5432:5432 postgres:10.7-alpine
```

 

