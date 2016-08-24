package by.topolev.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigUtil {
	private static Map<String, String> defaultValues = new HashMap<String, String>();
	private static Properties properties = null;
	private static final Logger LOG = LoggerFactory.getLogger(ConfigUtil.class);
	public static final String PATH_UPLOAD_IMAGE = "pathUploadImage";
	static {
		defaultValues.put(PATH_UPLOAD_IMAGE, "upload/");
		Properties property = new Properties();
		try {
			FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
			property.load(fis);
			properties = property;
			LOG.debug("Properties file upload success");
		} catch (IOException e) {
			LOG.debug("Property file config.properties isn't existed or problem with loading");
		}
	}

	public ConfigUtil() {

	}

	public static String getValue(String parameter) {
		LOG.debug("Get config value for parameter = '{}'", parameter);
		if (!defaultValues.containsKey(parameter))
			throw new IllegalArgumentException();
		if (properties == null) {
			return defaultValues.get(parameter);
		} else {
			return properties.getProperty(parameter);
		}
	}
}
