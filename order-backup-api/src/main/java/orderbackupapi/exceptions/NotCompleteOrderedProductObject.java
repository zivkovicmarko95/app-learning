package orderbackupapi.exceptions;

public class NotCompleteOrderedProductObject extends Exception {
    
    /*
        Exception which is thrown if ordered product object is not complete, e.g if
        the list of ordered products is empty
    */

    private static final long serialVersionUID = -1667427088128911488L;

    public NotCompleteOrderedProductObject(String msg) {
        super(msg);
    }

}
