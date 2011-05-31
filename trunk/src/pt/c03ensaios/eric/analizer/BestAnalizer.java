package pt.c03ensaios.eric.analizer;

/*
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
*/
import java.util.HashMap;
import java.util.Vector;

import pt.c03ensaios.analizers.IAnalizer;
import pt.c03ensaios.dratini.simpleDecisionTree.EntropyDecisionTree;
import pt.c03ensaios.optimizers.IEnquirerSupports;
//import pt.c03ensaios.optimizers.IOptimizer;
//import pt.c03ensaios.parassauro.list.ParassauroLists;
import anima.annotation.Component;
import anima.component.base.ComponentBase;
//import anima.context.exception.ContextException;
//import anima.factory.IGlobalFactory;
//import anima.factory.context.componentContext.ComponentContextFactory;
//import anima.factory.exception.FactoryException;


@Component(id = "<http://purl.org/dcc/pt.c03ensaios.eric.analizer.BestAnalizer>", 
		provides = { "<http://purl.org/dcc/pt.c03ensaios.analizers.IAnalizer>" })

public class BestAnalizer extends ComponentBase implements IAnalizer
{
	
	public IEnquirerSupports getBest(String tag) {
		/*File f = new File("serializedBest.obj");
		ObjectInputStream ois = null;
		HashMap<String,IEnquirerSupports> copia = null;
		
		try {
			ois = new ObjectInputStream(new FileInputStream(f));
			copia =  (HashMap<String,IEnquirerSupports>) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return copia.get(tag);	*/
		HashMap<String,IEnquirerSupports> ret = GenDocs.generateAll();
		return ret.get(tag);
	}

	@Override
	public IEnquirerSupports getBest() {
		/*File f = new File("serializedTagEnqList.obj");
		ObjectInputStream ois = null;
		IEnquirerSupports copia = null;
		
		try {
			ois = new ObjectInputStream(new FileInputStream(f));
			copia = (IEnquirerSupports) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(copia==null){
			IGlobalFactory factory;
			IOptimizer aux = null;
			try{
				factory = ComponentContextFactory.createGlobalFactory();
				aux = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.dratini.timeOptimizer.LeastWorseTimeOptimizer>");
				copia = aux.getBest();
			} catch (ContextException e) {
				e.printStackTrace();
			} catch (FactoryException e) {
				e.printStackTrace();
			}
		}*/
		EntropyDecisionTree ent = new EntropyDecisionTree();
		return ent;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vector<String> getOPTList() {
		HashMap<String,IEnquirerSupports> ret = GenDocs.generateAll();
		return (Vector<String>)ret.keySet();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vector<String> getOPTTypes() {
		HashMap<String,IEnquirerSupports> ret = GenDocs.generateAll();
		return (Vector<String>)ret.keySet();
	}
}
