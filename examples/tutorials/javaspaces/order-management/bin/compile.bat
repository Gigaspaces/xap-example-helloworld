@rem Environment changes set after SETLOCAL has been issued are local to the batch file.
@SETLOCAL

@rem Setting environment (calls <GigaSpace Home Dir>\bin\setEnv.bat to set %GS_JARS%,%JAVACCMD%)
@call setExampleEnv.bat

rem Remove class files to ensure recompilation
@if exist ..\classes\com\*.* rmdir /S/Q ..\classes\com

@rem Compiling the example files:
@rem
@rem -classpath %GS_JARS% 	- Specifies where to find GigaSpaces jars.
@rem 
@rem -d ..\classes				- Specifies where to place generated class files.
@rem
@rem ..\src\com\gigaspaces\examples\tutorials\ordermanagement\*.java	- Location of the source files to be compiled.

@%JAVACCMD% -classpath %GS_JARS% -d ..\classes ..\src\com\gigaspaces\examples\tutorials\ordermanagement\*.java

@pause
