package by.topolev.courses.convertors;

import java.util.ArrayList;
import java.util.List;

import by.ropolev.courses.jsonobject.DataJson;
import by.topolev.courses.validator.Data;

public class DataJsonToDataConvertor implements Convertor<DataJson, Data> {

	public void convert(DataJson from, Data to) {
		List<String> listValidators = new ArrayList<String>();
		for(String validator : from.getValidators()){
			listValidators.add(validator);
		}
		to.setValue(from.getValue());
		to.setListTypeValidators(listValidators);
	}

	@Override
	public Data convert(DataJson source) {
		Data target = new Data();
		convert(source, target);
		return target;
	}
	
	

}
