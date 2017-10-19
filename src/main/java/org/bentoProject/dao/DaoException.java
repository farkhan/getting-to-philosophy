package org.bentoProject.dao;

public class DaoException extends Exception {

    private final Exception originalException;

    public DaoException(Exception originalException, String msg) {
        super(msg);
        this.originalException = originalException;
    }
}