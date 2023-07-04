import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Services service = new Services();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your ATM card number : ");
        int atmNo = sc.nextInt();
        System.out.println("Enter your PIN : ");
        int atmPin = sc.nextInt();
        int choice;
        while (service.authenticate(atmNo, atmPin)) {
            System.out.println("Press 1 for Check Balance\nPress 2 for Balance Deposit");
            System.out.println("Press 3 for Balance Withdraw\nPress 4 for Changing The Pin");
            System.out.println("Press 5 for Fund Transfer\nPress 0 for Exit");
            choice = sc.nextInt();
            if (choice == 0){
                break;
            }
            switch (choice) {
                case 1 -> service.checkBalance();
                case 2 -> service.deposit();
                case 3 -> service.withdraw();
                case 4 -> service.changePin();
                case 5 -> service.fundTransfer();
                default -> {
                }
            }
        }
    }
}
