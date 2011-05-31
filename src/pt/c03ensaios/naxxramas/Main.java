package pt.c03ensaios.naxxramas;

import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c03ensaios.afinder.animalpix.IAnimalPix;
import pt.c03ensaios.relu.ISearcher;
import anima.component.IRequires;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

/**
 * App to test Sapphiron and Naxxramas components.
 * 
 * @author Danilo Carvalho Grael<dancgr@gmail.com> - 101961
 * @author Flavia Pisani<flavia.pisani@gmail.com> - 104941
 */
public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) {
        IGlobalFactory factory = null;
        try {
            factory = ComponentContextFactory.createGlobalFactory();
        } catch (ContextException e) {
            System.err.println(e.getMessage());
        } catch (FactoryException e) {
            System.err.println(e.getMessage());
        }

        IRequires<INaxxramas> sapphironNaxxramas = factory.createInstance(
        "<http://purl.org/dcc/pt.c03ensaios.naxxramas.Sapphiron>");

        INaxxramas requiredFrame = factory.createInstance(
            "<http://purl.org/dcc/pt.c03ensaios.naxxramas.Naxxramas>");
         
        // Creates a window for the Sapphiron component using Naxxramas.
        sapphironNaxxramas.connect(requiredFrame);

        IEnquirerComponentReceptacle sapphironEnquirer =
            (IEnquirerComponentReceptacle)sapphironNaxxramas;

        String enquirers[] = {
            "pt.c01interfaces.s01chaveid.pizza.EnquirerAdvanced",
            "pt.c01interfaces.s01chaveid.lobo.EnquirerAdvanced",
            "pt.c01interfaces.s01chaveid.naxxramas.EnquirerAdvanced"
        };

        try {
            for (String enq : enquirers) {
                IEnquirerWrapperComponent cw = factory.createInstance(
                "<http://purl.org/dcc/pt.c03ensaios.naxxramas.EnquirerWrapper>");

                cw.wrap(enq);
                // Add a new Enquirer to the test list.
                sapphironEnquirer.connect(cw);
            }
        }
        catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

        ISearcherReceptacle sapphironSearcher =
            (ISearcherReceptacle)sapphironNaxxramas;

        ISearcher searcher = factory.createInstance(
            "<http://purl.org/dcc/pt.c03ensaios.relu.Searcher>");

        // Add a new tab to the Sapphiron component.
        sapphironSearcher.connect(searcher);

        IAnimalPixReceptacle sapphironAnimalPix =
            (IAnimalPixReceptacle)sapphironSearcher;

        IAnimalPix animalPix = factory.createInstance(
            "<http://purl.org/dcc/pt.c03ensaios.afinder.animalpix.AnimalPix>");

        // Add a new tab to the Sapphiron component.
        sapphironAnimalPix.connect(animalPix);

        requiredFrame.setVisible(true);
    }
}
