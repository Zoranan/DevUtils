package dev.zoranan.utils;

import java.util.ArrayList;
import java.util.Stack;

public class MathString {
	private static final char[] operators = {'+', '-', '/', '*', '^', '(', ')'};
	private static Stack<String> operationStack;
	
	public static double eval(String equation)
	{	
		operationStack = new Stack<String>();
		equation = equation.replaceAll("\\s", "");
		String next, right, op, left;
		
		while (equation.length() > 0)
		{
			next = getNext(equation);
			equation = equation.substring(next.length());	//Cut off the part we just separated
			operationStack.push(next);	//Add the next part of the equation
			
			//Solve what we have
			//if (operationStack.size() >= 3 && !(isOperator(next.charAt(0)) && next.length() == 1));
			if (operationStack.size() >= 3)
			{
				right = operationStack.pop();
				op = operationStack.pop();
				left = operationStack.pop();
				operationStack.push(Double.toString(simpleEval(left, right, op)));
			}
			//Push the answer to the stack
		}
		
		return Double.parseDouble(operationStack.pop());	//At this point there should only be one object left in the stack
	}
	
	//Evaluate simple equations that consist of 2 numbers and an operator
	private static double simpleEval(String leftString, String rightString, String op)
	{
		double answer = 0;
		
		double left = Double.parseDouble(leftString);
		double right = Double.parseDouble(rightString);
		
		if (op.equals("^"))
			answer = Math.pow(left, right);
		else if (op.equals("*"))
			answer = left * right;
		else if (op.equals("/"))
			answer = left / right;
		else if (op.equals("+"))
			answer = left + right;
		else if (op.equals("-"))
			answer = left - right;
		
		return answer;
	}
	
	
	/*
	 * VALIDATION FUNCTIONS
	 */
	//Scan the string to make sure the equation is in proper form (no operators next to each other)
	private static boolean isOperatorPlacementValid(String equation)
	{
		boolean valid = true;
		int operatorsInARow = 0;
		char lastOp = ' ';
		char currentOp;
		
		for (int i = 0; i < equation.length() && valid; i++)
		{	
			currentOp = equation.charAt(i);
			//Count the operators in a row, and store this operator as the last operator for later comparison
			if (isOperator(currentOp))
			{
				//Check the beginning and end of our equation
				valid &= !(i == 0 && currentOp != '-' && currentOp != '(');
				valid &= !(i == equation.length()-1 && currentOp != ')');
				
				operatorsInARow++;
				//There are a few exceptions where two operators next to each other is okay. Lets check for them
				if (operatorsInARow > 1)
				{
					valid = false;
					valid |= (currentOp == '-' && lastOp != '-');	//Could be a negative sign. This is okay. Double negatives are not okay
					valid |= (currentOp == '(');	//You can have another operator before a set of parenthesis
					valid |= (lastOp == ')');		//You can have another operator after a set of parenthesis

					System.out.println(operatorsInARow + " operators in a row");
					System.out.println("Current operator: " + currentOp);
					System.out.println("Last operator: " + lastOp);
				}
				
				lastOp = currentOp;
			}
			//If we are not looking at an operator, reset the counter
			else
				operatorsInARow = 0;
		}
		
		return valid;
	}
	
	/*
	 * parenthesis checker
	 * Determines if all parenthesis that were opened, were closed.
	 * Also makes sure there are no extra closing parenthesis.
	 */
	private static boolean parenthesisClosed(String equation)
	{
		int parenthesisCount = 0;
		
		for (int i = 0; i < equation.length(); i++)
		{
			if (equation.charAt(i) == '(')
				parenthesisCount++;
			else if (equation.charAt(i) == ')')
				parenthesisCount--;
		}
		
		return (parenthesisCount == 0);
	}
	
	//This function gets the next number or operator in our equation
	public static String getNext(String equation)
	{
		String next = "";
		int i = 0;
			
		if (isOperator(equation.charAt(0)))
		{
			next = equation.substring(0, 1);
			i = 1;
		}
		
		if (next.isEmpty() || next.equals("-"))
		{
			while (i < equation.length() && !isOperator(equation.charAt(i)))
			{
				next += equation.charAt(i);
				i++;
			}
		}
		
		return next;
	}
	
	//Break the whole string down into numbers and operators
	private static ArrayList<String> getParts(String equation)
	{
		equation = TextValidator.eatChars(equation, ' ');
		ArrayList<String> parts = new ArrayList<String>();
		String currentPart = "";
		
		for (int i = 0; i < equation.length(); i++)
		{
			char c = equation.charAt(i);
			//if the current character is a digit, concat it to the current part of the equation
			if (TextValidator.isNumeric(c))
			{
				currentPart += c;
			}
			//If we have an operator, add it to the list
			else if (isOperator(c))
			{
				currentPart = "";
				parts.add("" + c);
			}
			
			//Add the new part
			else if (!currentPart.isEmpty())
				parts.add(currentPart);
		}
		
		return parts;
	}
	
	//Determines if the passed in character is a valid math operator
	private static boolean isOperator(char c)
	{
		Boolean valid = false;
		//Break if the character is determined to be an operator
		for (int i=0; i < operators.length & !valid; i++)
		{
			valid |= (operators[i] == c);
		}
		return valid;
	}
}
