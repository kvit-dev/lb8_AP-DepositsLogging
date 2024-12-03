package deposit;

public class Deposit {
    private double amount;
    private double interestRate;
    private int term;
    private boolean isWithdrawable;
    private boolean isReplenishable;

    public Deposit(double amount, double interestRate, int term, boolean isWithdrawable, boolean isReplenishable) {
        this.amount = amount;
        this.interestRate = interestRate;
        this.term = term;
        this.isWithdrawable = isWithdrawable;
        this.isReplenishable = isReplenishable;
    }

    public double getAmount() {return amount;}
    public double getInterestRate() {return interestRate;}
    public int getTerm() {return term;}
    public boolean isWithdrawable() {return isWithdrawable;}
    public boolean isReplenishable() {return isReplenishable;}

    public void setInterestRate(double newInterestRate) {
        this.interestRate = newInterestRate;
    }

    public void setTerm(int newTerm) {
        this.term = newTerm;
    }

    public void setReplenishable(boolean newReplenishable) {
        this.isReplenishable = newReplenishable;
    }
}