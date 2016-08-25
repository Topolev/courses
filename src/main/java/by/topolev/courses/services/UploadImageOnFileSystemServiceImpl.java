package by.topolev.courses.services;

import static by.topolev.config.ConfigUtil.PATH_UPLOAD_IMAGE;
import static by.topolev.config.ConfigUtil.getValue;
import static by.topolev.config.Validators.ENTERED_FILENAME_EXTANSION_VALIDATOR;
import static by.topolev.config.Validators.UPLOAD_FILENAME_EXTANSION_VALIDATOR;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.topolev.courses.validator.Data;
import by.topolev.courses.validator.DataValidator;
import by.topolev.courses.validator.ValidationResult;

public class UploadImageOnFileSystemServiceImpl implements UploadImageService {

	private static final Logger LOG = LoggerFactory.getLogger(UploadImageOnFileSystemServiceImpl.class);

	public ValidationResult uploadImage(HttpServletRequest req, ServletFileUpload upload) {
		LOG.info("Executing doPOST() for upload image");
		createUploadDirIfNotExist();
		ValidationResult result = new ValidationResult();

		boolean isMultipart = ServletFileUpload.isMultipartContent(req);

		if (!isMultipart) {
			LOG.info("Form isn't exsited multipart data");
			result.setErrorMessage("Form isn't exsited multipart data");
			return result;
		}

		List<FileItem> items = null;
		try {
			items = upload.parseRequest(req);

			String userFileName = getValueField("file_name", items);
			FileItem uploadImage = getUploadImage(items);

			validateEnteredFilename(result, userFileName);
			validateUploadFilename(result, uploadImage);

			if (result.isValid()) {
				saveImageInFile(result, userFileName, uploadImage);
			}

		} catch (FileUploadException e) {
			result.setErrorMessage("Problems with parsing request to FileItem");
			LOG.info("Problems with parsing request to FileItem", e);
		}

		return result;
	}
	
	private void createUploadDirIfNotExist() {
		File file = new File(getValue(PATH_UPLOAD_IMAGE));
		if (!file.exists()) {
			file.mkdir();
			LOG.debug("Create folder for uploading images: {}", file.getAbsolutePath());
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

	private FileItem getUploadImage(List<FileItem> items) {
		for (FileItem item : items) {
			if (!item.isFormField()) {
				return item;
			}
		}
		return null;
	}

	private void validateUploadFilename(ValidationResult errors, FileItem uploadImage) {
		validateData(uploadImage.getName(), errors, "emptyString", UPLOAD_FILENAME_EXTANSION_VALIDATOR);
	}

	private void validateEnteredFilename(ValidationResult errors, String userFileName) {
		validateData(userFileName, errors, ENTERED_FILENAME_EXTANSION_VALIDATOR, "uniqueImageFileName");
	}

	private void validateData(String valueData, ValidationResult result, String... listValidators) {
		List<String> typeValidators = Arrays.asList(listValidators);
		Data data = new Data(valueData, typeValidators);
		DataValidator.validField(data, result);
	}

	private void saveImageInFile(ValidationResult errors, String userFileName, FileItem uploadImage) {
		File file = new File(getValue(PATH_UPLOAD_IMAGE) + userFileName);
		try {
			uploadImage.write(file);
			LOG.debug(String.format("Image upload is success, file path = %s", file.getAbsolutePath()));
		} catch (Exception e) {
			errors.setErrorMessage("Problem with upload image");
			LOG.debug("Problem with upload image", e);
		}
	}
}
