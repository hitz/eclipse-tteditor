package tteditor.editors;

import org.eclipse.ui.editors.text.TextEditor;

public class TTEditor extends TextEditor {

	private ColorManager colorManager;

	public TTEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new TTConfiguration(colorManager));
		setDocumentProvider(new TTDocumentProvider());
	}
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
