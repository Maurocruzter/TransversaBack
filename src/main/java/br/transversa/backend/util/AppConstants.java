package br.transversa.backend.util;

import java.util.Arrays;
import java.util.List;

public interface AppConstants {
    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "30";

    int MAX_PAGE_SIZE = 50;

    final List<String> FORMA_PAGAMENTOS =  Arrays.asList("CARTAO", "BOLETO", "DEPOSITO", "TRANSFERENCIA"); 
    
    final List<String> ESTADOS_BRASILEIROS =  Arrays.asList("AC", "AL", "AP", "AM",
    														"BA", "CE", "DF", "ES",
    														"GO", "MA", "MT", "MS",
    														"MG", "PA", "PB", "PR",
    														"PE", "PI", "RJ", "RN",
    														"RS", "RO", "RR", "SC",
    														"SP", "SE", "TO"); 

    
    final List<String> TIPO_ESTABELECIMENTOS =  Arrays.asList(
    		"BOTECO", "BAR", "LOJA","SUPERMERCADO", "MINIMERCADO", "MERCEARIA"); 

    
    final List<String> ROLES =  Arrays.asList("ROLE_VENDEDOR", "ROLE_ENTREGADOR", "ROLE_CLIENTE","ROLE_FORNECEDOR", "ROLE_ADMIN", "ROLE_BASE"); 
}
