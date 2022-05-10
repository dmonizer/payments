# Payments

## Running

./gradlew :bootRun

API can be accessed at http://localhost:8080/ [create|cancel|bydebtor|bycreditor|byid]

## Payment validation rules
To be valid, all payments need to have amount > 0 and:

| Payment type   	 | Currency	   | Rules                        |
|------------------|-------------|------------------------------|
| TYPE_1           | EUR         | Needs to have details string |
| TYPE_2           | USD         | none                         |
| TYPE_3           | EUR or USD  | Needs to have BIC defined    |

## Running tests

### Unit tests
./gradlew :test

### Integration tests
./gradlew :integrationTests

## Notes
(Mostly for myself to remember)

### Tests
Tests are @Tag'ged with TestCategories.INTEGRATION_TEST or TestCategories.UNIT_TEST.

TestCategories.UNIT_TEST are run with Gradle task :test.

TestCategories.INTEGRATION_TEST are run with :integrationTests.

:allTests runs all tests.

