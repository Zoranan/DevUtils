package dev.zoranan.utils.mathString;

import java.util.Stack;

import dev.zoranan.utils.mathString.operations.AbstractOperation;
import dev.zoranan.utils.mathString.exceptions.MathException;
import dev.zoranan.utils.mathString.operations.*;
import dev.zoranan.utils.tokenizer.Token;

public class CompiledEquation {
	private Stack<Token> rpnEquationStack;
	public final AbstractOperation rootOperation; 
	
	public CompiledEquation(String eq)
	{
		rpnEquationStack = RPNConverter.convertToRPNstack(eq);
		rootOperation = compile();
	}
	
	private AbstractOperation compile()
	{
		Token current = rpnEquationStack.pop();
		System.out.print(current + " ");
		
		if (current.tokenCode == MathTokenizer.NUMBER)
			return new NumberValue(Double.parseDouble(current.tokenContents));
		
		else if (current.tokenCode == MathTokenizer.VARIABLE)
			return new VariableValue(current.tokenContents);
		
		else if (current.tokenContents.equals("+"))
			return new AddOperation(compile(), compile());
		
		else if (current.tokenContents.equals("-"))
			return new SubtractionOperation(compile(), compile());
		
		else if (current.tokenContents.equals("*"))
			return new MultiplicationOperation(compile(), compile());
		
		else if (current.tokenContents.equals("/"))
			return new DivisionOperation(compile(), compile());
		
		else if (current.tokenContents.equals("^"))
			return new ExponentOperation(compile(), compile());
		
		else
		{
			throw new MathException ("Undefined operation: " + current);
		}
		
	}
	
}
