import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
public class account {
    private final String DB_Url = "jdbc:mysql://localhost/Atm?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Boody_500";


    public boolean Activate(String cardno){
        boolean f = true;
        try(Connection con = DriverManager.getConnection(DB_Url,USERNAME,PASSWORD)){

            PreparedStatement s = con.prepareStatement("select CardNo from account where CardNo = ?");
            s.setString(1,cardno);

            ResultSet res = s.executeQuery();

            if (!res.next())
                f = false;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return f;

    }

    public boolean add_account(String cardno,String pass){
        try(Connection con = DriverManager.getConnection(DB_Url,USERNAME,PASSWORD))
        {
            PreparedStatement p = con.prepareStatement("insert into account (CardNo,password) values (?,?)");
            p.setString(1,cardno);
            p.setString(2,pass);
            int res = p.executeUpdate();

            if (res == 1)
                return true;

            else
                return false;

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return true;
    }

    public boolean check_account_password(String cardno,String pass){
        try(Connection con = DriverManager.getConnection(DB_Url,USERNAME,PASSWORD))
        {
            PreparedStatement p = con.prepareStatement("select password from account where CardNo = ?");
            p.setString(1,cardno);
            ResultSet res = p.executeQuery();
            res.next();

            if (res.getString(1).equals(pass))
                return true;

            else return false;


        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return true;
    }
}
