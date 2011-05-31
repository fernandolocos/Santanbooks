package pt.c03ensaios.pizza;

import anima.annotation.Component;

@Component(id="<http://purl.org/dcc/pt.c03ensaios.pizza.IQuestion>",
        provides={"<http://purl.org/dcc/pt.c03ensaios.pizza.Question>"})
public class Question implements IQuestion {
	public String text;
	public volatile int yeps, nops;
	
	public Question() {
		this.yeps = 0;
		this.nops = 0;
	}
	
	public Question(String text) {
		this.text = text;
		this.yeps = 0;
		this.nops = 0;
	}
	
	public String getQuestionText() {
		return this.text;
	}
	
	public synchronized void incrementYeps() {
		this.yeps++;
	}
	
	public synchronized void incrementNopes() {
		this.nops++;
	}
	
	public int getYeps() {
		return this.yeps;
	}
	
	public int getNopes() {
		return this.nops;
	}
	
	public void setYeps(int value) {
		this.yeps = value;
	}
	
	public void setNopes(int value) {
		this.nops = value;
	}
}
