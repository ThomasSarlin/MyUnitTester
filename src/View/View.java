package View; /**
 * Class Respnsibility: GUI
 *@Author Thomas Sarlin - id15tsn, thomas.sarlin@gmail.com
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View {
    private JFrame frame;
    private JButton runTestButton;
    private JButton clearFieldButton;
    private JTextArea textArea;
    private JTextField textField;
    private JComboBox<Integer> comboBox;

    public View() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
        setFrame();
    }

    public void show() {
        if (frame != null)
            frame.setVisible(true);
    }

    /**
     * Assembly for JFrame
     */
    private void setFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1080, 720));
        frame.setLayout(new BorderLayout());
        frame.add(setTopPanel(), BorderLayout.NORTH);
        frame.add(setMiddlePanel(), BorderLayout.CENTER);
        frame.add(setBottomPanel(), BorderLayout.SOUTH);
    }

    /**
     * Assembly of top panel
     *
     * @return panel representing top row
     */
    private JPanel setTopPanel() {
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
     *
     * @return panel with a text-area
     */
    private JPanel setMiddlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.setLayout(new GridLayout(0,1));
        panel.add(scrollPane);
        return panel;
    }

    /**
     * @return panel with a clear button
     */
    private JPanel setBottomPanel() {
        JPanel panel = new JPanel();
        JPanel innerPanel = new JPanel();
        clearFieldButton = new JButton("Clear");
        JLabel label = new JLabel("Text size:");
        comboBox = new JComboBox<>();

        panel.setBackground(Color.darkGray);
        panel.setLayout(new GridLayout(1,3));
        innerPanel.setBackground(Color.darkGray);
        innerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        label.setForeground(Color.white);
        for(int i=12;i<32;i+=2)comboBox.addItem(i);
        innerPanel.add(label);
        innerPanel.add(comboBox);
        panel.add(new JLabel(),0);
        panel.add(clearFieldButton,1);
        panel.add(innerPanel,2);

        return panel;
    }

    /**
     * Signals to View.View to append set strings.
     *
     * @param strings to be appended.
     */
    public void stringsToTextArea(ArrayList<String> strings) {
        for (String string : strings)
            textArea.append(string);
    }

    /**
     * called if said class is not an implementation of "Model.TestClass.class"
     */
    public void alertInvalidClass(){
        JOptionPane.showMessageDialog(frame
                ,textField.getText()
                        + " does not implement Model.TestClass", "Oops",
                JOptionPane.ERROR_MESSAGE);
    }

    public void clearTextArea(){
        textArea.setText("");
    }

    public void setTextSize(){
        textArea.setFont(textArea.getFont().deriveFont(new Float((Integer)comboBox.getSelectedItem())));
    }
    /**
     * @param listener representing where to send information of button clicks.
     */
    public void addRunTestButtonAL(ActionListener listener) {
        runTestButton.addActionListener(listener);
    }

    /**
     * @param listener representing where to send information of button clicks.
     */

    public void addClearButtonAL(ActionListener listener) {
        clearFieldButton.addActionListener(listener);
    }

    public void addComboBoxListener(ActionListener listener){
        comboBox.addActionListener(listener);
    }

    public JTextField getTextField() {
        return textField;
    }
}
