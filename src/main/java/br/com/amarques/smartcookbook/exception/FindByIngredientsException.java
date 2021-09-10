package br.com.amarques.smartcookbook.exception;

public class FindByIngredientsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FindByIngredientsException(final String message) {
        super(message);
    }
}
