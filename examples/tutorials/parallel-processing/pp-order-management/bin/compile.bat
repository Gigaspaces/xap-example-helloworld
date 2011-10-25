@echo on

@rem Environment changes set after SETLOCAL has been issued are local to the batch file.
@SETLOCAL

@rem Setting environment (calls <GigaSpace Home Dir>\bin\setEnv.bat to set %GS_JARS%,%JAVACCMD%)
@rem setExampleEnv scripts also set %JARS% variable to include the product jars + openspaces jars.
call setExampleEnv.bat

@rem Setting environment variables
@rem -----------------------------
set EXAMPLE_DIR=%~dp0\..
set BIN_DIR=%~dp0

set COMMON_SRC_DIR=%EXAMPLE_DIR%\common\src\com\gigaspaces\examples\tutorials\parallelprocessing\common
set COMMON_CLASSES_DIR=%EXAMPLE_DIR%\common\classes
set COMMON_DIST_DIR=%EXAMPLE_DIR%\common\dist
set COMMON_DIST_JAR=%COMMON_DIST_DIR%\pp-oms-common.jar

set CLIENT_SRC_DIR=%EXAMPLE_DIR%\client\src\com\gigaspaces\examples\tutorials\parallelprocessing\client
set CLIENT_PU_DIR=%EXAMPLE_DIR%\client\pu\pp-oms-client
set CLIENT_SHARED_LIB_DIR=%CLIENT_PU_DIR%\shared-lib
set CLIENT_SRC_META-INF_DIR=%EXAMPLE_DIR%\client\src\META-INF

set VALIDATOR_SRC_DIR=%EXAMPLE_DIR%\validator\src\com\gigaspaces\examples\tutorials\parallelprocessing\validator
set VALIDATOR_PU_DIR=%EXAMPLE_DIR%\validator\pu\pp-oms-validator
set VALIDATOR_SHARED_LIB_DIR=%VALIDATOR_PU_DIR%\shared-lib
set VALIDATOR_SRC_META-INF_DIR=%EXAMPLE_DIR%\validator\src\META-INF

set PROCESSOR_SRC_DIR=%EXAMPLE_DIR%\processor\src\com\gigaspaces\examples\tutorials\parallelprocessing\processor
set PROCESSOR_PU_DIR=%EXAMPLE_DIR%\processor\pu\pp-oms-processor
set PROCESSOR_SHARED_LIB_DIR=%PROCESSOR_PU_DIR%\shared-lib
set PROCESSOR_SRC_META-INF_DIR=%EXAMPLE_DIR%\processor\src\META-INF

@rem Remove compiled class files to ensure recompilation
@rem ---------------------------------------------------
if exist "%COMMON_CLASSES_DIR%\*.*" rmdir /S/Q "%COMMON_CLASSES_DIR%"
if exist "%COMMON_DIST_DIR%\*.*" rmdir /S/Q "%COMMON_DIST_DIR%"
if exist "%CLIENT_PU_DIR%\*.*" rmdir /S/Q "%CLIENT_PU_DIR%"
if exist "%VALIDATOR_PU_DIR%\*.*" rmdir /S/Q "%VALIDATOR_PU_DIR%"
if exist "%PROCESSOR_PU_DIR%\*.*" rmdir /S/Q "%PROCESSOR_PU_DIR%"

@echo.
@echo Compiling the common files and packing classes and xml files
@echo into pp-oms-common.jar ready to distribute to all processing units 
@echo ---------------------------------------------------------------
md "%COMMON_CLASSES_DIR%"
md "%COMMON_DIST_DIR%"
cd "%COMMON_SRC_DIR%"
%JAVACCMD% -classpath %JARS% -d "%COMMON_CLASSES_DIR%" *.java
cd "%COMMON_CLASSES_DIR%"
%JAVA_HOME%\bin\jar cvf "%COMMON_DIST_JAR%" *.*
cd "%BIN_DIR%"

@echo.
@echo Compiling the CLIENT processing unit and adding META-INF and common jar to it
@echo ------------------------------------------------------------------------------
md "%CLIENT_PU_DIR%"
cd "%CLIENT_SRC_DIR%"
%JAVACCMD% -classpath %JARS%;"%COMMON_CLASSES_DIR%" -d "%CLIENT_PU_DIR%" *.java
md "%CLIENT_PU_DIR%\META-INF"
xcopy /e/s "%CLIENT_SRC_META-INF_DIR%" "%CLIENT_PU_DIR%\META-INF"
md "%CLIENT_SHARED_LIB_DIR%"
copy "%COMMON_DIST_JAR%" "%CLIENT_SHARED_LIB_DIR%"
cd "%BIN_DIR%"

@echo.
@echo Compiling the VALIDATOR processing unit and adding META-INF and common jar to it
@echo -----------------------------------------------------------------------------
md "%VALIDATOR_PU_DIR%"
cd "%VALIDATOR_SRC_DIR%"
%JAVACCMD% -classpath %JARS%;"%COMMON_CLASSES_DIR%" -d "%VALIDATOR_PU_DIR%" *.java
md "%VALIDATOR_PU_DIR%\META-INF"
xcopy /s/e "%VALIDATOR_SRC_META-INF_DIR%" "%VALIDATOR_PU_DIR%\META-INF"
md "%VALIDATOR_SHARED_LIB_DIR%"
copy "%COMMON_DIST_JAR%" "%VALIDATOR_SHARED_LIB_DIR%"
cd "%BIN_DIR%"

@echo.
@echo Compiling the PROCESSOR processing unit and adding META-INF and common jar to it
@echo ----------------------------------------------------------------------------
md "%PROCESSOR_PU_DIR%"
cd %PROCESSOR_SRC_DIR%"
%JAVACCMD% -classpath %JARS%;"%COMMON_CLASSES_DIR%" -d "%PROCESSOR_PU_DIR%" *.java
md "%PROCESSOR_PU_DIR%\META-INF"
xcopy /e/s "%PROCESSOR_SRC_META-INF_DIR%" "%PROCESSOR_PU_DIR%\META-INF"
md "%PROCESSOR_SHARED_LIB_DIR%"
copy "%COMMON_DIST_JAR%" "%PROCESSOR_SHARED_LIB_DIR%"
cd "%BIN_DIR%"

echo.
pause
