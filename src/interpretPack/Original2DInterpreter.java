package interpretPack;

import java.util.ArrayList;

public class Original2DInterpreter {
	
	public ArrayList<String> segments;
	
	public Original2DInterpreter(String expression) {
		this.segments = Splitter(expression);
		for (int i=0;i<100;i++) {
			System.out.println(substitute(segments,i));
		}
		
	}
	
	private boolean checkNum(String e) {
		try {
			Double.parseDouble(e);
			return true;
		} catch(NumberFormatException NFE) {
			return false;
		}
	}
	
	
	public ArrayList<String> Splitter(String expression) {
		ArrayList<String> segments = new ArrayList<String>();
		boolean NumSel = false;
		for (int i = 0;i<expression.length();i++) {
			String cchar = expression.substring(i,i+1);
			if (checkNum(cchar) || cchar.equals(".")) { //if it is a number and if the last character was not a number
				if (NumSel == false) {
					NumSel = true;//tell program most recent character is a number and therefore if the next is a number ignore it
					int v = i;
					boolean fullNum = false;
					while (fullNum == false) {//until the program has confirmed that the number has ended, keep checking the next character until you find one that isn't a number
						if (v<expression.length()-1) {
							if (checkNum(expression.substring(v+1, v+2)) || expression.substring(v+1,v+2).equals(".")) {
								v++;
							} else {
								fullNum = true;
							}
						} else {
							fullNum = true;
						}
					}
					segments.add(expression.substring(i,v+1));//add the whole number when it is confirmed to be the whole number
				}
			} else if (cchar.equals(" ")) { //else if the character is space, do nothing except tell the program that a character other than a number has been found
				NumSel = false;
				
				
			
				
			} else { // if the character is anything else, add it to the array and tell program that a character other than a number has been found
				segments.add(cchar);
				NumSel = false;
			}
		}
		
		
		
		
		return segments;
	}
	
	public double substitute(ArrayList<String> segments, int sub) {
		ArrayList<String> sgm = new ArrayList<String>();
		double total = 0;
		for (int i = 0;i<segments.size();i++) {
			sgm.add(segments.get(i));
		}
		
		for (int i = 0;i<sgm.size();i++) { //search for variable x
			if (sgm.get(i).equals("x")) {
				sgm.set(i, String.valueOf(sub)); //replace with substitute
			}
		}
		for (int i = 0;i<sgm.size();i++) {
			if (sgm.get(i).equals("+")) {
				sgm.remove(i);
				i--;
			} else if (sgm.get(i).equals("-")) {
				String e = String.valueOf(Double.parseDouble(sgm.get(i+1)) * -1);
				sgm.remove(i);
				sgm.set(i, e);
				i--;
			}
			
		}
		for (int i = 0;i<sgm.size();i++) {
			if (sgm.get(i).equals("^")) {
				Double e = Math.pow(
						Double.parseDouble(sgm.get(i-1)),
						Double.parseDouble(sgm.get(i+1))
						);
				
				sgm.set(i+1, String.valueOf(e));sgm.remove(i-1);sgm.remove(i-1);
			}
		}
		
		//for (int i = 0;i<sgm.size();i++) {
			//System.out.println(sgm.get(i));
		//}
		
		for (int i = 0;i<sgm.size();i++) { //search for * symbol or / symbol
			System.err.println(sgm.get(i));
			if (sgm.get(i).equals("*")) {
				System.out.println(sgm.get(i-1));System.out.println(sgm.get(i+1));
				Double e = Double.parseDouble(sgm.get(i-1)) * Double.parseDouble(sgm.get(i+1));
				sgm.set(i+1, String.valueOf(e));sgm.remove(i-1);sgm.remove(i-1);
				
				
				
			} else if (sgm.get(i).equals("/")) {
				Double e = (Double.parseDouble(sgm.get(i-1)) / Double.parseDouble(sgm.get(i+1)));
				sgm.set(i+1, String.valueOf(e));sgm.remove(i-1);sgm.remove(i-1);
				
			}
		}
		
		for (int i = 0;i<sgm.size();i++) {
			System.out.println(sgm.get(i));
			
			total = total + Double.parseDouble(sgm.get(i));
		}
		
		
		
		return total;
	}
}
