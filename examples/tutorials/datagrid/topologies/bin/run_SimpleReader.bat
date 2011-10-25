@title=Simple Reader From Space Topologies

@rem Setting environment (sets %JAVACMD%,%JARS%,%LOOKUPGROUPS%)
@call setExampleEnv.bat

%JAVACMD% -classpath %JARS%;..\classes com.gigaspaces.examples.tutorials.topologies.SimpleReader "jini://*/*/myDataGrid?groups=%LOOKUPGROUPS%"

pause
