package by.topolev.courses.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.topolev.config.InitValues;
import by.topolev.courses.validators.AvailableImageExpansionValidator;
import by.topolev.courses.validators.ValidateField;
import by.topolev.courses.validators.json.ErrorFieldJson;

public class UploadImage extends HttpServlet {
	private static ServletFileUpload upload;
	private static final Logger LOG = LoggerFactory.getLogger(UploadImage.class);
	private static String pathUploadImage;

	public void init() {
		pathUploadImage = InitValues.getValue("pathUploadImage");
		
		File file = new File(pathUploadImage);
		if (!file.exists()){
			file.mkdir();
			LOG.debug(String.format("Create folder %s", file.getAbsolutePath()));
		}

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
		LOG.info("Execiting doPOST() for upload image");
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		ErrorFieldJson errorField = null;
		if (!isMultipart) {
			LOG.info("Form isn't exsited multipart data");
		} else {
			List<FileItem> items = null;
			try {
				items = upload.parseRequest(req);
			} catch (FileUploadException e) {
				LOG.info("Problems with parsing request to FileItem", e);
			}

			String fileName = getValueField("file_name", items);

			errorField = validateFieldFilename(fileName);

			if (errorField.isValid()) {
				File file = new File(pathUploadImage + fileName);
				for (FileItem item : items) {
					if (!item.isFormField()) {
						if (!(new AvailableImageExpansionValidator().isValidData(item.getName()))) {
							errorField.setValid(false);
							errorField.setErrorMessage("Upload file should contain an extension 'JPG', 'GIF', 'PNG'");
						} else {
							try {
								item.write(file);
								LOG.info(String.format("Image upload is success, file path = %s",
										file.getAbsolutePath()));
							} catch (Exception e) {
								LOG.info("Problem with upload image", e);
							}
						}
					}
				}
			}
		}
		
		if (errorField.isValid()){
			RequestDispatcher dispetcher = req.getRequestDispatcher("list_image.jsp");
			dispetcher.forward(req, resp);
		} else {
			RequestDispatcher dispetcher = req.getRequestDispatcher("error_upload.jsp");
			dispetcher.forward(req, resp);
			LOG.debug("ERROR");
		}
	}

	private String getValueField(String nameField, List<FileItem> items) {
		String valueField = null;
		for (FileItem item : items) {
			if (item.isFormField() && item.getFieldName().equals(nameField)) {
				return item.getString();
			}
		}
		return valueField;
	}

	private ErrorFieldJson validateFieldFilename(String fileName) {
		ValidateField validateField = new ValidateField();
		validateField.setValidator("availableImageExpansion");
		validateField.setValidator("uniqueImageFileName");
		validateField.setValue(fileName);
		ErrorFieldJson errorField = validateField.validateField();
		return errorField;
	}
}
