/**
 * Class Respnsibility: Controller, communication between Model & View
 *@Author Thomas Sarlin - id15tsn, thomas.sarlin@gmail.com
 */

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

    /**
     * Initiation method for Controller, Model and View.
     * @throws IOException
     */
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

    /**
     * Communicates what should happen in case "Clear" button is pressed
     * Clears the textArea in MiddlePane.
     */
    public class ClearActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getTextArea().setText("");
        }
    }

    /**
     * Starts a separate SwingWorker with the assignment to
     * check if the class specified is of "TestClass" if yes,
     * initiate the test in Model.
     *
     * When test is done, display result in textArea.
     */
    public class RunTestActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SwingWorker<ArrayList<String>,Object>(){

                @Override
                protected ArrayList<String> doInBackground() throws Exception {
                    return(model.checkTextField(view.getTextField().getText())
                            ?model.runTest(view.getTextField().getText()):null);
                }

                @Override
                protected void done() {
                    try {
                        ArrayList<String> strings=get();
                        if(strings!=null)
                            stringsToTextArea(strings);
                        else
                            alertInvalidClass();
                    }  catch (InterruptedException | ExecutionException e) {
                            Debug.log(Level.WARNING,e.getCause()
                                    +"generated in method done");
                        }
                }
            }.execute();
        }
    }

    /**
     * Signals to View to append set strings.
     * @param strings to be appended.
     */
    private void stringsToTextArea(ArrayList<String> strings) {
        for (String string : strings)
            view.getTextArea().append(string);
    }

    /**
     * called if said class is not an implementation of "TestClass.class"
     */
    private void alertInvalidClass(){
        JOptionPane.showMessageDialog(view.getFrame()
                ,view.getTextField().getText()
                        + " does not implement TestClass", "Oops",
                JOptionPane.ERROR_MESSAGE);
    }
}

