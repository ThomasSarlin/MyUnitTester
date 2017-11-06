package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class Controller {
    View view;
    Model model;

    public void run(){
        model = new Model();
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                view = new View();
                view.addClearButtonAL(new ClearActionListener());
                view.addRunTestButtonAL(new RunTestActionListener());
                view.show();
            }
        });
    }


    public class ClearActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.textArea.setText("");
        }
    }
    public class RunTestActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SwingWorker<ArrayList<String>,Object>(){

                @Override
                protected ArrayList<String> doInBackground() throws Exception {
                    return (model.checkTextField()?model.runTest():null);
                }

                @Override
                protected void done() {
                    try {
                        if(get()!=null) {
                            ArrayList<String> strings = get();
                            for (String string : strings) {
                                view.textArea.append(string);
                            }
                        }else{

                            view.frame.add(new JOptionPane(
                                    "Class does no exist",
                                    JOptionPane.ERROR_MESSAGE));
                        }
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    } catch (ExecutionException e1) {
                        e1.printStackTrace();
                    }
                }
            };
        }
    }
}

