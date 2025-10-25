package com.solvd.financialinstitution.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import com.solvd.financialinstitution.model.FinancialNetwork;

import java.io.InputStream;
import java.util.List;

public class JsonReader {
    public FinancialNetwork read(InputStream is) throws Exception {
        ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());
        return om.readValue(is, FinancialNetwork.class);
    }

    public void demoJsonPath(InputStream is) {
        Object doc = JsonPath.parse(is).json();
        List<String> bankNames = JsonPath.read(doc, "$.banks[*].name");
        List<String> ibans = JsonPath.read(doc, "$..accounts[*].iban");
        List<Object> bigTx = JsonPath.read(doc, "$..transactions[?(@.amount > 100)]");
        List<String> mgrs = JsonPath.read(doc, "$..employees[?(@.manager==true)].fullName");
        String firstCustomer = JsonPath.read(doc, "$.banks[0].customers[0].fullName");
        System.out.println("Banks: " + bankNames);
        System.out.println("IBANs: " + ibans);
        System.out.println("Tx>100: " + bigTx);
        System.out.println("Managers: " + mgrs);
        System.out.println("First customer: " + firstCustomer);
    }
}
