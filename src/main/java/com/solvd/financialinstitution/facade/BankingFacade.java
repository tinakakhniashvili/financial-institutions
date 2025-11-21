package com.solvd.financialinstitution.facade;

import com.solvd.financialinstitution.domain.Bank;
import com.solvd.financialinstitution.domain.Customer;
import com.solvd.financialinstitution.domain.FinancialNetwork;
import com.solvd.financialinstitution.domain.Loan;
import com.solvd.financialinstitution.domain.builder.LoanBuilder;
import com.solvd.financialinstitution.service.AddressService;
import com.solvd.financialinstitution.service.BankService;
import com.solvd.financialinstitution.service.BranchService;
import com.solvd.financialinstitution.service.CustomerService;
import com.solvd.financialinstitution.service.LoanService;
import com.solvd.financialinstitution.service.parsers.json.JsonReader;
import com.solvd.financialinstitution.strategy.SimpleInterestStrategy;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BankingFacade {

    private final BankService bankService;
    private final CustomerService customerService;
    private final BranchService branchService;
    private final AddressService addressService;
    private final LoanService loanService;

    public BankingFacade(BankService bankService,
                         CustomerService customerService,
                         BranchService branchService,
                         AddressService addressService,
                         LoanService loanService) {
        this.bankService = bankService;
        this.customerService = customerService;
        this.branchService = branchService;
        this.addressService = addressService;
        this.loanService = loanService;
    }

    public void importNetworkFromJson(InputStream jsonStream) throws Exception {
        JsonReader jsonReader = new JsonReader();
        FinancialNetwork netForDb = jsonReader.read(jsonStream);

        if (netForDb.getBanks() == null || netForDb.getBanks().isEmpty()) {
            System.out.println("No banks found in JSON, skipping DB import.");
            return;
        }

        for (Bank bank : netForDb.getBanks()) {
            bankService.create(bank);
            System.out.println("Inserted bank: " + bank.getName() +
                    " (id=" + bank.getId() + ")");
        }
    }

    public void printAllCustomers() {
        System.out.println("---- All customers from DB ----");
        List<Customer> customers = customerService.getAll();
        for (Customer c : customers) {
            System.out.println(c.getId() + " | " + c.getFullName() + " | " + c.getBirthDate());
        }
    }

    public void demoAdvancedServiceMethods() {
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

    public Loan createLoanWithSimpleInterest(BigDecimal principal,
                                             BigDecimal rate,
                                             LocalDate start,
                                             LocalDate end) {
        Loan loan = new LoanBuilder()
                .withPrincipal(principal)
                .withRate(rate)
                .withStart(start)
                .withEnd(end)
                .withInterestStrategy(new SimpleInterestStrategy())
                .build();

        loanService.create(loan);
        BigDecimal interest = loan.calculateInterest();
        System.out.println("Created loan id=" + loan.getId() + " interest=" + interest);
        return loan;
    }
}
