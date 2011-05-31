package pt.c03ensaios.relu;

import anima.annotation.Component;
import anima.component.base.ComponentBase;
/**
 * @author Lucas Farris e Renato Lochetti
 *
 */
 @Component(id="<http://purl.org/dcc/pt.c03ensaios.relu.Searcher>",
        provides={"<http://purl.org/dcc/pt.c03ensaios.relu.ISearcher>"})
public class Searcher extends ComponentBase implements ISearcher {
	
	private String nomeDoAnimal;

	/** (non-Javadoc)
	 * @see pt.c03ensaios.relu.ISearcher#openBrowser(java.lang.String)
	 */
	public void openBrowser(String url, String nomeAnimal) {
		this.nomeDoAnimal = nomeAnimal;
		String urlFinal = null;
		if(url == ISearcher.URL_GOOGLE || url == ISearcher.URL_WIKI)
			urlFinal = url+ nomeDoAnimal;
		else
			urlFinal = ISearcher.URL_GOOGLE + nomeDoAnimal;
		String os = System.getProperty("os.name").toLowerCase();
		Runtime rt = Runtime.getRuntime();

		try {
			if (os.indexOf("win") >= 0) {
				rt.exec("rundll32 url.dll,FileProtocolHandler " + urlFinal);

			} else if (os.indexOf("mac") >= 0) {
				rt.exec("open " + urlFinal);
			} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {

				String[] browsers = { "epiphany", "firefox", "mozilla",
						"konqueror", "netscape", "opera", "links", "lynx" };
				StringBuffer cmd = new StringBuffer();
				for (int i = 0; i < browsers.length; i++)
					cmd.append((i == 0 ? "" : " || ") + browsers[i] + " \""
							+ urlFinal + "\" ");
				rt.exec(new String[] { "sh", "-c", cmd.toString() });
			} else {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
}
