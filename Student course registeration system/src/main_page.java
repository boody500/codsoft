import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class main_page extends JDialog{
    private JPanel mypanel = new JPanel();
    private JFormattedTextField header;
    private JTable mycourses;
    private JMenuBar mybar = new JMenuBar();
    private JMenu mymenu = new JMenu("menu");

    private JMenuItem Register_course = new JMenuItem("Register course");
    private JMenuItem Drop_course = new JMenuItem("Drop course");

    private String[][] data;
    private String[]col_names = {"Course Code","Course Name"};
    private final String DB_Url = "jdbc:mysql://localhost/Student_System?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Boody_500";


    public main_page(JFrame parent,String SID){
        super(parent);
        setTitle("Login");
        setContentPane(mypanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        mymenu.add(Register_course);
        mymenu.add(Drop_course);
        mybar.add(mymenu);
        setJMenuBar(mybar);
        header.setText("Welcome "+SID);
        header.setEnabled(false);
        header.setPreferredSize(new Dimension(450,50));
        mypanel.add(header);

        load_courses(SID);

        mycourses = new JTable(data,col_names);
        JScrollPane j = new JScrollPane(mycourses);
        j.setPreferredSize(new Dimension(250,100));
        mypanel.add(j);



        Register_course.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                register r = new register(null,SID);
            }
        });

        Drop_course.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                drop d = new drop(null,SID);
            }
        });


        setVisible(true);
    }


    void load_courses(String SID){
        try (Connection con = DriverManager.getConnection(DB_Url, USERNAME, PASSWORD)) {
            PreparedStatement p = con.prepareStatement("select CID,cname from Student_Courses where SID = ?");
            p.setString(1, SID);

            ResultSet res = p.executeQuery();

            ArrayList c_id = new ArrayList<String>();
            ArrayList c_name = new ArrayList<String>();
            while (res.next()) {
                c_id.add(res.getString(1));
                c_name.add(res.getString(2));
            }
            data = new String[c_id.size()][2];

            int j = 0;
            for (int i = 0; i < c_id.size(); i++) {
                data[i][j] = c_id.get(i).toString();
                data[i][j+1] = c_name.get(i).toString();

            }



        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
