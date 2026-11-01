@echo off
cd /d "%~dp0"

if exist app.log del app.log
if exist watchdog.pid del watchdog.pid

REM --- Find the PID of this terminal window (the cmd running this script) ---
for /f "usebackq" %%p in (`powershell -NoProfile -Command "$p=(Get-CimInstance Win32_Process -Filter ('ProcessId='+$PID)).ParentProcessId; (Get-CimInstance Win32_Process -Filter ('ProcessId='+$p)).ParentProcessId"`) do set "WINPID=%%p"

REM --- Start a hidden watchdog: when this window closes, stop the server ---
powershell -NoProfile -Command "$w = Start-Process powershell -WindowStyle Hidden -PassThru -ArgumentList '-NoProfile','-Command','Wait-Process -Id %WINPID% -ErrorAction SilentlyContinue; Get-NetTCPConnection -LocalPort 8080 -State Listen -ErrorAction SilentlyContinue | ForEach-Object { taskkill /F /T /PID $_.OwningProcess }'; $w.Id" > watchdog.pid

REM --- Start Spring Boot in the background, logs go to app.log ---
start "" /B cmd /c "mvnw.cmd spring-boot:run > app.log 2>&1"

:wait
timeout /t 1 /nobreak >nul

findstr /C:"BUILD FAILURE" app.log >nul 2>&1
if not errorlevel 1 (
    echo Spring failed to start. Check app.log
    goto cleanup
)

findstr /C:"Started RedisApplication" app.log >nul 2>&1
if errorlevel 1 goto wait

echo Spring running

REM --- Wait for commands ---
:input
set "cmd=> "
set /p "cmd=>"
if /i "%cmd%"=="/stop" goto stop
goto input

:stop
for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":8080" ^| findstr "LISTENING"') do (
    taskkill /F /T /PID %%a >nul 2>&1
)
echo Server stopped.

:cleanup
REM Kill the watchdog so it doesn't fire after a normal exit
if exist watchdog.pid (
    set /p WDPID=<watchdog.pid
    taskkill /F /PID %WDPID% >nul 2>&1
    del watchdog.pid
)
exit /b 0