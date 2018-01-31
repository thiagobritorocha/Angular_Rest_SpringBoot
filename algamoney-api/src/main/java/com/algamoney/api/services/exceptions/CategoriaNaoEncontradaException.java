package com.algamoney.api.services.exceptions;

public class CategoriaNaoEncontradaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6931406961267145556L;

	public CategoriaNaoEncontradaException(String mensagem){
		super(mensagem);
	}
	
	public CategoriaNaoEncontradaException(String mensagem, Throwable causa){
		super(mensagem, causa);
	}
}
