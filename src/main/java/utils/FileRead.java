package utils;

import bank.Bank;
import deposit.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileRead {
    private static final Logger logger = LoggingApp.getLogger();
    private boolean criticalErrorLogged = false;

    public void readFromFile(String fileName, List<Bank> banks) {
        logger.info("Reading deposits from file: " + fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 7) {
                    if (!criticalErrorLogged) {
                        logger.log(Level.SEVERE, "Critical error: Inconsistent data in file: " + fileName);
                        criticalErrorLogged = true;
                    }
                    continue;
                }

                String bankName = parts[0].trim();
                String depositType = parts[1].trim();
                double amount = Double.parseDouble(parts[2].trim());
                double interestRate = Double.parseDouble(parts[3].trim());
                int term = Integer.parseInt(parts[4].trim());
                boolean isWithdrawable = parts[5].trim().equalsIgnoreCase("yes");
                boolean isReplenishable = parts[6].trim().equalsIgnoreCase("yes");

                Deposit deposit = createDeposit(depositType, amount, interestRate, term, isWithdrawable, isReplenishable);
                if (deposit != null) {
                    Bank bank = findBank(bankName, banks);
                    if (bank != null) {
                        addDepositIfUnique(bank, deposit);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            logger.log(Level.SEVERE, "Error while reading file: " + fileName, e);
        }
    }

    private Bank findBank(String bankName, List<Bank> banks) {
        for (Bank bank : banks) {
            if (bank.getName().equalsIgnoreCase(bankName)) {
                return bank;
            }
        }
        logger.warning("Bank not found: " + bankName);
        return null;
    }

    private Deposit createDeposit(String type, double amount, double rate, int term, boolean withdrawable, boolean replenishable) {
        switch (type) {
            case "Flexible Deposit":
                return new FlexibleDeposit(amount, rate, term, withdrawable, replenishable);
            case "Standard Deposit":
                return new StandartDeposit(amount, rate, term, withdrawable, replenishable);
            case "Junior Deposit":
                return new JuniorDeposit(amount, rate, term, withdrawable, replenishable);
            case "Special Deposit":
                return new SpecialDeposit(amount, rate, term, withdrawable, replenishable);
            default:
                logger.warning("Unknown deposit type: " + type);
                return null;
        }
    }

    private void addDepositIfUnique(Bank bank, Deposit deposit) {
        boolean exists = bank.getDeposits().stream()
                .anyMatch(d -> d.getClass().getSimpleName().equals(deposit.getClass().getSimpleName()));
        if (!exists) {
            bank.addDeposit(deposit);
            logger.fine("Deposit added: " + deposit);
        } else {
            logger.info("Deposit already exists, not added: " + deposit);
        }
    }
}
