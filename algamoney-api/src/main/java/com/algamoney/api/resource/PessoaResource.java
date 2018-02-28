package com.algamoney.api.resource;

import java.net.URI;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

import com.algamoney.api.model.Pessoa;
import com.algamoney.api.services.PessoaService;

@RestController
@RequestMapping("pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService pessoaService;

	@GetMapping
	public ResponseEntity<List<Pessoa>> listar() {
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.listar());
	}

	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody Pessoa pessoa) {
		// public ResponseEntity<?> criar(@RequestBody Pessoa pessoa,
		// HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaService.salvar(pessoa);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
									.buildAndExpand(pessoaSalva.getCodigo()).toUri();

		// response.setHeader("Location", uri.toASCIIString());
		// return ResponseEntity.created(uri).body(pessoaSalva);
		return ResponseEntity.created(uri).build();
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscar(@PathVariable Long codigo) {
		return ResponseEntity.status(HttpStatus.OK).body(pessoaService.buscar(codigo));
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Void> atualizar(@RequestBody Pessoa pessoa, @PathVariable Long codigo, HttpServletResponse response) {
		pessoa.setCodigo(codigo);
		pessoaService.atualizar(pessoa);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> deletar(@PathVariable Long codigo) {
		pessoaService.deletar(codigo);
		return ResponseEntity.noContent().build();
	}
}
