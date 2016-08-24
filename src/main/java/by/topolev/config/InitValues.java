package by.topolev.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitValues {
	private static Map<String, String> defaultValues = new HashMap<String, String>();
	private static Properties properties = null;
	private static final Logger LOG = LoggerFactory.getLogger(InitValues.class);
	
	static {
		defaultValues.put("pathUploadImage", "upload/");
		Properties property = new Properties();
		try {			
			FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
			property.load(fis);
			properties = property;
			LOG.debug("Properties file upload success");
		} catch (IOException e) {
			LOG.debug("Property file config.properties isn't excited or problem with loading");
		}	
	}
	
	public static String getValue(String nameValue){
		if (!defaultValues.containsKey(nameValue)) throw new IllegalArgumentException();
		if (properties == null){
			return defaultValues.get(nameValue);
		} else{
			properties.getProperty(nameValue);
		}
		return null;
	}
}
