/**
 * 
 */
package net.ramso.doc.svg.shapes;

/**
 * @author ramso
 *
 */
public class SVGException extends Exception {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8347342262831501612L;
	private String message = null;
	 
    public SVGException() {
        super();
    }
 
    public SVGException(String message) {
        super(message);
        this.message = message;
    }
 
    public SVGException(Throwable cause) {
        super(cause);
    }
 
    @Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }
	
}
