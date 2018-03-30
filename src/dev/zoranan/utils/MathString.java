package dev.zoranan.utils;

import java.util.ArrayList;
import java.util.Stack;

public class MathString {
	private static final char[] operators = {'+', '-', '/', '*', '^', '(', ')'};
	private static Stack<String> operationStack;
	
	/*
	 * This is the main evaluation function. 
	 */
	public static double eval(String equation)
	{	
		operationStack = new Stack<String>();
		equation = equation.replaceAll("\\s", "");
		String next, right, op, left, nextOp, lastOp = "";
		
		while (equation.length() > 0)
		{
			//Get the next piece of the equation, and add it to the stack.
			next = getNext(equation);
			equation = equation.substring(next.length());	//Cut off the part we just separated
			operationStack.push(next);	//Add the next part of the equation
			
			//Store the next and last operator for PEMDAS (We dont remove this from the equation string, this is a 'peek' ahead)
			nextOp = getNextOp(equation);
			if (isOperator(next))
				lastOp = next;
			
			//Solve what we have			//If the next op is less important than the last op, and the last added string is not an operator
			while (operationStack.size() >= 3 && (getOpOrder(nextOp) <= getOpOrder(lastOp)) && !isOperator(operationStack.peek()))
			{
				right = operationStack.pop();
				op = operationStack.pop();
				left = operationStack.pop();
				operationStack.push(Double.toString(simpleEval(left, right, op)));
				
				nextOp = getNextOp(equation);
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
	private static String getNext(String equation)
	{
		String next = "";
		int i = 0;
		boolean lastPartWasOp;	
		
		if (isOperator(equation.charAt(0)))
		{
			next = equation.substring(0, 1);
			i = 1; //We may need to append more to 'next' if next is a '-', so look past it
		}
		
		lastPartWasOp = (operationStack.isEmpty()) || isOperator(operationStack.peek());
		
		if (next.isEmpty() || (next.equals("-") && lastPartWasOp))	//Only continue on if 'next' is empty, or if 'next' is a '-'
		{
			//Continue adding characters to 'next' until we run out of characters, or the character is an operator
			while (i < equation.length() && !isOperator(equation.charAt(i)))
			{
				next += equation.charAt(i);
				i++;
			}
		}
		System.out.println("Next part: " + next);
		return next;
	}
	
	//Get next operator (Used to implement PEMDAS)
	private static String getNextOp(String equation)
	{
		char c = 0;
		for (int i = 0; i < equation.length(); i++)
		{
			c = equation.charAt(i);
			
			if (isOperator(c))
				break;
		}
		if (c == 0)
			return ("");
		else
			return (Character.toString(c));
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
	
	private static boolean isOperator(String s)
	{
		return (s.length() == 1) && isOperator(s.charAt(0));
	}
	
	//Gets the order that an operation should be done in
	private static int getOpOrder(String op)
	{
		int order = -1;
		if (op.equals("+") || op.equals("-"))
			order = 1;
		else if (op.equals("*") || op.equals("/"))
			order = 2;
		else if (op.equals("^"))
			order = 3;
		
		return order;
	}
}
