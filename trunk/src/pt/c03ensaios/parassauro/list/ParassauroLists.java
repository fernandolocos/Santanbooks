package pt.c03ensaios.parassauro.list;

import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import pt.c03ensaios.optimizers.IEnquirerSupports;
import pt.c03ensaios.optimizers.IOptimizer;
import pt.c03ensaios.parassauro.list.xml.XMLChupinzer;
import anima.annotation.Component;
import anima.component.base.ComponentBase;
import anima.factory.context.componentContext.ComponentContextFactory;

/**
 * Componente que possibilita obter listagens de outros componentes de certo tipo.
 * Quando algum método get desse componente é chamado, ele pede o parse do arquivo
 * config.xml utilizando {@link XMLChupinzer}, um handler XML que utiliza
 * um {@link Class} para reconhecer os componentes de certo tipo. Ao final, esse
 * método get chamado retorna uma {@link ArrayList} de URIs ({@link String}) ou
 * de Componentes daquele tipo.
 */
@Component(id = "<http://purl.org/dcc/pt.c03ensaios.parassauro.list.ParassauroLists>",
        provides = {"<http://purl.org/dcc/pt.c03ensaios.parassauro.list.IParassauroLists>"})
public class ParassauroLists extends ComponentBase implements IParassauroLists{
	
	/**
	 * Retorna todos os {@link IEnquirerSupports}s do acervo de componentes.
	 * 
	 * @return todos os {@link IEnquirerSupports}s do acervo de componentes. 
	 */
	@Override
	public ArrayList<IEnquirerSupports> getEnquirerSupports(){
		XMLChupinzer<IEnquirerSupports> chupin=new XMLChupinzer<IEnquirerSupports>(IEnquirerSupports.class);
		parse(chupin);
		return chupin.getComponents();
	}
	
	/**
	 * Retorna todos os {@link IOptimizer}s do acervo de componentes.
	 * 
	 * @return todos os {@link IOptimizer}s do acervo de componentes.
	 */
	@Override
	public ArrayList<IOptimizer> getOptimizers(){
		XMLChupinzer<IOptimizer> chupin=new XMLChupinzer<IOptimizer>(IOptimizer.class);
		parse(chupin);
		return chupin.getComponents();
	}
	
	/**
	 * Retorna todos os URIs dos {@link IEnquirerSupports}s do acervo de componentes.
	 * 
	 * @return todos os URIs dos {@link IEnquirerSupports}s do acervo de componentes. 
	 */
	@Override
	public ArrayList<String> getEnquirerSupportsURIs(){
		XMLChupinzer<IEnquirerSupports> chupin=new XMLChupinzer<IEnquirerSupports>(IEnquirerSupports.class);
		parse(chupin);
		return chupin.getURIs();
	}
	
	/**
	 * Retorna todos os URIs dos {@link IOptimizer}s do acervo de componentes.
	 * 
	 * @return todos os URIs dos {@link IOptimizer}s do acervo de componentes.
	 */
	@Override
	public ArrayList<String> getOptimizersURIs(){
		XMLChupinzer<IOptimizer> chupin=new XMLChupinzer<IOptimizer>(IOptimizer.class);
		parse(chupin);
		return chupin.getURIs();
	}
	
	/**
	 * Faz o parse do arquivo XML usando SAX.
	 * 
	 * @param chupin O Handler XML de SAX para ser utilizado.
	 */
	protected void parse(XMLChupinzer<?> chupin){
		try{
			// cria a factory e o parser
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			InputSource input=new InputSource(new FileReader(new File("config.xml")));
			parser.parse(input,chupin);
			System.gc();
		}
		catch(Exception w){
			System.out.println("Erro: Veja se esta usando a pasta /bin como raiz de execucao.");
		}
	}
	
	/**
	 * Teste que lista tudo.
	 * 
	 * @param args não serve pra nada.
	 * @throws Exception Como é um teste, que a Exception seja lançada.
	 */
	public static void main(String[] args)throws Exception{
		
		PrintStream syso=System.out;
		ParassauroLists para=ComponentContextFactory.createGlobalFactory().createInstance(
				"<http://purl.org/dcc/pt.c03ensaios.parassauro.list.ParassauroLists>");
		StringBuffer buffer=new StringBuffer();
		
		for(IOptimizer ies:para.getOptimizers())
			buffer.append("\nOptimizer: "+ies);
		
		for(IEnquirerSupports ies:para.getEnquirerSupports())
			buffer.append("\nEnquirer: "+ies);
		
		for(String ies:para.getOptimizersURIs())
			buffer.append("\nOptimizer: "+ies);
		
		for(String ies:para.getEnquirerSupportsURIs())
			buffer.append("\nEnquirer: "+ies);
		
		syso.println(buffer);
		System.exit(0);
	}
}