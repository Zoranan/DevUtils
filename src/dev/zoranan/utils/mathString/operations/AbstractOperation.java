package dev.zoranan.utils.mathString.operations;

import dev.zoranan.utils.mathString.exceptions.MathException;
import dev.zoranan.utils.mathString.exceptions.VariableException;

/**Operations contain a piece of a math equation, such as numbers, variables, or binary operations.
 * @author Will
 *
 */
public abstract class AbstractOperation {
	
	/**
	 * Evaluates the operation
	 * @return a numeric value based on the operation performed
	 */
	public abstract double eval() throws MathException;
	
	
	/**
	 * Attempts to set a variable of the given name, to the given value
	 * @param name  The name of the variable to set
	 * @param value The desired value of the variable
	 * @return A boolean indicating if the variable was found, and the value was set
	 * @throws VariableException if the variable name is invalid (contains spaces, or has no alpha characters)
	 */
	public abstract boolean setVariable (String name, double value);
}
