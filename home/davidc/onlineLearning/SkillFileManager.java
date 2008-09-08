package onlineLearning;

import java.io.BufferedReader;

import onlineLearning.utils.DataLogger;
import onlineLearning.utils.FileLoader;

public class SkillFileManager {
	public static void initLogFiles(String moduleType, String robotName, String dirID) {
		DataLogger.init("Online Learning//"+moduleType+" Experiments//"+robotName+"-"+dirID);
		DataLogger.addFile("rewards.txt");
		DataLogger.addFile("Q-Values.txt");
		DataLogger.addFile("Role-Rewards.txt");
		DataLogger.addFile("BestRole-Rewards.txt");
	}
	public static void loadSkills() {
		BufferedReader br = FileLoader.loadFile();
		if(br!=null) {
			float[][] skills = FileLoader.convertToFloatArray(br);
			SkillLearner.setSkillsData(skills); 
		}
	}
}
