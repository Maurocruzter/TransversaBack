package br.transversa.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.transversa.backend.model.Cidade;
import br.transversa.backend.model.EstadoPedido;
import br.transversa.backend.model.User;
import br.transversa.backend.payload.ApiResponse;
import br.transversa.backend.service.CidadeService;
import br.transversa.backend.util.AppConstants;

@RestController
@RequestMapping("/api/v1")
public class CidadeController {
	
	@Autowired
	CidadeService cidadeService;
	
	@PostMapping(path = "/cidade/novaCidade")
	ResponseEntity<?> inserirNovaCidade(
			@RequestBody Cidade cidade) {


		
		cidade.setIdEstado(AppConstants.ESTADOS_BRASILEIROS.indexOf(cidade.getEstadoNome()));
		
		if(cidade.getIdEstado() == -1) {
			return new ResponseEntity(new ApiResponse(true, "Produto adicionado com sucesso"), HttpStatus.BAD_REQUEST); 
		}
		
		cidadeService.save(cidade);
		return new ResponseEntity(new ApiResponse(true, "Produto adicionado com sucesso"), HttpStatus.ACCEPTED); 
		
        		
	}
	
	
	@GetMapping(path = "/cidade/search/nome/{nome}/"
			+ "abreviacao/{abreviacao}/estado/{estado}/"
			+ "page/{pageNumber}")
	public Page<Cidade> searchCidadeByFields(
			@PathVariable(name = "nome", required = true) String nome,
			@PathVariable(name = "abreviacao", required = true) String abreviacao,
			@PathVariable(name = "estado", required = true) String estado,
			@PathVariable(name = "pageNumber", required = true) int pageNumber) {

		
		return cidadeService.findCidadeFilter(nome, abreviacao, estado, pageNumber);

	}
	
	
	@GetMapping(path = "/cidade/searchAll/nome/{nome}/"
			+ "abreviacao/{abreviacao}/estado/{estado}")
	public List<Cidade> searchAllCidadeByFields(
			@PathVariable(name = "nome", required = true) String nome,
			@PathVariable(name = "abreviacao", required = true) String abreviacao,
			@PathVariable(name = "estado", required = true) String estado) {

		return cidadeService.findAllCidadeFilter(nome, abreviacao, estado, 1);

	}
	


}
