package com.algamoney.api.services;

import java.util.List;

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
		Categoria categoria = categoriaReposiroty.findOne(codigo);
		if(categoria == null){
			throw new CategoriaNaoEncontradaException("A categoria não pode ser encontrada");	
		}
		return categoria;
	}
	
	public Categoria salvar(Categoria categoria){
		categoria.setCodigo(null);
		return categoriaReposiroty.save(categoria);
	}
	
	public void deletar(Long codigo){
		try {
			categoriaReposiroty.delete(codigo);
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
