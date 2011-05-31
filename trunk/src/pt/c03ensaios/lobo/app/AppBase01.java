package pt.c03ensaios.lobo.app;

import pt.c01interfaces.s01chaveid.lobo.EnquirerAdvanced;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c03ensaios.afinder.animalpix.IAnimalPix;
import pt.c03ensaios.lobo.impl.ResponderGraphics;
import pt.c03ensaios.lobo.inter.IResponderGraphics;
import pt.c03ensaios.naxxramas.INaxxramas;
import pt.c03ensaios.relu.ISearcher;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

public class AppBase01 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            IGlobalFactory factory = ComponentContextFactory
                    .createGlobalFactory();

            IEnquirer enquirer = new EnquirerAdvanced();

            IResponderGraphics responderRequires = factory
                    .createInstance("<http://purl.org/dcc/pt.c03ensaios.lobo.impl.ResponderGraphics>");

            INaxxramas requiredFrame = factory
                    .createInstance("<http://purl.org/dcc/pt.c03ensaios.naxxramas.Naxxramas>");

            IAnimalPix animalpix = factory
                    .createInstance("<http://purl.org/dcc/pt.c03ensaios.afinder.animalpix.AnimalPix>");

            ISearcher searcher = factory
                    .createInstance("<http://purl.org/dcc/pt.c03ensaios.relu.Searcher>");

            responderRequires.connect(requiredFrame);

            IResponderGraphics responder = (ResponderGraphics) responderRequires;
            responder.connect(animalpix);
            responder.connect(searcher);

            responder.showGameFrame();

            enquirer.connect(responder);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
