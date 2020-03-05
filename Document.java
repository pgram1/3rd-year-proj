import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Document {
	private String path;
	private String outputFolder;
	private String outputFile;
	private ArrayList<DataImage> badges;

	private String unitTitle;
	private UnitCodeHelper unitCodeHelper;
	private String department;
	private int levelofstudy;
	private String programme;
	private String semester;

	public Document(String path) {
		this.path = path;

		this.outputFolder = (String.valueOf(path) + "-exports").replace(".", "-");

		this.outputFile = String.valueOf(this.outputFolder) + "\\exported";

		this.badges = new ArrayList<DataImage>();

		this.unitTitle = "GenericUnitTitle";
		this.unitCodeHelper = new UnitCodeHelper();
		this.department = "GenericDepartment";
		this.levelofstudy = 0;
		this.programme = "GenericProgramme";
		this.semester = "GenericSemester";

	}

	void getImages() throws IOException, InterruptedException {
		System.out.println("\n-----\nCreating output directory\n-----\n");
		System.out.println((this.outputFolder).toString());
		new File(this.outputFolder).mkdirs();

		System.out.println("\n-----\nGetting document info\n-----\n");
		getDocInfo();

		System.out.println("\n-----\nExporting all images as PNG\n-----\n");
		System.out.println("pdfimages.exe \"" + this.path + "\" \"" + this.outputFile + "\"");
		Runtime.getRuntime().exec("3rdbinaries\\pdfimages.exe \"" + this.path + "\" \"" + this.outputFile + "\"")
				.waitFor();

	}

	private void getDocInfo() throws InterruptedException, IOException {
		Runtime.getRuntime().exec("3rdbinaries\\pdftotext.exe -q -l 1 -raw -table -lineprinter -linespacing 1 \""
				+ this.path + "\" \"" + this.outputFile + ".txt\"").waitFor();

		String content = readFile(this.outputFile + ".txt", StandardCharsets.UTF_8);

		if (content.length() == 1)
			return;

		this.unitTitle = between(content, "UNIT TITLE", "CREDITS");
		this.unitCodeHelper = new UnitCodeHelper(between(content, "UNIT CODE", "UNIT TITLE"));

		// we use the information derived from the unit code to achieve data uniformity
		this.department = this.unitCodeHelper.getDepartment();
		this.levelofstudy = this.unitCodeHelper.getLevelofstudy();
		this.programme = this.unitCodeHelper.getProgramme();
		this.semester = between(content, "SEMESTER/SESSION", "LEVEL OF STUDY");

	}

	private String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	private String between(String value, String a, String b) {
		// Return a substring between the two strings.
		int posA = value.indexOf(a);
		if (posA == -1) {
			return "";
		}
		int posB = value.lastIndexOf(b);
		if (posB == -1) {
			return "";
		}
		int adjustedPosA = posA + a.length();
		if (adjustedPosA >= posB) {
			return "";
		}
		return value.substring(adjustedPosA, posB).trim().replaceAll("\\s{2,}", " ");
	}

	public String getOutputFolder() {
		return outputFolder;
	}

	public void addToBadges(DataImage x) {
		this.badges.add(x);
	}

	public void showBadges() {
		System.out.println("Totally " + getNumberOfBadges() + " badges in this document: " + this.badges.toString());
	}

	public void showUnitInfo() {
		System.out.println("\n-----\nUnit Information\n-----\n");
		System.out.println("Unit Title: " + this.unitTitle);
		System.out.println("Unit Code: " + this.unitCodeHelper.getUnitCode());
		System.out.println("Department: " + this.department);
		System.out.println("Level of Study: " + this.levelofstudy);
		System.out.println("Programme: " + this.programme);
		System.out.println("Semester/Session: " + this.semester);
		System.out.println();
	}

	public int getNumberOfBadges() {
		return this.badges.size();
	}

	public void deleteLeftovers() {

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
