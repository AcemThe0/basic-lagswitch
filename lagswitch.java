import javax.swing.JFrame;
import javax.swing.JButton;
import javax.security.auth.callback.LanguageCallback;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.text.AttributeSet.ColorAttribute;
import java.awt.Color;

public class lagSwitch extends JFrame implements ActionListener
{
    JButton toggleLag;
    int toggled = 1;


    public int toggle(int val)
    {
        if (val == 0)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    public String announce(int val)
    {
        if (val >= 1)
        {
            return "on";
        }
        else
        {
            return "off";
        }
    }


    public lagSwitch()
    {
        //to make the GUI
        JButton toggleLag = new JButton("LAG");

        toggleLag.setBounds(15, 15, 75, 25);
        toggleLag.addActionListener(this);
        toggleLag.setFocusable(false);

        this.setTitle("A.C.M.E. lagswitch v0.1");
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(null);
        this.setSize(300, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);

        this.add(toggleLag);
    }


    public static void main(String[] args)
    {
        lagSwitch lagGUI = new lagSwitch();
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        toggled = toggle(toggled);
        System.out.println("WIFI " + announce(toggled));

        try
        {
            //just stops your wifi using linkeks quirks
            Process process = Runtime.getRuntime().exec("nmcli radio wifi " + announce(toggled));
        }
        catch (Exception e) {
        e.printStackTrace();
        System.out.println("FAILED\nRun \"nmcli radio wifi on\" to regain WIFI");
    }
    }
}