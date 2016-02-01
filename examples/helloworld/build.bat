@echo off

@call "%~dp0\..\..\bin\setenv.bat"

@%JAVACMD% %XAP_OPTIONS% -classpath %SIGAR_JARS%;%GS_JARS%;%ANT_JARS%;"%JAVA_HOME%/lib/tools.jar" org.apache.tools.ant.Main %1
