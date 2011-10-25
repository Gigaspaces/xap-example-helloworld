----------------------------------------------------------------------------------------------------------
 Purpose
---------

This example illustrates GigaSpaces simple order management system implementation. 
The order management system is compiled from a set of applications that form the entire workflow:

Client	- writes Order entries into the space and takes multiple processed processedOrders 
		entries and rejected Order entries (in batches). 

Validator	- takes new Order entries, validates them and writes back approved or rejected Order entries. 

Processeor	- takes approved Order entries and writes back processed processedOrder entries. 


You may start multiple Clients on the same machine or different machines. 

You may start multiple validators and processors on the same machine or different machines to improve 
the Client overall orders processing throughput.


----------------------------------------------------------------------------------------------------------
 Tutorial Wiki Page
--------------------

This example is explained in detail in the following wiki tutorial page:
http://www.gigaspaces.com/wiki/display/QSG6/JavaSpaces+Tutorial+B+-+Order+Management


----------------------------------------------------------------------------------------------------------
 Building and running the example
----------------------------------

execute bin\compile.bat/.sh  to compile the demo.
execute bin\startAll.bat/.sh to start the space.
execute bin\run_Client.bat/.sh to run the Client. (you can run several clients)
execute bin\run_Validator.bat/.sh to run the Validator. (you can run several validators)
execute bin\run_Processor.bat/.sh to run the processor. (you can run several processors)

You must have at least one instance of each application running (Client, Validator and Processor)
to see the complete workflow.


----------------------------------------------------------------------------------------------------------
 Source files
--------------

All the source files are located in <Example folder>\src\com\gigaspaces\examples\tutorials\ordermanagement

Client.java	- Client application.
Validator.java	- Validator application.
Processor.java	- Processor application.

Order.java	- The order entry class.
processedOrder.java	- The processed order entry class.
