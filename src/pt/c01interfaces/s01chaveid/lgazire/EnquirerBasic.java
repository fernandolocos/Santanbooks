package pt.c01interfaces.s01chaveid.lgazire;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;



import pt.c01interfaces.s01chaveid.lgazire.MapReduce.Map;
import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquirerBasic implements IEnquirer
{
	public static final String DIRETORIO_RAIZ = "pt/c01interfaces/s01chaveid/s01base/bd/",
    EXTENSAO = ".txt";
	public Hashtable<String, Hashtable<String, Boolean>>  BuildBase(){
		IBaseConhecimento baseCon = (IBaseConhecimento) new BaseConhecimento();
		 Hashtable<String, IObjetoConhecimento> dicIt = new Hashtable<String, IObjetoConhecimento>();
	    ArrayList<String> lista = GetOnlyNames();
	    
	    Hashtable<String, Hashtable<String, Boolean>> animals = new Hashtable<String, Hashtable<String, Boolean>>();
	    
	    for (int i = 0; i < lista.size(); i++)
	    {
	        dicIt.put(lista.toArray()[i].toString(), baseCon.recuperaObjeto(lista.toArray()[i].toString()));
	    }
	    
	    Enumeration<String> keys = dicIt.keys();
	    while ( keys.hasMoreElements() )
	    {
	       String key = (String)keys.nextElement();
	       IObjetoConhecimento objCon = (IObjetoConhecimento)dicIt.get( key );
	       IDeclaracao getDeclaration = objCon.primeira();
	       Hashtable<String,Boolean> dump = new Hashtable<String,Boolean>();
	       while(null != getDeclaration)
	       {            	
	       	Boolean bu = false;
	       	if(getDeclaration.getValor().equalsIgnoreCase("sim"))
	       		bu = true;
	       	
	       	if((animals.containsValue(key)) && (!dump.containsKey(getDeclaration.getPropriedade())))
	       	{
	       		dump.put(getDeclaration.getPropriedade(), bu);
	       		animals.put(key, dump);
	       	}
	       	 else
	            {
	                dump.put(getDeclaration.getPropriedade(), bu);
	                animals.put(key, dump);
	            }
	            getDeclaration = objCon.proxima();
	       }
	    
	    }
	    return animals;
	}
	@Override
	public void connect(IResponder responder) {
		Hashtable<String, Hashtable<String, Boolean>> animals = BuildBase();
		//Creates a Map with the animals in the Hash. This is actually pretty cool. 
		//He will organize the list of animals by questions with a true false value in the beginning of the Key.
		Map theMap = Map.Data(animals);
		String result = null;
		
		Hashtable<String,Boolean> parameters = new Hashtable<String,Boolean>();
		
		String maxKey = null;
        int maxSize = 0;
        //This iterator here should be useless as soon as i discover how to get the first element of my Sorted TokenMap.
        //The maxSize variable will give me the maximum number of questions to make, the TokenMap is a Sorted Dictionary 
        //in quantities of question.
        Iterator<Entry<String, Hashtable<String, Hashtable<String, Boolean>>>> it = theMap.TokenMap.entrySet().iterator();
        while(it.hasNext())
        {
            String currentKey = it.next().getKey();
            int currentSize = theMap.TokenMap.get(currentKey).size();
            if (currentSize > maxSize)
            {
                  maxKey = currentKey;
                  maxSize = currentSize;
            }
        }
		
		//At the beginning i thought that the i wasn't able to know the answer so i build another solution. 
		//This is a workaround to avoid the code waste.
		List<String> questions = theMap.GetTokenMapQuestions(theMap.TokenMap);
		for(int i = 0; i < questions.size(); i++){
       	 String as = responder.ask(questions.get(i)); 
       	 if(as.equalsIgnoreCase("nao sei"))
       		 continue;
       	 
       	 Boolean myQ = false;
       	 if(as.equalsIgnoreCase("sim"))
       		 myQ = true;
       	 parameters.put(questions.get(i),myQ);
       	 result = theMap.FilterBy(parameters).ReduceToResult();
    	 if (result != null && i > maxSize)
    		 break;
     }
		if (responder.finalAnswer(result))
            System.out.println("Done!");
		else
            System.out.println("Help me Obi Wan.");
		
				
	}	
	
    public ArrayList<String> GetOnlyNames(){
        File file = new File(DIRETORIO_RAIZ);
    	File[] lista = file.listFiles();
		ArrayList<String> filteredList = new ArrayList<String>();

        for (int n = 0; n < lista.length; n++)
            if (lista[n].toString().endsWith(".txt"))
                filteredList.add(lista[n].toString().substring((DIRETORIO_RAIZ).length(), lista[n].toString().length() - 4));

        return filteredList;
    	
    }
}
