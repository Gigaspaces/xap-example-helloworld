@echo off

@rem Environment changes set after SETLOCAL has been issued are local to the batch file.
@SETLOCAL

@rem Setting environment (calls <GigaSpace Home Dir>\bin\setEnv.bat to set %GS_JARS%,%JAVACCMD%)
@rem setExampleEnv scripts also set %JARS% variable to include the product jars + openspaces jars.
call setExampleEnv.bat

@rem Setting environment variables
@rem -----------------------------
set EXAMPLE_DIR=%~dp0\..
set GS_HOME=%~dp0\..\..\..\..\..
set RUNTIME_PU_DIR=%EXAMPLE_DIR%\runtime\pu\oms-runtime
set FEEDER_PU_DIR=%EXAMPLE_DIR%\feeder\pu\oms-feeder
set STATS_PU_DIR=%EXAMPLE_DIR%\stats\pu\oms-stats
set RUNTIME_DEPLOY_TARGET_DIR=%GS_HOME%\deploy\oms-runtime
set FEEDER_DEPLOY_TARGET_DIR=%GS_HOME%\deploy\oms-feeder
set STATS_DEPLOY_TARGET_DIR=%GS_HOME%\deploy\oms-stats

@echo Delete previous deployments from GigaSpaces deploy directory if exist
@echo ---------------------------------------------------------------------
if exist "%RUNTIME_DEPLOY_TARGET_DIR%" rmdir /S/Q "%RUNTIME_DEPLOY_TARGET_DIR%"
if exist "%FEEDER_DEPLOY_TARGET_DIR%" rmdir /S/Q "%FEEDER_DEPLOY_TARGET_DIR%"
if exist "%STATS_DEPLOY_TARGET_DIR%" rmdir /S/Q "%STATS_DEPLOY_TARGET_DIR%"

@echo.
@echo Copy runtime, feeder, and stats processing units to GigaSpaces deploy directory
@echo -------------------------------------------------------------------------------
md "%RUNTIME_DEPLOY_TARGET_DIR%"
md "%FEEDER_DEPLOY_TARGET_DIR%"
md "%STATS_DEPLOY_TARGET_DIR%"
xcopy /s/e "%RUNTIME_PU_DIR%" "%RUNTIME_DEPLOY_TARGET_DIR%"
xcopy /s/e "%FEEDER_PU_DIR%" "%FEEDER_DEPLOY_TARGET_DIR%"
xcopy /s/e "%STATS_PU_DIR%" "%STATS_DEPLOY_TARGET_DIR%"

echo.
pause
