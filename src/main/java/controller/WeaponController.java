package controller;

import model.Model;
import utils.observer.WeaponObserver;

public class WeaponController extends Controller implements WeaponObserver {
    public WeaponController(Model model){
        super(model);
    }
}
