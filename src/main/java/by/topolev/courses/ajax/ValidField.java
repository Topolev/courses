package by.topolev.courses.ajax;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.topolev.config.InitValues;
import by.topolev.courses.validators.ValidateField;
import by.topolev.courses.validators.json.ErrorFieldJson;
import by.topolev.courses.validators.json.ValidFieldJson;

public class ValidField extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(ValidField.class);
	private static String pathUploadImage;
	private static ObjectMapper map = new ObjectMapper();

	public void init() {
		pathUploadImage = InitValues.getValue("pathUploadImage");
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		LOG.debug("Valid field");
		String fileName = req.getParameter("fileName");
		File file = new File(getServletContext().getRealPath(pathUploadImage + fileName));
		
		ErrorFieldJson errorField = new ErrorFieldJson();
		LOG.debug(String.valueOf(errorField.isValid()));
		System.out.println();
		/*Check out image file with the same name*/
		if (file.exists()){
			errorField.setValid(false);
			errorField.setErrorMessage(String.format("Image file with name %s is exsited", file.getName()));
			LOG.debug(String.format("Image file with name %s is exsited", file.getName()));
		}
		
		/*Check out available expansion for image file*/
		LOG.debug(fileName);
		String[] splits = fileName.split("\\.");
		if (splits.length == 1) {
			errorField.setValid(false);
			errorField.setErrorMessage("Available expansion for upload image is 'JPG', 'GIF', 'PNG'");
			LOG.debug("File name isn't included expansion");
		} else{
			String eps = splits[1].toUpperCase();
			Pattern p = Pattern.compile("^(JPG|PNG|GIF)$");
			Matcher m = p.matcher(eps);
			if (!m.matches()){
				errorField.setValid(false);
				errorField.setErrorMessage("Available expansion for upload image is 'JPG', 'GIF', 'PNG'");
				LOG.debug(String.format("Expansion %s isn't available",eps));
			}
		};
		
		LOG.debug(String.valueOf(errorField.isValid()));
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		out.print(map.writeValueAsString(errorField));
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		LOG.debug("Execiting doPost()");
		BufferedReader in = req.getReader();
		String json = in.readLine();
		ValidFieldJson validField = map.readValue(json, ValidFieldJson.class);
		
		ErrorFieldJson errorField = new ValidateField(validField).validateField();
		PrintWriter out = resp.getWriter();
		out.print(map.writeValueAsString(errorField));
	}
	
	
}
