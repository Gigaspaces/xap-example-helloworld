#!/bin/sh
# purpose: copmiles the runtime, feeder and stats processing units

# Setting environment (calls <GigaSpace Home Dir>\bin\setEnv.bat to set GS_JARS,JAVACCMD)
# setExampleEnv scripts also set JARS variable to include the product jars + openspaces jars.

. ./setExampleEnv.sh
echo on

# Setting environment variables
# -----------------------------
EXAMPLE_DIR=`dirname $0`/..
BIN_DIR=`dirname $0`

COMMON_SRC_DIR=${EXAMPLE_DIR}/common/src/org/openspaces/example/oms/common
COMMON_CLASSES_DIR=${EXAMPLE_DIR}/common/classes
COMMON_DIR=${EXAMPLE_DIR}/common
COMMON_DIST_DIR=${EXAMPLE_DIR}/common/dist
COMMON_DIST_JAR=${COMMON_DIST_DIR}/oms-common.jar
COMMON_XML_DIST_DIR=${EXAMPLE_DIR}/common/classes/org/openspaces/example/oms/common

RUNTIME_SRC_DIR=${EXAMPLE_DIR}/runtime/src/org/openspaces/example/oms/runtime
RUNTIME_PU_DIR=${EXAMPLE_DIR}/runtime/pu/oms-runtime
RUNTIME_SHARED_LIB_DIR=${RUNTIME_PU_DIR}/lib
RUNTIME_SRC_META_INF_DIR=${EXAMPLE_DIR}/runtime/src/META-INF

FEEDER_SRC_DIR=${EXAMPLE_DIR}/feeder/src/org/openspaces/example/oms/feeder
FEEDER_PU_DIR=${EXAMPLE_DIR}/feeder/pu/oms-feeder
FEEDER_SHARED_LIB_DIR=${FEEDER_PU_DIR}/lib
FEEDER_SRC_META_INF_DIR=${EXAMPLE_DIR}/feeder/src/META-INF

STATS_SRC_DIR=${EXAMPLE_DIR}/stats/src/org/openspaces/example/oms/stats
STATS_PU_DIR=${EXAMPLE_DIR}/stats/pu/oms-stats
STATS_SHARED_LIB_DIR=${STATS_PU_DIR}/lib
STATS_SRC_META_INF_DIR=${EXAMPLE_DIR}/stats/src/META-INF

echo
echo Remove compiled class files to ensure recompilation
echo ---------------------------------------------------
if [ -d ${COMMON_CLASSES_DIR} ]
then
	rm -rf ${COMMON_CLASSES_DIR}
fi
if [ -d ${COMMON_DIST_DIR} ]
then
	rm -rf ${COMMON_DIST_DIR}
fi
if [ -d ${RUNTIME_PU_DIR} ]
then
	rm -rf  ${RUNTIME_PU_DIR}
fi
if [ -d ${FEEDER_PU_DIR} ]
then
	rm -rf ${FEEDER_PU_DIR}
fi
if [ -d ${STATS_PU_DIR} ]
then
	rm -rf ${STATS_PU_DIR}
fi

echo
echo Compiling the common files and packing classes and xml files
echo into oms-common.jar ready to distribute to all processing units 
echo ---------------------------------------------------------------
mkdir ${COMMON_CLASSES_DIR}
mkdir ${COMMON_DIST_DIR}
"$JAVACCMD" -classpath "${JARS}" -d ${COMMON_CLASSES_DIR} ${COMMON_SRC_DIR}/*.java
cd ${COMMON_DIR}
"$JAVA_HOME"/bin/jar cvf ${COMMON_DIST_JAR} -C classes/ .
cd ${BIN_DIR}

echo
echo Compiling the RUNTIME processing unit and adding META-INF and common jar to it
echo ------------------------------------------------------------------------------
mkdir -p ${RUNTIME_PU_DIR}
"$JAVACCMD" -classpath "${JARS}${CPS}${COMMON_CLASSES_DIR}" -d ${RUNTIME_PU_DIR} ${RUNTIME_SRC_DIR}/*.java
cp -r ${RUNTIME_SRC_META_INF_DIR} ${RUNTIME_PU_DIR}
mkdir ${RUNTIME_SHARED_LIB_DIR}
cp ${COMMON_DIST_JAR} ${RUNTIME_SHARED_LIB_DIR}

echo
echo Compiling the FEEDER processing unit and adding META-INF and common jar to it
echo -----------------------------------------------------------------------------
mkdir -p ${FEEDER_PU_DIR}
"$JAVACCMD" -classpath "${JARS}${CPS}${COMMON_CLASSES_DIR}" -d ${FEEDER_PU_DIR} ${FEEDER_SRC_DIR}/*.java
cp -r ${FEEDER_SRC_META_INF_DIR} ${FEEDER_PU_DIR}
mkdir ${FEEDER_SHARED_LIB_DIR}
cp ${COMMON_DIST_JAR} ${FEEDER_SHARED_LIB_DIR}

echo
echo Compiling the STATS processing unit and adding META-INF and common jar to it
echo ----------------------------------------------------------------------------
mkdir -p ${STATS_PU_DIR}
"$JAVACCMD" -classpath "${JARS}${CPS}${COMMON_CLASSES_DIR}" -d ${STATS_PU_DIR} ${STATS_SRC_DIR}/*.java
cp -r ${STATS_SRC_META_INF_DIR} ${STATS_PU_DIR}
mkdir ${STATS_SHARED_LIB_DIR}
cp ${COMMON_DIST_JAR} ${STATS_SHARED_LIB_DIR}


