package by.topolev.courses.validator;

import java.util.HashMap;
import java.util.Map;
import static by.topolev.config.Validators.*;


public class ValidatorFactory {
	private static Map<String, Validator> accessValidators = new HashMap<String, Validator>();
	private static final String ERR_MSG_ENTERED_FILENAME = "The entered filename should contain the following extensions 'JPG', 'GIF', 'PNG'";
	private static final String ERR_MSG_UPLOAD_FILENAME = "The upload file is not supported. Please upload file with the following extensions 'JPG', 'GIF', 'PNG'";
	
	static {
		accessValidators.put(ENTERED_FILENAME_EXTANSION_VALIDATOR, new AvailableImageExtansionValidator(ERR_MSG_ENTERED_FILENAME));
		accessValidators.put(UPLOAD_FILENAME_EXTANSION_VALIDATOR, new AvailableImageExtansionValidator(ERR_MSG_UPLOAD_FILENAME));
		accessValidators.put("uniqueImageFileName", new UniqueImageFileNameValidator());
		accessValidators.put("emptyString", new EmptyStringValidator());
	}
	
	public static Validator getValidator(String typeValidator){
		if (!accessValidators.containsKey(typeValidator)) throw new NotSupportValodator();
		return accessValidators.get(typeValidator);
	}
}
