package handle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class MessageStoreHelper {
	private static Logger LOGGER = Logger.getLogger(MessageStoreHelper.class);

	public void writeHashMap1(HashMap<String, Object> hashMap) {
		// HashMap<String, Object> fileObj = new HashMap<String, Object>();

		/*
		 * ArrayList<String> cols = new ArrayList<String>(); cols.add("a");
		 * cols.add("b"); cols.add("c");
		 */
		String fileDirectory = RpcPropertiesHandler.getInstance().getProperty("MSG_STORE_LOCATION");

		// String PATH = "/remote/dir/server/";
		// String directoryName = PATH.concat(this.getClassName());
		// String fileName = id + getTimeStamp() + ".txt";

		File directory = new File(String.valueOf(fileDirectory));
		if (!directory.exists()) {
			directory.mkdir();
		}

		File file = new File(fileDirectory + "/HashMapStore.txt");

		// File file = new File(fileLocation);
		FileOutputStream f;
		try {
			f = new FileOutputStream(file);
			ObjectOutputStream s = new ObjectOutputStream(f);
			s.writeObject(hashMap);
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public HashMap<String, Object> readHashMap1() {
		// HashMap<String, Object> fileObj = new HashMap<String, Object>();

		String fileDirectory = RpcPropertiesHandler.getInstance().getProperty("MSG_STORE_LOCATION");
		File file = new File(fileDirectory + "/HashMapStore.txt");
		FileInputStream f;
		HashMap<String, Object> fileObj2 = null;
		try {

			f = new FileInputStream(file);
			ObjectInputStream s = new ObjectInputStream(f);
			fileObj2 = (HashMap<String, Object>) s.readObject();
			if (fileObj2 != null) {
				Iterator iterator = fileObj2.keySet().iterator();

				while (iterator.hasNext()) {
					String key = iterator.next().toString();
					String value = fileObj2.get(key).toString();
					System.out.println(key + " " + value);
				}
			}
			s.close();

		} catch (FileNotFoundException e) {
			LOGGER.error("Message store file is not yet created.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileObj2;
	}

	public static void main(String... args) throws IOException, ClassNotFoundException {
		HashMap<String, Object> fileObj = new HashMap<String, Object>();
		MessageStoreHelper helper = new MessageStoreHelper();
		// helper.writeHashMap(fileObj);
		// helper.readHashMap1();
	}

	public void writeHashMap(HashMap<String, String> hashMap) {
		FileWriter fstream;
		BufferedWriter out;

		// create your filewriter and bufferedreader
		try {
			
			String fileDirectory = RpcPropertiesHandler.getInstance().getProperty("MSG_STORE_LOCATION");

			// String PATH = "/remote/dir/server/";
			// String directoryName = PATH.concat(this.getClassName());
			// String fileName = id + getTimeStamp() + ".txt";

			File directory = new File(String.valueOf(fileDirectory));
			if (!directory.exists()) {
				directory.mkdir();
			}

			File file = new File(fileDirectory + "/HashMapStore.txt");
			
			fstream = new FileWriter(file);

			out = new BufferedWriter(fstream);

			// initialize the record count
			int count = 0;

			// create your iterator for your map
			Iterator<Entry<String, String>> it = hashMap.entrySet().iterator();

			// then use the iterator to loop through the map, stopping when we
			// reach
			// the
			// last record in the map or when we have printed enough records
			while (it.hasNext() && count < hashMap.size()) {

				// the key/value pair is stored here in pairs
				Map.Entry<String, String> pairs = it.next();
				System.out.println("Value is " + pairs.getValue());

				// since you only want the value, we only care about
				// pairs.getValue(), which is written to out
				out.write(pairs.getKey() + "," + pairs.getValue() + "\n");

				// increment the record count once we have printed to the file
				count++;
			}
			// lastly, close the file and end
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String, String> readHashMap() {
		//String csvFile = RpcPropertiesHandler.getInstance().getProperty("MSG_STORE_LOCATION");
		String fileDirectory = RpcPropertiesHandler.getInstance().getProperty("MSG_STORE_LOCATION");
		File file = new File(fileDirectory + "/HashMapStore.txt");
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		HashMap<String, String> maps = null;

		try {

			br = new BufferedReader(new FileReader(file));
			maps = new HashMap<String, String>();
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] keyValue = line.split(cvsSplitBy);
				maps.put(keyValue[0], keyValue[1]);

			}

			// loop map
			for (Map.Entry<String, String> entry : maps.entrySet()) {

				LOGGER.debug("key:" + entry.getKey() + " , value:" + entry.getValue());

			}

		} catch (FileNotFoundException e) {
			LOGGER.error("Message store file is not yet created.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return maps;

	}

}
