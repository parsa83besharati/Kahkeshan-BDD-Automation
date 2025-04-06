@echo off
mvn clean test

allure generate allure-results --clean -o allure-report

allure serve
pause