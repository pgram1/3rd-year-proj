import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Comparator {

	DataImage image1 = new DataImage("Knowledgeable", "badges\\1-1_Knowledgeable.png.gif");
	DataImage image2 = new DataImage("Experienced Collaborator", "badges\\1-2_Experienced_Collaborator.png.gif");
	DataImage image3 = new DataImage("Confident in Authentic Challenges", "badges\\1-3_Conf_In_Auth_Challenges.png.gif");
	DataImage image4 = new DataImage("Ethical", "badges\\1-4_Ethical.png.gif");
	DataImage image5 = new DataImage("Culturally Agile", "badges\\2-1_Culturally_Agile.png.gif");
	DataImage image6 = new DataImage("Active Citizen Respecting Diversity",
			"badges\\2-2_Act_Cit_Respecting_Diversity.png.gif");
	DataImage image7 = new DataImage("Working Outside & Across Disciplines",
			"badges\\2-3_Work_Out_And_Across_Disciplines.png.gif");
	DataImage image8 = new DataImage("Adaptable Problem Solver", "badges\\2-4_Adaptable_Problem_Solver.png.gif");
	DataImage image9 = new DataImage("Experienced in Research", "badges\\3-1_Experienced_In_Research.png.gif");
	DataImage image10 = new DataImage("Critical Thinker", "badges\\3-2_Critical_Thinker.png.gif");
	DataImage image11 = new DataImage("Creative & Innovative", "badges\\3-3_Creative_and_Innovative.png.gif");
	DataImage image12 = new DataImage("Information & Digitally Literate", "badges\\3-4_Info_and_Dig_Literate.png.gif");
	DataImage image13 = new DataImage("Lifelong Learner", "badges\\4-1_Lifelong_Learner.png.gif");
	DataImage image14 = new DataImage("Team Player & Time Manager", "badges\\4-2_Team_Player_And_Time_Manager.png.gif");
	DataImage image15 = new DataImage("Skilled Communicator", "badges\\4-3_Skilled_Communicator.png.gif");
	DataImage image16 = new DataImage("Reflective & Self Aware", "badges\\4-4_Reflective_And_Self_Aware.png.gif");
	DataImage image17 = new DataImage("Professional & Adaptable", "badges\\4-5_Professional_And_Adaptable.png.gif");

	DataImage image18 = new DataImage("Knowledgeable", "badges\\1-1_Knowledgeable.pngW.gif");
	DataImage image19 = new DataImage("Experienced Collaborator", "badges\\1-2_Experienced_Collaborator.pngW.gif");
	DataImage image20 = new DataImage("Confident in Authentic Challenges", "badges\\1-3_Conf_In_Auth_Challenges.pngW.gif");
	DataImage image21 = new DataImage("Ethical", "badges\\1-4_Ethical.pngW.gif");
	DataImage image22 = new DataImage("Culturally Agile", "badges\\2-1_Culturally_Agile.pngW.gif");
	DataImage image23 = new DataImage("Active Citizen Respecting Diversity",
			"badges\\2-2_Act_Cit_Respecting_Diversity.pngW.gif");
	DataImage image24 = new DataImage("Working Outside & Across Disciplines",
			"badges\\2-3_Work_Out_And_Across_Disciplines.pngW.gif");
	DataImage image25 = new DataImage("Adaptable Problem Solver", "badges\\2-4_Adaptable_Problem_Solver.pngW.gif");
	DataImage image26 = new DataImage("Experienced in Research", "badges\\3-1_Experienced_In_Research.pngW.gif");
	DataImage image27 = new DataImage("Critical Thinker", "badges\\3-2_Critical_Thinker.pngW.gif");
	DataImage image28 = new DataImage("Creative & Innovative", "badges\\3-3_Creative_and_Innovative.pngW.gif");
	DataImage image29 = new DataImage("Information & Digitally Literate", "badges\\3-4_Info_and_Dig_Literate.pngW.gif");
	DataImage image30 = new DataImage("Lifelong Learner", "badges\\4-1_Lifelong_Learner.pngW.gif");
	DataImage image31 = new DataImage("Team Player & Time Manager", "badges\\4-2_Team_Player_And_Time_Manager.pngW.gif");
	DataImage image32 = new DataImage("Skilled Communicator", "badges\\4-3_Skilled_Communicator.pngW.gif");
	DataImage image33 = new DataImage("Reflective & Self Aware", "badges\\4-4_Reflective_And_Self_Aware.pngW.gif");
	DataImage image34 = new DataImage("Professional & Adaptable", "badges\\4-5_Professional_And_Adaptable.pngW.gif");

	
	DataImage[] baseimages = { image1,image2,image3,image4,image5,image6,image7,image8,image9,image10,image11,image12,image13,image14,image15,image16,image17,image18,image19,image20,image21,image22,image23,image24,image25,image26,image27,image28,image29,image30,image31,image32,image33,image34 };
	
	public DataImage[] getBaseimages() {
		return baseimages;
	}
	
	
	
	
	public void compare(ScannedImage x, Document a) throws IOException {
		//all comparisons
		int compar[]=new int[34];
		System.out.println("Percentages for "+x.getTitle());
		for(int i=0;i<34;i++) {
			compar[i]=getDifferencePercent(x,baseimages[i]);
			System.out.println(compar[i]);
		}
		// wanted percentage of difference
		int percentage = 25;		
		//comparison checks	based on wanted percentage of difference	
		for(int i=0;i<34;i++) {
			if(percentage>compar[i]) {
				a.addToBadges(baseimages[i]);
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
