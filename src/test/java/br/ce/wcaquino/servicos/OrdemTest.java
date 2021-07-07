package br.ce.wcaquino.servicos;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


import static org.junit.jupiter.api.Assertions.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdemTest {

    public static int contador = 0;

    @Test
    public void inicio(){
        contador = 1;
    }

    @Test
    public void verifica(){
        assertEquals(1, contador);
    }


}
