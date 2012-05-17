#!/bin/sh
# purpose: copies the previously compiled processing units to the GigaSpaces deploy folder

# Setting environment (calls <GigaSpace Home Dir>\bin\setEnv.bat to set GS_JARS,JAVACCMD)
# setExampleEnv scripts also set JARS variable to include the product jars + openspaces jars.
. ./setExampleEnv.sh

echo Setting environment variables
echo -----------------------------
EXAMPLE_DIR=`dirname $0`/..
GS_HOME=`dirname $0`/../../../../..
RUNTIME_PU_DIR=${EXAMPLE_DIR}/runtime/pu/oms-runtime
FEEDER_PU_DIR=${EXAMPLE_DIR}/feeder/pu/oms-feeder
STATS_PU_DIR=${EXAMPLE_DIR}/stats/pu/oms-stats

GS_DEPLOY_DIR=${GS_HOME}/deploy
RUNTIME_DEPLOY_TARGET_DIR=${GS_DEPLOY_DIR}/oms-runtime
FEEDER_DEPLOY_TARGET_DIR=${GS_DEPLOY_DIR}/oms-feeder
STATS_DEPLOY_TARGET_DIR=${GS_DEPLOY_DIR}/oms-stats

echo
echo Deleting previous deployments from GigaSpaces deploy directory if exist
echo -----------------------------------------------------------------------
if [ -d ${RUNTIME_DEPLOY_TARGET_DIR} ]
then
	rm -r ${RUNTIME_DEPLOY_TARGET_DIR}
fi
if [ -d ${FEEDER_DEPLOY_TARGET_DIR} ]
then
	rm -r ${FEEDER_DEPLOY_TARGET_DIR}
fi
if [ -d ${RUNTIME_DEPLOY_TARGET_DIR} ]
then
	rm -r ${STATS_DEPLOY_TARGET_DIR}
fi

echo
echo Copy runtime, feeder, and stats processing units to GigaSpaces deploy directory
echo -------------------------------------------------------------------------------
cp -r ${RUNTIME_PU_DIR} ${GS_DEPLOY_DIR}
cp -r ${FEEDER_PU_DIR} ${GS_DEPLOY_DIR}
cp -r ${STATS_PU_DIR} ${GS_DEPLOY_DIR}

