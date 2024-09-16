package interpretPack;

import java.util.ArrayList;
import java.util.Stack;

import renderPack.Vertex;

//1st step - split expression into an array where each element is a token which
// can be either a number, function or operator.												DONE

//2nd step - rearrange array into postfix notation or 'reverse polish notation' 				DONE 
// this makes the next step much easier.														

//3rd step - for every x and y, substitute into evaluated RPN expression,
//calculate the value and store it.



public class Interpreter {
	private ArrayList<String> tokens;
	private String[] functions = {"sin","cos","tan","asin","acos","atan","sinh","cosh","tanh","asinh","acosh","atanh","sqrt","ln","log"}; 
	private String[] chars = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	
	
	private boolean isNum(String n) {
		boolean value = false;
		
		try {
			Double.parseDouble(n);
			value = true;
		} catch (NumberFormatException NFE) {
			value = false;
		}
		
		return value;
	}
	private boolean isOperator(String a) {
		if (a.equals("+")) {
			return true;
		} else if (a.equals("-")) {
			return true;
		} else if (a.equals("*")) {
			return true;
		} else if (a.equals("/")) {
			return true;
		} else if (a.equals("^")) {
			return true;
		} else {
			return false;
		}
	}
	private boolean isChar(String c) {
		for (int i =0;i<chars.length;i++) {
			if (c.equalsIgnoreCase(chars[i])) {
				return true;
			}
		}
		return false;
	}
	private boolean isFunc(String s) {
		for (int i =0;i<functions.length;i++) {
			if (functions[i].equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}
	private int getPrecedence(String a) {
		
		if (a.equals("+") || a.equals("-")) {
			return 1;
		} else if (a.equals("*") || a.equals("/")) {
			return 2; 
		} else if (a.equals("^")) {
			return 3;
		}
		
		
		
		return 0;
	}
	
	// methods in order they are used
	
	/////////////////////////////////////////////////////////////////1
	private ArrayList<String> getTokens(String expression) {
		ArrayList<String> p = new ArrayList<String>();
		boolean numberTrail = false;
		boolean charTrail = false;
		
		for (int i =0;i<expression.length();i++) {
			Character c = expression.charAt(i);//System.err.println(i + ", " + c + ", " + numberTrail +  "," + charTrail +  ", "+ expression.length());
			if (isNum(String.valueOf(c))) {
				charTrail = false;
				boolean decimalfound = false;
				if (!numberTrail) {
					String num = "";
					for (int v = i;v<expression.length();v++) {
						if (!isNum(String.valueOf(expression.charAt(v)))) { // go forward in expression until you find a character which is not a number.
							if (String.valueOf(expression.charAt(v)).equals(".")) {
								if (decimalfound == false) {
									decimalfound = true;
								} else {
									System.err.println("invalid expression");
								}
							} else {
								num = expression.substring(i,v);
								break;
							}
							
						} else if (v == expression.length()-1) {
							num = expression.substring(i,v+1);
						}
					}
					p.add(num);
				}
				numberTrail = true;
				
			} else if (isChar(String.valueOf(c))) {
				if (!charTrail) {
					String str = "";
					for (int v = i;v<expression.length();v++) {
						if (!isChar(String.valueOf(expression.charAt(v)))) {
							str = expression.substring(i,v);
							break;
						} else if (v == expression.length()-1) {
							str = expression.substring(i,v+1);
						}
					}
					if (isFunc(str)) {
						if (numberTrail == true) {
							p.add("*");
						}
						p.add(str);
						charTrail = true;
					} else {
						System.out.println(c + " not a func");
						if ((String.valueOf(c).equalsIgnoreCase("X"))) {
							System.out.println(c);
							if (i!=0) {
								if (numberTrail || String.valueOf(expression.charAt(i-1)).equalsIgnoreCase("X") || String.valueOf(expression.charAt(i-1)).equalsIgnoreCase("Y")) {
									numberTrail = false;
									p.add("*");
								}
							}
							p.add(String.valueOf(c));
						} else if ((String.valueOf(c).equalsIgnoreCase("Y"))) {
							if (i!=0) {
								if (numberTrail || String.valueOf(expression.charAt(i-1)).equalsIgnoreCase("X") || String.valueOf(expression.charAt(i-1)).equalsIgnoreCase("Y")) {
									numberTrail = false;
									p.add("*");
								}
							}
							p.add(String.valueOf(c));
						} else if (String.valueOf(c).equalsIgnoreCase("e")) {
							if (i!=0) {
								if (numberTrail || String.valueOf(expression.charAt(i-1)).equalsIgnoreCase("X") || String.valueOf(expression.charAt(i-1)).equalsIgnoreCase("Y")) {
									numberTrail = false;
									p.add("*");
								}
							}
							p.add(String.valueOf(Math.E));
							System.out.println(Math.E);
						}
						numberTrail = false;
					} 
					
					
				}
				

				
			} else if (isOperator(String.valueOf(c))) {
				numberTrail = false;
				charTrail = false;
				p.add(String.valueOf(c));
				
			} else if (c.equals('(') || c.equals(')')) {
				numberTrail = false;
				charTrail = false;
				p.add(String.valueOf(c));
			}
		}
		ArrayList<String> q = new ArrayList<String>();
		for (int i =0;i<p.size();i++) {
			if (p.get(i).equals("-")) {
				if (i == 0) {
					q.add("0");
					q.add("-");
				} else if (!isNum(p.get(i-1))) {
					if (p.get(i-1).equals("-")) {
						q.add("+");
					} else {
						q.add("0");
						q.add("-");
					}
				}
			} else {
				q.add(p.get(i));
			}
			
		}
		
		return q;
		
	}
	
	
	/////////////////////////////////////////////////////////////////2
	private ArrayList<String> toRPN(ArrayList<String> t){
		// a variation of shunting yard algorithm.
		

		Stack<String> Operators = new Stack<String>();
		ArrayList<String> Output = new ArrayList<String>();
		for (int i = 0;i<t.size();i++) {
			String c = t.get(i); //for simplicity call current token 'c'
			if (isNum(c) || c.equalsIgnoreCase("x") || c.equalsIgnoreCase("y")) { //if the current token is a number (variables x and y are treated as such since 
				//they will be substituted in the next step) :
				Output.add(c);//add it to the output List
				
			} else if (isFunc(c)) { //if current token is a function (see function "isFunc()" to see how this is decided.) then push to Operators stack
				Operators.push(c);
			} else if (isOperator(c)) { // if the current token is an operator then :
				
				while ( // while there is an operator o2 at the top of the operator stack which is not a left bracket, and (o2 has greater or equal precedence than o1)
						!Operators.isEmpty() && isOperator(Operators.peek())&& getPrecedence(Operators.peek()) >= getPrecedence(c)) 
				{ 
					Output.add(Operators.pop());//pop o2 into output
				}
				Operators.push(c); //once all those operators have been popped into output, push current token onto Operators.
				
			} else if (c.equals("(")) { //if current token is a left bracket push straight to Operator stack.
				Operators.push(c);
				
			} else if (c.equals(")")) { //if current token is a right bracket follow next steps
				while (!Operators.isEmpty() && !Operators.peek().equals("(")) { //while there is not a left bracket on the top of the operator stack
					//and Operator stack isn't empty, pop the top operator and add it to Output
					Output.add(Operators.pop()); 
				}
				if (!Operators.isEmpty() && Operators.peek().equals("(")) { //assert that there is now a left bracket at the top of the operator stack
					
					Operators.pop(); //pop it and discard it.
				}
				if (!Operators.isEmpty() && isFunc(Operators.peek())) { //if there is a function at the top of the stack, then pop it into output
					Output.add(Operators.pop());
				}
				
			} 
		}
		while (!Operators.isEmpty()) { //while there are still tokens on the operator stack,
			if (!Operators.peek().equals("(")) {
				Output.add(Operators.pop());//pop them all into Output as long as they are not a left parenthesis.
			} else {
				Operators.pop();
			}
		}
		return Output;
	}
	
	
	/////////////////////////////////////////////////////////////////3
	public double substitute(double x, double y) {
		//exp will be in postfix so first scan for Xs / Ys, substitute them, then, scan for any operators, and perform their operations.
		ArrayList<String> exp = new ArrayList<String>();
		
		for (int i = 0;i<tokens.size();i++) {
			exp.add(tokens.get(i));
		}
		
		
		Stack<String> s = new Stack<String>();
		for (int i = 0;i<exp.size();i++) {
			if (exp.get(i).equalsIgnoreCase("x")) {
				exp.set(i, String.valueOf(x));
			} else if (exp.get(i).equalsIgnoreCase("y")) {
				exp.set(i, String.valueOf(y));
			}
		}
		for (int i = 0;i<exp.size();i++) {
			String c = exp.get(i);
			System.out.println(c + ", " + s);
			if (isNum(c)) {
				s.push(c);
			} else if (isOperator(c)) {
				String a = null;
				String b = null;
				
				if (!s.isEmpty()) {
					b = s.pop();
				} else {
					//System.err.println("invalid expression");
				}
				if (!s.isEmpty()) {
					a = s.pop();
				} else {
					//System.err.println("invalid expression");
				}
				
				double result = 0;
				if (c.equals("+")) {
					result = Double.parseDouble(a) + Double.parseDouble(b);
				} else if (c.equals("-")) {
					if (a != null) {
						result = Double.parseDouble(a) - Double.parseDouble(b);
					}
				} else if (c.equals("*")) {
					result = Double.parseDouble(a) * Double.parseDouble(b);
				} else if (c.equals("/")) {
					result = Double.parseDouble(a) / Double.parseDouble(b);
				} else if (c.equals("^")) {
					result = Math.pow(Double.parseDouble(a), Double.parseDouble(b));
				}
				s.push(String.valueOf(result));
			} else if (isFunc(c)) {
				String a = null;
				if (!s.isEmpty()) {
					a = s.pop();
				}
				double result = 0;
				if (c.equalsIgnoreCase("sin")) {
					result = Math.sin(Double.parseDouble(a));
				} else if (c.equalsIgnoreCase("cos")) {
					result = Math.cos(Double.parseDouble(a));
				} else if (c.equalsIgnoreCase("tan")) {
					result = Math.tan(Double.parseDouble(a));
				} else if (c.equalsIgnoreCase("asin")) {
					result = Math.asin(Double.parseDouble(a));
				} else if (c.equalsIgnoreCase("acos")) {
					result = Math.acos(Double.parseDouble(a));
				} else if (c.equalsIgnoreCase("atan")) {
					result = Math.atan(Double.parseDouble(a));
				} else if (c.equalsIgnoreCase("sinh")) {
					result = Math.sinh(Double.parseDouble(a));
				} else if (c.equalsIgnoreCase("cosh")) {
					result = Math.cosh(Double.parseDouble(a));
				} else if (c.equalsIgnoreCase("tanh")) {
					result = Math.tanh(Double.parseDouble(a));
					
					
					
					
					
					
					
				} else if (c.equalsIgnoreCase("sqrt")) {
					result = Math.sqrt(Double.parseDouble(a));
				} else if (c.equalsIgnoreCase("ln")) {
					result = Math.log(Double.parseDouble(a));
				} 
				s.push(String.valueOf(result));
				
				
				
			}
		}
		double total = Double.parseDouble(s.pop());
		
		//////////////////////////////////////////////////////////////////////////////////// CHANGE \/
		return total;
	}
	
	public Interpreter(String expression) {
		ArrayList<String> t;
		t = getTokens(expression);
		System.out.println(t);
		t = toRPN(t);
		System.err.println(t);
		tokens = t;
		
		
	}
	
	
	
}
