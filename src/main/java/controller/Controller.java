package controller;

import model.Model;

/**
 * Class that all the specific MVC controllers extend
 */
public class Controller {

    private final Model model;

    /**
     * Constructor for the Controller class
     * @param model model class
     */
    public Controller(Model model){
        this.model = model;
    }

    /**
     * returns the model class
     */
    public Model getModel(){
        return model;
    }

}
