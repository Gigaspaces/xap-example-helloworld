/*
 * Copyright 2007 GigaSpaces Technologies Ltd. All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED INCLUDING BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT. GIGASPACES WILL NOT 
 * BE LIABLE FOR ANY DAMAGE OR LOSS IN CONNECTION WITH THE SOFTWARE.
 */
package com.gigaspaces.examples.tutorials.ordermanagement;

import net.jini.core.transaction.server.TransactionManager;	
import net.jini.core.transaction.Transaction;				
import net.jini.core.transaction.TransactionFactory;		

import com.j_spaces.core.IJSpace;							
import com.j_spaces.core.client.LocalTransactionManager;	
import com.j_spaces.core.client.FinderException;			
import com.j_spaces.core.client.SpaceFinder;				
import java.net.InetAddress;								

/**
 * Title:        	GigaSpaces Order Management Example - Processor Application.<p>
 * Description:   	The Processor application connects to the space and under transaction 
 * 					takes approved orders (order.status="Approved"), processes them 
 * 					and writes the processed orders (ProcessedOrder object) to the space.
 * @since 6.0
 */
public class Processor 
{
	private static TransactionManager txnManager; 	//	The local transaction manager object.
	private static IJSpace space; 					//	The space object.
	
	/**
	 * @param args
	 *            args[0] contains the URL of the space that the client will
	 *            connect to. this value is passed through the running script
	 *            "bin\run_Processor.bat".
	 */
	public static void main(String[] args) 
	{
		//	Checks if any arguments were passed from batch file.
		if (args.length != 1) 
		{
			// No argumants passed - the processor will exit.
			System.out.println("Usage: <spaceURL>");
			System.out.println("<protocol>://host:port/containername/spacename");
			System.exit(1);
		}
		
		try 
		{
			// Connecting to the space.
			System.out.println("Try to connect to " + args[0]);
			space = (IJSpace) SpaceFinder.find(args[0]);
		
			space.setFifo(true); //	Set space to fifo.
			
			System.out.println("Connected to " + args[0] + " OK!" );
			
			//	Get the processor machine hostname.
			String hostname = InetAddress.getLocalHost().getHostAddress();
			System.out.println("Processor started at host " + hostname);
			
			//	Create Order template to find approved orders (Order.status="Approved").
			Order template = new Order(Order.STATUS_APPROVED);
			
			//	Reset processed orders counter.
			int countProcessedOrders = 0;
	
			//	Start rounds of taking an approved order from the space, processing it and writing
			//	the processed order to the space.
			while (true) 
			{
				Transaction txn = null;
				try 
				{
					// Start new transaction.
					txn = getTrans();
					//	Checking if transaction object ok.
					if (txn == null) {
						System.err.println("FATAL...  transaction is NULL");
						System.exit(1);
					}
					// Try to take an approved order from the space.
					System.out.println("Seeking approved orders...");
					Order order = (Order) space.take(template, txn, 30000);
					if (order != null) {
						System.out.println("Approved order number " + order.number + " found from client: " + order.clientID);

						// Create the processed order.
						ProcessedOrder processedOrder = order.process(hostname); 
						space.write(processedOrder, txn, 100 * 1000); // Write back the processed order.
						
						txn.commit(); // Commit transaction.

						//	Update the counter and display status.
						countProcessedOrders++;
						System.out.println("Order processed.");
						System.out.println("Processed " + countProcessedOrders + " orders.\n");
					}
					else
					{
						// No order found - aborting Transaction.
						txn.abort();
					}
				} 
				catch (Exception tx) 
				{
					transError(txn, tx);
				}
			}
		}
		catch (FinderException ex) 
		{
			System.out.println("Could not find space: " + args[0]);
			System.out.println("Please make sure that the GigaSpaces Server is started.");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	/**	
	 * Method for handling transaction error exceptions.
	 * @param txn - Transaction object.
	 * @param ex - Exception.
	 */
	private static void transError(Transaction txn, Exception ex) 
	{
		if (ex instanceof java.io.IOException) 
		{
			System.err.println(ex.getMessage());
			System.exit(1);
		}
		
		System.err.println("*****Processor Error: " + ex.getMessage());
		try 
		{
			txn.abort();
		} 
		catch (Exception ux) 
		{
			System.err.println("TransError problem..." + ux.getMessage());
		}
	}

	/**
	 * Method for getting a transaction.
	 * @return transaction proxy object with 60 sec timeout, if fails returns null.
	 */ 
	private static Transaction getTrans() 
	{
		try 
		{
			if (txnManager == null) 
			{
				txnManager = LocalTransactionManager.getInstance(space);
			}
			// txn timeout is 60 sec (60*1000ms)...
			Transaction.Created tcreate = TransactionFactory.create(txnManager, 60 * 1000);
			return tcreate.transaction;
		} 
		catch (net.jini.core.lease.LeaseDeniedException ldx) 
		{
			System.err.println("Fatal... Lease denied.");
		} 
		catch (java.rmi.RemoteException rx) 
		{
			System.err.println("Fatal... Cannot create transaction manager.");
		}
		return null;
	}
}
