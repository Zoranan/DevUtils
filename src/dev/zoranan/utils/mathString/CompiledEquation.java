package dev.zoranan.utils.mathString;

import java.util.ArrayList;
import java.util.Stack;

import dev.zoranan.utils.mathString.operations.AbstractOperation;
import dev.zoranan.utils.mathString.exceptions.MathException;
import dev.zoranan.utils.mathString.operations.*;
import dev.zoranan.utils.tokenizer.Token;

/**An equation tree that can be evaluated quickly at a later time.
 * @author Will
 */
public class CompiledEquation extends AbstractOperation {
	private Stack<Token> rpnEquationStack;
	private ArrayList<String> variables;
	public final AbstractOperation rootOperation;
	
	/**Creates a new compiled equation from the input string.
	 * @param eq The original equation (in infix notation)
	 */
	public CompiledEquation(String eq)
	{
		variables = new ArrayList<String>();
		rpnEquationStack = RPNConverter.convertToRPNstack(eq);
		rootOperation = compile();
	}
	
	/**Uses an RPN Stack to create an equation tree of math operations in proper order.
	 * @return A Compiled equation than can be quickly evaluated at any time.
	 */
	private AbstractOperation compile()
	{
		Token current = rpnEquationStack.pop();
		//System.out.print(current + " ");
		
		if (current.tokenCode == MathTokenizer.NUMBER)
			return new NumberValue(Double.parseDouble(current.tokenContents));
		
		else if (current.tokenCode == MathTokenizer.VARIABLE)
		{
			variables.add(current.tokenContents);
			return new VariableValue(current.tokenContents);
		}
		
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

	/* (non-Javadoc)
	 * @see dev.zoranan.utils.mathString.operations.AbstractOperation#eval()
	 */
	@Override
	public double eval() throws MathException {
		return rootOperation.eval();
	}

	/* (non-Javadoc)
	 * @see dev.zoranan.utils.mathString.operations.AbstractOperation#setVariable(java.lang.String, double)
	 */
	@Override
	public boolean setVariable(String name, double value) {
		return rootOperation.setVariable(name, value);
	}
	
}
