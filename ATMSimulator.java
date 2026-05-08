import java.util.Scanner;

// 1. Model the User Account
class Account {
    private String accountNumber;
    private String pin;
    private double balance;

    public Account(String accountNumber, String pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = initialBalance;
    }

    public boolean validatePin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Successfully deposited: $" + amount);
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Successfully withdrew: $" + amount);
            return true;
        } else {
            System.out.println("Insufficient funds or invalid amount.");
            return false;
        }
    }
}

// 2. Manage ATM Operations and Session States
class ATM {
    private Account currentAccount;
    private boolean isAuthenticated;
    private Scanner scanner;

    public ATM(Account account) {
        this.currentAccount = account;
        this.isAuthenticated = false;
        this.scanner = new Scanner(System.in);
    }

    // State Transition: IDLE -> AUTHENTICATED
    public void start() {
        System.out.println("--- Welcome to the Java Bank ATM ---");
        System.out.print("Please enter your PIN: ");
        String inputPin = scanner.nextLine();

        if (currentAccount.validatePin(inputPin)) {
            isAuthenticated = true;
            System.out.println("Login Successful!\n");
            showMenu();
        } else {
            System.out.println("Incorrect PIN. Access Denied.");
        }
    }

    private void showMenu() {
        while (isAuthenticated) {
            System.out.println("--- Main Menu ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit / Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Current Balance: $" + currentAccount.getBalance());
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    double dAmount = scanner.nextDouble();
                    currentAccount.deposit(dAmount);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double wAmount = scanner.nextDouble();
                    currentAccount.withdraw(wAmount);
                    break;
                case 4:
                    logout();
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
            System.out.println();
        }
    }

    // State Transition: AUTHENTICATED -> IDLE
    private void logout() {
        isAuthenticated = false;
        System.out.println("Session ended. Please take your card.");
    }
}

// 3. Entry Point
public class ATMSimulator {
    public static void main(String[] args) {
        // Mocking a single account for simulation
        Account myAccount = new Account("123456", "1234", 1000.00);
        
        ATM atm = new ATM(myAccount);
        atm.start();
    }
}