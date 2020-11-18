package com.algamoney.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algamoney.api.model.Categoria;
import com.algamoney.api.repository.CategoriaRepository;
import com.algamoney.api.services.exceptions.CategoriaNaoEncontradaException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaReposiroty;
	
	public List<Categoria> listar(){
		return categoriaReposiroty.findAll();
	}
	
	public Categoria buscar(Long codigo){
		Optional<Categoria> categoria = categoriaReposiroty.findById(codigo);
		if(categoria == null){
			throw new CategoriaNaoEncontradaException("A categoria não pode ser encontrada");	
		}
		return categoria.get();
	}
	
	public Categoria salvar(Categoria categoria){
		categoria.setCodigo(null);
		return categoriaReposiroty.save(categoria);
	}
	
	public void deletar(Long codigo){
		try {
			categoriaReposiroty.deleteById(codigo);
		} catch (EmptyResultDataAccessException e) {
			throw new CategoriaNaoEncontradaException("A categoria não pode ser encontrada");
		}
	}
	
	public void atualizar(Categoria categoria){
		verificarExistencia(categoria);
		categoriaReposiroty.save(categoria);
	}
	
	private void verificarExistencia(Categoria categoria){
		buscar(categoria.getCodigo());	
	}
}
