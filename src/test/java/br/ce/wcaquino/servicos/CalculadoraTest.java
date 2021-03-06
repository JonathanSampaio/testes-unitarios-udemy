package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.NaoPodeDivirPorZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CalculadoraTest {

   private Calculadora calc;

   @Before
   public void setup(){
       calc = new Calculadora();
   }

    @Test
    public void deveSomarDoisValores(){
        //cenario
        int a = 5;
        int b = 3;

        //acao
        int resultado = calc.somar(a, b);

        //verificacao
        Assert.assertEquals(8 , resultado);
    }

    @Test
    public void deveSubtrairDoisValores(){
        //cenario
        int a = 4;
        int b = 1;

        //acao
        int resultado = calc.subtrair(a, b);

        //verificacao
        Assert.assertEquals(3, resultado);
    }

    @Test
    public void deveDividirDoisValores() throws NaoPodeDivirPorZeroException {
        //cenario
        int a = 8;
        int b = 4;

        //acao
        int resultado = calc.dividir(a, b);

        //verificacao
        Assert.assertEquals(2, resultado);
    }

    @Test(expected = NaoPodeDivirPorZeroException.class)
    public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDivirPorZeroException {

        int a = 0;
        int b = 3;

//       int resultado = calc4.dividir(a, b);
        calc.dividir(a, b);
//        exception.expectMessage("Lancar excecao");
//        Assert.assertEquals(0, resultado);
    }

}
