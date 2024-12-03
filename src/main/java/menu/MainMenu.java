package menu;

import bank.Bank;
import utils.LoggingApp;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenu {
    private static final Logger logger = LoggingApp.getLogger();
    public Map<String, Option> menuItems;

    public MainMenu(List<Bank> banks) {
        menuItems = new LinkedHashMap<>();
        menuItems.put("0", new Option("Exit program", null));
        menuItems.put("1", new Option("Add deposit", new AddDeposit(banks)));
        menuItems.put("2", new Option("Remove deposit", new RemoveDeposit(banks)));
        menuItems.put("3", new Option("Search deposits", new SearchDeposits(banks)));
        menuItems.put("4", new Option("Sort deposits", new SortDeposits(banks)));
        menuItems.put("5", new Option("Change deposit info", new ChangeDepositInfo(banks)));
    }

    public void displayMenu() {
        System.out.println("\n\tMenu");
        menuItems.forEach((key, option) ->
                System.out.println(key + "-" + option.getName()));
        logger.fine("Displayed main menu");
    }

    public void executeCommand(String command) {
        logger.info("Executing command: " + command);

        if ("0".equals(command)) {
            logger.info("Exiting program");
            System.out.println("Exiting program...");
            System.exit(0);
        }

        Option option = menuItems.getOrDefault(command, new Option("Invalid", () -> {
            System.out.println("Incorrect command was used");
            logger.warning("Invalid command entered: " + command);
            System.out.println("Available commands: " + getAvailableCommands());
        }));

        if (option.getCommand() != null) {
            try {
                option.getCommand().execute();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error while executing command: " + command, e);
            }
        } else {
            logger.warning("No action assigned to command: " + command);
            System.out.println("No action assigned");
        }
    }

    public Set<String> getAvailableCommands() {return menuItems.keySet();}
}