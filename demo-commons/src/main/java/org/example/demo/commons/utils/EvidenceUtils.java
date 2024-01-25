package org.example.demo.commons.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Joword
 * @date: 2023/9/12 15:46
 * @version: 0.1.0
 * @description: assemble evidence utils 
 */
@Component
public class EvidenceUtils {
	private static Map<String, String> classificationMap = null;
	public static Map<String, String> inheritanceMap = null;
	static {
		inheritanceMap=new HashMap<String, String>();
		inheritanceMap.put("autosomal recessive", "AR");
		inheritanceMap.put("autosomal dominant", "AD");
		inheritanceMap.put("AR", "autosomal recessive");
		inheritanceMap.put("AD", "autosomal dominant");
	}
    
    public static String toStrength(String arg){
        if (arg.split("_").length>1){
            switch (arg.split("_")[1]){
                case "P":
                case "Supporting":
                    return "Supporting";
                case "M":
                case "Moderate":
                    return "Moderate";
                case "S":
                case "Strong":
                    return "Strong";
                default:
                    return "Unmet";
            }
        } else {
            switch (arg.substring(0,2)){
                case "PV":
                case "BA":
                    return "VeryStrong";
                case "PS":
                case "BS":
                    return "Strong";
                case "PM":
                    return "Moderate";
                case "PP":
                case "BP":
                    return "Supporting";
                default:
                    return "Unmet";
            }
        }
    }
    
    public static List<String> toPopulationCriteria(){
        List<String> lists = new ArrayList<>();
        lists.add("PM2");
        lists.add("BS1");
        lists.add("BS2");
        lists.add("BA1");
        return lists;
    }
    
    public static List<String> toComputation(){
        List<String> lists = new ArrayList<>();
        lists.add("PVS1");
        lists.add("PM1");
        lists.add("PP3");
        lists.add("BP4");
        lists.add("PM4");
        lists.add("BP3");
        lists.add("BP7");
        lists.add("PS1");
        lists.add("PM5");
        return lists;
    }
    
    public static List<String> toSemiAutoCriteria(){
        List<String> lists = new ArrayList<>();
        lists.add("PM3");
        lists.add("BP2");
        lists.add("PS2");
        lists.add("PM6");
        lists.add("PP1");
        lists.add("BS4");
        lists.add("PP4");
        lists.add("BP5");
        lists.add("PS4");
        lists.add("PS3");
        lists.add("BS3");
        return lists;
    }
    
    public static String normalizeClassification(String classification) {
    	if (classification==null) {
			return null;
		}
    	if (classificationMap==null) {
    		classificationMap=new HashMap<>();
    		classificationMap.put("P", "Pathogenic");
    		classificationMap.put("LP", "Likely_Pathogenic");
    		classificationMap.put("VUS", "Uncertain_Significance");
    		classificationMap.put("LB", "Likely_Benign");
    		classificationMap.put("B", "Benign");
		}
    	return classificationMap.getOrDefault(classification, classification);
    	
    }


	public static String getInheritanceAlia(String string) {
		return inheritanceMap.getOrDefault(string, string);
	}

}
