package tteditor.editors;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.rules.*;

public class TTTagScanner extends RuleBasedScanner {

	public TTTagScanner(ColorManager manager) {

		IToken code =
			new Token(
				new TextAttribute(manager.getColor(ITTColorConstants.TAG)));

		IToken string =
			new Token(
				new TextAttribute(manager.getColor(ITTColorConstants.STRING)));

		IToken keyword =
			new Token(
				new TextAttribute(manager.getColor(ITTColorConstants.TT_KEYWORD)));
		
		IRule[] rules = new IRule[4];

		// Add rule for double quotes
		rules[0] = new SingleLineRule("\"", "\"", string, '\\');
		// Add a rule for single quotes
		rules[1] = new SingleLineRule("'", "'", string, '\\');
		// Add generic whitespace rule.
		rules[2] = new WhitespaceRule(new TTWhitespaceDetector());
		// Add TT Keywords
		WordRule keywordRule = 
		   new WordRule(new TTWordDetector(), code);
		
	    TTKeywordConstants keywords = new TTKeywordConstants();
	    
	    String[] keywordList = keywords.TTKeyWord();
	    for(int i=0; i<keywordList.length; i++) {
	    	
	    	keywordRule.addWord(keywordList[i],keyword);
	    }
		
	    rules[3] = keywordRule;
		
		setRules(rules);
	}
}
