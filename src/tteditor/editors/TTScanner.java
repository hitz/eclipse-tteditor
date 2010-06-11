package tteditor.editors;

import org.eclipse.jface.text.rules.*;
import org.eclipse.jface.text.*;

public class TTScanner extends RuleBasedScanner {

	public TTScanner(ColorManager manager) {
		IToken procInstr =
			new Token(
				new TextAttribute(
					manager.getColor(ITTColorConstants.PROC_INSTR)));

		IRule[] rules = new IRule[2];
		//Add rule for processing instructions
		rules[0] = new SingleLineRule("<?", "?>", procInstr);
		// Add generic whitespace rule.
		rules[1] = new WhitespaceRule(new TTWhitespaceDetector());

		setRules(rules);
	}
}
