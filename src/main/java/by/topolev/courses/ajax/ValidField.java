package by.topolev.courses.ajax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.ropolev.courses.jsonobject.DataJson;
import by.ropolev.courses.jsonobject.ErrorJson;
import by.topolev.config.InitValues;
import by.topolev.courses.convertors.DataJsonToDataConvertor;
import by.topolev.courses.convertors.ValidateResultToErrorJson;
import by.topolev.courses.validator.Data;
import by.topolev.courses.validator.DataValidator;
import by.topolev.courses.validator.ValidateResult;
import by.topolev.courses.validators.ValidateField;
import by.topolev.courses.validators.json.ErrorFieldJson;

public class ValidField extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(ValidField.class);
	private static String pathUploadImage;
	private static ObjectMapper map = new ObjectMapper();

	public void init() {
		pathUploadImage = InitValues.getValue("pathUploadImage");
	}

	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		LOG.debug("Executing ajax check field ");
		BufferedReader in = req.getReader();
		String json = in.readLine();
		
		DataJson dataJson = map.readValue(json, DataJson.class);
		LOG.debug(dataJson.getValue());
		LOG.debug(dataJson.getValidators().toString());
		
		Data data = new Data();
		ValidateResult errors = new ValidateResult();
		ErrorJson errorJson = new ErrorJson();
		new DataJsonToDataConvertor().convert(dataJson, data);
		
		
		DataValidator.validField(data, errors);
		
		new ValidateResultToErrorJson().convert(errors, errorJson);
		
		PrintWriter out = resp.getWriter();
		out.print(map.writeValueAsString(errorJson));
	}
	
	
}
