package by.topolev.courses.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AvailableImageExtansionValidator implements Validator {

	private static final String IMG_EXTANSION = "^(JPG|PNG|GIF)$";
	private static final String EMPTY = "";
	
	private String errMsg;

	public AvailableImageExtansionValidator(String errMsg) {
		super();
		this.errMsg = errMsg;
	}
	
	@Override
	public boolean isValid(String fileName) {
		if (EMPTY.equals(fileName.trim())) {
			return false;
		}
		String[] splits = fileName.split("\\.");
		if (splits.length > 1) {
			String ext = splits[1].toUpperCase();
			Pattern p = Pattern.compile(IMG_EXTANSION);
			Matcher m = p.matcher(ext);
			return m.matches();
		}
		return false;
	}
	
	@Override
	public String getErrorMessage() {
		return errMsg;
	}


}
