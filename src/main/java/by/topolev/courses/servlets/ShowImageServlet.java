package by.topolev.courses.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static by.topolev.config.ConfigUtil.*;

public class ShowImageServlet extends HttpServlet {
	
	private static final Logger LOG = LoggerFactory.getLogger(ShowImageServlet.class);

	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String rootDirectory = getValue(PATH_UPLOAD_IMAGE);
		File file = new File(rootDirectory + req.getParameter("file"));
		if (!file.exists()){
			LOG.info(String.format("File with path '%s' isn't excited.", file.getAbsolutePath()));
		} else{
			InputStream in = new FileInputStream(file);
			byte[] image = IOUtils.toByteArray(in);
			OutputStream out = resp.getOutputStream();
			out.write(image);
		}	
	}
}
