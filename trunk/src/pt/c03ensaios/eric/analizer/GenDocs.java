package pt.c03ensaios.eric.analizer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

import pt.c03ensaios.optimizers.IEnquirerSupports;
import pt.c03ensaios.optimizers.IOptimizer;
import pt.c03ensaios.parassauro.list.ParassauroLists;

public class GenDocs {
	public static void main(String[] args){
		System.out.println(generateAll());
	}
	public static HashMap<String,IEnquirerSupports> generateAll(){
		ArrayList<IEnquirerSupports> a = new ArrayList<IEnquirerSupports>();
		System.out.println("Criando lista de otimizadores");
		IGlobalFactory factory = null;
		try {
			factory = ComponentContextFactory.createGlobalFactory();
		} catch (ContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<IOptimizer> lista = new ArrayList<IOptimizer>();
		HashMap<String,IEnquirerSupports> listaTagEnq = new HashMap<String,IEnquirerSupports>();
		IOptimizer otm1=factory.createInstance(
				"<http://purl.org/dcc/pt.c03ensaios.dratini.timeOptimizer.AverageTimeOptimizerWrapper>");
		lista.add(otm1);
		IOptimizer otm2=factory.createInstance(
				"<http://purl.org/dcc/pt.c03ensaios.eric.optimizer.NumQuestionsOptimizer>");
		lista.add(otm2);
		
		System.out.println("Lista Gerada "+lista);
		for(IOptimizer opt: lista){
			try{
				listaTagEnq.put(opt.getOPTType(), opt.getBest());
			}
			catch(Exception err){
				
			}
			//System.out.println(opt.getBest()+" "+opt.getOPTType()+" "+opt.getBest());
		}
		/*
		File f1 = new File("serializedTagEnqList.obj");
		File f2 = new File("serializedBest.obj");
		try{
			ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(f1));
			oos1.writeObject(listaTagEnq);
			oos1.close();
			
			ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(f2));
			oos2.writeObject(listaTagEnq.get("min number of questions"));
			oos2.close();
			
		}catch(IOException err){
			err.printStackTrace();
		}
		System.out.println("objetos criados com sucesso");
		*/
		return listaTagEnq;
	}
}
