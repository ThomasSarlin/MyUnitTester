import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

public class Controller {
    View view;
    Model model;

    public void run() throws IOException {
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
                    return(model.checkTextField(view.textField.getText())
                            ?model.runTest(view.textField.getText()):null);
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
                            JOptionPane.showMessageDialog(view.frame,
                                    view.textField.getText()
                                            + " does not implement TestClass"
                                    ,"Oops",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (InterruptedException e) {
                        DebugLog.log(Level.WARNING,e.getCause()
                                +"generated in method done");
                    } catch (ExecutionException e) {
                        DebugLog.log(Level.WARNING,e.getCause()
                                +"generated in method done");
                    }
                }
            }.execute();
        }
    }
}

