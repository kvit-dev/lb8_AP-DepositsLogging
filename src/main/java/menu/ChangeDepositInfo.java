package menu;

import bank.Bank;
import deposit.Deposit;
import utils.LoggingApp;
import utils.Utils;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangeDepositInfo implements MenuCommand {
    private static final Logger logger = LoggingApp.getLogger();

    private List<Bank> banks;

    public ChangeDepositInfo(List<Bank> banks) {
        this.banks = banks;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        try {
            Bank selectedBank = Utils.selectBank(scanner, banks);
            if (selectedBank == null) {
                return;
            }

            logger.info("Selected bank: " + selectedBank.getName());

            if (selectedBank.getDeposits().isEmpty()) {
                System.out.println("\nThe bank has no deposits to modify");
                logger.warning("No deposits available in bank " + selectedBank.getName());
                return;
            }

            Deposit selectedDeposit = Utils.selectDeposit(scanner, selectedBank);
            if (selectedDeposit == null) {
                return;
            }
            logger.info("Selected deposit: " + selectedDeposit.getClass().getSimpleName());

            System.out.println("\nChanging deposit info: " + selectedDeposit.getClass().getSimpleName());
            System.out.print("Enter new interest rate (current: " + selectedDeposit.getInterestRate() + "): ");
            double newInterestRate = Utils.readPositiveDouble(scanner);

            System.out.print("Enter new term in months (current: " + selectedDeposit.getTerm() + "): ");
            int newTerm = Utils.readPositiveInt(scanner);

            System.out.print("Allow early replenish? (yes/no, current: " +
                    (selectedDeposit.isReplenishable() ? "yes" : "no") + "): ");
            boolean newReplenishable = Utils.getBooleanInput(scanner);

            if (newInterestRate == selectedDeposit.getInterestRate() &&
                    newTerm == selectedDeposit.getTerm() &&
                    newReplenishable == selectedDeposit.isReplenishable()) {
                System.out.println("\nThe new data is equal to the current one, no updates have been made");
                logger.info("No changes made to deposit.");
                return;
            }

            updateDeposit(selectedDeposit, newInterestRate, newTerm, newReplenishable);
            logger.info("Updated deposit: " + selectedDeposit);
            System.out.println("\nDeposit is updated");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred while modifying deposit info", e);
        }
    }

    public void updateDeposit(Deposit deposit, double newInterestRate, int newTerm, boolean newReplenishable) {
        deposit.setInterestRate(newInterestRate);
        deposit.setTerm(newTerm);
        deposit.setReplenishable(newReplenishable);
        logger.fine("Deposit updated: interestRate=" + newInterestRate +
                ", term=" + newTerm + ", replenishable=" + newReplenishable);
    }
}
