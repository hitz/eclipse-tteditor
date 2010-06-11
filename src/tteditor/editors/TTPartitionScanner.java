package tteditor.editors;

import org.eclipse.jface.text.rules.*;

public class TTPartitionScanner extends RuleBasedPartitionScanner {
	public final static String TT_COMMENT = "__tt_comment";
	public final static String TT_TAG = "__tt_tag";
	public final static String XML_TAG = "__xml_tag";
	public static final String XML_COMMENT = "__xml_comment";

	public TTPartitionScanner() {

		IToken ttComment = new Token(TT_COMMENT);
		IToken xmlComment = new Token(XML_COMMENT);
		IToken tag = new Token(TT_TAG);
		IToken xmlTag = new Token(XML_TAG);

		IPredicateRule[] rules = new IPredicateRule[4];

		rules[0] = new MultiLineRule("[%#", "%]", ttComment);
		rules[1] = new MultiLineRule("[%", "%]",tag);
		rules[2] = new TagRule(xmlTag);
		rules[3] = new MultiLineRule("<!--", "-->", xmlComment);


		setPredicateRules(rules);
	}
}
