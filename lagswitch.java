import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.security.auth.callback.LanguageCallback;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class lagSwitch extends JFrame implements ActionListener
{
    JButton toggleLag;
    JButton pulseLag;
    JLabel wifiState;
    JTextField pulseTime;
    int toggled = 1;


    public void wifi(String state)
    //TODO: find a way to instantly disconnect and reconnect
    {
        try
            {
                Process process = Runtime.getRuntime().exec("nmcli networking " + state);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("FAILED\nRun \"nmcli networking on\" to regain WIFI");
            }
    }

    public String toString(int val)
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

    public boolean toBool(int val)
    {
        if (val >= 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

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

    public void pulse(int ms)
    {
        toggle();
            wifi(toString(toggled));
            try
            {
                Thread.sleep(ms);
            }
            catch(InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
            toggle();
            wifi(toString(toggled));
    }


    public lagSwitch()
    {
        toggleLag = new JButton("Toggle");
        pulseLag = new JButton("Pulse");
        wifiState = new JLabel();
        pulseTime = new JTextField("1000");

        toggleLag.setBounds(15, 15, 100, 25);
        toggleLag.addActionListener(this);
        toggleLag.setFocusable(false);
        toggleLag.setToolTipText("Toggle internet");
        pulseLag.setBounds(15, 55, 100, 25);
        pulseLag.addActionListener(this);
        pulseLag.setFocusable(false);
        pulseLag.setToolTipText("Pulse internet for X milliseconds");
        wifiState.setBounds(130, 15, 100, 25);
        wifiState.setBackground(Color.BLUE);
        wifiState.setOpaque(true);
        pulseTime.setBounds(130, 55, 100, 25);
        pulseTime.setToolTipText("Time between toggling in ms (for Pulse)");

        this.setTitle("Lagswitch v0.3");
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(null);
        this.setSize(255, 129);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);

        this.add(toggleLag);
        this.add(pulseLag);
        this.add(pulseTime);
        this.add(wifiState);
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
            wifi(toString(toggled));
        }
        if (event.getSource() == pulseLag)
        {
            try
            {
                pulse(Integer.parseInt(pulseTime.getText()));
            }
            catch (Exception e)
            {
                pulse(1000);
            };
        }
        wifiState.setVisible(toBool(toggled));
    }
}
