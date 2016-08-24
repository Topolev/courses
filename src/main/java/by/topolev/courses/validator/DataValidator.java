package by.topolev.courses.validator;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class DataValidator {
	private static final Logger LOG = LoggerFactory.getLogger(DataValidator.class);
	
	public static void validField(Data field, ValidateResult result){
		List<Validator> validators = createValidators(field.getListTypeValidators());
		for (Validator validator : validators){
			LOG.debug(String.format("Validate data = '%s' using validator '%s'", field.getValue(), validator.getClass()));
			if (!validator.isValid(field.getValue())){
				result.setErrorMessage(validator.getErrorMessage());
				LOG.debug("Validate is not successful");
			} else{
				LOG.debug("Validate is successful");
			}
		}
		
	}
	
	private static List<Validator> createValidators(List<String> listTypeValidators){
		List<Validator> listValidators = new ArrayList<Validator>();
		for (String typeValidator : listTypeValidators){
			try{
				listValidators.add(ValidatorFactory.getValidator(typeValidator));
				LOG.debug(String.format("Create validator with name='%s'", typeValidator));
			} catch (NotSupportValodator ex){
				LOG.debug(String.format("Validator with name '%s' isn't supported",typeValidator));
			}
		}
		return listValidators;
	}
}
