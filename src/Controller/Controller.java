package Controller; /**
 * Class Responsibility: Controller.Controller, communication between Model.Model & View.View
 *@Author Thomas Sarlin - id15tsn, thomas.sarlin@gmail.com
*/
import Model.Model;
import View.*;
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
     * Initiation method for Controller.Controller, Model.Model and View.View.
     */
    public void run(){
        model = new Model();

        SwingUtilities.invokeLater(() -> {
            view = new View();
            view.addClearButtonAL(new ClearActionListener());
            view.addRunTestButtonAL(new RunTestActionListener());
            view.addComboBoxListener(new ComboBoxListener());
            view.show();
        });

        Runtime.getRuntime().addShutdownHook(new ShutDownThread());
    }

    /**
     * Communicates what should happen in case "Clear" button is pressed
     * Clears the textArea in MiddlePane.
     */
    public class ClearActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.clearTextArea();
        }
    }

    /**
     * Starts a separate SwingWorker with the assignment to
     * check if the class specified is of "Model.TestClass" if yes,
     * initiate the test in Model.Model.
     *
     * When test is done, display result in textArea.
     */
    public class RunTestActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SwingWorker<ArrayList<String>,Object>(){

                @Override
                protected ArrayList<String> doInBackground() throws Exception {
                    return model.initiateTest(view.getTextField().getText());
                }

                @Override
                protected void done() {
                    try {
                        ArrayList<String> strings=get();
                        if(strings!=null)
                            SwingUtilities.invokeLater(()
                                    -> view.stringsToTextArea(strings));
                        else
                            SwingUtilities.invokeLater(()
                                    -> view.alertInvalidClass());
                    }  catch (InterruptedException | ExecutionException e) {
                            Debug.log(Level.WARNING,e.getCause()
                                    +"generated in method done");
                        }
                }
            }.execute();
        }
    }

    public class ComboBoxListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setTextSize();
        }
    }

    public class ShutDownThread extends Thread{
        public void run(){
            Debug.shutDown();
        }
    }
}

