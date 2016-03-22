@echo off

call "%~dp0..\..\bin\setenv.bat"
set DIR_NAME=%~dp0
rem maven needed to upgraded to 3.2.5
if not defined M2_HOME set M2_HOME=%~dp0..\..\tools\maven\apache-maven-3.2.5

if "%1" == "clean" (
  cd %DIR_NAME%
  call "%M2_HOME%\bin\mvn" clean
  cd %CD%
) else (
	if "%1" == "compile" (
	    cd %DIR_NAME%
		call "%M2_HOME%\bin\mvn" compile
		cd %CD%
	) else (
		if "%1" == "package" (
		    cd %DIR_NAME%		
			call "%M2_HOME%\bin\mvn" package
			cd %CD%
		) else (
			if "%1" == "deploy-processor" (
				cd %DIR_NAME%		
				call "%XAP_HOME%\bin\gs.bat" deploy processor\target\hello-processor.jar
				cd %CD%
			) else (
				if "%1" == "run-feeder" (
					cd %DIR_NAME%
                    call "%M2_HOME%\bin\mvn" install
					cd feeder
					call "%M2_HOME%\bin\mvn" exec:java -Dexec.mainClass=org.openspaces.example.helloworld.feeder.Feeder -Dexec.args="processorSpace" -Dexec.classpathScope=compile
					cd %CD%
				) else (
					if "%1" == "intellij" (
						xcopy %DIR_NAME%\runConfigurations %DIR_NAME%\.idea\runConfigurations\
					) else (
					  	echo.
                      	echo "Error: Invalid input command %1"
						echo. 
						echo The available commands are:
						echo. 
						echo clean                    --^> Cleans all output dirs
						echo compile                  --^> Builds all; don't create JARs
						echo package                  --^> Builds the distribution
						echo deploy-processor         --^> Deploys the processor onto the service grid
						echo run-feeder               --^> Starts the feeder
						echo intellij                 --^> Creates run configuration for IntelliJ IDE
						echo. 
					)	
				)
			)
		)
	)
)
 



