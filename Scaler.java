import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Scaler {
	private String path;

	public Scaler(String path) {
		this.path = path;

	}

	void scaleImages() {

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
		
		System.out.println("\n-----\nDeleting all PNGs\n-----\n");
		
		Arrays.asList(fileList).forEach(x -> {
			x.delete();
			
		});

	}

}
