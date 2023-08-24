import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gameplay extends JDialog{

    private JPanel mypanel;
    private JTextField input;
    private JButton ExitButton;
    private JButton OKButton;

    private JTextField Score;
    private JTextField Attempts;
    private JTextField header;

    private String message = new String();

    private int attempts = 5;

    private int score = 0;

    private algorithm a = new algorithm();
    public gameplay(JFrame parent){
        super(parent);
        setTitle("welcome");
        setContentPane(mypanel);
        setMinimumSize(new Dimension(550,500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        header.setEnabled(false);



        Score.setText(String.valueOf(score));
        Attempts.setText(String.valueOf(attempts));
        Score.setEnabled(false);
        Attempts.setEnabled(false);

        ExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = input.getText();
                int x = Integer.parseInt(s);

                if (x < 1 || x > 100){
                    JOptionPane.showMessageDialog(null,"You must Guess a number from 1 to 100","Error",JOptionPane.ERROR_MESSAGE);
                }
                else {

                    message = a.find(x);


                    if (attempts > 0){
                        attempts--;
                        score++;
                        Score.setText(String.valueOf(score));
                        Attempts.setText(String.valueOf(attempts));
                        if ( message != "true" ){
                            JOptionPane.showMessageDialog(null,message,"try again",JOptionPane.INFORMATION_MESSAGE);
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Right Guess\nYour Score Is "+String.valueOf(score),"Congrats",JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                        }

                    }

                    else{
                        int y = JOptionPane.showConfirmDialog(null,"you dont have attempts left, wanna have more?","Select option",JOptionPane.YES_NO_OPTION);

                        if (y == JOptionPane.YES_OPTION){
                            attempts = 5;
                            Attempts.setText(String.valueOf(attempts));

                        }

                        else
                            dispose();

                    }
                }



            }
        });

        setVisible(true);
    }
}
