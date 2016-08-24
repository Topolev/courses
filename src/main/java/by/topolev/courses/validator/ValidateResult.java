package by.topolev.courses.validator;

import java.util.ArrayList;
import java.util.List;

public class ValidateResult {
	private boolean isValid = true;
	private List<String> errorMessages = new ArrayList<String>();
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public List<String> getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	public void setErrorMessage(String errorMessage){
		this.setValid(false);
		this.errorMessages.add(errorMessage);
	}
}
