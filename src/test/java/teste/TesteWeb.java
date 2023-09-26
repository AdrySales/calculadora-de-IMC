package teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

class TesteWeb {

	static WebDriver driver;
	private static Document doc;
	private static NodeList ListaProcedimentos;
	private static NodeList ListaCasos;
	private Element caso;
	
	
	
	private String valorElementXml(String tag, int numCasoAtual) throws Exception {
		this.caso=(Element) ListaCasos.item(numCasoAtual);
		NodeList lista=this.caso.getElementsByTagName(tag);
		if(lista.getLength()<=0)
			throw new Exception("tag "+tag+ " não localizada!");
		return lista.item(0).getTextContent();
		
	}
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	
	System.setProperty("webdriver.gecko.driver","C:\\Users\\Yasmim Sales\\Pictures\\geckodriver.exe");
	driver = new FirefoxDriver();
	
	
	File inputFile=new File("src/test/resources/casos_testex.xml");
	DocumentBuilderFactory dbfactory=DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder=dbfactory.newDocumentBuilder();
	doc = dBuilder.parse(inputFile);
	doc.getDocumentElement().normalize();
	
	
	ListaProcedimentos=doc.getElementsByTagName("procedimento");

	Element procedimento=(Element) ListaProcedimentos.item(0);
	
	ListaCasos=procedimento.getElementsByTagName("caso");
	}


	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		
		
		driver.get("https://www.icatuseguros.com.br/PortalClienteOnLine/Simuladores/Area_Aberta_New/VGBL/frmSimuladorVgblnet_Icatu.asp?css=https://www.orama.com.br/static/css/icatu-simulator.css&taxaRetorno=3");	
		
	}
	

	@AfterEach
	void tearDown() throws Exception {
		
	}
	
	
	
	void idadeAtual(String idadeAtual) {
		driver.findElement(By.cssSelector("#Text7")).sendKeys(idadeAtual);}
	
	
	
	void idadeAposentadoria(String idadeAposentadoria) {
		driver.findElement(By.cssSelector("input[name='idadeApos']")).sendKeys(idadeAposentadoria);}
	
	
	void periodicidade(String periodicidade ) {
		Select select = new Select(driver.findElement(By.cssSelector("#Select4")));
		select.selectByVisibleText(periodicidade);}

	

	void rentabilidade(String rentabilidade ) {
		Select select = new Select(driver.findElement(By.cssSelector("select[name='taxa']")));
		select.selectByVisibleText(rentabilidade);}
	
	
	void tipoDeRenda(String tipoDeRenda ) {
		Select select = new Select(driver.findElement(By.cssSelector("#Select6")));
		select.selectByVisibleText(tipoDeRenda);}

	
	

	void opcaoRadio2() {
		driver.findElement(By.cssSelector("#Radio2")).click();}
	void opcaoRadio() {
		driver.findElement(By.cssSelector("#radio")).click();}
	
	
	void aporteInicial(String aporteInicial) {
		driver.findElement(By.cssSelector("input[name='contrib']")).sendKeys(aporteInicial);}
	

	void botaoCalcular() {
		driver.findElement(By.cssSelector(".Botao_simulador")).click();}
	

	void simulacaoAcertiva(String simulacaoAcertiva) {
		 assertEquals(simulacaoAcertiva,driver.findElement(By.cssSelector(".Texto_resultado_simualdor:nth-of-type(9)")).getText());}
	void simulacaoErronea(String resultado) {
		 assertEquals(resultado,driver.findElement(By.cssSelector(".Texto_erro")).getText());}
	
	

			@ParameterizedTest
			@ValueSource(strings= {"0","1"})
			void testesAcertivos(String codCaso) throws Exception {
				
				this.idadeAtual(this.valorElementXml("idadeAtual",Integer.parseInt(codCaso)));
				this.idadeAposentadoria(this.valorElementXml("idadeAposentadoria",Integer.parseInt(codCaso)));
			    this.periodicidade(this.valorElementXml("periodicidade",Integer.parseInt(codCaso)));
			    this.rentabilidade(this.valorElementXml("rentabilidade",Integer.parseInt(codCaso)));
			    this.tipoDeRenda(this.valorElementXml("tipoDeRenda",Integer.parseInt(codCaso)));
			    opcaoRadio2();
			    this.aporteInicial(this.valorElementXml("aporteInicial",Integer.parseInt(codCaso)));
			    this.botaoCalcular();
			    this.simulacaoAcertiva(this.valorElementXml("simulacaoAcertiva",Integer.parseInt(codCaso)));
			}
		
			@ParameterizedTest
			@ValueSource(strings= {"2","3"})
			void testesErroneos(String codCaso) throws Exception {
				
				this.idadeAtual(this.valorElementXml("idadeAtual",Integer.parseInt(codCaso)));
				this.idadeAposentadoria(this.valorElementXml("idadeAposentadoria",Integer.parseInt(codCaso)));
			    this.periodicidade(this.valorElementXml("periodicidade",Integer.parseInt(codCaso)));
			    this.rentabilidade(this.valorElementXml("rentabilidade",Integer.parseInt(codCaso)));
			    this.tipoDeRenda(this.valorElementXml("tipoDeRenda",Integer.parseInt(codCaso)));
			    opcaoRadio();
			    this.aporteInicial(this.valorElementXml("aporteInicial",Integer.parseInt(codCaso)));
			    this.botaoCalcular();
			    this.simulacaoErronea(this.valorElementXml("simulacaoErronea",Integer.parseInt(codCaso)));
			}
		
			
			

	}

	


