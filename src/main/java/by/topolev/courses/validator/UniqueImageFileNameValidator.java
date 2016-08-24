
package by.topolev.courses.validator;

import java.io.File;

import by.topolev.config.ConfigUtil;

public class UniqueImageFileNameValidator implements Validator {

	public boolean isValid(String value) {
		if (value.trim().equals("")) return true;
		String filePath = ConfigUtil.getValue("pathUploadImage");
		File file = new File(filePath + value);
		if (file.exists()) {
			return false;
		}
		return true;
	}

	public String getErrorMessage() {
		return "The image with this filename is existed.";
	}

}
