package pt.c03ensaios.gnrpack;

import java.util.Vector;

public class Results {
	int numIter = -1;
	long totalTime = -1;
	Vector<String> askedQuestion;
	
	public Results() {
		askedQuestion = new Vector<String>();
	}
	
	public void setNumIter(int numIter) {
		this.numIter = numIter;
	}
	
	public int getNumIter() {
		return numIter;
	}
	
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}
	
	public long getTotalTime() {
		return totalTime;
	}
	
	public void addAskedQuestion(String question) {
		askedQuestion.add(question);
	}
	
	public Vector<String> getAskedQuestion() {
		return askedQuestion;
	}
}
