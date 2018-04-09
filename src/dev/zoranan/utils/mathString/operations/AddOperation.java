package dev.zoranan.utils.mathString.operations;

import dev.zoranan.utils.mathString.exceptions.MathException;

public class AddOperation extends BinaryOperation{

	/**Creates an Addition operation between the left and right operands.
	 * @param left  The left operand
	 * @param right The right operand
	 */
	public AddOperation(AbstractOperation right, AbstractOperation left) {
		super(right, left);
	}

	/**Evaluates the left and right operands, and adds them together.
	 * @see dev.zoranan.utils.mathString.operations.AbstractOperation#eval()
	 */
	@Override
	public double eval() throws MathException {
		return (left.eval() + right.eval());
	}

}
