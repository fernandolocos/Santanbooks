package pt.c01interfaces.s01chaveid.s01base.inter;

public interface IStatistics {
	
	public void addInfo(int numeroPergunta, String pergunta, String resposta);
		
	public String toString();
	
	public boolean repetiu();
	
	public int totalPerguntas();
}
