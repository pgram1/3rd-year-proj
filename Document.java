import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Document {
	private String path;
	private String outputFolder;
	private String outputFile;
	private ArrayList<DataImage> badges;

	public Document(String path) {
		this.path = path;

		this.outputFolder = (String.valueOf(path) + "-exports").replace(".", "-");

		this.outputFile = String.valueOf(this.outputFolder) + "\\exported";
		
		this.badges = new ArrayList<DataImage>();

	}

	void getImages() throws IOException, InterruptedException {
		System.out.println("\n-----\nCreating output directory\n-----\n");
		System.out.println((this.outputFolder).toString());
		new File(this.outputFolder).mkdirs();

		System.out.println("\n-----\nExporting all images as PNG\n-----\n");
		System.out.println("pdfimages.exe \"" + this.path + "\" \"" + this.outputFile + "\"");
		Runtime.getRuntime().exec("3rdbinaries\\pdfimages.exe \"" + this.path + "\" \"" + this.outputFile + "\"").waitFor();

	}

	public String getOutputFolder() {
		return outputFolder;
	}
	
	public void addToBadges(DataImage x) {
		this.badges.add(x);
	}
	
	public void showBadges() {
		System.out.println("Badges totally "+getNumberOfBadges()+" badges in this document: "+this.badges.toString());
	}
	
	public int getNumberOfBadges() {
		return this.badges.size();
	}
	
	public void deleteLeftovers(){
		
		File directory = new File(this.outputFolder);
		
		deleteDirectory(directory);
        
	}
	

    private boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
