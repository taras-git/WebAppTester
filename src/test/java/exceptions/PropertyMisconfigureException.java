package exceptions;

public class PropertyMisconfigureException extends RuntimeException {

    public PropertyMisconfigureException(String message){
        super(message);
    }

    public PropertyMisconfigureException(){
        super("Property is not properly set, please check property JSON files!!!");
    }

}
