package by.topolev.courses.validators;

public interface Validator {
	public boolean isValidData(String value);
	public String getErrorMessage();
}
