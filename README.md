# Payments

### Running

./gradlew :bootRun

API can be accessed at http://localhost:8080/ [create|cancel|bydebtor|bycreditor|byid]

### Running tests

#### Unit tests
./gradlew :test

#### Integration tests
./gradlew :integrationTests


### Notes
(Mostly for myself to remember)

#### Tests
Tests are @Tag'ged with TestCategories.INTEGRATION_TEST or TestCategories.UNIT_TEST.

TestCategories.UNIT_TEST are run with Gradle task :test.

TestCategories.INTEGRATION_TEST are run with :integrationTests.

:allTests runs all tests.