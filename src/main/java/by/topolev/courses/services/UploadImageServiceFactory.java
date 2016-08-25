package by.topolev.courses.services;

public class UploadImageServiceFactory {
	public static UploadImageService getInstance(){
		return new UploadImageOnFileSystemServiceImpl();
	}
}
