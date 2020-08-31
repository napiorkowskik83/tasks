call runcrud.bat
if "%ERRORLEVEL%" == "0" goto openchrome
echo.
echo There were errors while executing runcrud.bat

:openchrome
start chrome http://localhost:8080/crud/v1/task/getTasks
goto end

:end
echo.
echo Work of 'showtasks' script is finished!