package main;

import javax.swing.*;

public class Controller {
    View view;
    Model model;

    public void run(){
        model = new Model();
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                view = new View();
                view.show();
            }
        });
    }
}

