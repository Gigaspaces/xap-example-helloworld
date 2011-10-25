#!/bin/sh
# Validator

. setExampleEnv.sh

"${JAVACMD}" ${JAVA_OPTIONS} -Dcom.gs.home=${JSHOMEDIR} -classpath "${JARS}" com.gigaspaces.examples.tutorials.ordermanagement.Validator jini://localhost/./oms?groups=${LOOKUPGROUPS}
