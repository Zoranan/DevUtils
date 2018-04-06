package dev.zoranan.utils.mathString.exceptions;

/**
 * Thrown when a variable name does not meet the required criteria, or when a null variable is evaluated.<br/>
 * Variable names must not contain spaces. <br/>
 * Variable names must contain alphabetic characters.<br/>
 * Variables must be set before being evaluated.
 * @author Will
 *
 */
public class VariableException extends MathException{
	public VariableException()
	{
		
	}
	
	public VariableException(String message)
	{
		super (message);
	}
}
