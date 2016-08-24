package by.topolev.courses.convertors;

public interface Convertor<FROM, TO> {
	public void convert(FROM from, TO to);
}
