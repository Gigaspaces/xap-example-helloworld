---------------------------------------------------------------------------------------------------------------
 Purpose
=========

This example demonstrates the usage of partition with backup and replicated cluster topologies.

After a chosen topology is deployed, a DataLoader application writes POJO accounts to the cluster 
(every time the loader is started it first clears the space from account objects to ensure unique IDs).
2 reader applications (simple and notified) are used to read the written accounts.
This example also shows the usage of client local cache and local view.


---------------------------------------------------------------------------------------------------------------
 Tutorial
==========

This example is described in details in the following wiki tutorial page:
http://www.gigaspaces.com/wiki/display/XAP8/EDG+-+Basic+Topologies+Tutorial


---------------------------------------------------------------------------------------------------------------
 Compiling
===========

Execute bin\compile.bat(.sh) to compile the demo .


---------------------------------------------------------------------------------------------------------------
 Running the example
=====================

Deploy the clusters based on the topologies you want to use,
use the deployment instructions with screenshots and tutorial refer to "Deploying the Data Grid" part in
http://www.gigaspaces.com/wiki/display/XAP8/EDG+-+Basic+Topologies+Tutorial

use the deployment instructions, name both deployment and space "myDataGrid".

Wait until the deployment is finished!

1. Execute bin\run_DataLoader.bat(.sh) to write to cache.

2. Execute bin\run_SimpleReader.bat(.sh) to read from cache.

	or Execute bin\run_SimpleReaderLocalCache.bat(.sh) to read from cache with client embedded local cache.

	or Execute bin\run_SimpleReaderLocalView.bat(.sh) to read from cache with client embedded local view cache.


To use notified reader run the notified reader before running the loader since this reader
gets notification only for newly written objects:

1. Execute bin\run_NotifiedReader.bat(.sh) to listen to cache account write operations.

2. Execute bin\run_DataLoader.bat(.sh) to write to cache.


Optionally execute <Gigaspaces>\bin\gs-ui.bat(.sh) to start the 
space-browser.


