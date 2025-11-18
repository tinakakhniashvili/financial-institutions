package com.solvd.financialinstitution;

import com.solvd.financialinstitution.domain.Bank;
import com.solvd.financialinstitution.domain.Customer;
import com.solvd.financialinstitution.domain.FinancialNetwork;
import com.solvd.financialinstitution.service.parsers.json.JsonReader;
import com.solvd.financialinstitution.persistence.BankDao;
import com.solvd.financialinstitution.persistence.CustomerDao;
import com.solvd.financialinstitution.persistence.impl.BankDaoImpl;
import com.solvd.financialinstitution.persistence.impl.CustomerDaoImpl;
import com.solvd.financialinstitution.service.BankService;
import com.solvd.financialinstitution.service.CustomerService;
import com.solvd.financialinstitution.service.impl.BankServiceImpl;
import com.solvd.financialinstitution.service.impl.CustomerServiceImpl;
import com.solvd.financialinstitution.service.parsers.XmlDomParser;
import com.solvd.financialinstitution.service.parsers.XmlValidator;
import com.solvd.financialinstitution.service.parsers.jaxb.JaxbReader;
import com.solvd.financialinstitution.service.parsers.sax.XmlSaxParser;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws Exception {
        validateXml();
        demoXmlParsers();
        demoJsonParsers();

        BankService bankService = createBankService();
        CustomerService customerService = createCustomerService();

        importNetworkFromJson(bankService);
        printAllCustomers(customerService);
        demoAdvancedServiceMethods(bankService, customerService);
    }

    private static BankService createBankService() {
        BankDao bankDao = new BankDaoImpl();
        return new BankServiceImpl(bankDao);
    }

    private static CustomerService createCustomerService() {
        CustomerDao customerDao = new CustomerDaoImpl();
        return new CustomerServiceImpl(customerDao);
    }

    private static void validateXml() throws Exception {
        try (InputStream xml = Main.class.getResourceAsStream("/network.xml");
             InputStream xsd = Main.class.getResourceAsStream("/network.xsd")) {

            new XmlValidator().validate(xml, xsd);
            System.out.println("XML validated against XSD");
        }
    }

    private static void demoXmlParsers() throws Exception {
        try (InputStream is = Main.class.getResourceAsStream("/network.xml")) {
            XmlDomParser domParser = new XmlDomParser();
            FinancialNetwork netDom = domParser.parse(is);
            System.out.println("DOM banks: " + (netDom.getBanks() == null ? 0 : netDom.getBanks().size()));
        }

        try (InputStream is = Main.class.getResourceAsStream("/network.xml")) {
            XmlSaxParser saxParser = new XmlSaxParser();
            FinancialNetwork netSax = saxParser.parse(is);
            System.out.println("SAX banks: " + (netSax.getBanks() == null ? 0 : netSax.getBanks().size()));
        }

        try (InputStream is = Main.class.getResourceAsStream("/network.xml")) {
            JaxbReader jaxb = new JaxbReader();
            FinancialNetwork netJaxb = jaxb.read(is);
            System.out.println("JAXB first bank: " +
                    (netJaxb.getBanks() != null && !netJaxb.getBanks().isEmpty()
                            ? netJaxb.getBanks().get(0).getName()
                            : "<none>"));
        }
    }

    private static void demoJsonParsers() throws Exception {
        JsonReader jsonReader = new JsonReader();
        try (InputStream is = Main.class.getResourceAsStream("/network.json")) {
            FinancialNetwork netJson = jsonReader.read(is);
            String firstCustomerName = "<none>";
            if (netJson.getBanks() != null
                    && !netJson.getBanks().isEmpty()
                    && netJson.getBanks().get(0).getCustomers() != null
                    && !netJson.getBanks().get(0).getCustomers().isEmpty()) {
                firstCustomerName = netJson.getBanks().get(0).getCustomers().get(0).getFullName();
            }
            System.out.println("JSON first customer: " + firstCustomerName);
        }

        jsonReader.demoJsonPath("/network.json");
    }

    private static void importNetworkFromJson(BankService bankService) throws Exception {
        JsonReader jsonReader = new JsonReader();
        try (InputStream is = Main.class.getResourceAsStream("/network.json")) {
            FinancialNetwork netForDb = jsonReader.read(is);

            if (netForDb.getBanks() == null || netForDb.getBanks().isEmpty()) {
                System.out.println("No banks found in JSON, skipping DB import.");
                return;
            }

            for (Bank bank : netForDb.getBanks()) {
                bankService.createWithBranchesAndAddresses(bank);
                System.out.println("Inserted bank: " + bank.getName() +
                        " (id=" + bank.getId() + ")");
            }
        }
    }

    private static void printAllCustomers(CustomerService customerService) {
        System.out.println("---- All customers from DB ----");
        List<Customer> customers = customerService.getAll();
        for (Customer c : customers) {
            System.out.println(c.getId() + " | " + c.getFullName() + " | " + c.getBirthDate());
        }
    }

    private static void demoAdvancedServiceMethods(BankService bankService,
                                                   CustomerService customerService) {

        System.out.println("---- Advanced service methods demo ----");

        long exampleBankId = 1L;
        Optional<Bank> bankOpt = bankService.getByIdWithBranchesAndAddresses(exampleBankId);
        bankOpt.ifPresent(bank -> {
            System.out.println("Bank with details: " + bank.getName());
            if (bank.getBranches() != null) {
                bank.getBranches().forEach(br ->
                        System.out.println("  Branch " + br.getCode() +
                                " | addressId=" + br.getAddressId()));
            }
        });

        long exampleCustomerId = 1L;
        Optional<Customer> custOpt = customerService.getByIdWithAccountsAndLoans(exampleCustomerId);
        custOpt.ifPresent(c -> {
            System.out.println("Customer with details: " + c.getFullName());

            if (c.getAccounts() != null) {
                System.out.println("  Accounts: " + c.getAccounts().size());
            }
            if (c.getLoans() != null) {
                System.out.println("  Loans: " + c.getLoans().size());
            }
        });

        String city = "Tbilisi";
        List<Customer> customersInCity = customerService.getByCity(city);
        System.out.println("Customers in " + city + ": " + customersInCity.size());
        for (Customer c : customersInCity) {
            System.out.println("  " + c.getId() + " | " + c.getFullName());
        }
    }
}
