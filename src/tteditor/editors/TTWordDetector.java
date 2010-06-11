package tteditor.editors;

import org.eclipse.jface.text.rules.IWordDetector;

public class TTWordDetector implements IWordDetector {

	public boolean isWordPart(char character) {
		return Character.isLetter(character);
	}
	public boolean isWordStart(char character) {
		return Character.isLetter(character);
	}
}
