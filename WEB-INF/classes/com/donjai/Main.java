package com.donjai;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class Main {
	private final Integer[] good_sum = {4, 5, 6, 9, 14, 15, 19, 23, 24, 32, 36, 40, 41, 42, 44, 45, 46, 50, 51, 54 };
	private final Integer[] midiem_sum = {8, 10, 13, 16, 18, 22, 25, 26, 28, 31, 35, 38, 39, 47, 49, 52, 53};
	private final Integer[] bad_sum = {3, 7, 11, 12, 17, 20, 21, 27, 29, 30, 33, 34, 37, 43, 48};
	private final String[] bad_near_digi = {"13","37","48","67"};
	private static final String[][] char_digi = new String[10][];
	static{
		char_digi[0] = new String[]{""};
		char_digi[1] = new String[]{"ก","ด","ถ","ท","ภ","ฤ"};
		char_digi[2] = new String[]{"ข","บ","ป","ง","ช"};
		char_digi[3] = new String[]{"ต","ฑ","ฒ","ฆ"};
		char_digi[4] = new String[]{"ค","ธ","ร","ญ","ษ"};
		char_digi[5] = new String[]{"ฉ","ณ","ฌ","น","ม","ห","ฮ","ฎ","ฬ"};
		char_digi[6] = new String[]{"จ","ล","ว","อ"};
		char_digi[7] = new String[]{"ซ","ศ","ส"};
		char_digi[8] = new String[]{"ย","ผ","ฝ","พ","ฟ"};
		char_digi[9] = new String[]{"ฏ","ฐ"};
	}
	private List<String> result;
	public Main(HttpServletRequest request,HttpServletResponse response) throws NumberFormatException, UnsupportedEncodingException{
		String prefix = new String(request.getParameter("prefix").getBytes("ISO-8859-1"),"UTF-8");
		String postfix = request.getParameter("postfix");
		int start_num = Integer.parseInt(request.getParameter("start_num"));
		int end_num = Integer.parseInt(request.getParameter("end_num"));
		String good_number = request.getParameter("good_number");
		int level = Integer.parseInt(request.getParameter("level"));
		boolean ignore_prefix = StringUtils.isNumeric(""+request.getParameter("ignore_prefix"));
		result = search(prefix,postfix,start_num,end_num,good_number,level,ignore_prefix);
		
	}
	public List<String> getResult(){
		return result;
	}
	
	private boolean isMatch(String khar,String[] list_char){
		for(String c : list_char){
			if(c.equals(khar)){
				return true;
			}
		}		
		return false;
	}
	
	private int getDigit(String khar,String[][] list_char){				
		for(int i =0;i<list_char.length;i++){
			if(isMatch(khar,list_char[i])){
				return i;
			}
		}		
		return -1;
	}
	
	private int getLevelOfSum(int sum){
		
		/* 
		 * -1 = n/a
		 * 0 = bad
		 * 1 = mediem
		 * 2 = good
		 * */
		
		for(Integer n : good_sum){
			if(sum == n.intValue()){
				return 2;
			}
		}
		for(Integer n : midiem_sum){
			if(sum == n.intValue()){
				return 1;
			}
		}
		for(Integer n : bad_sum){
			if(sum == n.intValue()){
				return 0;
			}
		}
		
		return -1;
	}
	
	private int sum(String number_str){
		
		int sum = 0;
		for(int i =0;i<number_str.length();i++){
			int num = -1;
			try{
				num = Integer.parseInt(""+number_str.charAt(i));
			}catch(NumberFormatException e1){
				
			}
			if(num == -1){
				num = getDigit(""+number_str.charAt(i),char_digi);
			}
			//System.out.println(number_str.charAt(i)+" -> "+num);
			sum += num;
		}
		return sum;
	}
	
	private String getMean(int sum){
		String msg = "N/A";
		switch(getLevelOfSum(sum)){
			case 0:{msg="Bad";};break;
			case 1:{msg="Good";};break;
			case 2:{msg="Very Good";};break;
		}
		return msg;
	}
	
	private boolean nearBadDigit(String license){		
		String[] couple = new String[license.length()-1];
		for(int i =0;i<couple.length;i++){
			String str = "";
			if(StringUtils.isNumeric(""+license.charAt(i))){
				str += ""+license.charAt(i);
			}else{
				str += getDigit(""+license.charAt(i),char_digi);
			}
			if(StringUtils.isNumeric(""+license.charAt(i+1))){
				str += ""+license.charAt(i+1);
			}else{
				str += getDigit(""+license.charAt(i+1),char_digi);
			}
			for(int j =0;j<bad_near_digi.length;j++){
				if(bad_near_digi[j].equals(str)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean allInGood(String license,Integer[] good_number){
		int total_good=0;
		for(int i=0;i<license.length();i++){
			int num = 0;
			if(StringUtils.isNumeric(""+license.charAt(i))){
				num = Integer.parseInt(""+license.charAt(i));
			}else{
				num = getDigit(""+license.charAt(i), char_digi);
			}
			int count_good = 0;
			for(int j=0;j<good_number.length;j++){
				if(num == good_number[j]){
					count_good++;
				}
			}
			if(count_good > 0 ){
				total_good++;
			}
		}
		if(total_good == license.length()){
			return true;
		}
		return false;
	}
	
	private List<String> search(String prefix,String postfix,int start_digit,int end_digit,String gn,int match_level,boolean ignore_prefix){	
		List<String> result = new ArrayList<String>();
		//if(args.length == 5){
			String[] good_number_str = gn.split(",");//{1,3,4,7,5,8,6};
			Integer[] good_number = new Integer[good_number_str.length];
			int k =0;
			for(String s : good_number_str){
				good_number[k++] = Integer.parseInt(s);
			}
			for(int i =start_digit;i<=end_digit;i++){
				String license = prefix+""+i+postfix;
				int s = sum(license);
				int level = getLevelOfSum(s);
				if(ignore_prefix){
					if(allInGood(license.replace(prefix, ""),good_number) && (match_level == -1 || level == match_level) && !nearBadDigit(license)){
						result.add("License["+license+"] => "+getMean(s));
						//System.out.println(license+"->"+getMean(s));
					}
				}else{
					if(allInGood(license,good_number) && (match_level == -1 || level == match_level) && !nearBadDigit(license)){
						result.add("License["+license+"] => "+getMean(s));
						//System.out.println(license+"->"+getMean(s));
					}
				}
				
			}
		//}else{
			/*System.out.println("args 1: prefix");
			System.out.println("args 2: postfix");
			System.out.println("args 3: start_digit");
			System.out.println("args 4: end_digit");
			System.out.println("args 5: good number 1,2,3");*/
		//}
		
		//System.exit(0);		
		return result;
	}
}
