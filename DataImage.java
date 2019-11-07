

public class DataImage {
	
	private String title;
	private String path;
	
	
	
	
	public DataImage(String title, String path) {
		this.title = title;
		this.path = path;
	}
	public String getTitle() {
		return this.title;
	}
	public String getPath() {
		return this.path;
	}
	
	public String toString() {
		return "["+title+"]";
	}
	
	

}
