# Test5
Maven Test Project -  (Selenide, JUnit, AllureReports, AssertJ)

- Smoke tests for https://kamil-demo.alpinizm.uz

- Project contains 3 test classes 
1. FiltersBoxTest
2. CalendarBoxTest
3. AllListingsTest

Currently all tests are runing in headless mode - to swich it put 'false' state for a property 'headlesMode' in ase properties
Base properties - src/test/resources/test.properties

Commands:

RUN TESTS - mvn test

OPEN ALLURE REPORTS - mvn allure:serve




