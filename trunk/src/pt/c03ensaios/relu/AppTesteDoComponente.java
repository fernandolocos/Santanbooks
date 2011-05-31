package pt.c03ensaios.relu;

import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

public class AppTesteDoComponente {

	public static void main(String[] args) {

		try{
			IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();
			ISearcher searcher = factory.createInstance(
					"<http://purl.org/dcc/pt.c03ensaios.relu.Searcher>");
			searcher.openBrowser(ISearcher.URL_WIKI, "Ornitorrinco");
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
