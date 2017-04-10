package allclasrpc;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import handle.MessageStoreHelper;

public class ClientServerInterfaceImpl extends UnicastRemoteObject implements ClientServerInterface, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Thread t;

	private static Logger LOGGER = Logger.getLogger(ClientServerInterfaceImpl.class);

	private String requestType;
	private String key;
	private String msgValue;
	private String returnMsg;

	public ClientServerInterfaceImpl() throws RemoteException {
	}

	public ClientServerInterfaceImpl(String requestType, String key, String msgValue) throws RemoteException {
		
		this.requestType = requestType;
		this.key = key;
		this.msgValue = msgValue;
	}

	public void processPutRequest(String key, String value) throws RemoteException {
		LOGGER.info("key is:" + key + " Value is:" + value);
		ClientServerInterfaceImpl serverThread = new ClientServerInterfaceImpl("PUT", key, value);
		serverThread.start();

	}

	public String processGetRequest(String key) throws RemoteException {
		LOGGER.info("key is: " + key);
		ClientServerInterfaceImpl serverThread = new ClientServerInterfaceImpl("GET", key, "");
		serverThread.start();
		return this.returnMsg;

	}

	public void processDeleteRequest(String key) throws RemoteException {
		LOGGER.info("key is: " + key);
		ClientServerInterfaceImpl serverThread = new ClientServerInterfaceImpl("DEL", key, "");
		serverThread.start();

	}

	public void start() {
		LOGGER.info("Starting thread..");
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	@Override
	public void run() {
		LOGGER.info("Run New thread " + Thread.currentThread().getName() + " is started..");
		LOGGER.debug("requestType: " + requestType + " msgKey" + this.key);

		try {
			if (this.requestType != "" && this.requestType.equalsIgnoreCase("PUT")) {
				addToMsgStore(this.key, this.msgValue);
			} else if (this.requestType != "" && this.requestType.equalsIgnoreCase("GET")) {
				getFromMsgStore(this.key);
			} else if (requestType != "" && requestType.equalsIgnoreCase("DEL")) {
				deleteFromMsgStore(this.key);
			} else {
				LOGGER.error("Unknown request type: " + requestType + " is received.");
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public synchronized void addToMsgStore(String key, String value) throws RemoteException {
		LOGGER.info("key is:" + key + " Value is:" + value);
		MessageStoreHelper msgHelper = new MessageStoreHelper();
		HashMap<String, String> retrievedMap = msgHelper.readHashMap();
		if (retrievedMap != null) {
			retrievedMap.put(key, value);
			msgHelper.writeHashMap(retrievedMap);
			LOGGER.info("Put successful");
		} else {
			HashMap<String, String> msgStrore = new HashMap<String, String>();
			msgStrore.put(key, value);
			msgHelper.writeHashMap(msgStrore);
			LOGGER.info("Put successful");
		}
		LOGGER.info("PUT request completed");

	}

	public void getFromMsgStore(String key) throws RemoteException {
		LOGGER.debug(" get From Data Store.. key is:" + key);
		MessageStoreHelper msgHelper = new MessageStoreHelper();
		HashMap<String, String> retrievedMap = msgHelper.readHashMap();
		if (retrievedMap != null) {
			LOGGER.debug("Retrieved Map size: "+retrievedMap.size());
			if (retrievedMap.containsKey(key.trim())) {
				LOGGER.debug("Retrieved message is: "+retrievedMap.get(key.trim()));
				this.returnMsg = retrievedMap.get(key.trim());
				LOGGER.info("Retrieve successful");
			} else {
				String failureMsg = "There is no key-value pair for key: " + key;
				LOGGER.error(failureMsg);
			}
		} else {
			LOGGER.error("Data store not created.");

		}
		LOGGER.info("Get request completed");

	}
	
	public synchronized void deleteFromMsgStore(String key) throws RemoteException {
		LOGGER.debug(" delete From  Data Store key is:" + key);
		MessageStoreHelper msgHelper = new MessageStoreHelper();
		HashMap<String, String> retrievedMap = msgHelper.readHashMap();
		if (retrievedMap != null) {
			LOGGER.debug("Retrieved Map size: "+retrievedMap.size());
			if (retrievedMap.containsKey(key.trim())) {
				
				retrievedMap.remove(key.trim());
				//Update the message store after removal
				msgHelper.writeHashMap(retrievedMap);
				LOGGER.info("Delete successful");
				LOGGER.debug("New size after deletion: "+retrievedMap.size());
				
			} else {
				String failureMsg = "There is no key-value pair for key: " + key;
				LOGGER.error(failureMsg);
			}
		} else {
			LOGGER.error("Data store not created.");

		}
		LOGGER.info("Delete request completed");

	}

	public static void main(String[] args) {
		try {

		} catch (Exception ex) {

		}

	}

}
