package com.algamoney.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algamoney.api.model.DetalhesErro;
import com.algamoney.api.services.exceptions.CategoriaNaoEncontradaException;

@ControllerAdvice
public class CategoriaExceptonHandler extends ResponseEntityExceptionHandler{

	@Autowired
	private MessageSource messageSource;
	
	//EXCEPTION LANÇADA QUANDO É PASSADO PARAMETROS DESCONHECIDOS NA REQUISIÇÃO
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause().toString();
		
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, headers, status, request);
	}
	
	//Captura exception de argumentos que não são validos
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	//EXCEPTION LANÇADA QUANDO A CATEGORIA NÃO É ENCONTRADA
	//TRATAMENTO QUE É FEITO QUANDO É LANÇADA UMA EXCEPTION CategoriaNaoEncontradaException
	@ExceptionHandler(CategoriaNaoEncontradaException.class) 
	public ResponseEntity<DetalhesErro> handlerCategoriaNaoEncontrada
												 (CategoriaNaoEncontradaException e, HttpServletRequest request){
		DetalhesErro detalheErro = new DetalhesErro();
		detalheErro.setTitulo("A categoria não pode ser Encontrada");
		detalheErro.setStatus(404l);
		detalheErro.setTimestamp(System.currentTimeMillis());
		detalheErro.setMensagemDesenvolvedor("http://erros.categoria.com.br");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detalheErro);
	}
	
	private List<Erro> criarListaDeErros(BindingResult bindingResult){
		List<Erro> erros = new ArrayList<>();
		
		for (FieldError filedError : bindingResult.getFieldErrors()) {
			String mensagemUsuario = messageSource.getMessage(filedError, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = filedError.toString();
			erros.add(new Erro(mensagemUsuario,mensagemDesenvolvedor));
		}
		return erros;
	}
	
	public static class Erro{
		
		private String mensagemUsuario;
		private String mensagemDesenvolvedor;
		
		public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public void setMensagemUsuario(String mensagemUsuario) {
			this.mensagemUsuario = mensagemUsuario;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}

		public void setMensagemDesenvolvedor(String mensagemDesenvolvedor) {
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}
		
	}
}
