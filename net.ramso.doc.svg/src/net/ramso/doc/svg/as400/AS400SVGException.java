/**
 * 
 */
package net.ramso.doc.svg.as400;

import net.ramso.doc.svg.shapes.SVGException;

/**
 * @author ramso
 *
 */
public class AS400SVGException extends SVGException {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8347342262831501612L;
	private String message = null;
	 
    public AS400SVGException() {
        super();
    }
 
    public AS400SVGException(String message) {
        super(message);
        this.message = message;
    }
 
    public AS400SVGException(Throwable cause) {
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
