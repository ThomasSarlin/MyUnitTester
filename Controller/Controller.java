/**
 * Class Responsibility: Controller.Controller, communication between Model & View.View
 *@Author Thomas Sarlin - id15tsn, thomas.sarlin@gmail.com
*/
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

public class Controller {
    View view;
    Model model;
    /**
     * Initiation method for Controller.Controller, Model and View.View.
     */
    public Controller(){
        model = new Model();

        SwingUtilities.invokeLater(() -> {
            view = new View();
            view.addClearButtonAL(new ClearActionListener());
            view.addRunTestButtonAL(new RunTestActionListener());
            view.addComboBoxAL(new ComboBoxListener());
            view.show();
        });

        Runtime.getRuntime().addShutdownHook(new ShutDownThread());
    }

    /**
     * Communicates what should happen in case "Clear" button is pressed
     * Clears the textArea in MiddlePane.
     */
    private class ClearActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.clearTextArea();
        }
    }

    /**
     * Starts a separate SwingWorker with the assignment to
     * check if the class specified is of "TestClass" if yes,
     * initiate the test in Model.
     *
     * When test is done, display result in textArea.
     */
    private class RunTestActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SwingWorker<ArrayList<String>,Object>(){

                @Override
                protected ArrayList<String> doInBackground(){
                    ArrayList <String> result=null;
                    try {
                        result= model.initiateTest
                                (view.getTextField().getText());
                    } catch (ClassNotFoundException e) {
                        Debug.log(Level.INFO, e.getMessage()
                                + " Class " + view.getTextField().getText()
                                + " invalid or non-exsisting.");
                    }
                    return result;
                }

                @Override
                protected void done() {
                    try {
                        ArrayList<String> strings=get();
                        if(strings.get(0)=="Valid class")
                            SwingUtilities.invokeLater(()
                                    -> view.stringsToTextArea(strings));
                        else
                            SwingUtilities.invokeLater(()
                                    -> view.alert("Class: "
                                    + view.getTextField().getText()
                                    + " " +  strings.get(0)));
                    }  catch (InterruptedException | ExecutionException e) {
                            Debug.log(Level.WARNING,e.getCause()
                                    +"generated in method done");
                        }
                }
            }.execute();
        }
    }

    private class ComboBoxListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setTextSize();
        }
    }

    private class ShutDownThread extends Thread{
        public void run(){
            Debug.shutDown();
        }
    }
}

