package com.algamoney.api.services;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.algamoney.api.model.Pessoa;
import com.algamoney.api.repository.PessoaRepository;
import com.algamoney.api.services.exceptions.PessoaNaoEncontradaException;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaReposiroty;
	
	public List<Pessoa> listar(){
		return pessoaReposiroty.findAll();
	}
	
	public Pessoa buscar(Long codigo){
		Pessoa pessoa = pessoaReposiroty.findOne(codigo);
		if(pessoa == null){
			throw new EmptyResultDataAccessException(1);
		}
		return pessoa;
	}
	
	public Pessoa salvar(Pessoa pessoa){
		pessoa.setCodigo(null);
		return pessoaReposiroty.save(pessoa);
	}
	
	public void deletar(Long codigo){
		try {
			pessoaReposiroty.delete(codigo);
		} catch (EmptyResultDataAccessException e) {
			throw new PessoaNaoEncontradaException("A pessoa não pode ser encontrada");
		}
	}
	
	public Pessoa atualizar(Pessoa pessoa, Long codigo){
		Pessoa pessoaSalva = buscar(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		pessoaReposiroty.save(pessoaSalva);
		return pessoaSalva;
	}

	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscar(codigo);
		pessoaSalva.setAtivo(ativo);
		pessoaReposiroty.save(pessoaSalva);
	}
}	
