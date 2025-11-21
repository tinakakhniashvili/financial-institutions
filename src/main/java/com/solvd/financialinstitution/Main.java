package com.solvd.financialinstitution;

import com.solvd.financialinstitution.facade.BankingFacade;
import com.solvd.financialinstitution.listener.LoggingLoanCreatedListener;
import com.solvd.financialinstitution.persistence.factory.DaoFactory;
import com.solvd.financialinstitution.persistence.factory.DaoFactories;
import com.solvd.financialinstitution.service.AddressService;
import com.solvd.financialinstitution.service.BankService;
import com.solvd.financialinstitution.service.BranchService;
import com.solvd.financialinstitution.service.CustomerService;
import com.solvd.financialinstitution.service.LoanService;
import com.solvd.financialinstitution.service.decorator.LoggingLoanServiceDecorator;
import com.solvd.financialinstitution.service.impl.AddressServiceImpl;
import com.solvd.financialinstitution.service.impl.BankServiceImpl;
import com.solvd.financialinstitution.service.impl.BranchServiceImpl;
import com.solvd.financialinstitution.service.impl.CustomerServiceImpl;
import com.solvd.financialinstitution.service.impl.LoanServiceImpl;
import com.solvd.financialinstitution.service.parsers.XmlDomParser;
import com.solvd.financialinstitution.service.parsers.XmlValidator;
import com.solvd.financialinstitution.service.parsers.jaxb.JaxbReader;
import com.solvd.financialinstitution.service.parsers.json.JsonReader;
import com.solvd.financialinstitution.service.parsers.sax.XmlSaxParser;
import com.solvd.financialinstitution.service.proxy.SecurityBankServiceProxy;

import java.io.InputStream;

public class Main {

    public static void main(String[] args) throws Exception {
        validateXml();
        demoXmlParsers();
        demoJsonParsers();

        DaoFactory daoFactory = DaoFactories.create("mybatis");

        AddressService addressService =
                new AddressServiceImpl(daoFactory.createAddressDao());
        BranchService branchService =
                new BranchServiceImpl(daoFactory.createBranchDao(), addressService);

        BankService coreBankService =
                new BankServiceImpl(daoFactory.createBankDao(), branchService);
        BankService bankService =
                new SecurityBankServiceProxy(coreBankService, false);

        CustomerService customerService =
                new CustomerServiceImpl(daoFactory.createCustomerDao());

        LoanService coreLoanService =
                new LoanServiceImpl(daoFactory.createLoanDao());
        LoanService loanService =
                new LoggingLoanServiceDecorator(coreLoanService);
        loanService.addLoanCreatedListener(new LoggingLoanCreatedListener());

        BankingFacade facade = new BankingFacade(
                bankService,
                customerService,
                branchService,
                addressService,
                loanService
        );

        try (InputStream is = Main.class.getResourceAsStream("/network.json")) {
            if (is != null) {
                facade.importNetworkFromJson(is);
            } else {
                System.out.println("network.json not found on classpath, skipping DB import.");
            }
        }

        facade.printAllCustomers();
        facade.demoAdvancedServiceMethods();
    }

    private static void validateXml() throws Exception {
        try (InputStream xml = Main.class.getResourceAsStream("/network.xml");
             InputStream xsd = Main.class.getResourceAsStream("/network.xsd")) {

            if (xml == null || xsd == null) {
                System.out.println("XML or XSD not found, skipping validation");
                return;
            }

            new XmlValidator().validate(xml, xsd);
            System.out.println("XML validated against XSD");
        }
    }

    private static void demoXmlParsers() throws Exception {
        try (InputStream is = Main.class.getResourceAsStream("/network.xml")) {
            if (is == null) {
                System.out.println("network.xml not found, skipping DOM demo");
            } else {
                XmlDomParser domParser = new XmlDomParser();
                var netDom = domParser.parse(is);
                System.out.println("DOM banks: " + (netDom.getBanks() == null ? 0 : netDom.getBanks().size()));
            }
        }

        try (InputStream is = Main.class.getResourceAsStream("/network.xml")) {
            if (is == null) {
                System.out.println("network.xml not found, skipping SAX demo");
            } else {
                XmlSaxParser saxParser = new XmlSaxParser();
                var netSax = saxParser.parse(is);
                System.out.println("SAX banks: " + (netSax.getBanks() == null ? 0 : netSax.getBanks().size()));
            }
        }

        try (InputStream is = Main.class.getResourceAsStream("/network.xml")) {
            if (is == null) {
                System.out.println("network.xml not found, skipping JAXB demo");
            } else {
                JaxbReader jaxb = new JaxbReader();
                var netJaxb = jaxb.read(is);
                System.out.println("JAXB first bank: " +
                        (netJaxb.getBanks() != null && !netJaxb.getBanks().isEmpty()
                                ? netJaxb.getBanks().get(0).getName()
                                : "<none>"));
            }
        }
    }

    private static void demoJsonParsers() throws Exception {
        JsonReader jsonReader = new JsonReader();
        try (InputStream is = Main.class.getResourceAsStream("/network.json")) {
            if (is == null) {
                System.out.println("network.json not found, skipping JSON demo");
                return;
            }

            var netJson = jsonReader.read(is);
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
}
