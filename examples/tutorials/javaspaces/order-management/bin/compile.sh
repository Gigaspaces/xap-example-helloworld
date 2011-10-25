#!/bin/sh

. setExampleEnv.sh

# Remove class files to ensure recompilation
rm -Rf `dirname $0`/../classes/com

${JAVACCMD} -classpath .${CPS}${GS_JARS} -d ../classes ../src/com/gigaspaces/examples/tutorials/ordermanagement/*.java
