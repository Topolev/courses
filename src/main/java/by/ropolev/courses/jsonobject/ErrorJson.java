package by.ropolev.courses.jsonobject;

import java.util.ArrayList;
import java.util.List;

public class ErrorJson {
	private boolean valid = true;
	private List<String> errorMessages = new ArrayList<String>();
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public List<String> getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	public void setErrorMessage(String errorMessage){
		errorMessages.add(errorMessage);
	}
}
