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
set CLIENT_PU_DIR=%EXAMPLE_DIR%\client\pu\pp-oms-client
set VALIDATOR_PU_DIR=%EXAMPLE_DIR%\validator\pu\pp-oms-validator
set PROCESSOR_PU_DIR=%EXAMPLE_DIR%\processor\pu\pp-oms-processor
set CLIENT_DEPLOY_TARGET_DIR=%GS_HOME%\deploy\pp-oms-client
set VALIDATOR_DEPLOY_TARGET_DIR=%GS_HOME%\deploy\pp-oms-validator
set PROCESSOR_DEPLOY_TARGET_DIR=%GS_HOME%\deploy\pp-oms-processor

@echo Delete previous deployments from GigaSpaces deploy directory if they exist
@echo --------------------------------------------------------------------------
if exist "%CLIENT_DEPLOY_TARGET_DIR%\*.*" rmdir /S/Q "%CLIENT_DEPLOY_TARGET_DIR%"
if exist "%VALIDATOR_DEPLOY_TARGET_DIR%\*.*" rmdir /S/Q "%VALIDATOR_DEPLOY_TARGET_DIR%"
if exist "%PROCESSOR_DEPLOY_TARGET_DIR%\*.*" rmdir /S/Q "%PROCESSOR_DEPLOY_TARGET_DIR%"

@echo.
@echo Copy client, validator, and processor processing units to GigaSpaces deploy directory
@echo -------------------------------------------------------------------------------
md "%CLIENT_DEPLOY_TARGET_DIR%"
md "%VALIDATOR_DEPLOY_TARGET_DIR%"
md "%PROCESSOR_DEPLOY_TARGET_DIR%"
cd "%CLIENT_PU_DIR%"
xcopy *.* "%CLIENT_DEPLOY_TARGET_DIR%" /e /k /i /c
cd "%VALIDATOR_PU_DIR%"
xcopy *.* "%VALIDATOR_DEPLOY_TARGET_DIR%" /e /k /i /c
cd "%PROCESSOR_PU_DIR%"
xcopy *.* "%PROCESSOR_DEPLOY_TARGET_DIR%" /e /k /i /c

echo.
pause
