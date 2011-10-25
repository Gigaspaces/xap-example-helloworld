#!/bin/sh
# purpose: copies the previously compiled processing units to the GigaSpaces deploy folder

# Setting environment (calls <GigaSpace Home Dir>\bin\setEnv.bat to set GS_JARS,JAVACCMD)
# setExampleEnv scripts also set JARS variable to include the product jars + openspaces jars.
. setExampleEnv.sh

echo Setting environment variables
echo -----------------------------
EXAMPLE_DIR=`dirname $0`/..
GS_HOME=`dirname $0`/../../../../..
CLIENT_PU_DIR=${EXAMPLE_DIR}/client/pu/pp-oms-client
VALIDATOR_PU_DIR=${EXAMPLE_DIR}/validator/pu/pp-oms-validator
PROCESSOR_PU_DIR=${EXAMPLE_DIR}/processor/pu/pp-oms-processor

GS_DEPLOY_DIR=${GS_HOME}/deploy
CLIENT_DEPLOY_TARGET_DIR=${GS_DEPLOY_DIR}/pp-oms-client
VALIDATOR_DEPLOY_TARGET_DIR=${GS_DEPLOY_DIR}/pp-oms-validator
PROCESSOR_DEPLOY_TARGET_DIR=${GS_DEPLOY_DIR}/pp-oms-processor

echo
echo Deleting previous deployments from GigaSpaces deploy directory if they exist
echo ----------------------------------------------------------------------------
if [ -d ${CLIENT_DEPLOY_TARGET_DIR} ]
then
	rm -r ${CLIENT_DEPLOY_TARGET_DIR}
fi
if [ -d ${VALIDATOR_DEPLOY_TARGET_DIR} ]
then
	rm -r ${VALIDATOR_DEPLOY_TARGET_DIR}
fi
if [ -d ${CLIENT_DEPLOY_TARGET_DIR} ]
then
	rm -r ${PROCESSOR_DEPLOY_TARGET_DIR}
fi

echo
echo Copy client, validator, and processor processing units to GigaSpaces deploy directory
echo -------------------------------------------------------------------------------
cp -r ${CLIENT_PU_DIR} ${GS_DEPLOY_DIR}
cp -r ${VALIDATOR_PU_DIR} ${GS_DEPLOY_DIR}
cp -r ${PROCESSOR_PU_DIR} ${GS_DEPLOY_DIR}

