package deposit;

public class SpecialDeposit extends Deposit {
    public SpecialDeposit(double amount, double interestRate, int term, boolean isWithdrawable, boolean isReplenishable) {
        super(amount, interestRate, term, isWithdrawable, isReplenishable);
    }

    @Override
    public String toString() {
        return "Special Deposit: amount: " + getAmount() +
                ", interest rate: " + getInterestRate() +
                ", term: " + getTerm() +
                ", early withdrawal (yes/no): " + (isWithdrawable() ? "yes" : "no") +
                ", can be replenished (yes/no): " + (isReplenishable() ? "yes" : "no");
    }
}