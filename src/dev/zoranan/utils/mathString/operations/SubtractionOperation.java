package dev.zoranan.utils.mathString.operations;

import dev.zoranan.utils.mathString.exceptions.MathException;

public class SubtractionOperation extends BinaryOperation{

	/**Creates a Subtraction operation between the left and right operands.
	 * @param left  The left operand
	 * @param right The right operand
	 */
	public SubtractionOperation(AbstractOperation right, AbstractOperation left) {
		super(right, left);
	}

	/**Evaluates the left and right operands, and subtracts them.
	 * @see dev.zoranan.utils.mathString.operations.AbstractOperation#eval()
	 */
	@Override
	public double eval() throws MathException {
		return (left.eval() - right.eval());
	}

}
