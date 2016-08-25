package by.topolev.courses.convertors;

import by.ropolev.courses.jsonobject.ErrorJson;
import by.topolev.courses.validator.ValidationResult;

public class ValidateResultToErrorJson implements Convertor<ValidationResult, ErrorJson>{

	public void convert(ValidationResult source, ErrorJson target) {
		target.setErrorMessages(source.getErrorMessages());
		target.setValid(source.isValid());
	}

	@Override
	public ErrorJson convert(ValidationResult source) {
		ErrorJson target = new ErrorJson();
		convert(source, target);
		return target;
	}

}
