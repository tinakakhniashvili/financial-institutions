package com.solvd.financialinstitution.jaxb;

import com.solvd.financialinstitution.model.FinancialNetwork;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.InputStream;

public class JaxbReader {
    public FinancialNetwork read(InputStream is) throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(FinancialNetwork.class);
        Unmarshaller um = ctx.createUnmarshaller();
        return (FinancialNetwork) um.unmarshal(is);
    }
}
