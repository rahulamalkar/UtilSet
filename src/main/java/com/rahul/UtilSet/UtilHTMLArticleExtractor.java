package com.rahul.UtilSet;

import java.net.URL;

import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;

public class UtilHTMLArticleExtractor {
	public UtilHTMLArticleExtractor()
	{
	}

	public String GetNewsData(String lUrl) throws Exception
	{
			final HTMLDocument htmlDoc = HTMLFetcher.fetch(new URL(lUrl));
			final TextDocument doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
			String content = CommonExtractors.ARTICLE_EXTRACTOR.getText(doc);
			return content;
	}
}