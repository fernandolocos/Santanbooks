package pt.c01interfaces.s01chaveid.lgazire.MapReduce;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class Reduce extends BaseMapReduce{
	
	public Map DataMap;

	public Map getDataMap(){
		return this.DataMap;	
	}
	protected void setDataMap(Map dataToSet){
		this.DataMap = dataToSet;		
	}	
	
	public String Result;
	
	public String getResult(){
		return this.Result;
	}
	
	protected void setResult(String resultToSet){
		this.Result = resultToSet;
	}

	public ArrayList<String> QuestionAsked;
	
	public ArrayList<String> getQuestion(){
		return this.QuestionAsked;
	}
	
	protected void setQuestion(ArrayList<String> questionToSet){
		this.QuestionAsked = questionToSet;
	}
	
	private Reduce(Map map){
		this.QuestionAsked = new ArrayList<String>();
		this.DataMap = map;
	}
	
	public static Reduce Using(Map map){
		return new Reduce(map);
	}
	
    public String ReduceToResult()
    {
        return this.ReduceTo(new ReduceToResultFunc(this));
    }

    public interface IFunc<TArg0, TReturn>
    {
        TReturn Execute(TArg0 a0);
    }
    
    class ReduceToResultFunc implements IFunc<Hashtable<String, Boolean>, Hashtable<String, Boolean>>
    {
        Reduce reduce;

        public ReduceToResultFunc(Reduce reduce)
        {
            this.reduce = reduce;
        }

        public Hashtable<String, Boolean> Execute(Hashtable<String,Boolean> question){
        	return reduce.DoReduce(question);        	
        }
    }
    
	
	private String ReduceTo(IFunc<Hashtable<String, Boolean>, Hashtable<String, Boolean>> myReducer)
	{
		Hashtable<String, Boolean> nextQuestion = this.DataMap.getQuestions();
		while (nextQuestion != null)
		{
			nextQuestion = myReducer.Execute(nextQuestion);
		}
		return this.Result;
	}

	
	private Hashtable<String,Boolean> DoReduce(Hashtable<String,Boolean> questions)
	{
		Hashtable<String,Boolean> nextQuestion = null;

		Iterator<Entry<String,Boolean>> it = questions.entrySet().iterator();
		/// Para cada questão informada...
		while(it.hasNext())
		{
			Entry<String,Boolean> t = it.next();
			/// Verifica se existe um dado correspondente
			String token = this.GetToken(t);

			/// Adiciona a pergunta à lista de perguntas respondidas
			this.UpdateAnswersList(token);

			/// Verifica a questão contra os clusters
			if (this.DataMap.getTokenMap().containsKey(token))
			{
				/// Caso exista mais de uma resposta possivel
				if (this.DataMap.getTokenMap().get(token).size() > 1)
				{
					this.DataMap = Map.Data(deepCopy(this.DataMap.getTokenMap().get(token)));
					nextQuestion = this.BuildNextQuestion(this.DataMap.TokenMap);
					//nextQuestion = this.BuildNextQuestion(this.DataMap.getTokenMap());
				}

				/// Caso apenas uma resposta seja encontrada
				else
				{
					this.Result = this.DataMap.getTokenMap().get(token).keys().nextElement(); //[token].First().Key;
					nextQuestion = null;
					break;
				}
			}
		}

		return nextQuestion;
	}
	
	private void UpdateAnswersList(String token)
	{
		
			String question = token.substring(1, (token.length() -1));
			Boolean answer = token.startsWith("1");
			this.QuestionAsked.add(question + " - " + answer);
		
	}
	
	private Hashtable<String, Boolean> BuildNextQuestion(Hashtable<String, Hashtable<String, Hashtable<String, Boolean>>> tokenMap)
	{
		Enumeration<String> newQuestion = tokenMap.keys();
		String q = newQuestion.nextElement();
		Boolean trueOrFalse = q.startsWith("1");
		Hashtable<String, Boolean> question = new Hashtable<String, Boolean>();
		question.put(q, trueOrFalse);
		
		return question;
	}
	
	public Hashtable<String,Hashtable<String,Boolean>> deepCopy(Hashtable<String,Hashtable<String,Boolean>> original){
		Hashtable<String,Hashtable<String,Boolean>> copy = new Hashtable<String,Hashtable<String,Boolean>>();
		for(Entry<String, Hashtable<String,Boolean>> entry : original.entrySet()){
			copy.put(entry.getKey(), entry.getValue());
		}
		return copy;
	}
	
}
