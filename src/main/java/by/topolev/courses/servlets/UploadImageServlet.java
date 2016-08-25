package by.topolev.courses.servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.topolev.courses.services.UploadImageService;
import by.topolev.courses.services.UploadImageServiceFactory;
import by.topolev.courses.validator.ValidationResult;

public class UploadImageServlet extends HttpServlet {

	private static final long serialVersionUID = -2883942647989292725L;

	private static final Logger LOG = LoggerFactory.getLogger(UploadImageServlet.class);

	private static ServletFileUpload upload;
	private static String pathUploadImage;
	private UploadImageService uploadImageService = UploadImageServiceFactory.getInstance();
	
	@Override
	public void init() {
		LOG.debug("The asigning directory for upload: {}", pathUploadImage);
		initServletFileUpload();
	}

	private void initServletFileUpload() {
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		upload = new ServletFileUpload(factory);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ValidationResult result = uploadImageService.uploadImage(req, upload);
		if (result.isValid()) {
			redirectToSuccessPage(req, resp);
		} else {
			redirectToErrorPage(req, resp, result);

		}
	}

	private void redirectToSuccessPage(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		RequestDispatcher dispetcher = req.getRequestDispatcher("list_image.jsp");
		dispetcher.forward(req, resp);
	}

	private void redirectToErrorPage(HttpServletRequest req, HttpServletResponse resp, ValidationResult errors)
			throws ServletException, IOException {
		LOG.debug("Uploading image is not successful.");
		RequestDispatcher dispetcher = req.getRequestDispatcher("error_upload.jsp");
		req.setAttribute("errors", errors.getErrorMessages());
		dispetcher.forward(req, resp);
	}

}
