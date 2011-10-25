#!/bin/sh
# purpose: compiles the client, validator and processor processing units

# Setting environment (calls <GigaSpace Home Dir>\bin\setEnv.bat to set GS_JARS,JAVACCMD)
# setExampleEnv scripts also set JARS variable to include the product jars + openspaces jars.

. setExampleEnv.sh
echo on

# Setting environment variables
# -----------------------------
EXAMPLE_DIR=`dirname $0`/..
BIN_DIR=`dirname $0`

COMMON_SRC_DIR=${EXAMPLE_DIR}/common/src/com/gigaspaces/examples/tutorials/parallelprocessing/common
COMMON_CLASSES_DIR=${EXAMPLE_DIR}/common/classes
COMMON_DIR=${EXAMPLE_DIR}/common
COMMON_DIST_DIR=${EXAMPLE_DIR}/common/dist
COMMON_DIST_JAR=${COMMON_DIST_DIR}/pp-oms-common.jar
COMMON_XML_DIST_DIR=${EXAMPLE_DIR}/common/classes/com/gigaspaces/examples/tutorials/parallelprocessing/common

CLIENT_SRC_DIR=${EXAMPLE_DIR}/client/src/com/gigaspaces/examples/tutorials/parallelprocessing/client
CLIENT_PU_DIR=${EXAMPLE_DIR}/client/pu/pp-oms-client
CLIENT_SHARED_LIB_DIR=${CLIENT_PU_DIR}/shared-lib
CLIENT_SRC_META_INF_DIR=${EXAMPLE_DIR}/client/src/META-INF

VALIDATOR_SRC_DIR=${EXAMPLE_DIR}/validator/src/com/gigaspaces/examples/tutorials/parallelprocessing/validator
VALIDATOR_PU_DIR=${EXAMPLE_DIR}/validator/pu/pp-oms-validator
VALIDATOR_SHARED_LIB_DIR=${VALIDATOR_PU_DIR}/shared-lib
VALIDATOR_SRC_META_INF_DIR=${EXAMPLE_DIR}/validator/src/META-INF

PROCESSOR_SRC_DIR=${EXAMPLE_DIR}/processor/src/com/gigaspaces/examples/tutorials/parallelprocessing/processor
PROCESSOR_PU_DIR=${EXAMPLE_DIR}/processor/pu/pp-oms-processor
PROCESSOR_SHARED_LIB_DIR=${PROCESSOR_PU_DIR}/shared-lib
PROCESSOR_SRC_META_INF_DIR=${EXAMPLE_DIR}/processor/src/META-INF

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
if [ -d ${CLIENT_PU_DIR} ]
then
	rm -rf  ${CLIENT_PU_DIR}
fi
if [ -d ${VALIDATOR_PU_DIR} ]
then
	rm -rf ${VALIDATOR_PU_DIR}
fi
if [ -d ${PROCESSOR_PU_DIR} ]
then
	rm -rf ${PROCESSOR_PU_DIR}
fi

echo
echo Compiling the common files and packing classes and xml files
echo into pp-oms-common.jar ready to distribute to all processing units 
echo ---------------------------------------------------------------
mkdir ${COMMON_CLASSES_DIR}
mkdir ${COMMON_DIST_DIR}
"$JAVACCMD" -classpath "${JARS}" -d ${COMMON_CLASSES_DIR} ${COMMON_SRC_DIR}/*.java
cd ${COMMON_DIR}
"$JAVA_HOME"/bin/jar cvf ${COMMON_DIST_JAR} -C classes/ .
cd ${BIN_DIR}

echo
echo Compiling the CLIENT processing unit and adding META-INF and common jar to it
echo ------------------------------------------------------------------------------
mkdir -p ${CLIENT_PU_DIR}
"$JAVACCMD" -classpath "${JARS}${CPS}${COMMON_CLASSES_DIR}" -d ${CLIENT_PU_DIR} ${CLIENT_SRC_DIR}/*.java
cp -r ${CLIENT_SRC_META_INF_DIR} ${CLIENT_PU_DIR}
mkdir ${CLIENT_SHARED_LIB_DIR}
cp ${COMMON_DIST_JAR} ${CLIENT_SHARED_LIB_DIR}

echo
echo Compiling the VALIDATOR processing unit and adding META-INF and common jar to it
echo -----------------------------------------------------------------------------
mkdir -p ${VALIDATOR_PU_DIR}
"$JAVACCMD" -classpath "${JARS}${CPS}${COMMON_CLASSES_DIR}" -d ${VALIDATOR_PU_DIR} ${VALIDATOR_SRC_DIR}/*.java
cp -r ${VALIDATOR_SRC_META_INF_DIR} ${VALIDATOR_PU_DIR}
mkdir ${VALIDATOR_SHARED_LIB_DIR}
cp ${COMMON_DIST_JAR} ${VALIDATOR_SHARED_LIB_DIR}

echo
echo Compiling the PROCESSOR processing unit and adding META-INF and common jar to it
echo ----------------------------------------------------------------------------
mkdir -p ${PROCESSOR_PU_DIR}
"$JAVACCMD" -classpath "${JARS}${CPS}${COMMON_CLASSES_DIR}" -d ${PROCESSOR_PU_DIR} ${PROCESSOR_SRC_DIR}/*.java
cp -r ${PROCESSOR_SRC_META_INF_DIR} ${PROCESSOR_PU_DIR}
mkdir ${PROCESSOR_SHARED_LIB_DIR}
cp ${COMMON_DIST_JAR} ${PROCESSOR_SHARED_LIB_DIR}


