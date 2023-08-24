import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class home extends JDialog{


    private JPanel mypanel;
    private JTextField numberGameTextField;
    private JButton startButton;
    private Font f;

    public home(JFrame parent){
        super(parent);
        setTitle("welcome");
        setContentPane(mypanel);
        setMinimumSize(new Dimension(450,474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);




        numberGameTextField.setForeground(Color.white);
        numberGameTextField.setEnabled(false);
        startButton.setFocusable(false);


        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameplay g = new gameplay(null);
            }
        });
        setVisible(true);
        dispose();
    }
}
