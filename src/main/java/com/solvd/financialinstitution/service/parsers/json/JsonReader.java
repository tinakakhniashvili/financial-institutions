package com.solvd.financialinstitution.service.parsers.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.solvd.financialinstitution.domain.FinancialNetwork;

import java.io.InputStream;
import java.util.List;

public class JsonReader {
    public FinancialNetwork read(InputStream is) throws Exception {
        ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());
        return om.readValue(is, FinancialNetwork.class);
    }

    public void demoJsonPath(String resourcePath) throws Exception {
        Configuration conf = Configuration.defaultConfiguration().addOptions(Option.SUPPRESS_EXCEPTIONS);
        try (InputStream is = JsonReader.class.getResourceAsStream(resourcePath)) {
            Object doc = JsonPath.using(conf).parse(is).json();
            List<String> bankNames = JsonPath.read(doc, "$.banks[*].name");
            List<String> ibans = JsonPath.read(doc, "$..accounts[*].iban");
            List<Object> bigTx = JsonPath.read(doc, "$..transactions[?(@.amount > 100)]");
            List<String> mgrs = JsonPath.read(doc, "$..employees[?(@.manager==true)].fullName");
            Object firstCustomer = JsonPath.read(doc, "$.banks[0].customers[0].fullName");
            System.out.println("Banks: " + bankNames);
            System.out.println("IBANs: " + ibans);
            System.out.println("Tx>100: " + bigTx);
            System.out.println("Managers: " + mgrs);
            System.out.println("First customer: " + firstCustomer);
        }
    }
}
