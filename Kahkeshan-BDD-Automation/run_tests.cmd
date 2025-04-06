@echo off
setlocal

echo ================================
echo ✅ Running TestRunner Java Class
echo ================================

:: Step 1: Clean and compile the project
mvn clean compile

:: Step 2: Run tests (assumes TestNG or Cucumber runner configured in Maven)
mvn test

:: Step 3: Generate and open Allure report
echo ================================
echo 📊 Generating Allure Report
echo ================================
allure serve target/allure-results

endlocal
pause
