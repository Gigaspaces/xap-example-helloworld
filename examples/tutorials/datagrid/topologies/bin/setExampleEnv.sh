#!/bin/sh

# Initializing the common environment for GigaSpaces
JSHOMEDIR=`dirname $0`/../../../../..; export JSHOMEDIR
. ${JSHOMEDIR}/bin/setenv.sh

# Setting JARS
JARS=${GS_JARS}${CPS}../classes"; export JARS
