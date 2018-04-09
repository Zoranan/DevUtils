package dev.zoranan.utils.mathString;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import dev.zoranan.utils.tokenizer.Token;

/**Contains static functions for converting an equation input string into 
 * Reverse Polish Notation (RPN) and Normal Polish Notation (NPN). 
 * @author Will
 */
public final class RPNConverter {
	private static MathTokenizer tokenizer = new MathTokenizer();
	
	/**Converts the input string into an RPN ArrayList.
	 * @param originalEquation The original equation (typically in infix notation).
	 * @return An ArrayList containing the equations {@link Token}s re-ordered to post-fix notation.
	 */
	public static ArrayList<Token> convertToRPNlist(String originalEquation)
	{
		//Tokenize the original equation
		tokenizer.tokenize(originalEquation);
		LinkedList<Token> tokens = tokenizer.getTokens();
		Stack<Token> operatorStack = new Stack<Token>();
		ArrayList<Token> outputQue = new ArrayList<Token>();
		int readIndex = 0;
		
		//BEGIN: The Shunting-yard algorithm
		while (readIndex < tokens.size())
		{
			Token current = tokens.get(readIndex);
			//If the next Token is a number or variable
			if (current.tokenCode >= MathTokenizer.NUMBER)
			{
				outputQue.add(current);
			}
			//If the next token is a function
			else if (current.tokenCode == MathTokenizer.TRIG_FUNC)
			{
				operatorStack.push(current);
			}
			//If the next token is an operator
			else if (current.tokenCode >= MathTokenizer.ADD_SUB && current.tokenCode <= MathTokenizer.EXPONENT)
			{
				while ( 
						operatorStack.isEmpty() == false &&
						(operatorStack.peek().tokenCode == MathTokenizer.TRIG_FUNC || 
						operatorStack.peek().tokenCode > current.tokenCode ||
						(operatorStack.peek().tokenCode == current.tokenCode && isLeftAssociative(operatorStack.peek()) )) &&
						! operatorStack.peek().tokenContents.equals("(")
						)
				{
					outputQue.add(operatorStack.pop());
				}
				
				operatorStack.push(current);
			}
			//If the current Token is a left parenthesis "("
			else if (current.tokenContents.equals("("))
			{
				operatorStack.push(current);
			}
			//If the current Token is a right parenthesis ")"
			else if (current.tokenContents.equals(")"))
			{
				while (! operatorStack.peek().tokenContents.equals("("))
				{
					outputQue.add(operatorStack.pop());
				}
				operatorStack.pop();	//Remove the remaining "("
			}
			
			readIndex++;
		}
		
		//Pop any remaining operators from the operator stack onto the outputQue
		if (readIndex >= tokens.size())
		{
			while (! operatorStack.isEmpty())
			{
				outputQue.add(operatorStack.pop());
			}
		}
		
		return outputQue;
	}
	
	
	//////////////////////////
	//THE WINNER
	/**Converts the input string into an RPN Stack (to be read backwards).
	 * @param originalEquation The original equation (typically in infix notation).
	 * @return A Stack containing the equation's {@link Token}s re-ordered to post-fix notation
	 */
	public static Stack<Token> convertToRPNstack(String originalEquation)
	{
		//Tokenize the original equation
		tokenizer.tokenize(originalEquation);
		LinkedList<Token> tokens = tokenizer.getTokens();
		Stack<Token> operatorStack = new Stack<Token>();
		Stack<Token> outputStack = new Stack<Token>();
		int readIndex = 0;
		
		//BEGIN: The Shunting-yard algorithm
		while (readIndex < tokens.size())
		{
			Token current = tokens.get(readIndex);
			//If the next Token is a number or variable
			if (current.tokenCode >= MathTokenizer.NUMBER)
			{
				outputStack.push(current);
			}
			//If the next token is a function
			else if (current.tokenCode == MathTokenizer.TRIG_FUNC)
			{
				operatorStack.push(current);
			}
			//If the next token is an operator
			else if (current.tokenCode >= MathTokenizer.ADD_SUB && current.tokenCode <= MathTokenizer.EXPONENT)
			{
				while ( 
						operatorStack.isEmpty() == false &&
						(operatorStack.peek().tokenCode == MathTokenizer.TRIG_FUNC || 
						operatorStack.peek().tokenCode > current.tokenCode ||
						(operatorStack.peek().tokenCode == current.tokenCode && isLeftAssociative(operatorStack.peek()) )) &&
						! operatorStack.peek().tokenContents.equals("(")
						)
				{
					outputStack.push(operatorStack.pop());
				}
				
				operatorStack.push(current);
			}
			//If the current Token is a left parenthesis "("
			else if (current.tokenContents.equals("("))
			{
				operatorStack.push(current);
			}
			//If the current Token is a right parenthesis ")"
			else if (current.tokenContents.equals(")"))
			{
				while (! operatorStack.peek().tokenContents.equals("("))
				{
					outputStack.push(operatorStack.pop());
				}
				operatorStack.pop();	//Remove the remaining "("
			}
			
			readIndex++;
		}
		
		//Pop any remaining operators from the operator stack onto the outputQue
		if (readIndex >= tokens.size())
		{
			while (! operatorStack.isEmpty())
			{
				outputStack.push(operatorStack.pop());
			}
		}
		
		return outputStack;
	}
	
	
	//Normal PolishNotation (PreFix).
	/**Converts the input string into an NPN Stack .
	 * @param originalEquation The original equation (typically in infix notation).
	 * @return A Stack containing the equations {@link Token}s re-ordered to pre-fix notation.
	 */
	public static Stack<Token> convertPN(String originalEquation)
	{
		//Tokenize the original equation
		tokenizer.tokenize(originalEquation);
		LinkedList<Token> tokens = tokenizer.getTokens();
		Stack<Token> operatorStack = new Stack<Token>();
		Stack<Token> outputStack = new Stack<Token>();
		int readIndex = tokens.size()-1;
		
		//BEGIN: The Backward Shunting-yard algorithm
		while (readIndex >= 0)
		{
			Token current = tokens.get(readIndex);
			//If the next Token is a number or variable
			if (current.tokenCode >= MathTokenizer.NUMBER)
			{
				outputStack.push(current);
			}
			//If the next token is a function
			else if (current.tokenCode == MathTokenizer.TRIG_FUNC)
			{
				operatorStack.push(current);
			}
			//If the next token is an operator
			else if (current.tokenCode >= MathTokenizer.ADD_SUB && current.tokenCode <= MathTokenizer.EXPONENT)
			{
				while ( 
						operatorStack.isEmpty() == false &&
						(operatorStack.peek().tokenCode == MathTokenizer.TRIG_FUNC || 
						operatorStack.peek().tokenCode > current.tokenCode ||
						(operatorStack.peek().tokenCode == current.tokenCode && isLeftAssociative(operatorStack.peek()) )) &&
						! operatorStack.peek().tokenContents.equals(")")
						)
				{
					outputStack.push(operatorStack.pop());
				}
				
				operatorStack.push(current);
			}
			//If the current Token is a right parenthesis ")"
			else if (current.tokenContents.equals(")"))
			{
				operatorStack.push(current);
			}
			//If the current Token is a left parenthesis "("
			else if (current.tokenContents.equals("("))
			{
				while (! operatorStack.peek().tokenContents.equals(")"))
				{
					outputStack.push(operatorStack.pop());
				}
				operatorStack.pop();	//Remove the remaining ")"
			}
			
			readIndex--;
		}
		
		//Pop any remaining operators from the operator stack onto the outputQue
		if (readIndex <= 0)
		{
			while (! operatorStack.isEmpty())
			{
				outputStack.push(operatorStack.pop());
			}
		}
		
		return outputStack;
	}
	
	private static boolean isLeftAssociative(Token t)
	{
		return (t.tokenCode >= MathTokenizer.ADD_SUB && t.tokenCode <= MathTokenizer.MULTI_DIV);
	}
	
	
}
