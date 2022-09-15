package auditory_exercises.aud03.bank;

import java.util.Arrays;

public class Bank {

    private Account[] accounts;
    private int totalAccounts;
    private int maxAccounts;

    public Bank(int maxAccounts) {
        this.maxAccounts = maxAccounts;
        accounts = new Account[maxAccounts];
        this.totalAccounts = 0;
    }

    public void addAccount(Account account) {
        if (totalAccounts == maxAccounts) {
            accounts = Arrays.copyOf(accounts, maxAccounts * 2);
            maxAccounts *= 2;
        }
        accounts[totalAccounts++] = account;
    }

    public double totalAssets() {
        double totalAssets = 0;
        for (Account account : accounts)
            totalAssets += account.getCurrentAmount();
        return totalAssets;
    }

    public void addInterestToAllAccounts() {
        for (Account account : accounts) {
            if (account.getAccountType() == AccountType.INTEREST) {
                InterestBearingAccount interestBearingAccount = (InterestBearingAccount) account;
                interestBearingAccount.addInterest();
            }
        }
    }
}
