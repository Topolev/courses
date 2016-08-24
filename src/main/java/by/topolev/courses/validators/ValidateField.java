package by.topolev.courses.validators;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.topolev.courses.validators.json.ErrorFieldJson;
import by.topolev.courses.validators.json.ValidFieldJson;

public class ValidateField {
	private static final Logger LOG = LoggerFactory.getLogger(ValidateField.class);
	private List<Validator> validators = new ArrayList<Validator>();
	private String value;
	
	public ValidateField(ValidFieldJson validField){
		String[] validators = validField.getValidators();
		for (String validator : validators){
			this.validators.add(ValidatorFactory.getValidator(validator));
			LOG.debug(String.format("Create validator with name = '%s'", validator));
		}
		this.value = validField.getValue();
	}
	public ValidateField(){}
	
	public void setValidator(String typeValidator){
		this.validators.add(ValidatorFactory.getValidator(typeValidator));
		LOG.debug(String.format("Create validator with name = '%s'", typeValidator));
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	public ErrorFieldJson validateField(){
		ErrorFieldJson errorField = new ErrorFieldJson();
		for (Validator validator : validators){
			if (!validator.isValidData(value)){
				errorField.setValid(false);
				errorField.setErrorMessage(validator.getErrorMessage());
			}
		}
		return errorField;
	}
}
