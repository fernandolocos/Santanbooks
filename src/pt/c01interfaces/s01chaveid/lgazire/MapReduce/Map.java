package pt.c01interfaces.s01chaveid.lgazire.MapReduce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class Map extends BaseMapReduce{
	public Hashtable<String, Hashtable<String,Boolean>> DataMap;
	public Hashtable<String,Hashtable<String,Hashtable<String,Boolean>>> TokenMap;
	public Hashtable<String,Boolean> Questions;
    public Map InnerMap; //Na verdade é quase irmão =P
    public Map ParentMap;
	

	public Map getInnerMap(){
		return this.InnerMap;	
	}
	
	public void setInnerMap(Map innerMapToSet){
		this.InnerMap = innerMapToSet;
	}
	
	public Map getParentMap(){
		return this.ParentMap;	
	}
	
	public void setParentMap(Map setParentMap){
		this.ParentMap = setParentMap;
	}
	
	public Hashtable<String, Hashtable<String,Boolean>> getDataMap(){
		return this.DataMap;	
	}
	
	public void setDataMap(Hashtable<String, Hashtable<String,Boolean>> DataMapToSet){
		this.DataMap = DataMapToSet;
	}
	
	public Hashtable<String,Hashtable<String,Hashtable<String,Boolean>>> getTokenMap(){
		return this.TokenMap;	
	}
	public void setTokenMap(Hashtable<String,Hashtable<String,Hashtable<String,Boolean>>> TokenMapToSet){
		this.TokenMap = TokenMapToSet;
	}
	
	public Hashtable<String, Boolean> getQuestions(){
		return this.Questions;	
	}
	
	public void setQuestion(Hashtable<String, Boolean> QuestionToSet){
		this.Questions = QuestionToSet;
	}
	
    private Map(Hashtable<String,Hashtable<String,Boolean>> data)  
    {
    	 this(data,null);
    }

	private Map(Hashtable<String, Hashtable<String, Boolean>> data, Object parentMap)
    {
        this.TokenMap = new Hashtable<String, Hashtable<String, Hashtable<String, Boolean>>>();
        this.ParentMap = (Map)parentMap;
        if (this.ParentMap == null)
        {
            this.InnerMap = new Map(data, this);
        }        	
        this.DataMap = data;
        this.DoMap();
    }
	
	public static Map Data(Hashtable<String, Hashtable<String,Boolean>> data){
		return new Map(data);
	}
	
	public Reduce FilterBy(Hashtable<String, Boolean> parameters)
	{
		this.Questions = parameters;
		return Reduce.Using(this);
	}
	
	class TokenMapComparator implements Comparator<Entry<String,Hashtable<String,Hashtable<String,Boolean>>>>
	{

		@Override
		public int compare(
				Entry<String, Hashtable<String, Hashtable<String, Boolean>>> arg0,
				Entry<String, Hashtable<String, Hashtable<String, Boolean>>> arg1) {
				int lCount = arg0.getValue().size();
				int rCount = arg1.getValue().size();
				if(lCount > rCount)
					return -1;
				else if(lCount < rCount)
					return 1;				
			return 0;
		}		
		
	}
	
	private void DoMap()
	{
		Iterator<Entry<String, Hashtable<String, Boolean>>> it = this.DataMap.entrySet().iterator();
		/// itera pelos nós de informação
		while(it.hasNext())
		{
			Entry<String, Hashtable<String, Boolean>> e = it.next();
			Iterator<Entry<String, Boolean>> it2 = e.getValue().entrySet().iterator();
			while(it2.hasNext()){
				Entry<String, Boolean> caracteristica = it2.next();
				Hashtable<String,Boolean> shallowCopy = GetShallowCopy(e.getValue(),caracteristica.getKey());
				String token = super.GetToken(caracteristica);
				this.MergeToDictionary(token, e.getKey(), shallowCopy);
			}
		}
		
		List<Entry<String, Hashtable<String, Hashtable<String, Boolean>>>> TokenMapList = new ArrayList<Entry<String, Hashtable<String, Hashtable<String, Boolean>>>>();
		Iterator<Entry<String, Hashtable<String, Hashtable<String, Boolean>>>> TokenMapIterator = TokenMap.entrySet().iterator();
		while(TokenMapIterator.hasNext())
		{
			TokenMapList.add(TokenMapIterator.next());
		}
		
		Collections.sort(TokenMapList, new TokenMapComparator());		

		/// Retorna os clusters indexados por token
		Hashtable<String, Hashtable<String, Hashtable<String, Boolean>>> temp = new Hashtable<String, Hashtable<String, Hashtable<String, Boolean>>>();
		for(int i = 0, j = TokenMapList.size(); i < j; i++){
			temp.put(TokenMapList.get(i).getKey(), TokenMapList.get(i).getValue());			
		}
		this.TokenMap = temp;
	}

	private void MergeToDictionary(String token, String animalId, Hashtable<String,Boolean> shallowCopy)
	{

		if (!this.TokenMap.containsKey(token))
		{
			this.TokenMap.put(token, new Hashtable<String,Hashtable<String,Boolean>>());
		}
		if (!this.TokenMap.get(token).containsKey(animalId))
		{
			this.TokenMap.get(token).put(animalId, shallowCopy);
		}
		else
        {
            if (!this.TokenMap.get(token).containsKey(animalId))
            {
                this.TokenMap.get(token).put(animalId, shallowCopy);
            }
        }
	}
	
	private Hashtable<String,Boolean> GetShallowCopy(Hashtable<String, Boolean> dictionaryToCopy, String itemToRemove){
		Hashtable<String,Boolean> copy = deepCopy(dictionaryToCopy);
		copy.remove(itemToRemove);
		return copy;
	}
	
	public Hashtable<String,Boolean> deepCopy(Hashtable<String,Boolean> original){
		Hashtable<String,Boolean> copy = new Hashtable<String,Boolean>();
		for(Entry<String, Boolean> entry : original.entrySet()){
			copy.put(entry.getKey(), entry.getValue());
		}
		return copy;
	}
	
	public List<String> GetTokenMapQuestions(Hashtable<String,Hashtable<String,Hashtable<String,Boolean>>> TokenToAsk){
		List<String> tokenResult = new ArrayList<String>();
		
		for(Entry<String,Hashtable<String,Hashtable<String,Boolean>>> item : TokenToAsk.entrySet()){
			String evaluate = item.getKey().substring(1, item.getKey().length());
			if(!tokenResult.contains(evaluate))
				tokenResult.add(evaluate);
		}
		
		return tokenResult;
		
	}
	
}
