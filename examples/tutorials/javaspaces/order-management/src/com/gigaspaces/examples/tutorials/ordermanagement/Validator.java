/*
 * Copyright 2007 GigaSpaces Technologies LTD. All rights reserved.
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
 * Title:        	GigaSpaces OMS Example - Validator Application.<p>
 * Description:   	The Validator application connects to the space and under transaction 
 * 					takes new orders (order.status="New") stamps them as rejected or approved
 * 					(random validation) and writes them	back to the space.
 * @since 6.0
 */
public class Validator
{
	static TransactionManager txnManager; 	//	The local transaction manager object.
	static IJSpace space; 					//	The space object.

	/**
	 * @param args
	 *            args[0] contains the URL of the space that the client will
	 *            connect to. In this example the value is passed through the running script
	 *            "bin\run_Validator.bat".
	 */
	public static void main(String[] args)
	{
		//	Checks if any arguments are passed from batch file
		if (args.length != 1) 
		{
			// No arguments passed - the validator will exit.
			System.out.println("Usage: <spaceURL>");
			System.out.println("<protocol>://host:port/containername/spacename");
			System.exit(1);
		}
		
		try 
		{
 			// Connecting to the space.
			System.out.println("try to connect to " + args[0]);
			space = (IJSpace) SpaceFinder.find(args[0]);
		
			space.setFifo(true); //	Set space to FIFO
			
			System.out.println("connected to " + args[0] + " OK!" );
			
			// Get the validator machine hostname.
			String hostname = InetAddress.getLocalHost().getHostAddress();
			System.out.println("Validator started at host " + hostname);
			
			//	Create Order template to find new orders (Order.status="New").
			Order template = new Order(Order.STATUS_NEW);
			
			//	Reset validated orders counters
			int countApprovedOrders = 0;
			int countRejectedOrders = 0;
			
			//	Start rounds of taking a new order from the space, validating it and writing the
			//	order with the status changed back to the space.
			while (true)
			{
				Transaction txn = null;
				try 
				{
					// Start new transaction.
					txn = getTrans();
					//	Checking if transaction object is ok.
					if (txn == null) {
						System.err.println("FATAL...  transaction is NULL");
						System.exit(1);
					}
					// Try to take a new order from the space.
					System.out.println("Seeking new orders...");
					Order order = (Order) space.take(template, txn, 30000);
					
					// Handling found order.
					if (order != null)
					{
						System.out.println("New order number " + order.number + " found from client: " + order.clientID);
						
						String status=order.validate(hostname);// Validate the order.
						
						space.write(order, txn, 100 * 1000);// Write back the validated order.
						
						txn.commit();// Commit transaction.
						
						//	Update the relevant counter and display the validation result.
						if (status==Order.STATUS_APPROVED) {
							System.out.println("Order approved.");
							countApprovedOrders++; 
						}
						else 
						{
							System.out.println("Order rejected.");
							countRejectedOrders++;
						}
						System.out.println("Validator examined " + (countApprovedOrders + countRejectedOrders) +
										   " new orders: " + countApprovedOrders + " orders approved and " + 
										   countRejectedOrders + " orders rejected.\n");
					}
					else  
						// No order found - aborting Transaction.
						txn.abort(); 
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
		
		System.err.println("*****Validator Error: " + ex.getMessage());
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
