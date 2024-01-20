package com.smsoft.board.exception;

public class DuplicatedIdException extends RuntimeException {
    public DuplicatedIdException(String duplicatedId) {
        super(duplicatedId);
    }
}
