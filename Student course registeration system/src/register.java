import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class register extends JDialog {
    private JFormattedTextField header;
    private JComboBox comboBox1;
    private JPanel mypanel;
    private JButton okButton;

    private final String DB_Url = "jdbc:mysql://localhost/Student_System?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Boody_500";
    public register(JFrame parent,String SID){
        super(parent);
        setTitle("register course");
        setContentPane(mypanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        load_courses(SID);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register(SID);
                dispose();
                main_page m = new main_page(null,SID);
            }
        });
        setVisible(true);
    }

    void load_courses(String SID){
        try (Connection con = DriverManager.getConnection(DB_Url, USERNAME, PASSWORD)) {
            PreparedStatement p = con.prepareStatement("select name from Courses where capacity > 0");

            ResultSet res = p.executeQuery();
            String t = "";
            if (!res.next()){
                t = "no avalible courses";
                comboBox1.addItem(t);
            }
            else{
                comboBox1.addItem(res.getString(1));
                while (res.next())
                    comboBox1.addItem(res.getString(1));


                try(Connection conn = DriverManager.getConnection(DB_Url, USERNAME, PASSWORD)){
                    PreparedStatement pp = conn.prepareStatement("select cname from Student_Courses where SID = ?");
                    pp.setString(1,SID);
                    ResultSet ress = pp.executeQuery();


                    while (ress.next()){
                        comboBox1.removeItem(ress.getString(1));
                    }



                }
                catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }




        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void register(String SID){

        if (!comboBox1.getSelectedItem().toString().isEmpty()){
            try (Connection con = DriverManager.getConnection(DB_Url, USERNAME, PASSWORD)) {
                PreparedStatement p = con.prepareStatement("select CID from courses where name = ? ");
                p.setString(1,comboBox1.getSelectedItem().toString());

                ResultSet res = p.executeQuery();
                res.next();
                String CID = res.getString(1);

                try(Connection conn = DriverManager.getConnection(DB_Url, USERNAME, PASSWORD)){
                    PreparedStatement pp = con.prepareStatement("insert into student_courses (SID,CID,cname) values (?,?,?) ");
                    pp.setString(1,SID);
                    pp.setString(2,CID);
                    pp.setString(3,comboBox1.getSelectedItem().toString());

                    int x = pp.executeUpdate();

                    if (x > 0){

                        try(Connection connn = DriverManager.getConnection(DB_Url, USERNAME, PASSWORD)){
                            PreparedStatement ppp = con.prepareStatement("update courses set capacity = capacity - 1 where CID = ?");
                            ppp.setString(1,CID);

                            int xx = ppp.executeUpdate();

                            if (xx > 0)
                                System.out.println("done");




                        }
                    }




                }





            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        else
            JOptionPane.showMessageDialog(null,"no avalible courses");

    }
}
