#!/bin/bash

DIR_NAME=$(dirname ${BASH_SOURCE[0]})
. $DIR_NAME/../../bin/setenv.sh

if [ "${M2_HOME}" = "" ] ; then
    M2_HOME="${XAP_HOME}/tools/maven/apache-maven-3.2.5"; export M2_HOME
fi

if [ -z "$1" ] || ([ $1 != "clean" ] && [ $1 != "compile" ] && [ $1 != "package" ] && [ $1 != "deploy-processor" ] && [ $1 != "run-feeder" ] && [ $1 != "intellij" ]); then
  echo ""
  echo "Error: Invalid input command $1 "
  echo ""
  echo "The available commands are:"
  echo ""
  echo "clean                    --> Cleans all output dirs"
  echo "compile                  --> Builds all; don't create JARs"
  echo "package                  --> Builds the distribution"
  echo "deploy-processor         --> Deploys the processor onto the service grid"
  echo "run-feeder               --> Starts the feeder"
  echo "intellij                 --> Creates run configuration for IntelliJ IDE"
  echo

elif [ $1 = "clean" ]; then
  (cd $DIR_NAME;
  ${M2_HOME}/bin/mvn clean; )

elif [ $1 = "compile" ]; then
  (cd $DIR_NAME;
  ${M2_HOME}/bin/mvn compile; )

elif [ $1 = "package" ]; then
  (cd $DIR_NAME;
  ${M2_HOME}/bin/mvn package; )

elif [ $1 = "deploy-processor" ]; then
  ${XAP_HOME}/bin/gs.sh deploy $DIR_NAME/processor/target/hello-processor.jar

elif [ $1 = "run-feeder" ]; then
  (cd $DIR_NAME;
  ${M2_HOME}/bin/mvn install;
  cd feeder;
  ${M2_HOME}/bin/mvn exec:java -Dexec.mainClass=org.openspaces.example.helloworld.feeder.Feeder -Dexec.args="processorSpace" -Dexec.classpathScope=compile; )

elif [ $1 = "intellij" ]; then
  cp -r $DIR_NAME/runConfigurations $DIR_NAME/.idea
  echo "Run configurations for IntelliJ IDE created successfully"
fi
