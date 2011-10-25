@title=Simple Reader (with Local View) From Space Topologies

@rem Setting environment (sets %JAVACMD%,%JARS%,%LOOKUPGROUPS%)
@call setExampleEnv.bat

%JAVACMD% -classpath %JARS%;..\classes com.gigaspaces.examples.tutorials.topologies.SimpleReader "jini://*/*/myDataGrid?useLocalCache&views={com.gigaspaces.examples.tutorials.topologies.Account:accountID=20 AND balance=200,com.gigaspaces.examples.tutorials.topologies.Account:accountID=17}&groups=%LOOKUPGROUPS%"

pause