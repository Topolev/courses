package by.topolev.courses.servlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import by.topolev.config.ConfigUtil;
import by.topolev.courses.validator.Data;
import by.topolev.courses.validator.DataValidator;
import by.topolev.courses.validator.ValidateResult;
import static by.topolev.config.ConfigUtil.*;
import static by.topolev.config.Validators.*;

public class UploadImage extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger(UploadImage.class);

	private static ServletFileUpload upload;
	private static String pathUploadImage;

	public void init() {
		pathUploadImage = getValue(PATH_UPLOAD_IMAGE);

		LOG.debug("The asigning directory for upload: {}", pathUploadImage);

		createUploadDirIfNotExist();
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

	private void createUploadDirIfNotExist() {
		File file = new File(pathUploadImage);
		if (!file.exists()) {
			file.mkdir();
			LOG.debug("Create folder for uploading images: {}", file.getAbsolutePath());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.info("Executing doPOST() for upload image");
		ValidateResult errors = new ValidateResult();

		boolean isMultipart = ServletFileUpload.isMultipartContent(req);

		if (!isMultipart) {
			errors.setErrorMessage("Form isn't exsited multipart data");
			LOG.info("Form isn't exsited multipart data");
		} else {
			List<FileItem> items = null;
			try {
				items = upload.parseRequest(req);

				String userFileName = getValueField("file_name", items);
				FileItem uploadImage = getUploadImage(items);
	
				validateEnteredFilename(errors, userFileName);
				validateUploadFilename(errors, uploadImage);

				if (errors.isValid()) {
					File file = new File(pathUploadImage + userFileName);
					try {
						uploadImage.write(file);
						LOG.debug(String.format("Image upload is success, file path = %s", file.getAbsolutePath()));
					} catch (Exception e) {
						errors.setErrorMessage("Problem with upload image");
						LOG.debug("Problem with upload image", e);
					}
				}

			} catch (FileUploadException e) {
				errors.setErrorMessage("Problems with parsing request to FileItem");
				LOG.info("Problems with parsing request to FileItem", e);
			}

		}

		if (errors.isValid()) {
			RequestDispatcher dispetcher = req.getRequestDispatcher("list_image.jsp");
			dispetcher.forward(req, resp);
		} else {
			LOG.debug("Uploading image is not successful.");
			RequestDispatcher dispetcher = req.getRequestDispatcher("error_upload.jsp");
			req.setAttribute("errors", errors.getErrorMessages());
			dispetcher.forward(req, resp);

		}
	}

	private void validateUploadFilename(ValidateResult errors, FileItem uploadImage) {
		validateData(uploadImage.getName(), errors, "emptyString", UPLOAD_FILENAME_VALIDATOR);
	}

	private void validateEnteredFilename(ValidateResult errors, String userFileName) {
		validateData(userFileName, errors, ENTERED_FILENAME_VALIDATOR, "uniqueImageFileName");
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

	private FileItem getUploadImage(List<FileItem> items) {
		for (FileItem item : items) {
			if (!item.isFormField())
				return item;
		}
		return null;
	}

	private void validateData(String valueData, ValidateResult result, String... listValidators) {
		List<String> typeValidators = new ArrayList<String>();
		for (String typeValidator : listValidators) {
			typeValidators.add(typeValidator);
		}
		Data data = new Data(valueData, typeValidators);
		DataValidator.validField(data, result);
	}
}
