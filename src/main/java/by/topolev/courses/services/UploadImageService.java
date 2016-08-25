package by.topolev.courses.services;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import by.topolev.courses.validator.ValidationResult;

public interface UploadImageService {
	ValidationResult uploadImage(HttpServletRequest req, ServletFileUpload upload);
}