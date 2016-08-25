package by.topolev.courses.servlets;

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
import by.topolev.config.ConfigUtil;
import by.topolev.courses.convertors.DataJsonToDataConvertor;
import by.topolev.courses.convertors.ValidateResultToErrorJson;
import by.topolev.courses.validator.Data;
import by.topolev.courses.validator.DataValidator;
import by.topolev.courses.validator.ValidationResult;

public class ValidFieldServlet extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(ValidFieldServlet.class);
	private static String pathUploadImage;
	private static ObjectMapper map = new ObjectMapper();

	public void init() {
		pathUploadImage = ConfigUtil.getValue("pathUploadImage");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.debug("Executing ajax check field ");
		BufferedReader in = req.getReader();
		String json = in.readLine();

		DataJson dataJson = map.readValue(json, DataJson.class);

		Data data = new DataJsonToDataConvertor().convert(dataJson);
		ValidationResult errors = new ValidationResult();
		DataValidator.validField(data, errors);

		ErrorJson errorJson = new ValidateResultToErrorJson().convert(errors);

		PrintWriter out = resp.getWriter();
		out.print(map.writeValueAsString(errorJson));
	}

}
