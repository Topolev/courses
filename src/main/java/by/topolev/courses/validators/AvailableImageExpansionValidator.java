package by.topolev.courses.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AvailableImageExpansionValidator implements Validator {

	public boolean isValidData(String value) {

		String[] splits = value.split("\\.");
		if (splits.length == 1) {
			return false;
		} else {
			String eps = splits[1].toUpperCase();
			Pattern p = Pattern.compile("^(JPG|PNG|GIF)$");
			Matcher m = p.matcher(eps);
			if (!m.matches()) {
				return false;
			}
		}
		return true;
	}

	public String getErrorMessage() {
		return "The image filename should contain an extension 'JPG', 'GIF', 'PNG'";
	}

}
