import java.sql.*;

public class BankDB {
    private int atmNo,atmPin;

    private Connection con;
    private static BankDB instance;

    private BankDB() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank",
                "root","1317");
    }

    public static BankDB getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null){
            instance = new BankDB();
        }
        return instance;
    }
    public boolean authenticate(int atmNo, int atmPin) throws SQLException {
        String query = "select pin from accounts where acc_no = ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1,atmNo);
        ResultSet set = statement.executeQuery();

        boolean verified = false;
        if (set.next()){
            if(set.getInt(1) == atmPin){
                verified = true;
                System.out.println("---------- Welcome! ---------------");
            } else {
                System.out.println("Incorrect PIN, Please Try Again");
            }
        } else {
            System.out.println("Invalid Card Number, Please Try Again");
        }
        return verified;
    }

    public int balance() throws SQLException{
        int bal;
        String query = "select balance from accounts where acc_no = ? AND pin = ?";
        PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, this.getAtmNo());
            statement.setInt(2,this.getAtmPin());
            ResultSet set = statement.executeQuery();
            set.next();
            bal = set.getInt(1);

        return bal;
    }

    public void deposit(int amount) throws SQLException{
        int newBal = amount + this.balance();
        String query = "update accounts set balance = ? where acc_no = ? AND pin = ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1,newBal);
        statement.setInt(2,this.getAtmNo());
        statement.setInt(3,this.getAtmPin());
        int row = statement.executeUpdate();
        System.out.println("Balance Deposited Successfully");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e){

        }
    }

    public void withdraw(int amount) throws SQLException {
        int currBal = this.balance();
        if (currBal < amount){
            System.out.println("Insufficient Balance");
            return;
        }
        int newBal = currBal - amount;
        String query = "update accounts set balance = ? where acc_no = ? AND pin = ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1,newBal);
        statement.setInt(2,this.getAtmNo());
        statement.setInt(3,this.getAtmPin());
        int row = statement.executeUpdate();
        System.out.println("Collect The Amount");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e){

        }
    }
    public void updatePin(int pin) throws SQLException {
        String query = "update accounts set pin = ? where acc_no = ? AND pin = ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1,pin);
        statement.setInt(2,this.getAtmNo());
        statement.setInt(3,this.getAtmPin());
        int row = statement.executeUpdate();
        this.setAtmPin(pin);
        System.out.println("Pin Successfully Updated!");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e){

        }
    }
    public void transfer(int cardNo,int amount) throws SQLException{
        int otherBal = searchCardNo(cardNo);
        if (otherBal < 0){
            System.out.println("Card Number Not Found");
            return;
        }
        int currBal = this.balance();
        if (currBal < amount){
            System.out.println("Insufficient Balance");
            return;
        }
        int newBal = currBal - amount;
        int newOtherBal = otherBal + amount;
        String query1 = "update accounts set balance = ? where acc_no = ? AND pin = ?";
        PreparedStatement statement1 = con.prepareStatement(query1);
        statement1.setInt(1,newBal);
        statement1.setInt(2,this.getAtmNo());
        statement1.setInt(3,this.getAtmPin());
        int row1 = statement1.executeUpdate();

        String query2 = "update accounts set balance = ? where acc_no = ? ";
        PreparedStatement statement2 = con.prepareStatement(query2);
        statement2.setInt(1,newOtherBal);
        statement2.setInt(2,cardNo);
        int row2 = statement2.executeUpdate();
        System.out.println("Balance Transferred Successfully");
    }
    private int searchCardNo(int cardNo) throws SQLException{
        int bal;
        String query = "select balance from accounts where acc_no = ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1,cardNo);
        ResultSet set = statement.executeQuery();
        if (set.next()){
            bal = set.getInt(1);
        } else {
            bal = -1;
        }
        return bal;
    }
    public void setAtmNo(int atmNo) {
        this.atmNo = atmNo;
    }

    public void setAtmPin(int atmPin) {
        this.atmPin = atmPin;
    }

    private int getAtmNo() {
        return atmNo;
    }

    private int getAtmPin() {
        return atmPin;
    }
}
