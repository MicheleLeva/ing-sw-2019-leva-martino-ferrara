package model.adrenaline_exceptions;

public class IllegalRunException extends Exception{

    public IllegalRunException(String message){
        super(message);
    }

    public IllegalRunException(){
        System.out.println("Illegal run");
    }

}
