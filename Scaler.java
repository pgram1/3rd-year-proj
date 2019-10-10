import java.io.File;
import java.util.Arrays;

public class Scaler {
	private String path;
	private String outputFile;

	public Scaler(String path) {
		this.path = path;

	}

	void scaleImages(){

		
		File folderPath = new File(this.path);

		File[] fileList = folderPath.listFiles();


        Arrays.asList(fileList).forEach(x -> {
                System.out.println(x.getPath());
                
                
                
            });

		/*System.out.println("\n-----\nScaling all images - Reducing to gif colours\n-----\n");

		System.out.println("ffmpeg.exe -i \"" + this.outputFile + "\" -vf scale=200:200 \"" + this.scaledFile + "\"");
		Runtime.getRuntime().exec(
				"3rdbinaries\\ffmpeg.exe -i \"" + this.outputFile + "\" -vf scale=200:200 \"" + this.scaledFile + "\"");
				
		*/
	}

}
