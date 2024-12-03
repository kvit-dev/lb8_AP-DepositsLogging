package bank;

import deposit.Deposit;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private String name;
    private List<Deposit> deposits = new ArrayList<>();

    public Bank(String name) {
        this.name = name;
    }

    public String getName() {return name;}
    public List<Deposit> getDeposits() {return deposits;}

    public void addDeposit(Deposit deposit) {deposits.add(deposit);
    }

    public void removeDeposit(Deposit deposit) {deposits.remove(deposit);
    }
}
