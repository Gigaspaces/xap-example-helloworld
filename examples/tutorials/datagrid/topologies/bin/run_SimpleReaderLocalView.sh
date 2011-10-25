#!/bin/sh
# Client

. setExampleEnv.sh

"${JAVACMD}" ${JAVA_OPTIONS} -Dcom.gs.home=${JSHOMEDIR} -classpath "${JARS}" com.gigaspaces.examples.tutorials.topologies.SimpleReader "jini://*/*/myDataGrid?useLocalCache&views={com.gigaspaces.examples.tutorials.topologies.Account:accountID=20 AND balance=200,com.gigaspaces.examples.tutorials.topologies.Account:accountID=17}&groups=${LOOKUPGROUPS}"
