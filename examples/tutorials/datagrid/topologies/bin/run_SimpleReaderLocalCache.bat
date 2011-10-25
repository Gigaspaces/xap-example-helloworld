@title=Simple Reader (with Local Cache) From Space Topologies

@rem Setting environment (sets %JAVACMD%,%JARS%,%LOOKUPGROUPS%)
@call setExampleEnv.bat

%JAVACMD% -classpath %JARS%;..\classes com.gigaspaces.examples.tutorials.topologies.SimpleReader "jini://*/*/myDataGrid?useLocalCache&groups=%LOOKUPGROUPS%"

pause