@title=Populate Space Topologies

@rem Setting environment (sets %JAVACMD%,%JARS%,%LOOKUPGROUPS%)
@call setExampleEnv.bat

%JAVACMD% -classpath %JARS%;..\classes com.gigaspaces.examples.tutorials.topologies.DataLoader "jini://*/*/myDataGrid?groups=%LOOKUPGROUPS%"

pause