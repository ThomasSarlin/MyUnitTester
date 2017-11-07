
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View {
    JFrame frame;
    private JButton runTestButton;
    private JButton clearFieldButton;
    JTextArea textArea;
    JTextField textField;

    public View(){
        setFrame();
    }

    public void show(){
        if(frame!=null)
            frame.setVisible(true);
    }
    private void setFrame(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(480,272));
        frame.setLayout(new BorderLayout());
        frame.add(setTopPanel(),BorderLayout.NORTH);
        frame.add(setMiddlePanel(),BorderLayout.CENTER);
        frame.add(setBottomPanel(),BorderLayout.SOUTH);
    }
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
    private JPanel setBottomPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        clearFieldButton = new JButton("Clear");
        panel.add(clearFieldButton);

        return panel;
    }
    public void addRunTestButtonAL(ActionListener listener){
        runTestButton.addActionListener(listener);
    }
    public void addClearButtonAL(ActionListener listener){
        clearFieldButton.addActionListener(listener);
    }
}
