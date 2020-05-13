package ru.javawebinar.graduation.util.exception;

public class NotChangeVoteException extends RuntimeException{
    public NotChangeVoteException(String message) {
        super(message);
    }
}
