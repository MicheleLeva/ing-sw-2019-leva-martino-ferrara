package controller;

import model.Model;
import utils.observer.PowerUpObserver;

public class PowerUpController extends Controller implements PowerUpObserver {
    public PowerUpController(Model model){
        super(model);
    }
}
