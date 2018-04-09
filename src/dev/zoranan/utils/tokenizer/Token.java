package dev.zoranan.utils.tokenizer;

/**A Token is part of a String that matches a certain Pattern, or {@link TokenDefinition}.<br/>
 * Token Objects store the contents of the token in a String, and a tokenCode, used to identify what type of token it is.   
 * @author Will
 *
 */
public class Token {
	public final int tokenCode;
	public final String tokenContents;
	
	/**Creates a new Token, which holds the token contents. 
	 * @param tokenCode A number used to identify what type of token this is.
	 * @param tokenContents The string value that makes up this token.
	 */
	public Token(int tokenCode, String tokenContents)
	{
		this.tokenContents = tokenContents;
		this.tokenCode = tokenCode;
	}
	
	/**Get the contents of this token.
	 * @return The token's contents (String)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return this.tokenContents;
	}
}
