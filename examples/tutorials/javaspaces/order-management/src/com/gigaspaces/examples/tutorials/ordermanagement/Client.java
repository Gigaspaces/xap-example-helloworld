/*
 * Copyright 2007 GigaSpaces Technologies Ltd. All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED INCLUDING BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT. GIGASPACES WILL NOT 
 * BE LIABLE FOR ANY DAMAGE OR LOSS IN CONNECTION WITH THE SOFTWARE.
 */

package com.gigaspaces.examples.tutorials.ordermanagement;

import java.rmi.server.UID;

import com.j_spaces.core.IJSpace;
import com.j_spaces.core.client.FinderException;
import com.j_spaces.core.client.SpaceFinder;

/**
 * Title:        	GigaSpaces Order Management Example - Client Application.<p>
 * Description:    	Client application connects to the space and starts rounds of 
 * 					submitting one new order and taking multiple processed and rejected orders 
 * 					matching its client ID.
 * @since 6.0
 */
public class Client 
{
	/**
	 * @param args
	 *            args[0] contains the URL of the space that the client will
	 *            connect to. in this example the value is passed through the running script
	 *            "bin\run_Client.bat".
	 */
	public static void main(String[] args) 
	{
		System.out.println("\nWelcome to GigaSpaces Order Management Example!\n");

		// Checks if any arguments were passed from batch file.
		if (args.length != 1) 
		{
			// No argumants passed - the client will exit.
			System.out.println("Usage: <URL>");
			System.out.println("<protocol>://host:port/containername/spacename");
			System.exit(1);
		}

		try 
		{
			// Find the Space using Gigaspaces SpaceFinder method.
			IJSpace space = (IJSpace) SpaceFinder.find(args[0]); 
			
			space.setFifo(true);//	Set space to fifo.

			long maxOrders = 100000; // Holds the number of orders the client will submit.

			UID clientID = new UID();//	Used to create a unique ID for this client.

			// Create a template for taking only this client's processed orders from the space.
			ProcessedOrder templateProcessedOrder = new ProcessedOrder(clientID.toString());

			// Create a template for taking only this client's rejected orders from the space.
			Order templateRejectedOrder = new Order(Order.STATUS_REJECTED/*status*/,clientID.toString());

			// Reset Counters
			int countProcessedOrders = 0; // Counts number of processed orders taken from the space.
			int countRejectedOrders = 0; // Counts number of rejected orders taken from the space.

			//	Start rounds of placing one order and taking 
			// 	multiple processed orders and multiple rejected orders.
			// --------------------------------------------------------
			for (int i = 1; i < maxOrders + 1; i++) 
			{

				// 	Prepare a new order and write it to the space.
				//	----------------------------------------------
				Order order = new Order(Order.STATUS_NEW// status.
										,clientID.toString()// clientID.
										,new Integer(i)// Order number (to demonstrate space fifo).
										,new Integer(i)// data - in this example the data is arbitrarily set equal to the order number.
										);

				// Write the order to the space with 10min expiration time.
				space.write(order, null, 10 * 60000);
				
				// Display submitted order information.
				System.out.println("Order number " + i + " submitted by client ID[" + order.clientID + "]");

				// Take processed orders and rejected orders from the space 
				// --------------------------------------------------------
				// Try to take all the processed orders from the space.
				Object[] processedOrders = space.takeMultiple(templateProcessedOrder, null, Integer.MAX_VALUE);
				if (processedOrders != null) 
				{
					countProcessedOrders = countProcessedOrders + processedOrders.length; //	Update counter.
					System.out.println(processedOrders.length + " Processed orders found this round.");
				}
				else 
				{
					System.out.println("0 processed orders found this round.");
				}
				// Try to take all the rejected orders from the space.
				Object[] rejectedOrders = space.takeMultiple(templateRejectedOrder, null, Integer.MAX_VALUE);
				if (rejectedOrders != null) 
				{
					countRejectedOrders = countRejectedOrders + rejectedOrders.length; //	Update Counter.
					System.out.println(rejectedOrders.length + " Rejected orders found this round.");
				}
				else 
				{
					System.out.println("0 rejected orders found this round.");
				}

				//	Display status for this round.
				System.out.println("Status: " + i + " Orders submitted, " + countProcessedOrders +
						" Processed, " + countRejectedOrders + " Rejected");
				System.out.println("");

				//	Pausing for 2 sec to show results on console. 
				nap(2000);
			}
		} 
		catch (FinderException ex) 
		{
			System.out.println("Could not find space: " + args[0]);
			System.out.println("Please, start the GigaSpaces Server in order to run this example.");


		} 
		catch (Exception e) 
		{
			if (e instanceof java.net.ConnectException) 
			{
				System.err.println(e.getMessage());
				System.exit(1);
			}

			if (e instanceof java.io.IOException) 
			{
				System.err.println(e.getMessage());
				System.exit(1);
			}

			System.err.println("transError problem..." + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Sleeping
	 * @param ms milliseconds to sleep
	 */
	public static void nap(long ms) 
	{
		try 
		{
			Thread.sleep(ms);
		} 
		catch (InterruptedException iex) 
		{
			System.err.println("Cannot sleep...");
		}
	}
}
