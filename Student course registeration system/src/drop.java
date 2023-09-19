import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class drop extends JDialog{
    private JPanel mypanel;
    private JFormattedTextField header;
    private JComboBox comboBox1;
    private JButton okButton;
    private final String DB_Url = "jdbc:mysql://localhost/Student_System?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Boody_500";
    public drop(JFrame parent,String SID){

        super(parent);
        setTitle("drop course");
        setContentPane(mypanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        load_courses(SID);


        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drop(SID);
                dispose();
                main_page m = new main_page(null,SID);
            }
        });
        setVisible(true);
    }
    void load_courses(String SID){
        try (Connection con = DriverManager.getConnection(DB_Url, USERNAME, PASSWORD)) {
            PreparedStatement p = con.prepareStatement("select cname from Student_Courses where SID = ?");
            p.setString(1,SID);
            ResultSet res = p.executeQuery();
            while (res.next()){

                comboBox1.addItem(res.getString(1));
            }





        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void drop(String SID){

        if (!comboBox1.getSelectedItem().toString().isEmpty()){
            try (Connection con = DriverManager.getConnection(DB_Url, USERNAME, PASSWORD)) {
                PreparedStatement p = con.prepareStatement("select CID from courses where name = ? ");
                p.setString(1,comboBox1.getSelectedItem().toString());

                ResultSet res = p.executeQuery();
                res.next();
                String CID = res.getString(1);

                try(Connection conn = DriverManager.getConnection(DB_Url, USERNAME, PASSWORD)){
                    PreparedStatement pp = con.prepareStatement("delete from student_courses where SID = ? and CID = ? and cname = ? ");
                    pp.setString(1,SID);
                    pp.setString(2,CID);
                    pp.setString(3,comboBox1.getSelectedItem().toString());

                    int x = pp.executeUpdate();

                    if (x > 0){

                        try(Connection connn = DriverManager.getConnection(DB_Url, USERNAME, PASSWORD)){
                            PreparedStatement ppp = con.prepareStatement("update courses set capacity = capacity + 1 where CID = ?");
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
