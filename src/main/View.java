package main;

import javax.swing.*;
import java.awt.*;

public class View {
    JFrame frame;
    JButton runTestButton;
    JButton clearFieldButton;
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
        frame.setSize(new Dimension(800,600));
        frame.setLayout(new BorderLayout());
        frame.add(setTopPanel(),BorderLayout.NORTH);
        frame.add(setMiddlePanel(),BorderLayout.CENTER);
        frame.add(setBottomPanel(),BorderLayout.SOUTH);
    }
    private JPanel setTopPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        textField = new JTextField(12);
        panel.add(textField);
        runTestButton = new JButton("Run test");
        panel.add(runTestButton);

        return panel;
    }
    private JPanel setMiddlePanel(){
        JPanel panel = new JPanel();
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(700,450));
        panel.add(scrollPane);
        return panel;
    }
    private JPanel setBottomPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        clearFieldButton = new JButton("Clear");
        panel.add(clearFieldButton);

        return panel;
    }
}
