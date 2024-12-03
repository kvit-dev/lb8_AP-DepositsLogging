package main;

import bank.Bank;
import bank.CloverBank;
import bank.SuperBank;
import menu.MainMenu;
import utils.FileRead;
import utils.LoggingApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = LoggingApp.getLogger();

    public static void main(String[] args) {
        try {
            logger.info("Log started");

            CloverBank cloverBank = new CloverBank();
            SuperBank superBank = new SuperBank();

            List<Bank> banks = new ArrayList<>();
            banks.add(cloverBank);
            banks.add(superBank);

            String fileName = "src/deposits.txt";
            FileRead fileReader = new FileRead();

            logger.info("Reading data from file: " + fileName);
            fileReader.readFromFile(fileName, banks);

            MainMenu menu = new MainMenu(banks);
            Scanner sc = new Scanner(System.in);

            while (true) {
                menu.displayMenu();
                System.out.println("\nWrite your option: ");
                String command = sc.nextLine();
                logger.info("User selected option: " + command);
                menu.executeCommand(command);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "A critical error occurred!", e);
        } finally {
            logger.info("Application is shutting down");
        }
    }
}
