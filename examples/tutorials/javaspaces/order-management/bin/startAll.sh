#!/bin/sh

. setExampleEnv.sh

${JSHOMEDIR}/bin/gsInstance.sh "/./oms?groups=${LOOKUPGROUPS}"
