package dev.zoranan.utils.tokenizer;

import java.util.regex.Pattern;

/**A TokenDefinition stores a compiled object, which can be used to to find a {@link Token}.
 * @author Will
 *
 */
public class TokenDefinition {
	public final Pattern pattern;
	public final int tokenCode;
	
	/**A token definition stores a compiled pattern object, which can be used to to find tokens
	 * @param pattern The pattern we are looking for, that makes up a possible token
	 * @param tokenCode	An integer that acts as an identifier for our TokenDefinition
	 */
	public TokenDefinition(Pattern pattern, int tokenCode)
	{
		this.pattern = pattern;
		this.tokenCode = tokenCode;
	}
}
