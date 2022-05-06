import javax.swing.JFrame;
import javax.swing.JButton;
import javax.security.auth.callback.LanguageCallback;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class lagSwitch extends JFrame implements ActionListener
{
    JButton toggleLag;
    JButton pulseLag;
    int toggled = 1;


    public void toggle()
    {
        if (toggled == 0)
        {
            toggled = 1;
        }
        else
        {
            toggled = 0;
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

    public void wifi(String state)
    {
        try
            {
                Process process = Runtime.getRuntime().exec("nmcli radio wifi " + state);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("FAILED\nRun \"nmcli radio wifi on\" to regain WIFI");
            }
    }


    public lagSwitch()
    {
        //to make the GUI
        toggleLag = new JButton("Toggle");
        pulseLag = new JButton("Pulse");

        toggleLag.setBounds(15, 15, 100, 25);
        toggleLag.addActionListener(this);
        toggleLag.setFocusable(false);
        pulseLag.setBounds(15, 55, 100, 25);
        pulseLag.addActionListener(this);
        pulseLag.setFocusable(false);

        this.setTitle("Lagswitch v0.2");
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(null);
        this.setSize(300, 129);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);

        this.add(toggleLag);
        this.add(pulseLag);
    }


    public static void main(String[] args)
    {
        lagSwitch lagGUI = new lagSwitch();
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource() == toggleLag)
        {
            toggle();
            System.out.println("WIFI " + announce(toggled));

            wifi(announce(toggled));
        }
        if (event.getSource() == pulseLag)
        {
            System.out.println("Pulse");

            toggle();
            wifi(announce(toggled));
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
            toggle();
            wifi(announce(toggled));
            
        }
    }
}
