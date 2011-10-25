#!/bin/sh
# Processor

. setExampleEnv.sh

"${JAVACMD}" ${JAVA_OPTIONS} -Dcom.gs.home=${JSHOMEDIR} -classpath "${JARS}" com.gigaspaces.examples.tutorials.ordermanagement.Processor jini://localhost/./oms?groups=${LOOKUPGROUPS}
