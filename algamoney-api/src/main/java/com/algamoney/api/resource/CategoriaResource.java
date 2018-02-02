package com.algamoney.api.resource;

import java.net.URI;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algamoney.api.model.Categoria;
import com.algamoney.api.services.CategoriaService;

@RestController
@RequestMapping("categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaService;


	@GetMapping
	public ResponseEntity<List<Categoria>> listar() {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.listar());
	}

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Categoria categoria) {
	//public ResponseEntity<?> criar(@RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaService.salvar(categoria);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(categoriaSalva.getCodigo()).toUri();

		//response.setHeader("Location", uri.toASCIIString());
		
		//return ResponseEntity.created(uri).body(categoriaSalva);
		return ResponseEntity.created(uri).build();
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscar(@PathVariable Long codigo) {
		return ResponseEntity.status(HttpStatus.OK).body(categoriaService.buscar(codigo));
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Void> atualizar(@RequestBody Categoria categoria, @PathVariable Long codigo, HttpServletResponse response) {
		categoria.setCodigo(codigo);
		categoriaService.atualizar(categoria);
		return ResponseEntity.noContent().build();
	}	
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> deletar(@PathVariable Long codigo){
		
		categoriaService.deletar(codigo);
		
		 return ResponseEntity.noContent().build();
	}
}
