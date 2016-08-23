package by.topolev.courses.validators;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidatorConfig {
	private static Map<String, Validator> validators = new HashMap<String, Validator>();
	private static final Logger LOG = LoggerFactory.getLogger(ValidatorConfig.class);
	static {
		validators.put("availableImageExpansion", new AvailableImageExpansionValidator());
		validators.put("uniqueImageFileName", new UniqueImageFileNameValidator());
	}

	public static Validator getValidator(String typeValidator){
		if (!validators.containsKey(typeValidator)) {
			LOG.debug(String.format("Validator with name '%s' isn't supported.", typeValidator));
			throw new NotSupportTypeValidator();
		};
		return validators.get(typeValidator);
	}
}
