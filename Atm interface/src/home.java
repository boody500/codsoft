import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.*;
public class home extends JDialog{
    private JTextField textField1;
    private JButton continueButton;
    private JFormattedTextField welcomeFormattedTextField;
    private JLabel cardlabel;
    private JLabel passlabel;
    private JPanel mypanel;
    private JPasswordField passwordField1;

    private Pattern card_pattern = Pattern.compile("^[0-9]{16}$");
    private Pattern pass_pattern = Pattern.compile("^[0-9]{4}$");

    public home(JFrame parent){
        super(parent);
        setTitle("home page");
        setContentPane(mypanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        welcomeFormattedTextField.enable(false);
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = textField1.getText();
                String pass = String.valueOf(passwordField1.getPassword());
                Matcher m = card_pattern.matcher(s);
                Matcher p = pass_pattern.matcher(pass);
                Boolean card_match = m.find();
                Boolean pass_match = p.find();

                if(card_match && pass_match){
                    account a = new account();
                    if (!a.Activate(s)){

                        int y = JOptionPane.showConfirmDialog(null,"your card need to be activated","Activation in requried", JOptionPane.ERROR_MESSAGE);
                        if (y == JOptionPane.OK_OPTION){
                            password_page pass_page = new password_page(null);
                            pass = pass_page.get_pass();
                            if (a.add_account(s,pass)){
                                dispose();
                                Atm_interface atm = new Atm_interface(null,s);
                            }
                        }


                    }

                    else{
                        if (a.check_account_password(s,pass)){

                            dispose();
                            Atm_interface atm = new Atm_interface(null,s);
                        }
                        else
                            JOptionPane.showMessageDialog(null,"password are not correct","invalid password",JOptionPane.ERROR_MESSAGE);

                    }

                }


                else{

                    if (!card_match){
                        JOptionPane.showMessageDialog(null,"card number must be 16 digit characters","invalid card number",JOptionPane.ERROR_MESSAGE);
                        textField1.setText("");
                    }

                    else if (!pass_match){
                        JOptionPane.showMessageDialog(null,"password must be 4 digit characters","invalid password",JOptionPane.ERROR_MESSAGE);
                        passwordField1.setText("");
                    }
                }



            }
        });

        setVisible(true);
    }
}
