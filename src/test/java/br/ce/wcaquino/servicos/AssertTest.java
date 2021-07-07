package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Usuario;
import org.junit.Assert;
import org.junit.Test;

public class AssertTest {

    @Test
    public void test(){
        Assert.assertTrue(true);
        Assert.assertFalse(false);

        Assert.assertEquals("Erro de comparação", 1, 1);
        Assert.assertEquals(0.51, 0.51, 0.01);// delta faz fucionar o teste se passando por "mediador"
        Assert.assertEquals(Math.PI, 3.14, 0.01);

        int i = 5;
        Integer i2 =5;
        Assert.assertEquals(Integer.valueOf(i), i2);//ou passa o tipo primitivo para objeto
        Assert.assertEquals(i, i2.intValue());//ou passa o objeto para tipo primitivo

        Assert.assertEquals("bola", "bola");
        Assert.assertTrue("bola".equalsIgnoreCase( "Bola"));//modo de fazer o teste aceitar letra maiuscula/ minuscula
        Assert.assertTrue("bola".startsWith("bo"));//força o teste a aceitar outra coisa no lugar

        Usuario u1 = new Usuario("usuario 1");
        Usuario u2 = new Usuario("usuario 1");
        Usuario u3 = null;

        Assert.assertEquals(u1, u2);

        Assert.assertSame(u2, u2); // verifica se sao da mesma instacia

        Assert.assertNull(u3); // verfica se esta vazio
    }
}
