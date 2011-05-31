package pt.c03ensaios.dratini.timeOptimizer;
import anima.annotation.Component;

import pt.c01interfaces.s01chaveid.dratini.Responder;
import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c03ensaios.optimizers.IOptimizer;

@Component(id = "<http://purl.org/dcc/pt.c03ensaios.dratini.timeOptimizer.LeastWorseTimeOptimizer>", 
		provides = { "<http://purl.org/dcc/pt.c03ensaios.optimizers.IOptimizer>"})

	
public class LeastWorseTimeOptimizer extends TimeOptimizer implements IOptimizer {

	public LeastWorseTimeOptimizer() {
	}
	
	
	protected void testTime(){
		int certos = 0;
		int size = enquirers.size();
		BaseConhecimento baseC = new BaseConhecimento();
		String[] animals = baseC.listaNomes();
		long timeInit;
		long timeEnd;
		Responder resp = new Responder();
		for (int i=0; i<size; i++){
			for (int j=0; j<animals.length; j++){
				resp.carregarAnimal(animals[j]);
				
				timeInit = System.currentTimeMillis();
				
				try{
				enquirers.get(i).connect(resp);
				
				if (resp.lastAnswer)
					certos +=1;
				}
				catch (Exception e) {
					System.err.println("Exceção no enquirer "+lista.get(i));
					times[i] = -1;
					break;
				}
				catch(Error e){
					System.err.println("Erro no enquirer "+lista.get(i));
					times[i] = -1;
					break;
				}
					
				timeEnd = System.currentTimeMillis();
				if (times[i]<(timeEnd - timeInit))
					times[i] = timeEnd - timeInit;
			}
			acertos[i] = certos/animals.length;
			certos = 0;
		}
	}

	@Override
	public String getOPTType() {
		return "least_worse_time";
	}


	@Override
	public String getOPTURI() {
		return "<http://purl.org/dcc/pt.c03ensaios.dratini.timeOptimizer.LeastWorseTimeOptimizer>";
	}
}
