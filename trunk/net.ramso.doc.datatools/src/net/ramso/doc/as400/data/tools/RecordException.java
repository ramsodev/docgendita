/*
 * � 2005 TRENTI S.A.
 * Arequipa 1 3� Planta, 28043, Madrid. Spain
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of TRENTI S.A. ("Confidential Information").
 *
 * Fecha Creaci�n 27-ene-2005
 */
package net.ramso.doc.as400.data.tools;

/**
 * @author jescudero
 * @viz.diagram RecordException.tpx
 */
public class RecordException extends Exception {
    /**
     *  
     */
    public RecordException() {
        super();
    }

    /**
     * @param arg0
     */
    public RecordException(String arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     */
    public RecordException(Throwable arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     */
    public RecordException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}