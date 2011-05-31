package pt.c03ensaios.parassauro.list.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import anima.component.ISupports;
import anima.factory.context.componentContext.ComponentContextFactory;

/**
 * Handler XML SAX que filtra compontentes, guardando os de certo tipo numa lista.
 * 
 * @param <A> Interface do tipo do componente a ser obtido.
 */
public class XMLChupinzer<A extends ISupports> extends DefaultHandler{
	
	private Class<?> tipo;
	private ArrayList<String> URIs;
	
	/**
	 * Cria instância com a Class necessária para comparações.
	 * 
	 * @param classType A classe em que estamos interessados em listar.
	 */
	public XMLChupinzer(Class<?> classType){
		this.tipo=classType;
		this.URIs=new ArrayList<String>();
	}
	
	/**
	 * Retorna a Lista de Componentes obtida.
	 * 
	 * @return a Lista de Componentes obtida.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<A> getComponents(){
		ArrayList<A> array=new ArrayList<A>();
		for(String uri:this.URIs)
			try{
				array.add((A)ComponentContextFactory.createGlobalFactory().createInstance(uri));
			}
			catch(Exception e){}
		return array;
	}
	
	/**
	 * Retorna a Lista de URIs dos Componentes obtida.
	 * 
	 * @return a Lista de URIs dos Componentes obtida.
	 */
	public ArrayList<String> getURIs(){
		return this.URIs;
	}
	
	/**
	 * Pega os nós de definição de componentes no arquivo XML e guarda os
	 * respectivos URI numa {@link ArrayList} caso eles sejam do tipo requerido.
	 * 
	 * @param uri Não é utilizado.
	 * @param localName Não é utilizado.
	 * @param qName É utilizado para saber o nome do elemento XML.
	 * @param attributes É utilizado para obter a URI do atributo mainModule.
	 */
	public void startElement(String uri, String localName, String qName, Attributes attributes){
		if(qName.equals("dcc")){
			boolean eh=false;
			try{
				for(Class<?> i:Class.forName(attributes.getValue("mainModule")).getInterfaces())
					if(this.tipo.isAssignableFrom(i)){
						eh=true;
						break;
					}
				if(eh)
					this.URIs.add("<http://purl.org/dcc/"+attributes.getValue("mainModule")+'>');
			}
			catch(ClassNotFoundException e){}
		}
	}
}