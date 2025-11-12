package com.solvd.financialinstitution.parsers.sax;

import com.solvd.financialinstitution.domain.FinancialNetwork;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;

public class XmlSaxParser {
    public FinancialNetwork parse(InputStream xmlStream) throws Exception {
        SAXParserFactory f = SAXParserFactory.newInstance();
        f.setNamespaceAware(true);
        f.setValidating(false);
        SAXParser sax = f.newSAXParser();
        FinancialNetworkHandler handler = new FinancialNetworkHandler();
        sax.parse(xmlStream, handler);
        return handler.getRoot();
    }
}
