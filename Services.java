import java.sql.SQLException;
import java.util.Scanner;

public class Services {
    private static BankDB db;
    Scanner sc ;
    public Services() throws SQLException, ClassNotFoundException {
        db = BankDB.getInstance();
        sc = new Scanner(System.in);
    }

    public boolean authenticate(int atmNo, int atmPin) throws SQLException {
        db.setAtmNo(atmNo);
        db.setAtmPin(atmPin);
        return db.authenticate(atmNo, atmPin);
    }
    public void checkBalance() throws SQLException {
        int balance = db.balance();
        System.out.println("Total Balance :\n " + balance + "$");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e){

        }
    }

    public void deposit() throws SQLException{
        System.out.println("Enter The Amount : ");
        int amount = sc.nextInt();
        while (amount <= 0){
            System.out.println("Enter valid Amount : ");
            amount = sc.nextInt();
        }
        db.deposit(amount);
    }

    public void withdraw() throws SQLException{
        System.out.println("Enter The Amount : ");
        int amount = sc.nextInt();
        while (amount <= 0){
            System.out.println("Enter valid Amount : ");
            amount = sc.nextInt();
        }
        db.withdraw(amount);
    }
    public void changePin() throws SQLException{
        System.out.println("Enter The New Pin : ");
        int pin = sc.nextInt();
        while (pin <= 999){
            System.out.println("Pin Must be at least Four Number ,Try Again");
            pin = sc.nextInt();
        }
        db.updatePin(pin);
        System.out.println("Thank You!");
        System.exit(0);
    }
    public void fundTransfer() throws SQLException{
        System.out.println("Enter The Recipient's Card Number : ");
        int atmNo = sc.nextInt();
        System.out.println("Enter The Amount : ");
        int amount = sc.nextInt();
        while (amount <= 0){
            System.out.println("Enter valid Amount : ");
            amount = sc.nextInt();
        }
        db.transfer(atmNo,amount);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
    }
}
