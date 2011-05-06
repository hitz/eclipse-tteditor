package tteditor.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;

public class TTFileDocumentProvider extends TextFileDocumentProvider  {

	protected FileInfo createFileInfo(Object element) throws CoreException {
		FileInfo info = super.createFileInfo(element);
		if(info == null) {
			info = createEmptyFileInfo();
		}

		IDocument document = info.fTextFileBuffer.getDocument();
		if (document != null) {
			IDocumentPartitioner partitioner =
				new FastPartitioner(
					new TTPartitionScanner(),
					new String[] {
						TTPartitionScanner.TT_TAG,
						TTPartitionScanner.TT_COMMENT,
						TTPartitionScanner.XML_TAG});
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}

		return info;
	}

}
