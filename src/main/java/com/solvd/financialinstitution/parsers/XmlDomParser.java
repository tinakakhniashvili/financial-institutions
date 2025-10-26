package com.solvd.financialinstitution.parsers;

import com.solvd.financialinstitution.model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XmlDomParser {
    public FinancialNetwork parse(InputStream is) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
        dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        dbf.setXIncludeAware(false);
        dbf.setExpandEntityReferences(false);
        Document doc = dbf.newDocumentBuilder().parse(is);
        doc.getDocumentElement().normalize();
        FinancialNetwork network = new FinancialNetwork();
        network.setGeneratedOn(LocalDate.parse(text(doc.getDocumentElement(), "generatedOn")));
        network.setLastUpdated(LocalDateTime.parse(text(doc.getDocumentElement(), "lastUpdated")));
        List<Bank> banks = new ArrayList<>();
        NodeList bankNodes = doc.getElementsByTagName("bank");
        for (int i = 0; i < bankNodes.getLength(); i++) {
            Element bEl = (Element) bankNodes.item(i);
            Bank bank = new Bank();
            bank.setName(text(bEl, "name"));
            bank.setActive(Boolean.parseBoolean(text(bEl, "active")));
            bank.setAddress(readAddress(child(bEl, "address")));
            bank.setBranches(readBranches(child(bEl, "branches")));
            bank.setCustomers(readCustomers(child(bEl, "customers")));
            banks.add(bank);
        }
        network.setBanks(banks);
        return network;
    }

    private List<Customer> readCustomers(Element customersEl) {
        if (customersEl == null) return Collections.emptyList();
        NodeList nodes = customersEl.getElementsByTagName("customer");
        List<Customer> list = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Element el = (Element) nodes.item(i);
            Customer c = new Customer();
            c.setId(text(el, "id"));
            c.setFullName(text(el, "fullName"));
            c.setBirthDate(LocalDate.parse(text(el, "birthDate")));
            c.setAccounts(readAccounts(child(el, "accounts")));
            list.add(c);
        }
        return list;
    }

    private List<Account> readAccounts(Element accountsEl) {
        if (accountsEl == null) return Collections.emptyList();
        NodeList nodes = accountsEl.getElementsByTagName("account");
        List<Account> list = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Element el = (Element) nodes.item(i);
            Account a = new Account();
            a.setIban(text(el, "iban"));
            a.setBalance(new BigDecimal(text(el, "balance")));
            a.setType(Enum.valueOf(AccountType.class, text(el, "type")));
            a.setOpenedOn(LocalDate.parse(text(el, "openedOn")));
            a.setTransactions(readTransactions(child(el, "transactions")));
            list.add(a);
        }
        return list;
    }

    private List<Transaction> readTransactions(Element txsEl) {
        if (txsEl == null) return Collections.emptyList();
        NodeList nodes = txsEl.getElementsByTagName("transaction");
        List<Transaction> list = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Element el = (Element) nodes.item(i);
            Transaction t = new Transaction();
            t.setId(text(el, "id"));
            t.setAmount(new BigDecimal(text(el, "amount")));
            t.setTimestamp(LocalDateTime.parse(text(el, "timestamp")));
            t.setKind(Enum.valueOf(TransactionType.class, text(el, "kind")));
            list.add(t);
        }
        return list;
    }

    private List<Branch> readBranches(Element branchesEl) {
        if (branchesEl == null) return Collections.emptyList();
        NodeList nodes = branchesEl.getElementsByTagName("branch");
        List<Branch> list = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Element el = (Element) nodes.item(i);
            Branch br = new Branch();
            br.setCode(text(el, "code"));
            br.setAddress(readAddress(child(el, "address")));
            br.setEmployees(readEmployees(child(el, "employees")));
            list.add(br);
        }
        return list;
    }

    private List<Employee> readEmployees(Element empsEl) {
        if (empsEl == null) return Collections.emptyList();
        NodeList nodes = empsEl.getElementsByTagName("employee");
        List<Employee> list = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Element el = (Element) nodes.item(i);
            Employee e = new Employee();
            e.setId(text(el, "id"));
            e.setFullName(text(el, "fullName"));
            e.setHiredDate(LocalDate.parse(text(el, "hiredDate")));
            e.setManager(Boolean.parseBoolean(text(el, "manager")));
            list.add(e);
        }
        return list;
    }

    private Address readAddress(Element addrEl) {
        if (addrEl == null) return null;
        Address a = new Address();
        a.setCountry(text(addrEl, "country"));
        a.setCity(text(addrEl, "city"));
        a.setLine1(text(addrEl, "line1"));
        a.setZip(text(addrEl, "zip"));
        return a;
    }

    private static String text(Element parent, String tag) {
        Element el = child(parent, tag);
        return el != null && el.getFirstChild() != null ? el.getFirstChild().getNodeValue() : null;
    }

    private static Element child(Element parent, String tag) {
        if (parent == null) return null;
        NodeList nodes = parent.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) n;
                if (tag.equals(e.getTagName())) return e;
            }
        }
        return null;
    }
}
