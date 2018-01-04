package com.algamoney.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algamoney.api.model.Categoria;
import com.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaReposiroty;
	
	@GetMapping
	public List<Categoria> listar(){
		return categoriaReposiroty.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria, HttpServletResponse response){
		 Categoria categoriaSalva = categoriaReposiroty.save(categoria);
		 
		 URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				 .buildAndExpand(categoriaSalva.getCodigo()).toUri();
		 
		 response.setHeader("Location",	 uri.toASCIIString());
		 
		 return ResponseEntity.created(uri).body(categoriaSalva);
	}
	
	
	@GetMapping("/{codigo}")
	public Categoria buscarPeloCodigo(@PathVariable Long codigo){
		return categoriaReposiroty.findOne(codigo);
	}
	
	
	
	
}
