#!/bin/sh
# Client

. setExampleEnv.sh

"${JAVACMD}" ${JAVA_OPTIONS} -Dcom.gs.home=${JSHOMEDIR} -classpath "${JARS}" com.gigaspaces.examples.tutorials.topologies.DataLoader jini://*/*/myDataGrid?groups=${LOOKUPGROUPS}
