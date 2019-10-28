package br.transversa.backend.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.transversa.backend.model.Automovel;
import br.transversa.backend.model.EstadoPedido;
import br.transversa.backend.model.ObservacaoEstadoPedido;
import br.transversa.backend.model.Produto;
import br.transversa.backend.model.Quilometragem;
import br.transversa.backend.model.User;
import br.transversa.backend.payload.ApiResponse;
import br.transversa.backend.payload.NewAutomovelRequest;
import br.transversa.backend.service.AutomovelService;

@RestController
@RequestMapping("/api/v1")
public class AutomovelController {

	@Autowired
	AutomovelService automovelService;

	@PostMapping(path = "/automovel/add")
	ResponseEntity<?> registrarProduto(@RequestBody NewAutomovelRequest automovelRequest) {

		Automovel automovel = new Automovel();
		automovel.setDescricao(automovelRequest.getDescricao());
		automovel.setMatricula(automovelRequest.getMatricula());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = new User();
		loggedUser.setId(Long.parseLong(auth.getName()));
		automovel.setUser(loggedUser);

		automovel.setDataAdicionado(new Timestamp(new Date().getTime()));

		automovelService.save(automovel);
		
		Quilometragem quilometragemObject = new Quilometragem();
		
		quilometragemObject.setQuilometragemAtual(new BigDecimal(0));
		quilometragemObject.setDataAdicionado(new Timestamp(new Date().getTime()));
		
		User userAux = new User();
		userAux.setId(Long.parseLong(auth.getName()));
		quilometragemObject.setUser(userAux);
		
//		quilometragemObject.setUser(new User(Long.parseLong(auth.getName())));
//		

//
//		
//		
////		Automovel automovelAux = new Automovel();
////		automovelAux.setId(automovel.getId());
		quilometragemObject.setAutomovel(new Automovel(automovel.getId()));


		automovelService.saveQuilometragem(quilometragemObject);

		return new ResponseEntity(new ApiResponse(true, "Produto adicionado com sucesso"), HttpStatus.CREATED);

	}
	
	@GetMapping(path = "/automovel/listAll")
	List<Automovel> findAllAutomoveis() {
				
		return automovelService.findAllAutomovel();	
	}
	
	@GetMapping(path = "/automovel/page/{pageNumber}")
	Page<Quilometragem> findAutomovelByPage( @PathVariable(name = "pageNumber", required = false) int pageNumber) {
				
		return automovelService.findAutomovelByPage(pageNumber);	
	}


	@PostMapping(path = "/quilometragem/add")
	ResponseEntity<?> registrarProduto(@RequestParam("file") MultipartFile file,
			@RequestParam("quilometragem") BigDecimal quilometragem, @RequestParam("matricula") Long matricula) throws IOException {

		Quilometragem quilometragemObject = new Quilometragem();

		quilometragemObject.setData(file.getBytes());
		quilometragemObject.setFileType(file.getContentType());
		
		UUID uuid = UUID.randomUUID();
		quilometragemObject.setUuid(uuid.toString());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = new User();
		loggedUser.setId(Long.parseLong(auth.getName()));
		quilometragemObject.setUser(loggedUser);
		
		quilometragemObject.setQuilometragemAtual(quilometragem);
		
		
		Automovel automovel = new Automovel();
		automovel.setId(matricula);

		quilometragemObject.setAutomovel(automovel);
		quilometragemObject.setDataAdicionado(new Timestamp(new Date().getTime()));


		automovelService.saveQuilometragem(quilometragemObject);
		

		return new ResponseEntity(new ApiResponse(true, "Quilometragem adicionada com sucesso"), HttpStatus.CREATED);

	}

}
