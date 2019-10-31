import java.util.ArrayList;

public class ScannedImageCollection {
	
	private ArrayList<ScannedImage> images;
	
	

	public ScannedImageCollection() {
		this.images = new ArrayList<ScannedImage>();
	}

	public void addToScannedImageCollection(ScannedImage x) {
		this.images.add(x);
	}	
	
	public int getSize() {
		return images.size();
	}
	
	public ScannedImage getImage(int x) {
		return images.get(x);
	}
	

}