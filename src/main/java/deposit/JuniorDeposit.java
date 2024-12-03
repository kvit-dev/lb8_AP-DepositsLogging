package deposit;

public class JuniorDeposit extends Deposit{
    public JuniorDeposit(double amount, double interestRate, int term, boolean isWithdrawable, boolean isReplenishable) {
        super(amount, interestRate, term, isWithdrawable, isReplenishable);
    }

    @Override
    public String toString() {
        return "Junior Deposit: amount: " + getAmount() +
                ", interest rate: " + getInterestRate() +
                ", term: " + getTerm() +
                ", early withdrawal (yes/no): " + (isWithdrawable() ? "yes" : "no") +
                ", can be replenished (yes/no): " + (isReplenishable() ? "yes" : "no");
    }
}