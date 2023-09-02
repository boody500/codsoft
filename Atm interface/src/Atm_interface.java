import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Atm_interface extends JDialog{
    private JPanel mypanel;
    private JLabel cardlabel;
    private JTextField balance;
    private JFormattedTextField welcomeFormattedTextField;
    private JButton continueButton1;
    private JButton deposit;
    private JButton withdraw;
    private JTextField withdraw_amount;
    private JTextField depoist_amount;
    private JButton exit;
    private JButton continueButton2;


    public Atm_interface(JFrame parent,String CardNo){
        super(parent);
        setTitle("Atm page");
        setContentPane(mypanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        welcomeFormattedTextField.setEnabled(false);
        Atm_operations Atm = new Atm_operations(CardNo);

        withdraw_amount.setVisible(false);
        depoist_amount.setVisible(false);
        continueButton1.setVisible(false);
        continueButton2.setVisible(false);
        balance.setEnabled(false);
        balance.setText(String.valueOf(Atm.get_balance()));

        deposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdraw.setEnabled(false);
                depoist_amount.setVisible(true);
                continueButton1.setVisible(true);
                setVisible(true);

            }
        });



        withdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deposit.setEnabled(false);
                withdraw_amount.setVisible(true);
                continueButton2.setVisible(true);

                setVisible(true);


            }
        });

        continueButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!depoist_amount.getText().isEmpty()){

                    int x = Integer.parseInt(depoist_amount.getText());

                    if (x < 5 || x > 20000){
                        JOptionPane.showMessageDialog(null,"minimum amount to deposit is 5\nmaxmimum amount to deposit is 20000","keep in mind",JOptionPane.INFORMATION_MESSAGE);
                    }

                    else{
                        balance.setText(String.valueOf(Atm.deposit(x)));

                        int y = JOptionPane.showConfirmDialog(null,"amount deposited succcessfully\nwant another process?","",JOptionPane.YES_NO_OPTION);
                        if (y == JOptionPane.YES_OPTION){
                            withdraw_amount.setVisible(false);
                            depoist_amount.setVisible(false);
                            depoist_amount.setText("");
                            deposit.setEnabled(true);
                            withdraw.setEnabled(true);
                            continueButton1.setVisible(false);
                        }
                        else
                            dispose();
                    }
                }





            }
        });



        continueButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!withdraw_amount.getText().isEmpty()){

                    int x = Integer.parseInt(withdraw_amount.getText());

                    if (x < 5 || x > 20000){
                        JOptionPane.showMessageDialog(null,"minimum amount to withdraw is 5\nmaxmimum amount to withdraw is 20000","keep in mind",JOptionPane.INFORMATION_MESSAGE);
                    }

                    else{
                        if (x > Integer.parseInt(balance.getText()))
                            JOptionPane.showMessageDialog(null,"your balance is not enough","balance error",JOptionPane.INFORMATION_MESSAGE);

                        else{
                            balance.setText(String.valueOf(Atm.withdraw(x)));
                            int y = JOptionPane.showConfirmDialog(null,"amount withdrawed succcessfully\nwant another process?","",JOptionPane.YES_NO_OPTION);
                            if (y == JOptionPane.YES_OPTION){
                                withdraw_amount.setVisible(false);
                                withdraw_amount.setText("");
                                depoist_amount.setVisible(false);
                                deposit.setEnabled(true);
                                withdraw.setEnabled(true);
                                continueButton2.setVisible(false);
                            }
                            else
                                dispose();

                        }
                    }
                }
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        setVisible(true);
    }


}
