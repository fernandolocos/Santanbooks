package pt.c03ensaios.relu;
import anima.annotation.ComponentInterface;
import anima.component.ISupports;
/**
 * Interface responsavel por fazer a busca de uma String em dois tipos de sistemas de
 * buscas diferentes: Wikipedia e Google.
 * @author Lucas Farris e Renato Lochetti
 *
 */
 @ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.relu.ISearcher>")
public interface ISearcher extends ISupports {
	
	/**
	 * Constante referente ao endereço de busca na Wikipedia
	 */
	public static final String URL_WIKI = "http://pt.wikipedia.org/wiki/";
	/**
	 * Constante referente ao endereço de busca no Google
	 */
	public static final String URL_GOOGLE = "http://www.google.com/search?q=";
	
	/**
	 * Abre o browser carregando a página referente à url do sistema de busca
	 * passado por parâmetro.
	 * Por motivos de segurança, se por acaso a string passada por parâmetro for 
	 * diferente das constantes definidas
	 * pela interface, o sistema utilizará por padrão a busca no Google.
	 * @param url String - Buscador que voce deseja usar.
	 * @param nomeAnimal - nome do Animal a ser procurado.
	 */
	public void openBrowser(String url, String nomeAnimal);

}
