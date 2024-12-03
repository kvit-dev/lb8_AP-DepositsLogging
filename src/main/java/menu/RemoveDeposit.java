package menu;

import bank.Bank;
import deposit.Deposit;
import utils.LoggingApp;
import utils.Utils;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoveDeposit implements MenuCommand {
    private static final Logger logger = LoggingApp.getLogger();
    private List<Bank> banks;

    public RemoveDeposit(List<Bank> banks) {
        this.banks = banks;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        try {
            Bank selectedBank = Utils.selectBank(scanner, banks);
            logger.info("Selected bank: " + selectedBank.getName());

            if (selectedBank.getDeposits().isEmpty()) {
                System.out.println("\nThe bank has no deposits to remove");
                logger.warning("Bank " + selectedBank.getName() + " has no deposits to remove");
                return;
            }

            Deposit selectedDeposit = Utils.selectDeposit(scanner, selectedBank);
            logger.info("Selected deposit for removal: " + selectedDeposit);

            System.out.print("\nAre you sure you want to remove this deposit? (yes/no): ");
            if (scanner.nextLine().equalsIgnoreCase("yes")) {
                selectedBank.removeDeposit(selectedDeposit);
                logger.info("Removed deposit from bank " + selectedBank.getName());
                System.out.println("\nDeposit is removed from " + selectedBank.getName());
            } else {
                logger.info("Deposit removal cancelled for bank " + selectedBank.getName());
                System.out.println("\nDeposit removal is canceled!");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred during deposit removal", e);
        }
    }
}