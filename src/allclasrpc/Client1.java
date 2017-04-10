package allclasrpc;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class Client1 {

	private static Logger LOGGER = Logger.getLogger(Client1.class);

	public static void main(String[] args) {

		if (args.length < 2) {
			LOGGER.error("java Client1 <Host Name> <Port Number>");
			System.exit(1);
		}

		String hostName = args[0].toString();
		int portNumber = Integer.valueOf(args[1]).intValue();

		try {
			// Take the Data set size from user
			Scanner in = new Scanner(System.in);

			// get request type
			String requestType = "";
			System.out.println("Please Enter Request type.that is: PUT / GET/ DEL");
			requestType = in.nextLine();

			// get key
			String key = "";
			System.out.println("Please Enter a Key");
			key = in.nextLine();

			// get message
			String msgValue = "";
			if ("" != requestType && requestType.equalsIgnoreCase("PUT")) {
				System.out.println("Please Enter Data for Key: " + key);
				msgValue = in.nextLine();
			}

			Registry registry = LocateRegistry.getRegistry(hostName, portNumber);

			ClientServerInterface messageServer = (ClientServerInterface) registry
					.lookup("ClientServerInterfaceImpl");
			
			if ("" != requestType && requestType.equalsIgnoreCase("PUT")) {
				
				messageServer.processPutRequest(key, msgValue);
				LOGGER.info("Put request done. See log ");

			} else if ("" != requestType && requestType.equalsIgnoreCase("GET")) {
				messageServer.processGetRequest(key);
				LOGGER.info("Get request done. See log ");

			} else if ("" != requestType && requestType.equalsIgnoreCase("DEL")) {
				messageServer.processDeleteRequest(key);
				LOGGER.info("Delete request done.");

			} else {
				LOGGER.error("Unknown request type: " + requestType);
			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
