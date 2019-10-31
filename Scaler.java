import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Scaler {
	private String path;

	public Scaler(String path) {
		this.path = path;

	}

	ScannedImageCollection processImages() {

		File folderPath = new File(this.path);

		File[] fileList = folderPath.listFiles();

		System.out.println("\n-----\nScaling all images - Reducing to gif colours\n-----\n");

		Arrays.asList(fileList).forEach(x -> {
			System.out.println(x.getPath());

			System.out.println(
					"3rdbinaries\\ffmpeg.exe -i \"" + x.getPath() + "\" -vf scale=200:200 \"" + x.getPath() + ".gif\"");
			try {
				Runtime.getRuntime().exec("3rdbinaries\\ffmpeg.exe -i \"" + x.getPath() + "\" -vf scale=200:200 \""
						+ x.getPath() + ".gif\"").waitFor();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}

		});
		
		System.out.println("\n-----\nDeleting all PNGs\n-----\n");
		
		Arrays.asList(fileList).forEach(x -> {
			x.delete();			
		});
		
		System.out.println("\n-----\nEnlisting new files\n-----\n");
		
		File[] newFileList = folderPath.listFiles();
		ScannedImageCollection collection = new ScannedImageCollection();
		
		for(File x : newFileList) {
			collection.addToScannedImageCollection(new ScannedImage(x.getName(),x.getPath()));
		}
		
//			for(int i=0;i<collection.getSize();i++) {
//			collection.addToScannedImageCollection(new ScannedImage(newFileList[i].getName(),this.path));
//			}
		
		return collection;

	}

}
