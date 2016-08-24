package by.topolev.courses.convertors;

import by.ropolev.courses.jsonobject.ErrorJson;
import by.topolev.courses.validator.ValidateResult;

public class ValidateResultToErrorJson implements Convertor<ValidateResult, ErrorJson>{

	public void convert(ValidateResult from, ErrorJson to) {
		to.setErrorMessages(from.getErrorMessages());
		to.setValid(from.isValid());
	}

}
