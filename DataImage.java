import java.util.ArrayList;

public class DataImage {
	
	private String title;
	private String path;
	private ArrayList<ScannedImage> possibleMatches;
	
	
	
	
	public DataImage(String title, String path) {
		this.title = title;
		this.path = path;
		this.possibleMatches = new ArrayList<ScannedImage>();
	}
	public ArrayList<ScannedImage> getPossibleMatches() {
		return this.possibleMatches;		
	}
	public void addPossibleMatches(ScannedImage image) {
		this.possibleMatches.add(image);
	}
	public String getTitle() {
		return this.title;
	}
	public String getPath() {
		return this.path;
	}

}
