
public class DataImage {

	private String title;
	private String path;
	private String altPath;

	public DataImage(String title, String path, String altPath) {
		this.title = title;
		this.path = path;
		this.altPath = altPath;
	}

	public String getTitle() {
		return this.title;
	}

	public String getPath() {
		return this.path;
	}

	public String getAltPath() {
		return altPath;
	}

	public String toString() {
		return "[" + title + "]";
	}

}
