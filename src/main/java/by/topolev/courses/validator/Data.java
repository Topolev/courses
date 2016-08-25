package by.topolev.courses.validator;

import java.util.ArrayList;
import java.util.List;

public class Data {
	private String value;
	private List<String> listTypeValidators = new ArrayList<String>();
	public Data(String value, List<String> listTypeValidators){
		this.value = value;
		this.listTypeValidators = listTypeValidators;		
	}
	public Data(){}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<String> getListTypeValidators() {
		return listTypeValidators;
	}
	public void setListTypeValidators(List<String> listTypeValidators) {
		this.listTypeValidators = listTypeValidators;
	}
	
}
