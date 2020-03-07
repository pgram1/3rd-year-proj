import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.*;

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
		// System.out.println("\n-----\nCreating output directory\n-----\n");
		// System.out.println((this.outputFolder).toString());
		new File(this.outputFolder).mkdirs();

		// System.out.println("\n-----\nGetting document info\n-----\n");
		getDocInfo();

		// System.out.println("\n-----\nExporting all images as PNG\n-----\n");
		// System.out.println("pdfimages.exe \"" + this.path + "\" \"" + this.outputFile
		// + "\"");
		Runtime.getRuntime().exec("3rdbinaries\\pdfimages.exe \"" + this.path + "\" \"" + this.outputFile + "\"")
				.waitFor();

	}

	private void getDocInfo() throws InterruptedException, IOException {
		Runtime.getRuntime().exec("3rdbinaries\\pdftotext.exe -q -l 1 -raw -table -lineprinter -linespacing 1 \""
				+ this.path + "\" \"" + this.outputFile + ".txt\"").waitFor();

		String content = readFile(this.outputFile + ".txt", StandardCharsets.UTF_8);

		if (content.length() == 1)
			return;

		// using a character that is highly unlikely to be used as a delimiter to
		// replace big empty space
		content = content.trim().replaceAll("\\s{2,}", "█");

		// using our delimiter to find all words of the unit title without knowing the
		// next field, parsing errors occur when the unit title contains more than 1
		// spaces
		this.unitTitle = between(content, "UNIT TITLE█", "█");
		this.unitCodeHelper = new UnitCodeHelper(findUnitCode(content, "UNIT CODE"));

		// we use the information derived from the unit code to achieve data uniformity
		this.department = this.unitCodeHelper.getDepartment();
		this.levelofstudy = this.unitCodeHelper.getLevelofstudy();
		this.programme = this.unitCodeHelper.getProgramme();
		this.semester = findSemester(content, "SEMESTER/SESSION");

	}

	private String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	private String between(String original, String a, String b) {
		Pattern pattern = Pattern.compile(a + "(.*?)" + b, Pattern.DOTALL);
		Matcher matcher = pattern.matcher(original);
		while (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

	private String findSemester(String str, String word) {
		Pattern p = Pattern.compile(word + "\\W+(\\w+)");
		Matcher m = p.matcher(str);
		if (m.find())
			if (m.group(1).equalsIgnoreCase("Fall")) {
				return "Autumn";
			} else {
				return m.group(1);
			}
		else
			return "Whole Session";
	}

	private String findUnitCode(String str, String word) {
		Pattern p = Pattern.compile(word + "\\W+(\\w+)");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return m.group(1);
		} else {
			return null;
		}
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
