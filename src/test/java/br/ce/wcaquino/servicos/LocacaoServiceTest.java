package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;

import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

import br.ce.wcaquino.utils.DataUtils;
import org.junit.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


public class LocacaoServiceTest {

	private LocacaoService service;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
//		System.out.println("Before");
		service = new LocacaoService();
	}
//	@After
//	public void tearDown(){
//		System.out.println("After");
//	}
//
//	@BeforeClass
//	public static void setupClass(){
//		System.out.println("Before Class");
//	}
//
//	@AfterClass
//	public static void tearDownClass() {
//		System.out.println("After Class");
//	}
	@Test
	public void deveAlugarFilme() throws Exception {
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));//faz excecao para um dia especifico

		//cenario
		//LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));

//		System.out.println("Teste!");

		//ação
		Locacao locacao = service.alugarFilme(usuario, filmes);

		//verificacao
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(),new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));

		//equalTo = é igual á...
	}

	//FORMA "ELEGANTE" DE REALIZAR UM TESTE.
	@Test(expected = FilmeSemEstoqueException.class)// seria uma excessão esperada no teste
	public void deveLançarExcecaoAoAlugarFilmeSemEsoque() throws Exception{ //parte da excessão

		//cenario
		//LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		//Filme filme = new Filme("Filme 1", 0 , 5.0);
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));

		//acao
		service.alugarFilme(usuario, filmes);
	}
     // F0RMA "ROBUSTA" DE REALIZAR UM TESTE.
	//Outro meio de fazer o mesmo teste igual ao de cima.
//	@Test
//	public void testLocacao_filmeSemEstoque_2() {
//
//		//cenario
//		LocacaoService service = new LocacaoService();
//		Usuario usuario = new Usuario("Usuario 1");
//		Filme filme = new Filme("Filme 1", 1 , 5.0);
//
//		//acao
//		try {
//			service.alugarFilme(usuario, filme);
//			fail("Deveria ter lançado uma excecao");
//		} catch (Exception e) {
//			assertThat(e.getMessage(), is("Filme sem estoque"));
//		}
//	}
//
	//FORMA "NOVA" DE REALIZAR UM TESTE
//	@Test
//	public void testLocacao_filmeSemEstoque_3() throws Exception{ // throws é parte da excessão
//
//		//cenario
//		LocacaoService service = new LocacaoService();
//		Usuario usuario = new Usuario("Usuario 1");
//		Filme filme = new Filme("Filme 1", 0 , 5.0);
//
//
//		//executando a exception
//		exception.expect(Exception.class);
//		exception.expectMessage("Filme sem estoque");
//
//		//acao
//		service.alugarFilme(usuario, filme);
//	}

	//FORMA "ROBUSTA" DE REALIZAR UM TESTE
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		//cenario
//		LocacaoService service = new LocacaoService();
//		Filme filme = new Filme("Filme 2", 1 , 4.0);
		List<Filme> filmes = Arrays.asList(new Filme("Filme 2", 1, 4.0));

		//acao
		try {
			service.alugarFilme(null, filmes);
			fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}
		//System.out.println("Forma Robusta");
	}

	//FORMA "NOVA" DE REALIZAR UM TESTE
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
//		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");

		//acao
		service.alugarFilme(usuario, null);

		System.out.println("Forma Nova");
	}

	@Test
	public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		Usuario usuario = new Usuario("usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1",2,4.0),
				                           new Filme("Filme 2", 2, 4.0),
		                                   new Filme("Filme 3", 2, 4.0));

		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);

		//verificacao
		Assert.assertThat(resultado.getValor(), is(11.0));
	}

	@Test
	public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		Usuario usuario = new Usuario("usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0),
				                           new Filme("Filme 2", 2, 4.0),
				                           new Filme("Filme 3", 2, 4.0),
		                                   new Filme("Filme 4", 2, 4.0));

		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);

		//verificacao
		Assert.assertThat(resultado.getValor(), is(13.0));
	}

	@Test
	public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		Usuario usuario = new Usuario("usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0),
				new Filme("Filme 5", 2, 4.0));

		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);

		//verificacao
		Assert.assertThat(resultado.getValor(), is(14.0));
	}

	@Test
	public void devePagar0PctNoFilme6() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		Usuario usuario = new Usuario("usuario 1");
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0),
				new Filme("Filme 5", 2, 4.0), new Filme("Filme 6", 2, 4.0));

		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);

		//verificacao
		Assert.assertThat(resultado.getValor(), is(14.0));
	}

	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		//Assumptions quando o teste pode ou não pode ser executado
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		//cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));

		//acao
		Locacao retorno = service.alugarFilme(usuario, filmes);

		//verificacao
		boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
		Assert.assertTrue(ehSegunda);
	}
}
