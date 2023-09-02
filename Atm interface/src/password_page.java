import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.*;
public class password_page extends JDialog {
    private JPanel mypanel;
    private JLabel cardlabel;
    private JLabel passlabel;
    private JFormattedTextField welcomeFormattedTextField;
    private JButton continueButton;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;

    private String pass;

    private String Cpass;
    public password_page(JFrame parent){
        super(parent);
        setTitle("Activation page");
        setContentPane(mypanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        welcomeFormattedTextField.enable(false);


        //Matcher Cm = p.matcher(Cpass);





        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                pass = String.valueOf(passwordField1.getPassword());
                Cpass = String.valueOf(passwordField2.getPassword());
                Pattern p = Pattern.compile("^[0-9]{4}$");
                Matcher m = p.matcher(pass);
                Boolean match = m.find();
                if (match && pass.equals( Cpass)){
                    JOptionPane.showMessageDialog(null,"card activated successfully","",JOptionPane.INFORMATION_MESSAGE);
                    dispose();

                }
                else{
                    if (!match)
                        JOptionPane.showMessageDialog(null,"password must be 4 digit characters","invalid password",JOptionPane.ERROR_MESSAGE);

                    else
                        JOptionPane.showMessageDialog(null,"password doesnot match try again","password confirmation failed",JOptionPane.INFORMATION_MESSAGE);


                }
            }
        });
        setVisible(true);
    }

    public String get_pass(){
        return pass;
    }


}
