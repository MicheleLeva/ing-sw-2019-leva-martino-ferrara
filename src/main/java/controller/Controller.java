package controller;

import model.Model;

public class Controller {

    private final Model model;

    public Controller(Model model){
        this.model = model;
    }

    public Model getModel(){
        return model;
    }

}
