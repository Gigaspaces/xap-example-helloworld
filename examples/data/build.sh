JSHOMEDIR=`dirname $0`/../..; export JSHOMEDIR
. ${JSHOMEDIR}/bin/setenv.sh

"$JAVACMD" ${LOOKUP_LOCATORS_PROP} ${LOOKUP_GROUPS_PROP} -classpath "${SIGAR_JARS}":"${GS_JARS}":"${ANT_JARS}":"${JAVA_HOME}/lib/tools.jar" org.apache.tools.ant.Main $1
