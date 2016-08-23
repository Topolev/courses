
package by.topolev.courses.validators;

import java.io.File;

import by.topolev.config.InitValues;

public class UniqueImageFileNameValidator implements Validator {

	public boolean isValidData(String value) {
		String filePath = InitValues.getValue("pathUploadImage");
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
