package productapi.exceptions;

public class NotValidProductQuantity extends Exception {

    /*
        Exception whhich is thrown if user tries to add the product which
        is not in stock
    */

    private static final long serialVersionUID = 8618377056323864844L;

    public NotValidProductQuantity(String msg) {
        super(msg);
    }

}
