@rem Setting environment (sets %JAVACCMD%,%JARS%)
@call setExampleEnv.bat

@rem Remove class files to ensure recompilation
@if exist ..\classes\com\*.* rmdir /S/Q ..\classes\com

@rem Create the classes dir if it doesn't exist
@if NOT exist ..\classes mkdir ..\classes

%JAVACCMD% -classpath %JARS% -d ..\classes ..\src\com\gigaspaces\examples\tutorials\topologies\*.java

pause

