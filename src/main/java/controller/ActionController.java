package controller;

import model.Model;
import utils.observer.ActionObserver;

public class ActionController extends Controller implements ActionObserver {
    //override di tutti gli update
    public ActionController(Model model){
        super(model);
    }
}
