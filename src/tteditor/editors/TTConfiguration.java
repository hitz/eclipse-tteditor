package tteditor.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class TTConfiguration extends SourceViewerConfiguration {
	private TTDoubleClickStrategy doubleClickStrategy;
	private TTTagScanner tagScanner;
	private XMLTagScanner xmlTagScanner;
	private TTScanner scanner;
	private ColorManager colorManager;

	public TTConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			TTPartitionScanner.TT_COMMENT,
			TTPartitionScanner.XML_COMMENT,
			TTPartitionScanner.TT_TAG,
			TTPartitionScanner.XML_TAG};
	}
	public ITextDoubleClickStrategy getDoubleClickStrategy(
		ISourceViewer sourceViewer,
		String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new TTDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected TTScanner getTTScanner() {
		if (scanner == null) {
			scanner = new TTScanner(colorManager);
			scanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(ITTColorConstants.DEFAULT))));
		}
		return scanner;
	}
	protected TTTagScanner getTTTagScanner() {
		if (tagScanner == null) {
			tagScanner = new TTTagScanner(colorManager);
			tagScanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(ITTColorConstants.TAG))));
		}
		return tagScanner;
	}

	protected XMLTagScanner getXMLTagScanner() {
		if (xmlTagScanner == null) {
			xmlTagScanner = new XMLTagScanner(colorManager);
			xmlTagScanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(ITTColorConstants.XML_TAG))));
		}
		return xmlTagScanner;
	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getXMLTagScanner());
		reconciler.setDamager(dr, TTPartitionScanner.XML_TAG);
		reconciler.setRepairer(dr, TTPartitionScanner.XML_TAG);

		dr =
			new DefaultDamagerRepairer(getTTTagScanner());
		reconciler.setDamager(dr, TTPartitionScanner.TT_TAG);
		reconciler.setRepairer(dr, TTPartitionScanner.TT_TAG);

		dr = new DefaultDamagerRepairer(getTTScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		NonRuleBasedDamagerRepairer ndr =
			new NonRuleBasedDamagerRepairer(
				new TextAttribute(
					colorManager.getColor(ITTColorConstants.TT_COMMENT)));
		reconciler.setDamager(ndr, TTPartitionScanner.TT_COMMENT);
		reconciler.setRepairer(ndr, TTPartitionScanner.TT_COMMENT);

		ndr = new NonRuleBasedDamagerRepairer(
				new TextAttribute(
					colorManager.getColor(ITTColorConstants.XML_COMMENT)));
		reconciler.setDamager(ndr, TTPartitionScanner.XML_COMMENT);
		reconciler.setRepairer(ndr, TTPartitionScanner.XML_COMMENT);
		
		return reconciler;
	}

}