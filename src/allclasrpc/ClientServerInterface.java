package allclasrpc;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientServerInterface extends Remote{
	
	public void processPutRequest (String key, String value) throws RemoteException;
	public String processGetRequest(String key) throws RemoteException;
	public void processDeleteRequest(String key) throws RemoteException;

}
