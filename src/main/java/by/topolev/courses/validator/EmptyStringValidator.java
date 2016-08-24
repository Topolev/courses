package by.topolev.courses.validator;

public class EmptyStringValidator implements Validator{

	public boolean isValid(String value) {
		if (value.trim().equals("")) return false;
		return true;
	}

	public String getErrorMessage() {
		return "The filename should not be empty";
	}

}
