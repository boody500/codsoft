import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class home extends JDialog {

    private JPanel mypanel;
    private JFormattedTextField header;
    private JPasswordField SCPass;
    private JPanel frame;
    private JTextField SID;
    private JPasswordField SPass;
    private JButton button;
    private JLabel confirm_label;
    private JLabel namelabel;
    private JTextField Sname;


    private JMenuBar mybar = new JMenuBar();
    private JMenu mymenu = new JMenu("menu");

    private JMenuItem login = new JMenuItem("Login");
    private JMenuItem register = new JMenuItem("Register");


    private final String DB_Url = "jdbc:mysql://localhost/Student_System?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Boody_500";

    public home(JFrame parent) {
        super(parent);
        setTitle("home page");
        setContentPane(mypanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        mymenu.add(login);
        mymenu.add(register);

        mybar.add(mymenu);

        setJMenuBar(mybar);
        frame.setVisible(false);
        header.disable();


        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SCPass.setVisible(false);
                confirm_label.setVisible(false);
                Sname.setVisible(false);
                namelabel.setVisible(false);
                button.setText("Login");
                frame.setVisible(true);


                setVisible(true);
            }
        });

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SCPass.setVisible(true);
                confirm_label.setVisible(true);
                Sname.setVisible(true);
                namelabel.setVisible(true);
                button.setText("Register");
                frame.setVisible(true);
                setVisible(true);
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pattern p = Pattern.compile("^(202)[0-9]{1,1}(0)[0-9]{3,3}$");
                Matcher m = p.matcher(SID.getText());
                if (confirm_label.isVisible()) {


                    if (m.find()){
                        if (!String.valueOf(SPass.getPassword()).equals(String.valueOf(SCPass.getPassword())) ){
                            JOptionPane.showMessageDialog(null, "password doesnot match", "Registeration failed", JOptionPane.OK_OPTION);

                        }
                        else{

                            int temp = register(Sname.getText(),SID.getText(),String.valueOf(SPass.getPassword()));

                            if (temp == 0)
                                JOptionPane.showMessageDialog(null, "Registeration failed", "", JOptionPane.OK_OPTION);

                            else{
                                dispose();
                                main_page page = new main_page(null,SID.getText());
                            }
                        }

                    }

                    else{
                        JOptionPane.showMessageDialog(null,"invalid ID");
                    }



                }
                else {
                    //login

                    if (m.find()){
                        int flag = login(SID.getText(), String.valueOf(SPass.getPassword()));
                        if (flag == 2) {
                            dispose();
                            main_page page = new main_page(null,SID.getText());
                        }
                        else if (flag == 0) {
                            JOptionPane.showMessageDialog(null, "you need to register", "Login failed", JOptionPane.OK_OPTION);
                            SCPass.setVisible(true);
                            confirm_label.setVisible(true);
                            Sname.setVisible(true);
                            namelabel.setVisible(true);
                            button.setText("Register");
                            SPass.setText("");
                            setVisible(true);

                        }
                        else if (flag == 1) {
                            JOptionPane.showMessageDialog(null, "incorrect password", "Login failed", JOptionPane.OK_OPTION);

                        }
                    }

                    else
                        JOptionPane.showMessageDialog(null,"invalid ID");



                }
            }
        });


        setVisible(true);
    }

    int login(String SID, String Pass) {

        int flag = 2;
        try (Connection con = DriverManager.getConnection(DB_Url, USERNAME, PASSWORD)) {
            PreparedStatement p = con.prepareStatement("select SID,Password from Student where SID = ?");
            p.setString(1, SID);

            ResultSet res = p.executeQuery();

            if (!res.next()) {
                flag = 0;
            } else {
                if (!Pass.equals(res.getString(2)))
                    flag = 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return flag;
    }

    int register(String Name, String SID, String Pass) {
        int res = 0;
        try (Connection con = DriverManager.getConnection(DB_Url, USERNAME, PASSWORD)) {
            PreparedStatement p = con.prepareStatement("insert into Student (SID,Password,Name) values(?,?,?) ");
            p.setString(1, SID);
            p.setString(2, Pass);
            p.setString(3, Name);

            res = p.executeUpdate();


        }

        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }
}