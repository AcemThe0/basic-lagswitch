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
    JButton tryFix;
    JLabel wifiState;
    JTextField pulseTime;
    JCheckBox offset;
    boolean wifi = true;


    public void wifi(String state)
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

    public String valToString(boolean val)
    {
        if (val == true)
        {
            return "on";
        }
        else
        {
            return "off";
        }
    }

    public void pulse(int ms)
    {
        wifi = !wifi;
        wifiState.setVisible(wifi);
            wifi(valToString(wifi));
            try
            {
                Thread.sleep(ms);
            }
            catch(InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
            wifi = !wifi;
            wifi(valToString(wifi));
    }


    public lagSwitch()
    {
        toggleLag = new JButton("Toggle");
        pulseLag = new JButton("Pulse");
        pulseTime = new JTextField("1000");
        offset = new JCheckBox("offset");
        wifiState = new JLabel();
        tryFix = new JButton("Try fix");

        toggleLag.setBounds(15, 15, 100, 25);
        pulseLag.setBounds(15, 55, 100, 25);
        pulseTime.setBounds(130, 55, 100, 25);
        offset.setBounds(130, 15, 100, 25);
        wifiState.setBounds(15, 95, 215, 25);
        tryFix.setBounds(15, 135, 215, 25);

        toggleLag.setToolTipText("Toggle internet");
        pulseLag.setToolTipText("Pulse internet for X milliseconds");
        pulseTime.setToolTipText("Time between toggling in ms (for Pulse)");
        offset.setToolTipText("Slightly change pulse time");
        tryFix.setToolTipText("Force turn on WIFI");

        toggleLag.setFocusable(false);
        pulseLag.setFocusable(false);
        offset.setFocusable(false);
        tryFix.setFocusable(false);

        toggleLag.addActionListener(this);
        pulseLag.addActionListener(this);
        tryFix.addActionListener(this);

        wifiState.setBackground(Color.BLUE);
        wifiState.setOpaque(true);

        this.setTitle("Lagswitch v0.5");
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(null);
        this.setSize(255, 209);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);

        this.add(toggleLag);
        this.add(pulseLag);
        this.add(pulseTime);
        this.add(wifiState);
        this.add(offset);
        this.add(tryFix);
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
            wifi = !wifi;
            wifi(valToString(wifi));
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
                pulseTime.setText(Integer.toString(pulseTimeInt+(pulseTimeInt/42))); //Convert str to int, add a val, convert to str and put it back
            }
        }
        if (event.getSource() == tryFix)
        {
            wifi("on");
        }
        wifiState.setVisible(wifi);
    }
}
