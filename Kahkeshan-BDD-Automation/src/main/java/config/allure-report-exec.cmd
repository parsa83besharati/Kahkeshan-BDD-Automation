@echo off
REM Check if allure command is installed
echo Checking if Allure is installed...
where allure >nul 2>nul
IF %ERRORLEVEL% NEQ 0 (
    echo Allure is not installed or not in the system PATH.
    echo Please install allure and ensure it is added to your PATH.
    exit /b 1
)

REM Change directory to the allure-results folder
echo Changing directory to allure-results...
cd E:\java\Kahkeshan-BDD-Automation\Kahkeshan-BDD-Automation\Kahkeshan-BDD-Automation\allure-results

IF %ERRORLEVEL% NEQ 0 (
    echo Failed to change directory to allure-results. Please make sure the folder exists.
    exit /b 1
)

REM Generate the Allure report
echo Generating Allure report...
allure generate --clean

IF %ERRORLEVEL% NEQ 0 (
    echo Failed to generate Allure report. Please check the allure-results directory.
    exit /b 1
)

REM Serve the Allure report (this will start a local server)
echo Serving the Allure report locally...
allure serve

REM End of script
