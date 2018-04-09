package dev.zoranan.utils.mathString;

import dev.zoranan.utils.tokenizer.Tokenizer;

public class MathTokenizer extends Tokenizer{
	
	public final static int TRIG_FUNC = 1;
	public final static int OPEN_PAR = 2;
	public final static int CLOSE_PAR = 3;
	public final static int ADD_SUB = 4;
	public final static int MULTI_DIV = 5;
	public final static int EXPONENT = 6;
	public final static int NUMBER = 7;
	public final static int VARIABLE = 8;
	
	public MathTokenizer ()
	{
		super();
		
		//Add our patterns
		this.add("sin|cos|ln|sqrt|root", TRIG_FUNC);
		this.add("\\(", OPEN_PAR);
		this.add("\\)", CLOSE_PAR);
		this.add("[+-]", ADD_SUB);
		this.add("[*/]", MULTI_DIV);
		this.add("\\^", EXPONENT);
		
		this.add("[0-9.]+", NUMBER);
		this.add("[a-zA-Z][a-zA-Z0-9_.]*", VARIABLE);
	}

}
