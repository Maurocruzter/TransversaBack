package br.transversa.backend.util;

import java.util.Arrays;
import java.util.List;

public interface AppConstants {
    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "30";

    int MAX_PAGE_SIZE = 50;

    final List<String> FORMA_PAGAMENTOS =  Arrays.asList("CARTAO", "BOLETO", "DEPOSITO", "TRANSFERENCIA"); 

    
    final List<String> ROLES =  Arrays.asList("ROLE_VENDEDOR", "ROLE_ENTREGADOR", "ROLE_CLIENTE","ROLE_FORNECEDOR", "ROLE_ADMIN", "ROLE_BASE"); 
}
