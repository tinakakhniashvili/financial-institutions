package com.solvd.financialinstitution;

import com.solvd.financialinstitution.jaxb.JaxbReader;
import com.solvd.financialinstitution.json.JsonReader;
import com.solvd.financialinstitution.model.FinancialNetwork;
import com.solvd.financialinstitution.parsers.XmlDomParser;
import com.solvd.financialinstitution.parsers.XmlValidator;
import com.solvd.financialinstitution.parsers.sax.XmlSaxParser;

public class Main {
    public static void main(String[] args) throws Exception {
        try (var xml = Main.class.getResourceAsStream("/network.xml");
             var xsd = Main.class.getResourceAsStream("/network.xsd")) {
            new XmlValidator().validate(xml, xsd);
            System.out.println("XML validated against XSD");
        }
        try (var is = Main.class.getResourceAsStream("/network.xml")) {
            var domParser = new XmlDomParser();
            FinancialNetwork net1 = domParser.parse(is);
            System.out.println("DOM banks: " + (net1.getBanks() == null ? 0 : net1.getBanks().size()));
        }
        try (var is = Main.class.getResourceAsStream("/network.xml")) {
            var saxParser = new XmlSaxParser();
            FinancialNetwork netSax = saxParser.parse(is);
            System.out.println("SAX banks: " + (netSax.getBanks() == null ? 0 : netSax.getBanks().size()));
        }
        try (var is = Main.class.getResourceAsStream("/network.xml")) {
            var jaxb = new JaxbReader();
            FinancialNetwork net2 = jaxb.read(is);
            System.out.println("JAXB first bank: " + net2.getBanks().get(0).getName());
        }
        var jsonReader = new JsonReader();
        try (var is = Main.class.getResourceAsStream("/network.json")) {
            FinancialNetwork net3 = jsonReader.read(is);
            System.out.println("JSON first customer: " + net3.getBanks().get(0).getCustomers().get(0).getFullName());
        }
        new JsonReader().demoJsonPath("/network.json");
    }
}
