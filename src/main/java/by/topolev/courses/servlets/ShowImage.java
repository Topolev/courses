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

import by.topolev.config.ConfigUtil;

public class ShowImage extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(ShowImage.class);
	private static String pathUploadImage;
	
	public void init(){
		pathUploadImage = ConfigUtil.getValue("pathUploadImage");
		pathUploadImage = "upload/";
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println(req.getParameter("file"));
		String rootDirectory = pathUploadImage;
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
