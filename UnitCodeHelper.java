import java.util.ArrayList;
import java.util.Scanner;

public class UnitCodeHelper {

	private String unitCode;
	private ArrayList<Object> splitUnitCode;
	private String department;
	private int levelofstudy;
	private String programme;

	public UnitCodeHelper() {
		this.unitCode = "GenericUnitCode";
		this.splitUnitCode = new ArrayList<Object>();
		this.department = "GenericDepartment";
		this.levelofstudy = 0;
		this.programme = "GenericProgramme";
	}

	public UnitCodeHelper(String unitCode) {
		this.unitCode = unitCode;
		this.splitUnitCode = splitCode(unitCode);
		this.department = generateDepartment();
		this.levelofstudy = generateLevelOfStudy();
		this.programme = generateProgramme();
		// programme is second because we can derive it from the level of study
	}

	private ArrayList<Object> splitCode(String originalCode) {

		ArrayList<Object> temp = new ArrayList<Object>();

		int numbers = new Scanner(originalCode).useDelimiter("\\D+").nextInt();

		int firstDigit = Integer.parseInt(Integer.toString(numbers).substring(0, 1));

		temp.add(0, firstDigit);

		temp.add(1, originalCode.replace(Integer.toString(numbers), ""));

		return temp;
	}

	private int generateLevelOfStudy() {
		return (int) this.splitUnitCode.get(0);
	}

	private String generateProgramme() {
		if ((int) this.splitUnitCode.get(0) > 3) {
			return "Master";
		} else if ((int) this.splitUnitCode.get(0) > 0) {
			return "Bachelor";
		} else {
			return "Special Programme";
		}
	}

	private String generateDepartment() {
		if (this.splitUnitCode.get(1).equals("CCP")) {
			return "Computer Science";
		} else if (this.splitUnitCode.get(1).equals("CBE")) {
			return "Business Administration and Economics";
		} else if (this.splitUnitCode.get(1).equals("CPY")) {
			return "Psychology";
		} else if (this.splitUnitCode.get(1).equals("CES")) {
			return "English Studies Department";
		} else
			return "Unknown Department";
	}

	public String getDepartment() {
		return department;
	}

	public int getLevelofstudy() {
		return levelofstudy;
	}

	public String getProgramme() {
		return programme;
	}

	public String getUnitCode() {
		return unitCode;
	}

}
