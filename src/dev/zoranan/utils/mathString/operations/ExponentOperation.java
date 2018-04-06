package dev.zoranan.utils.mathString.operations;

import dev.zoranan.utils.mathString.exceptions.MathException;

public class ExponentOperation extends BinaryOperation{

	/**Creates an Exponential operation between the left and right operands
	 * @param left  The left operand
	 * @param right The right operand
	 */
	public ExponentOperation(AbstractOperation left, AbstractOperation right) {
		super(left, right);
	}

	/**Evaluates the left and right operands, and raises the left operand to the power of the right operand.
	 * @see dev.zoranan.utils.mathString.operations.AbstractOperation#eval()
	 */
	@Override
	public double eval() throws MathException {
		return (Math.pow(left.eval(), right.eval()));
	}

}
