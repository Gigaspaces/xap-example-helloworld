@echo off

@rem Environment changes set after SETLOCAL has been issued are local to the batch file.
@SETLOCAL

@rem Setting environment (calls <GigaSpace Home Dir>\bin\setEnv.bat to set %GS_JARS%,%JAVACCMD%)
@rem setExampleEnv scripts also set %JARS% variable to include the product jars + openspaces jars.
call setExampleEnv.bat

@rem Setting environment variables
@rem -----------------------------
set EXAMPLE_DIR=%~dp0\..
set BIN_DIR=%~dp0

set COMMON_SRC_DIR=%EXAMPLE_DIR%\common\src\org\openspaces\example\oms\common
set COMMON_CLASSES_DIR=%EXAMPLE_DIR%\common\classes
set COMMON_DIST_DIR=%EXAMPLE_DIR%\common\dist
set COMMON_DIST_JAR=%COMMON_DIST_DIR%\oms-common.jar

set RUNTIME_SRC_DIR=%EXAMPLE_DIR%\runtime\src\org\openspaces\example\oms\runtime
set RUNTIME_PU_DIR=%EXAMPLE_DIR%\runtime\pu\oms-runtime
set RUNTIME_SHARED_LIB_DIR=%RUNTIME_PU_DIR%\shared-lib
set RUNTIME_SRC_META-INF_DIR=%EXAMPLE_DIR%\runtime\src\META-INF

set FEEDER_SRC_DIR=%EXAMPLE_DIR%\feeder\src\org\openspaces\example\oms\feeder
set FEEDER_PU_DIR=%EXAMPLE_DIR%\feeder\pu\oms-feeder
set FEEDER_SHARED_LIB_DIR=%FEEDER_PU_DIR%\shared-lib
set FEEDER_SRC_META-INF_DIR=%EXAMPLE_DIR%\feeder\src\META-INF

set STATS_SRC_DIR=%EXAMPLE_DIR%\stats\src\org\openspaces\example\oms\stats
set STATS_PU_DIR=%EXAMPLE_DIR%\stats\pu\oms-stats
set STATS_SHARED_LIB_DIR=%STATS_PU_DIR%\shared-lib
set STATS_SRC_META-INF_DIR=%EXAMPLE_DIR%\stats\src\META-INF

@rem Remove compiled class files to ensure recompilation
@rem ---------------------------------------------------
if exist "%COMMON_CLASSES_DIR%\*.*" rmdir /S/Q "%COMMON_CLASSES_DIR%"
if exist "%COMMON_DIST_DIR%\*.*" rmdir /S/Q "%COMMON_DIST_DIR%"
if exist "%RUNTIME_PU_DIR%\*.*" rmdir /S/Q "%RUNTIME_PU_DIR%"
if exist "%FEEDER_PU_DIR%\*.*" rmdir /S/Q "%FEEDER_PU_DIR%"
if exist "%STATS_PU_DIR%\*.*" rmdir /S/Q "%STATS_PU_DIR%"

@echo.
@echo Compiling the common files and packing classes and xml files
@echo into oms-common.jar ready to distribute to all processing units 
@echo ---------------------------------------------------------------
md "%COMMON_CLASSES_DIR%"
md "%COMMON_DIST_DIR%"
cd "%COMMON_SRC_DIR%"
%JAVACCMD% -classpath %JARS% -d "%COMMON_CLASSES_DIR%" *.java
cd "%COMMON_CLASSES_DIR%"
%JAVA_HOME%\bin\jar cvf "%COMMON_DIST_JAR%" *
cd "%BIN_DIR%"

@echo.
@echo Compiling the RUNTIME processing unit and adding META-INF and common jar to it
@echo ------------------------------------------------------------------------------
md "%RUNTIME_PU_DIR%"
cd "%RUNTIME_SRC_DIR%"
%JAVACCMD% -classpath %JARS%;"%COMMON_CLASSES_DIR%" -d "%RUNTIME_PU_DIR%" *.java
md "%RUNTIME_PU_DIR%\META-INF"
xcopy /e/s "%RUNTIME_SRC_META-INF_DIR%" "%RUNTIME_PU_DIR%\META-INF"
md "%RUNTIME_SHARED_LIB_DIR%"
copy "%COMMON_DIST_JAR%" "%RUNTIME_SHARED_LIB_DIR%"

@echo.
@echo Compiling the FEEDER processing unit and adding META-INF and common jar to it
@echo -----------------------------------------------------------------------------
md "%FEEDER_PU_DIR%"
cd "%FEEDER_SRC_DIR%"
%JAVACCMD% -classpath %JARS%;"%COMMON_CLASSES_DIR%" -d "%FEEDER_PU_DIR%" *.java
md "%FEEDER_PU_DIR%\META-INF"
xcopy /s/e "%FEEDER_SRC_META-INF_DIR%" "%FEEDER_PU_DIR%\META-INF"
md "%FEEDER_SHARED_LIB_DIR%"
copy "%COMMON_DIST_JAR%" "%FEEDER_SHARED_LIB_DIR%"

@echo.
@echo Compiling the STATS processing unit and adding META-INF and common jar to it
@echo ----------------------------------------------------------------------------
md "%STATS_PU_DIR%"
cd "%STATS_SRC_DIR%"
%JAVACCMD% -classpath %JARS%;"%COMMON_CLASSES_DIR%" -d "%STATS_PU_DIR%" *.java
md "%STATS_PU_DIR%\META-INF"
xcopy /e/s "%STATS_SRC_META-INF_DIR%" "%STATS_PU_DIR%\META-INF"
md "%STATS_SHARED_LIB_DIR%"
copy "%COMMON_DIST_JAR%" "%STATS_SHARED_LIB_DIR%"

echo.
pause
