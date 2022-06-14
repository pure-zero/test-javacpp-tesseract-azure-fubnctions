# Azure function, quarkus, javacpp tesseract

Steps to reproduce locally.

1. podman build --tag docker/azurefunctionsimage:v1.0.0 .

2. podman run -p 8080:80  -it localhost/docker/azurefunctionsimage:v1.0.0

3. navigate to <http://localhost:8080/api/vertx/hello>

Running locally and working

1. mvn clean install -DskipTests azure-functions:run

2. Change /Users/kartika/workspace/quarkus-azure/src/main/resources/tessdata to local path

3. navigate to <http://localhost:7071/api/vertx/hello/local>