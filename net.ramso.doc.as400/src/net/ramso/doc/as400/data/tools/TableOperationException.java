/*
 * � 2005 TRENTI S.A.
 * Arequipa 1 3� Planta, 28043, Madrid. Spain
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of TRENTI S.A. ("Confidential Information").
 *
 * Fecha Creaci�n 01-feb-2005
 */
package net.ramso.doc.as400.data.tools;

/**
 * @author jescudero
 * @viz.diagram TableOperationException.tpx
 */
public class TableOperationException extends Exception {
    /**
     *  
     */
    public TableOperationException() {
        super();
    }

    /**
     * @param arg0
     */
    public TableOperationException(String arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     */
    public TableOperationException(Throwable arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     */
    public TableOperationException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}