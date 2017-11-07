/**
 * Class Respnsibility: GUI
 *@Author Thomas Sarlin - id15tsn, thomas.sarlin@gmail.com
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View {
    private JFrame frame;
    private JButton runTestButton;
    private JButton clearFieldButton;
    private JTextArea textArea;
    private JTextField textField;

    public View(){
        setFrame();
    }

    public void show(){
        if(frame!=null)
            frame.setVisible(true);
    }

    /**
     * Assembly for JFrame
     */
    private void setFrame(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(480,272));
        frame.setLayout(new BorderLayout());
        frame.add(setTopPanel(),BorderLayout.NORTH);
        frame.add(setMiddlePanel(),BorderLayout.CENTER);
        frame.add(setBottomPanel(),BorderLayout.SOUTH);
    }

    /**
     * Assembly of top panel
     * @return panel representing top row
     */
    private JPanel setTopPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(Color.darkGray);
        JLabel label = new JLabel("TestClass name:");
        label.setForeground(Color.white);
        textField = new JTextField(12);
        panel.add(label);
        panel.add(textField);
        runTestButton = new JButton("Run tests");
        panel.add(runTestButton);

        return panel;
    }

    /**
     * Assembly of middlePanel
     * @return panel with a text-area
     */
    private JPanel setMiddlePanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(440,140));
        panel.add(scrollPane);
        return panel;
    }

    /**
     * @return panel with a clear button
     */
    private JPanel setBottomPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        clearFieldButton = new JButton("Clear");
        panel.add(clearFieldButton);

        return panel;
    }

    /**
     * @param listener representing where to send information of button clicks.
     */
    public void addRunTestButtonAL(ActionListener listener){
        runTestButton.addActionListener(listener);
    }

    /**
     * @param listener representing where to send information of button clicks.
     */

    public void addClearButtonAL(ActionListener listener){
        clearFieldButton.addActionListener(listener);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JTextField getTextField() {
        return textField;
    }

    public JFrame getFrame() {
        return frame;
    }
}
