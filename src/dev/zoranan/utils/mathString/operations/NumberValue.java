package dev.zoranan.utils.mathString.operations;

/**The Number Value class is an Operation that stores a number, and returns the number later.<br/>
 * This class has no nested operations, unlike the {@link BinaryOperation}} class children.<br/>
 * This operation is simply a stored double value, no math operations are performed.
 * @author Will
 */
public class NumberValue extends AbstractOperation{
	private double value;
	
	
	/**
	 * Creates a NumberValue with the desired value
	 * @param value The value of our number
	 */
	public NumberValue(double value)
	{
		this.value = value;
	}
	
	
	/**
	 * Evaluates the operation. In this case, simply returns the stored value
	 * @return The stored double value
	 */
	@Override
	public double eval() {
		return value;
	}
	
	/**
	 * This class has no nested operations, and therfore no variables further down the tree to set.<br/>
	 * We return false to notify the calling object of this.
	 *  
	 * @return false
	 */
	@Override
	public boolean setVariable(String name, double value) {
		return false;
	}

}
