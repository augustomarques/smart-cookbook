package br.com.amarques.smartcookbook.exception;

public class FindByIngredientesException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FindByIngredientesException(String message) {
        super(message);
    }
}
