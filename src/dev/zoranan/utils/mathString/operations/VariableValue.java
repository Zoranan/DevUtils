package dev.zoranan.utils.mathString.operations;

import dev.zoranan.utils.TextValidator;
import dev.zoranan.utils.mathString.exceptions.VariableException;

/**
 * The VariableValue class stores a variable name that can be updated later by calling
 * the {@link #setVariable(java.lang.String, double)} function.
 * 
 * @see #VariableValue(String)
 */
public class VariableValue extends AbstractOperation{
	private String name;
	private double value;
	private boolean variableSet = false;
	
	/**
	 * Creates a new variable with the name specified by our 'name' parameter.
	 * Stores the variable name in {@link #name}
	 * @param name The name we want to call our variable
	 * @throws VariableException 
	 */
	public VariableValue (String name) throws VariableException
	{	
		
		//If we find a space, the name is invalid
		if (name.contains(" "))
		{
			throw new VariableException("\"" + name + "\" is not a valid variable name. Variable names can not contain whitespace");
		}
		else if (name.startsWith(".") || name.endsWith("."))
		{
			throw new VariableException("\"" + name + "\" is not a valid variable name. Variable names can not begin or end with a \".\"");
		}
		
		//Variable names are cut into parts using the dot operator
		//If any of those parts are numbers, the name is invalid
		else
		{
			String parts[] = name.split("\\.");
			for (int i = 0; i < parts.length; i++)
			{
				//If any part of our variable name is numeric, it is invalid 
				if (TextValidator.isNumeric(parts[i]))
				{
					throw new VariableException("\"" + name + "\" is not a valid variable name. Variable names must contain alphabetic characters");
				}
				//If any part of our name is not alpha numeric, it is invalid
				else if (!TextValidator.isAlphaNumeric(parts[i]))
				{
					throw new VariableException("\"" + name + "\" is not a valid variable name. Variable names must contain Alpha-Numeric characters only");
				}
			}
		}
		
		//Store the name we passed in
		this.name = name;
	}

	/**Returns the value stored in this variable 
	 * 
	 * @throws VariableException
	 * @see dev.zoranan.utils.mathString.operations.AbstractOperation#eval()
	 */
	@Override
	public double eval() throws VariableException {
		if (!this.variableSet)
			throw new VariableException("The variable \"" + this.name + "\" has not been initialized");
		return value;
	}

	/**
	 * Attempts to set the value of this variable.
	 * If the passed in 'name' parameter matches the name we have stored, the value is updated and the function returns true.<br/>
	 * If the passed in 'name' parameter does not match, we return false.
	 * 
	 * @param name   The name of the variable we want to set
	 * @param value  The value we want to set the variable to
	 * @return A boolean indicating if the value was set
	 * @see dev.zoranan.utils.mathString.operations.AbstractOperation#setVariable(java.lang.String, double)
	 */
	@Override
	public boolean setVariable(String name, double value) {
		if (this.name.equals(name))
		{
			this.value = value;
			this.variableSet = true;
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
