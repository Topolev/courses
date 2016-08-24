package by.topolev.courses.validator;

public interface Validator {
	public boolean isValid(String value);
	public String getErrorMessage();
}
