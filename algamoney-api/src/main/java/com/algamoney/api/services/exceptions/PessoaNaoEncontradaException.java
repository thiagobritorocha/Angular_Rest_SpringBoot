package com.algamoney.api.services.exceptions;

public class PessoaNaoEncontradaException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6931406961267145556L;

	public PessoaNaoEncontradaException(String mensagem){
		super(mensagem);
	}
	
	public PessoaNaoEncontradaException(String mensagem, Throwable causa){
		super(mensagem, causa);
	}
}
