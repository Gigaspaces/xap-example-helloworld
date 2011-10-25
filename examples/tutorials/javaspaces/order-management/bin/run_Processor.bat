@rem Command line window title
@title=Processor

@rem Environment changes set after SETLOCAL has been issued are local to the batch file.
@SETLOCAL

@rem Setting environment (sets %JAVACMD%,%JARS%,%LOOKUPGROUPS%)
@call setExampleEnv.bat

rem Running the processor:
rem
rem ..\classes	- Specifies where to find the example classes.
rem
rem %JARS% 	- Specifies where to find GigaSpaces jars.
rem
rem jini://localhost/./oms?groups=%LOOKUPGROUPS%	- The url of the space the processor connects to.
rem				  		 				 jini://localhost/ - look for the space in this hostname.
rem				  		  				"." sets the container name equal to the space name.
rem				  		  				"oms" is the space name.
rem										%LOOKUPGROUPS% holds the jini lookup groups.
rem
rem com.gigaspaces.examples.tutorials.ordermanagement.Processor 	- Location of the processor application class file.

%JAVACMD% -classpath ..\classes;%JARS% com.gigaspaces.examples.tutorials.ordermanagement.Processor jini://localhost/./oms?groups=%LOOKUPGROUPS%

@pause
