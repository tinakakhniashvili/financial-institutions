package com.solvd.financialinstitution.parsers;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;

public class XmlValidator {
    public void validate(InputStream xml, InputStream xsd) throws Exception {
        var factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        var schema = factory.newSchema(new StreamSource(xsd));
        var validator = schema.newValidator();
        validator.validate(new StreamSource(xml));
    }
}
