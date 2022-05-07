import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
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
    JCheckBox offset;
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

    public String valToString(int val)
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
            wifi(valToString(toggled));
            try
            {
                Thread.sleep(ms);
            }
            catch(InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
            toggle();
            wifi(valToString(toggled));
    }


    public lagSwitch()
    {
        toggleLag = new JButton("Toggle");
        pulseLag = new JButton("Pulse");
        wifiState = new JLabel();
        pulseTime = new JTextField("1000");
        offset = new JCheckBox("offset");

        toggleLag.setBounds(15, 15, 100, 25);
        toggleLag.addActionListener(this);
        toggleLag.setFocusable(false);
        toggleLag.setToolTipText("Toggle internet");
        pulseLag.setBounds(15, 55, 100, 25);
        pulseLag.addActionListener(this);
        pulseLag.setFocusable(false);
        pulseLag.setToolTipText("Pulse internet for X milliseconds");
        wifiState.setBounds(15, 95, 215, 25);
        wifiState.setBackground(Color.BLUE);
        wifiState.setOpaque(true);
        pulseTime.setBounds(130, 55, 100, 25);
        pulseTime.setToolTipText("Time between toggling in ms (for Pulse)");
        offset.setBounds(130, 15, 100, 25);
        offset.setFocusable(false);
        offset.setToolTipText("Slightly change pulse time");

        this.setTitle("Lagswitch v0.4");
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(null);
        this.setSize(255, 169);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);

        this.add(toggleLag);
        this.add(pulseLag);
        this.add(pulseTime);
        this.add(wifiState);
        this.add(offset);
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
            wifi(valToString(toggled));
        }
        if (event.getSource() == pulseLag)
        {
            int pulseTimeInt = Integer.parseInt(pulseTime.getText());
            try
            {
                pulse(pulseTimeInt);
            }
            catch (Exception e)
            {
                pulse(1000);
            };
            if (offset.isSelected() == true)
            {
                pulseTime.setText(Integer.toString(pulseTimeInt+(pulseTimeInt/42)));
                //Convert str to int, add a val, convert to str and put it back
            }
        }
        wifiState.setVisible(toBool(toggled));
    }
}
