package pt.c03ensaios.tochinhas2.impl;

import anima.component.IRequires;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import pt.c03ensaios.linnaeus.*;
import pt.c03ensaios.tochinhas2.impl.*; // only needed outside our package, but I put it anyway

public class HowTo {

    public static void main(String[] args)
    {
        try {
            IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();

            IQuestions questions = factory.createInstance(
            	"<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.Questions>",
            	"<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.IQuestions>");
            
            IAnimalsDatabase database = factory.createInstance(
                "<http://purl.org/dcc/pt.c03ensaios.linnaeus.AnimalsDatabase>",
                "<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalsDatabase>");
            
            IRequires<IAnimalsDatabase> connectQuestions = questions.queryReceptacle(
            	"<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalsDatabase>");
            
            IRequires<IQuestions> connectDatabase = database.queryReceptacle(
        		"<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.IQuestions>");
            
            connectQuestions.connect(database);
            connectDatabase.connect(questions);
            
            for (String s : questions.vectorToArray(questions.getQuestions())) {
            	System.out.println(s);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
