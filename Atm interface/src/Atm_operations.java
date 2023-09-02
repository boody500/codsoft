import java.sql.*;

public class Atm_operations {
    private final String DB_Url = "jdbc:mysql://localhost/Atm?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Boody_500";

    private String CardNo;
    private int balance = 0;
    public Atm_operations(String s){
        CardNo = s;
    }
    public int get_balance(){
        int temp = 0;
        try(Connection con = DriverManager.getConnection(DB_Url,USERNAME,PASSWORD)){
            PreparedStatement p = con.prepareStatement("select balance from account where CardNo = ?");
            p.setString(1,CardNo);

            ResultSet res = p.executeQuery();
            res.next();

            temp = res.getInt(1);
            balance = temp;

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return temp;
    }


    public int deposit(int b){

        try(Connection con = DriverManager.getConnection(DB_Url,USERNAME,PASSWORD)){
            PreparedStatement p = con.prepareStatement("update account set balance = ? where CardNo = ?");
            p.setInt(1,balance+b);
            p.setString(2,CardNo);

            int res = p.executeUpdate();
            if (res == 1){

                balance += b;
            }


        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return balance;
    }



    public int withdraw(int b){

        try(Connection con = DriverManager.getConnection(DB_Url,USERNAME,PASSWORD)){
            PreparedStatement p = con.prepareStatement("update account set balance = ? where CardNo = ?");
            p.setInt(1,balance-b);
            p.setString(2,CardNo);

            int res = p.executeUpdate();
            if (res == 1)
                balance -= b;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return balance;
    }
}
