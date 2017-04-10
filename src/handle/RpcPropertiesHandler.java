package handle;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * This class reads the properties from properties file
 * 
 * @author Manoj Bisht
 *
 */
public class RpcPropertiesHandler {

	private final Properties configProp = new Properties();

	private RpcPropertiesHandler() {

		InputStream in = this.getClass().getClassLoader().getResourceAsStream("RpcProgramming.properties");
		try {
			configProp.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class InstanceHolder {
		private static final RpcPropertiesHandler INSTANCE = new RpcPropertiesHandler();
	}

	public static RpcPropertiesHandler getInstance() {
		return InstanceHolder.INSTANCE;
	}

	public String getProperty(String key) {
		return configProp.getProperty(key);
	}

	public Set<String> getAllPropertyNames() {
		return configProp.stringPropertyNames();
	}

	public boolean containsKey(String key) {
		return configProp.containsKey(key);
	}
}
