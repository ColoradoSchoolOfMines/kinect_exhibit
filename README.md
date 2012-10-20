This is the frontend of the recycler game for CSCI 598B.

# Build Instructions

## To produce a jar file:
* mvn assembly:assembly

## To run the tests:
* mvn test

## To run tests and show code coverage:
* mvn cobertura:cobertura
* open target/site/cobertura/index.html

## To create the HTML site:
* mvn site

## To clean the project:
* mvn clean

# Run Instructions
1. Create a jar file.
2. java -jar target/frontend-exe.jar

