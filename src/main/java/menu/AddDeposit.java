package menu;

import bank.Bank;
import deposit.*;
import utils.LoggingApp;
import utils.Utils;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddDeposit implements MenuCommand {
    private static final Logger logger = LoggingApp.getLogger();
    public List<Bank> banks;

    public AddDeposit(List<Bank> banks) {
        this.banks = banks;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        Bank selectedBank = Utils.selectBank(scanner, banks);
        logger.info("Selected bank: " + selectedBank.getName());

        Deposit newDeposit = null;
        while (newDeposit == null) {
            try {
                System.out.println("\nSelect the type of deposit");
                if (selectedBank.getName().equals("Clover Bank")) {
                    System.out.println("1-Flexible Deposit");
                    System.out.println("2-Standard Deposit");
                } else if (selectedBank.getName().equals("Super Bank")) {
                    System.out.println("1-Junior Deposit");
                    System.out.println("2-Special Deposit");
                }
                System.out.print("Enter your choice: ");

                String typeChoice = scanner.nextLine();
                if (selectedBank.getName().equals("Clover Bank")) {
                    if ("1".equals(typeChoice)) {
                        newDeposit = new FlexibleDeposit(0, 0, 0, false, false);
                    } else if ("2".equals(typeChoice)) {
                        newDeposit = new StandartDeposit(0, 0, 0, false, false);
                    } else {
                        System.out.println("Wrong choice! Try again");
                        logger.warning("Invalid choice: " + typeChoice);
                    }
                } else if (selectedBank.getName().equals("Super Bank")) {
                    if ("1".equals(typeChoice)) {
                        newDeposit = new JuniorDeposit(0, 0, 0, false, false);
                    } else if ("2".equals(typeChoice)) {
                        newDeposit = new SpecialDeposit(0, 0, 0, false, false);
                    } else {
                        System.out.println("Wrong choice! Try again");
                        logger.warning("Invalid choice: " + typeChoice);
                    }
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error while selecting deposit type", e);
            }
        }

        try {
            System.out.print("\nEnter the deposit amount: ");
            double amount = Utils.readPositiveDouble(scanner);

            System.out.print("Enter the interest rate: ");
            double interestRate = Utils.readPositiveDouble(scanner);

            System.out.print("Enter the term (in months): ");
            int term = Utils.readPositiveInt(scanner);

            System.out.print("Is early withdrawal allowed? (yes/no): ");
            boolean isWithdrawable = Utils.getBooleanInput(scanner);

            System.out.print("Is replenishment allowed? (yes/no): ");
            boolean isReplenishable = Utils.getBooleanInput(scanner);

            newDeposit = createDeposit(newDeposit.getClass(), amount, interestRate, term, isWithdrawable, isReplenishable);

            selectedBank.addDeposit(newDeposit);
            logger.info("New deposit added to bank " + selectedBank.getName() + ": " + newDeposit);

            System.out.println("\nDeposit is successfully added to " + selectedBank.getName());

            System.out.println("\nCurrent list of deposits in " + selectedBank.getName() + ":");
            for (Deposit deposit : selectedBank.getDeposits()) {
                String withdrawal = deposit.isWithdrawable() ? "yes" : "no";
                String replenishment = deposit.isReplenishable() ? "yes" : "no";
                System.out.println("Type: " + deposit.getClass().getSimpleName() +
                        ", amount: " + deposit.getAmount() +
                        ", interest rate: " + deposit.getInterestRate() +
                        ", term: " + deposit.getTerm() +
                        ", withdrawal: " + withdrawal +
                        ", replenishment: " + replenishment);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while creating or adding deposit", e);
        }
    }

    public Deposit createDeposit(Class<? extends Deposit> depositType, double amount, double interestRate, int term, boolean isWithdrawable, boolean isReplenishable) {
        try {
            return depositType.getConstructor(double.class, double.class, int.class, boolean.class, boolean.class)
                    .newInstance(amount, interestRate, term, isWithdrawable, isReplenishable);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while creating deposit of type: " + depositType.getSimpleName(), e);
            return null;
        }
    }
}
