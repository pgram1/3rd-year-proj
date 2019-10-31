import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Comparator {

	DataImage aImage = new DataImage("Knowledgeable", "badges\\1-1_Knowledgeable.png.gif");
	DataImage bImage = new DataImage("Experienced Collaborator", "badges\\1-2_Experienced_Collaborator.png.gif");
	DataImage cImage = new DataImage("Confident in Authentic Challenges", "badges\\1-3_Conf_In_Auth_Challenges.png.gif");
	DataImage dImage = new DataImage("Ethical", "badges\\1-4_Ethical.png.gif");
	DataImage eImage = new DataImage("Culturally Agile", "badges\\2-1_Culturally_Agile.png.gif");
	DataImage fImage = new DataImage("Active Citizen Respecting Diversity",
			"badges\\2-2_Act_Cit_Respecting_Diversity.png.gif");
	DataImage gImage = new DataImage("Working Outside & Across Disciplines",
			"badges\\2-3_Work_Out_And_Across_Disciplines.png.gif");
	DataImage hImage = new DataImage("Adaptable Problem Solver", "badges\\2-4_Adaptable_Problem_Solver.png.gif");
	DataImage iImage = new DataImage("Experienced in Research", "badges\\3-1_Experienced_In_Research.png.gif");
	DataImage jImage = new DataImage("Critical Thinker", "badges\\3-2_Critical_Thinker.png.gif");
	DataImage kImage = new DataImage("Creative & Innovative", "badges\\3-3_Creative_and_Innovative.png.gif");
	DataImage lImage = new DataImage("Information & Digitally Literate", "badges\\3-4_Info_and_Dig_Literate.png.gif");
	DataImage mImage = new DataImage("Lifelong Learner", "badges\\4-1_Lifelong_Learner.png.gif");
	DataImage nImage = new DataImage("Team Player & Time Manager", "badges\\4-2_Team_Player_And_Time_Manager.png.gif");
	DataImage oImage = new DataImage("Skilled Communicator", "badges\\4-3_Skilled_Communicator.png.gif");
	DataImage pImage = new DataImage("Reflective & Self Aware", "badges\\4-4_Reflective_And_Self_Aware.png.gif");
	DataImage qImage = new DataImage("Professional & Adaptable", "badges\\4-5_Professional_And_Adaptable.png.gif");

	DataImage[] baseimages = { aImage, bImage, cImage, dImage, eImage, fImage, gImage, hImage, iImage, jImage, kImage,
			lImage, mImage, nImage, oImage, pImage, qImage };
	
	public DataImage[] getBaseimages() {
		return baseimages;
	}
	
	
	
	
	public void compare(ScannedImage x) throws IOException {
		//all comparisons
		int compar[]=new int[17];
		System.out.println("Percentages for "+x.getTitle());
		for(int i=0;i<17;i++) {
			compar[i]=getDifferencePercent(x,baseimages[i]);
			System.out.println(compar[i]);
		}
		// wanted percentage of difference
		int percentage = 10;		
		//comparison checks	based on wanted percentage of difference	
		for(int i=0;i<17;i++) {
			if(percentage>compar[i]) {
				baseimages[i].addPossibleMatches(x);
				System.out.println(baseimages[i].getTitle());
			}
		}
		
	}
	private int getDifferencePercent(ScannedImage file1, DataImage file2) throws IOException {

		BufferedImage img1 = ImageIO.read(new File(file1.getPath()));
		BufferedImage img2 = ImageIO.read(new File(file2.getPath()));

		int width = img1.getWidth();
		int height = img1.getHeight();

		long diff = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				diff += pixelDiff(img1.getRGB(x, y), img2.getRGB(x, y));
			}
		}
		// long maxDiff = 3L * 255 * width * height;
		long maxDiff = 255 * width * height;

		return (int) (100.0 * diff / maxDiff);

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
