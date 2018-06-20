package com.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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

import com.algamoney.api.event.RecursoCriadoEvent;
import com.algamoney.api.model.Pessoa;
import com.algamoney.api.services.PessoaService;

@RestController
@RequestMapping("pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public ResponseEntity<List<Pessoa>> listar() {
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.listar());
	}

	@PostMapping
	public ResponseEntity<?> criar(@RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaService.salvar(pessoa);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscar(@PathVariable Long codigo) {
		return ResponseEntity.status(HttpStatus.OK).body(pessoaService.buscar(codigo));
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@Valid @RequestBody Pessoa pessoa, @PathVariable Long codigo) {
		Pessoa pessoaSalva = pessoaService.atualizar(pessoa, codigo);
		return ResponseEntity.ok(pessoaSalva);
	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> deletar(@PathVariable Long codigo) {
		pessoaService.deletar(codigo);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{codigo}/ativo")
	public ResponseEntity<Pessoa> atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		pessoaService.atualizarPropriedadeAtivo(codigo, ativo);
		return ResponseEntity.noContent().build();
	}
}
