package org.farkhan.dao;

/**
 *
 * @author Shafeeq Khan
 */
public class DaoException extends Exception {

    private final Exception originalException;

    /**
     *
     * @param originalException
     * @param msg
     */
    public DaoException(Exception originalException, String msg) {
        super(msg);
        this.originalException = originalException;
    }
}