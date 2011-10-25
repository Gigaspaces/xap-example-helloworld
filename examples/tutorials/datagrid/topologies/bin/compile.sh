#!/bin/sh

. setExampleEnv.sh

# Remove class files to ensure recompilation
rm -Rf `dirname $0`/../classes/com

# Create the classes dir if it doesn't exist
if [ ! -d ../classes ]
then
    mkdir ../classes
fi

${JAVACCMD} -classpath .${CPS}${JARS} -d ../classes ../src/com/gigaspaces/examples/tutorials/topologies/*.java
