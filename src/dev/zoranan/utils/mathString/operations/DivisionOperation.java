package dev.zoranan.utils.mathString.operations;

import dev.zoranan.utils.mathString.exceptions.MathException;
import dev.zoranan.utils.mathString.exceptions.OperationException;

public class DivisionOperation extends BinaryOperation{

	/**Creates a Division operation between the left and right operands
	 * @param left  The left operand
	 * @param right The right operand
	 */
	public DivisionOperation(AbstractOperation left, AbstractOperation right) {
		super(left, right);
	}

	/**Evaluates the left and right operands, and divides them.
	 * @throws OperationException if the right operand evaluates to 0
	 * @see dev.zoranan.utils.mathString.operations.AbstractOperation#eval()
	 */
	@Override
	public double eval() throws MathException {
		double rightValue = right.eval();
		
		if (rightValue == 0)
		{
			throw new OperationException("Division by zero error");
		}
		
		return (left.eval() / rightValue);
	}

}
