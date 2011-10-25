#!/bin/sh
# Client

. setExampleEnv.sh

"${JAVACMD}" ${JAVA_OPTIONS} -Dcom.gs.home=${JSHOMEDIR} -classpath "${JARS}" com.gigaspaces.examples.tutorials.topologies.SimpleReader jini://*/*/myDataGrid?groups=${LOOKUPGROUPS}
