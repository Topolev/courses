package by.topolev.courses.convertors;

public interface Convertor<SOURCE, TARGET> {
	public void convert(SOURCE source, TARGET target);
	public TARGET convert(SOURCE source);
}
