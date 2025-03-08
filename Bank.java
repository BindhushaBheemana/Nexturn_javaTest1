abstract class BankAccount {
    String accountNumber;
    double balance;

    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited " + amount + ", New balance: " + balance);
    }
    abstract boolean withdraw(double amount);
    public double getBalance() {
        return balance;
    }
}

interface Transaction {
    boolean transfer(BankAccount toAccount, double amount);
}

class SavingsAccount extends BankAccount implements Transaction {
    public SavingsAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    boolean withdraw(double amount) {
        if (balance - amount >= 500) {
            balance -= amount;
            System.out.println("Withdrawal of " + amount + " successful, new balance: " + balance);
            return true;
        }
        System.out.println("Withdrawal failed, minimum 500 balance required.");
        return false;
    }

    @Override
    public boolean transfer(BankAccount toAccount, double amount) {
        if (withdraw(amount)) {
            toAccount.deposit(amount);
            System.out.println("Transfer of " + amount + " successful. New savings balance: " + balance +
                    ", New target account balance: " + toAccount.getBalance());
            return true;
        }
        return false;
    }
}

class CurrentAccount extends BankAccount implements Transaction {
    private static final double OVERDRAFT_LIMIT = -5000;

    public CurrentAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    boolean withdraw(double amount) {
        if (balance - amount >= OVERDRAFT_LIMIT) {
            balance -= amount;
            System.out.println("Withdrawal of " + amount + " successful, new balance: " + balance);
            return true;
        }
        System.out.println("Withdrawal failed, overdraft limit exceeded.");
        return false;
    }

    @Override
    public boolean transfer(BankAccount toAccount, double amount) {
        if (withdraw(amount)) {
            toAccount.deposit(amount);
            System.out.println("Transfer of " + amount + " successful. New current balance: " + balance +
                    ", New target account balance: " + toAccount.getBalance());
            return true;
        }
        return false;
    }
}

public class Bank {
    public static void main(String[] args) {
        BankAccount savings = new SavingsAccount("SAV123", 5000);
        BankAccount current = new CurrentAccount("CUR456", 2000);
        savings.deposit(1000);
        current.withdraw(3000); 
        ((Transaction) savings).transfer(current, 1500);
        ((Transaction) current).transfer(savings, 6000);
    }
}
