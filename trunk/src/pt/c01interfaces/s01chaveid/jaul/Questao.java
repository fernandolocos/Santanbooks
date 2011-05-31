package pt.c01interfaces.s01chaveid.jaul;

public class Questao implements Comparable<Questao> {
	
	private Integer count;
	private String quest;
	
	public Questao(String quest, int count) {
		super();
		this.count = count;
		this.quest = quest;
	}
	
	public void increment() {
		count++;
	}
	
	public String getTexto() {
		return quest;
	}
	
	public Integer getCount() {
		return count;
	}

	@Override
	public int compareTo(Questao arg0) {
		return getCount().compareTo(arg0.getCount());
	}
	
	public String toString() {
		return quest + " : " + count + "vezes;";
	}

}
