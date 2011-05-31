package pt.c03ensaios.dratini.timeOptimizer;

import java.util.ArrayList;
import java.util.Vector;

import pt.c03ensaios.optimizers.IEnquirerSupports;
import pt.c03ensaios.parassauro.list.IParassauroLists;
import anima.component.IRequires;
import anima.component.base.ComponentBase;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

public abstract class TimeOptimizer extends ComponentBase implements IRequires<IParassauroLists>{
	protected Vector<IEnquirerSupports> enquirers = new Vector<IEnquirerSupports>();
	protected long[] times;
	protected ArrayList<String> lista;
	private IParassauroLists lists;
	protected float[] acertos;
	
	protected void InitializeEnquirers(){
		times = new long[lista.size()];
		acertos = new float[lista.size()];
		for (int i =0; i<lista.size(); i++){
			initialize(lista.get(i), i);
		}
	}
	
	private void initialize(String uri, int pos){
		IGlobalFactory factory;
			
		try {
			
			factory = ComponentContextFactory.createGlobalFactory();
			long ini = System.currentTimeMillis();
			IEnquirerSupports aux0 = factory.createInstance(uri);
	        long fim = System.currentTimeMillis();
	        enquirers.add(aux0);
	        times[pos] = fim-ini;
	        
		} catch (ContextException e) {
			e.printStackTrace();
		} catch (FactoryException e) {
			e.printStackTrace();
		}		
	}
	
	public IEnquirerSupports getBest() {
		int best = -1;
		for (int i=0; i<times.length; i++){
			if (best == (-1)){
				if(times[i]!=(-1) && acertos[i]>= 0.85)
					best = i;
			} else if (times[i]<times[best] && times[i]!=(-1) && acertos[i]>= 0.85)
				best = i;
			System.out.println(lista.get(i)+" "+times[i]+ " porcentagem acertos "+acertos[i]);
		}
		if (best != (-1))
			return enquirers.get(best);
		return null;
	}
	
	public int getBestInt() {
		int best = -1;
		for (int i=0; i<times.length; i++){
			if (best == (-1) && times[i]!=(-1))
				best = i;
			if (times[i]<times[best] && times[i]!=(-1))
				best = i;
			System.out.println(lista.get(i)+" "+times[i]);
		}
		return best;
	}
	
	@Override
	public void connect(IParassauroLists arg0) {
		lists = arg0;
		lista = lists.getEnquirerSupportsURIs();
		InitializeEnquirers();
		testTime();
	}

	protected abstract void testTime();
}
