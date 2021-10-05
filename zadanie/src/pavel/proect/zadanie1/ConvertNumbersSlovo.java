package pavel.proect.zadanie1;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Properties;
import java.util.Map.Entry;

public class ConvertNumbersSlovo{
	private final String PATH_NUMBER = "DataNumbers/Numbers.properties";
	private final String PATH_DEGREE = "DataNumbers/DegreeOfNumber.properties";
	
	private boolean FLAG = false;
	
	private HashMap<String, String> number;
	private HashMap<String, String> degreeOfNumber;
	
	private final String[][] endings = {{"а", "и", ""}, {"", "а", "ов"}};
	
	private HashMap<String, String> parsPropertiesForMap(String PATH) throws IOException{
		
		HashMap<String, String> map = new HashMap<String, String>();

		try(FileInputStream fileInputStream = new FileInputStream(PATH)){
			Properties properties = new Properties();
		    properties.load(fileInputStream);

			for (Entry<Object, Object> entry : properties.entrySet()) {
			    map.put((String) entry.getKey(), (String) entry.getValue());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	public String converter(BigInteger numb) throws IOException {
		
		if(!FLAG) {
			number = parsPropertiesForMap(PATH_NUMBER);
			degreeOfNumber = parsPropertiesForMap(PATH_DEGREE);
			FLAG = true;
		}
		
		String numbStr = numb.toString();
		String word = "";
		
		if(numbStr.substring(0, 1).equals("-")) {
			word = "минус ";
			numbStr = numbStr.substring(1);
		}
		
		Integer k = 0, degree = 0;
		if(numbStr.length()%3 == 0) {
			k = 3;
			degree = numbStr.length()/3 - 1;
		}
		else{
			k = numbStr.length()%3;
			degree = numbStr.length()/3;
		}
		
		if(degree > 20) {
			throw new NullPointerException();
		}
		
		try {
			int i = 0, j = 0;
			while(i*3+k-3 != numbStr.length()) {
				String degre = degree.toString();
				word += (convertNumber(numbStr.substring(((i-1)*3+k)*j, i*3+k), degre));
	
				j = 1; 
				i++; degree--;
			}
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		
		return word.trim();
	}

	private String convertNumber(String numb, String degree) {
	
		if(numb.length() == 3){
			if(numb.substring(0, 2).equals("00")) numb = numb.substring(2);
			if(numb.substring(0, 1).equals("0")) numb = numb.substring(1);
		}
		
		String word = "";
		int sum = numb.length();
		switch(sum) {
			case 3:
			
				if(numb.substring(1, 3).equals("00")) {
					word += number.get(numb.substring(0, 1)+"00") + " "
							+ convertDegree(degree, "0");
					break;
				}
	
				if(numb.substring(1, 2).equals("0")) {
					word += number.get(numb.substring(0, 1)+"00") + " " 
							+ ((degree.equals("1") && (numb.substring(2, 3).equals("1")||numb.substring(2, 3).equals("2"))) ? number.get(numb.substring(2, 3)+"-"):number.get(numb.substring(2, 3))) + " " 
							+ convertDegree(degree, numb.substring(2, 3));
					break;
				}
				word += number.get(numb.substring(0, 1)+"00") + " " 
				        + convertTwoDigitNumb(numb.substring(1, 3), degree); 
				break;
			case 2:
				word += convertTwoDigitNumb(numb, degree);
				break;
			case 1:
				word += ((degree.equals("1") && (numb.substring(0).equals("1")||numb.substring(0).equals("2"))) ? number.get(numb.substring(0)+"-"):number.get(numb.substring(0))) + " "
						+ convertDegree(degree, numb.substring(0));
				break;
		}
		return word + " ";
	}
	
	private String convertTwoDigitNumb(String numb, String degree) {
		String word = "";
		Integer num = Integer.valueOf(numb);
		if(num <= 19 && num >= 10) {
			word += number.get(numb) + " "
					+ convertDegree(degree, "0");
		}
		else {
			if(numb.substring(1, 2).equals("0")) {
				word += number.get(numb.substring(0, 1) + "0") + " "
						+ convertDegree(degree, "0");
			}
			else {
			word += number.get(numb.substring(0, 1) + "0") + " " 
			        + ((degree.equals("1") && (numb.substring(1, 2).equals("1")||numb.substring(1, 2).equals("2"))) ? number.get(numb.substring(1, 2)+"-"):number.get(numb.substring(1, 2))) + " "
					+ convertDegree(degree, numb.substring(1, 2));
			}
		}
		return word;
	}
	
	private String convertDegree(String degree, String numb) {
		if(degree.equals("0")) return "";
		
		int i = 0;
		if(degree.equals("1"))	i = 0;
		else i = 1;
		
		
		return degreeOfNumber.get(degree)
				+ endings[Integer.valueOf(i)][convertEnding(numb)];
	}
	
	private Integer convertEnding(String numb) {
		
		Integer end = Integer.valueOf(numb);
		if(end == 1) return 0;
		if(end >= 2 && end <= 4) return 1;
		else return 2;
	}
}
