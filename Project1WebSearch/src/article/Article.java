package article;

import java.io.Serializable;

public class Article implements Serializable {

	public String fileName;
	public String title;
	public String content;

	public String toString() {
		return fileName + ", " + title + ", " + content;
	}
}
