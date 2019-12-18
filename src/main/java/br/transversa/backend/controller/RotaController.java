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
import br.transversa.backend.model.Rota;
import br.transversa.backend.model.RotaHasUser;
import br.transversa.backend.model.User;
import br.transversa.backend.payload.ApiResponse;
import br.transversa.backend.service.CidadeService;
import br.transversa.backend.service.RotaHasUserService;
import br.transversa.backend.service.RotaService;
import br.transversa.backend.util.AppConstants;

@RestController
@RequestMapping("/api/v1")
public class RotaController {
	
	@Autowired
	RotaService rotaService;
	
	@Autowired
	RotaHasUserService rotaHasUserService;
	
	@PostMapping(path = "/rota/novaRota")
	ResponseEntity<?> inserirNovaCidade(
			@RequestBody Rota rota) {

		Cidade cidade = new Cidade();
		cidade.setId(rota.getCidadeId());
		rota.setCidade(cidade);

		rotaService.save(rota);

		
		
		return new ResponseEntity(new ApiResponse(true, "Rota adicionada com sucesso"), HttpStatus.ACCEPTED); 
			
	}
	
	
	@GetMapping(path = "/rotaVenda/search/rota/{rota}/"
			+ "cidade/{cidade}/estado/{estado}/"
			+ "page/{pageNumber}")
	public Page<Rota> searchCidadeByFields(
			@PathVariable(name = "rota", required = true) String rota,
			@PathVariable(name = "cidade", required = true) String cidade,
			@PathVariable(name = "estado", required = true) String estado,
			@PathVariable(name = "pageNumber", required = true) int pageNumber) {

		return rotaService.findrotaVendaFilter(rota, cidade, estado, pageNumber);
		
//		return cidadeService.findCidadeFilter(nome, abreviacao, estado, pageNumber);

	}
	
	@GetMapping(path = "/rotaVenda/findAll")
	public List<Rota> searchAllRotaVenda() {

		return rotaService.findAllRotaVenda();

	}
	
	@PostMapping(path = "/rotaVenda/vendedor/{vendedor}/"
			+ "rota/{rota}")
	ResponseEntity<?> saveVendedorRota(
			@PathVariable(name = "vendedor", required = true) Long vendedor,
			@PathVariable(name = "rota", required = true) Long rota) {


		RotaHasUser rotaHasUser = new RotaHasUser();
		User user = new User();
		Rota rotaAux = new Rota();
		user.setId(vendedor);
		
		
		rotaAux.setId(rota);
		
		
		rotaHasUser.setUser(user);
		rotaHasUser.setRota(rotaAux);
	 
		if(rotaHasUserService.findIfExists(vendedor, rota).isPresent()) {
			return null;
		}

		rotaHasUserService.save(rotaHasUser);

		return new ResponseEntity(new ApiResponse(true, "Rota adicionada com sucesso"), HttpStatus.ACCEPTED); 

	}
	
	
	@GetMapping(path = "/rotaVenda/{idRota}")
	public Rota searchRotaVendaById(
			@PathVariable(name = "idRota", required = true) Long idRota) {

		return rotaService.findrotaVendaById(idRota);
		
	}



}
