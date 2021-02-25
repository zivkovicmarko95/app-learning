package orderbackupapi.exceptions;

public class NotCompletedOrderObject extends Exception {
   
    /*
        Excpetion which is thrown if order object is not completed,
        e.g if some field in the object is null
    */

    private static final long serialVersionUID = 916519081288487399L;

    public NotCompletedOrderObject(String msg) {
        super(msg);
    }

}
