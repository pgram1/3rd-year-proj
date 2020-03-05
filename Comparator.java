import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Comparator {

	DataImage image1 = new DataImage("Knowledgeable", "badges\\1-1_Knowledgeable.png.gif",
			"badges\\1-1_Knowledgeable.pngW.gif");
	DataImage image2 = new DataImage("Experienced Collaborator", "badges\\1-2_Experienced_Collaborator.png.gif",
			"badges\\1-2_Experienced_Collaborator.pngW.gif");
	DataImage image3 = new DataImage("Confident in Authentic Challenges", "badges\\1-3_Conf_In_Auth_Challenges.png.gif",
			"badges\\1-3_Conf_In_Auth_Challenges.pngW.gif");
	DataImage image4 = new DataImage("Ethical", "badges\\1-4_Ethical.png.gif", "badges\\1-4_Ethical.pngW.gif");
	DataImage image5 = new DataImage("Culturally Agile", "badges\\2-1_Culturally_Agile.png.gif",
			"badges\\2-1_Culturally_Agile.pngW.gif");
	DataImage image6 = new DataImage("Active Citizen Respecting Diversity",
			"badges\\2-2_Act_Cit_Respecting_Diversity.png.gif", "badges\\2-2_Act_Cit_Respecting_Diversity.pngW.gif");
	DataImage image7 = new DataImage("Working Outside & Across Disciplines",
			"badges\\2-3_Work_Out_And_Across_Disciplines.png.gif",
			"badges\\2-3_Work_Out_And_Across_Disciplines.pngW.gif");
	DataImage image8 = new DataImage("Adaptable Problem Solver", "badges\\2-4_Adaptable_Problem_Solver.png.gif",
			"badges\\2-4_Adaptable_Problem_Solver.pngW.gif");
	DataImage image9 = new DataImage("Experienced in Research", "badges\\3-1_Experienced_In_Research.png.gif",
			"badges\\3-1_Experienced_In_Research.pngW.gif");
	DataImage image10 = new DataImage("Critical Thinker", "badges\\3-2_Critical_Thinker.png.gif",
			"badges\\3-2_Critical_Thinker.pngW.gif");
	DataImage image11 = new DataImage("Creative & Innovative", "badges\\3-3_Creative_and_Innovative.png.gif",
			"badges\\3-3_Creative_and_Innovative.pngW.gif");
	DataImage image12 = new DataImage("Information & Digitally Literate", "badges\\3-4_Info_and_Dig_Literate.png.gif",
			"badges\\3-4_Info_and_Dig_Literate.pngW.gif");
	DataImage image13 = new DataImage("Lifelong Learner", "badges\\4-1_Lifelong_Learner.png.gif",
			"badges\\4-1_Lifelong_Learner.pngW.gif");
	DataImage image14 = new DataImage("Team Player & Time Manager", "badges\\4-2_Team_Player_And_Time_Manager.png.gif",
			"badges\\4-2_Team_Player_And_Time_Manager.pngW.gif");
	DataImage image15 = new DataImage("Skilled Communicator", "badges\\4-3_Skilled_Communicator.png.gif",
			"badges\\4-3_Skilled_Communicator.pngW.gif");
	DataImage image16 = new DataImage("Reflective & Self Aware", "badges\\4-4_Reflective_And_Self_Aware.png.gif",
			"badges\\4-4_Reflective_And_Self_Aware.pngW.gif");
	DataImage image17 = new DataImage("Professional & Adaptable", "badges\\4-5_Professional_And_Adaptable.png.gif",
			"badges\\4-5_Professional_And_Adaptable.pngW.gif");

	DataImage[] baseimages = { image1, image2, image3, image4, image5, image6, image7, image8, image9, image10, image11,
			image12, image13, image14, image15, image16, image17 };

	public DataImage[] getBaseimages() {
		return baseimages;
	}

	public void compare(ScannedImage x, Document a) throws IOException {
		// all comparisons
		int compar[] = new int[17];
		//System.out.println("Percentages for " + x.getTitle());
		for (int i = 0; i < 17; i++) {
			compar[i] = getDifferencePercent(x, baseimages[i]);
			//System.out.println(compar[i]);
		}
		// wanted percentage of difference
		int percentage = 25;
		// comparison checks based on wanted percentage of difference
		for (int i = 0; i < 17; i++) {
			if (percentage > compar[i]) {
				a.addToBadges(baseimages[i]);
				//System.out.println(baseimages[i].getTitle());
			}
		}

	}

	private int getDifferencePercent(ScannedImage file1, DataImage file2) throws IOException {

		BufferedImage img1 = ImageIO.read(new File(file1.getPath()));
		BufferedImage img2 = ImageIO.read(new File(file2.getPath()));
		BufferedImage img3 = ImageIO.read(new File(file2.getAltPath()));

		int width = img1.getWidth();
		int height = img1.getHeight();

		long diff = 0;
		long altDiff = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				diff += pixelDiff(img1.getRGB(x, y), img2.getRGB(x, y));
				altDiff += pixelDiff(img1.getRGB(x, y), img3.getRGB(x, y));
			}
		}
		// long maxDiff = 3L * 255 * width * height;
		long maxDiff = 255 * width * height;

		// return the smallest difference because that's what we want for a match
		return (int) (100.0 * Math.min(diff, altDiff) / maxDiff);

	}

	private int pixelDiff(int rgb1, int rgb2) {
		int r1 = (rgb1 >> 16) & 0xff;
		int g1 = (rgb1 >> 8) & 0xff;
		int b1 = rgb1 & 0xff;
		int r2 = (rgb2 >> 16) & 0xff;
		int g2 = (rgb2 >> 8) & 0xff;
		int b2 = rgb2 & 0xff;
		return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
	}

}
