package dev.zoranan.utils.tokenizer;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jdk.nashorn.internal.runtime.ParserException;

/**The Tokenizer class is used to process a String into Tokens.
 * 
 * The {@link #add(String, int)} method is used to define Tokens.<br/>
 * The {@link #tokenize(String)} method splits the input string into its tokens 
 * @author Will
 * @see #tokenDefinitions
 * @see #tokens
 */
public class Tokenizer {
	/**
	 * This list holds all of the token definitions to be pulled from a line of text
	 */
	private LinkedList<TokenDefinition> tokenDefinitions;
	/**
	 * This list holds all of the tokens pulled from a line of text
	 */
	private LinkedList<Token> tokens;
	
	public Tokenizer()
	{
		this.tokenDefinitions = new LinkedList<TokenDefinition>();
		this.tokens = new LinkedList<Token>();
	}
	
	/**Creates a TokenDefinition to the Tokenizer from the regex and tokenCode parameters.
	 * @param definition The TokenDefinition to be added.
	 * @param regex	The regex specifying what characters to look for within a string.
	 * @param tokenCode An integer value used to identify a TokenDefinition
	 */
	public void add (String regex, int tokenCode)
	{
		add (new TokenDefinition(Pattern.compile("^(" + regex + ")"), tokenCode));
	}
	
	/**Add the TokenDefinition to the Tokenizer.
	 * @param definition The TokenDefinition to be added.
	 */
	public void add (TokenDefinition definition)
	{
		tokenDefinitions.add(definition);
	}
	
	/**Cuts a string up into tokens, based on the token definitions added to the Tokenizer object.
	 * @param originalInput The String to be tokenized
	 */
	public void tokenize(String originalInput)
	{
		//Create a copy of our string so we dont chop up the original
		String input = new String (originalInput);
		//The actual matching string found within the input string.
		String tokenContents;
		//Clear the token list before starting
		tokens.clear();
		
		//Begin processing our copied string until it is empty
		boolean found;
		Matcher m;
		while (!input.isEmpty())
		{
			found = false;
			//Check the beginning of the string for each type of token definition.
			//Breaks as soon as we find a matching token
			//If we dont find a match, the string is invalid
			for (TokenDefinition td : tokenDefinitions)
			{
				//Get a Matcher object from our current pattern, and the copied input string.
				m = td.pattern.matcher(input);
				
				/* If we find a token in the input string, mark that we have found it
				 * Group the found characters together into a string, and trim any excess space.
				 * Create a new token object from this string, and add it to the token list
				 * Remove the processed token from our copied input string
				 * exit the loop 
				 */
				if (m.find())
				{
					found = true;
					
					tokenContents = m.group().trim();
					tokens.add(new Token(td.tokenCode, tokenContents));
					//System.out.println(tokenContents);
					input = m.replaceFirst("");
					input = input.trim();
					break;
				}
			}
			
			//If we did not find a match, invalid syntax was in the string
			if (!found)
			{
				throw new ParserException("Invalid characters found in input string: " + originalInput);
			}
		}
	}
	
	
	/**Get the list of tokens, processed from the last string that was tokenized.
	 * @return LinkedList of tokens.
	 */
	public LinkedList<Token> getTokens()
	{
		return tokens;
	}
}
