package dev.zoranan.utils.mathString.operations;


/**Binary operations perform a calculation between a left and right operand.<br/>
 * Can be nested to create an order of operations
 * @author Will
 */
public abstract class BinaryOperation extends AbstractOperation {
	protected AbstractOperation left;
	protected AbstractOperation right;
	
	
	/**Creates a BinaryOperation, which has a left and right operand.
	 * @param left	The left operand
	 * @param right	The right operand
	 */
	public BinaryOperation(AbstractOperation left, AbstractOperation right)
	{
		this.left = left;
		this.right = right;
	}
	
	/**Passes the variable information to its left and right operands to be set.
	 * @return True if the variable was set within the left or right operand, or false if the variable was not found.
	 * @see dev.zoranan.utils.mathString.operations.AbstractOperation#setVariable(java.lang.String, double)
	 */
	public boolean setVariable (String name, double value)
	{
		return (left.setVariable(name, value) || right.setVariable(name, value));
	}
}
