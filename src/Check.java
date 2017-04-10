import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Check {

	public static void main(String[] args) throws IOException {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "Value1");
		map.put(2, "Value2");
		map.put(3, "Value3");
		map.put(4, "Value4");
		map.put(5, "Value5");

		int recordsToPrint = 3;

		FileWriter fstream;
		BufferedWriter out;

		fstream = new FileWriter("C:" + File.separator + "RPC_Logs" + File.separator + "values.txt");
		out = new BufferedWriter(fstream);

		int count = 0;

		Iterator<Entry<Integer, String>> it = map.entrySet().iterator();

		while (it.hasNext() && count < recordsToPrint) {

			Map.Entry<Integer, String> pairs = it.next();
			System.out.println("Value is " + pairs.getValue());

			out.write(pairs.getKey() + "," + pairs.getValue() + "\n");

			count++;
		}
		
		out.close();
		storeinMap();
	}

	public static void storeinMap() {
		String csvFile = "C:" + File.separator + "RPC_Logs" + File.separator + "values.txt";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			Map<String, String> maps = new HashMap<String, String>();

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				
				String[] country = line.split(cvsSplitBy);

				maps.put(country[0], country[1]);

			}

			// loop map
			for (Map.Entry<String, String> entry : maps.entrySet()) {

				System.out.println("key:" + entry.getKey() + " , value:" + entry.getValue());

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
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

		System.out.println("Done");
	}
}
