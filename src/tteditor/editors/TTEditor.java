package tteditor.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;

public class TTEditor extends TextEditor {

	private ColorManager colorManager;

	public TTEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new TTConfiguration(colorManager));
	}

	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

	private IDocumentProvider createDocumentProvider(IEditorInput input) {
		if(input instanceof IFileEditorInput){
			return new TTDocumentProvider();
		}
		else {
			return new TTFileDocumentProvider();
		}
	}

	@Override
	protected final void doSetInput(IEditorInput input) throws CoreException {
		setDocumentProvider(createDocumentProvider(input));
		super.doSetInput(input);
	}
}
