import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Program {
	public static void main(String[] args) {
		if (args.length == 1) {
			System.out.println("'" + args[0].toString() + "' was the path you provided!");
			Document a = new Document(args[0].toString());
			try {
				a.getImages();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Scaler scal = new Scaler(a.getOutputFolder());
			ScannedImageCollection collection = scal.processImages();

			Comparator comp = new Comparator();
			// for all the images in collection do compare
			for (int i = 0; i < collection.getSize(); i++) {
				try {
					comp.compare(collection.getImage(i), a);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			a.showUnitInfo();
			a.showBadges();
			a.deleteLeftovers(); // get rid of image files that are not needed anymore

		} else {
			JFileChooser jfc = new JFileChooser(".");
			jfc.setDialogTitle("Select the PDF files of the Syllabi:");
			jfc.setMultiSelectionEnabled(true);
			jfc.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Syllabi PDF Documents",
					new String[] { "pdf" });
			jfc.addChoosableFileFilter(filter);

			int returnValue = jfc.showOpenDialog(null);
			if (returnValue == 0) {

				File[] files = jfc.getSelectedFiles();

				// for every document do...
				Arrays.asList(files).forEach(x -> {

					if (x.isFile()) {

						System.out.println("----------------");
						System.out.println();
						System.out.println("A file you provided is:");
						System.out.println(x.getPath());

						Document a = new Document(x.getPath());
						try {
							a.getImages();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						Scaler scal = new Scaler(a.getOutputFolder());
						ScannedImageCollection collection = scal.processImages();

						Comparator comp = new Comparator();
						// for all the images in collection do compare
						for (int i = 0; i < collection.getSize(); i++) {
							try {
								comp.compare(collection.getImage(i), a);
								//System.out.println("---------------------------------");
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						a.showUnitInfo();
						a.showBadges();
						a.deleteLeftovers(); // get rid of image files that are not needed anymore
					}

				});
			}

		}

		// fin
		System.exit(0);
	}
}
