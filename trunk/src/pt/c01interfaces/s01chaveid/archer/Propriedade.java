package pt.c01interfaces.s01chaveid.archer;

public class Propriedade 
{
	private String string;
	private int ocorrencia = 1;
	
	public Propriedade(String string) {
		this.string = string;
	}
	
	public String getString() {
		
		return string;
	}
	
	public int getOcorrencia() {
		
		return ocorrencia;
	}
	
	public void iOcorrencia() {
		
		ocorrencia++;
	}
	
	public void setOcorrencia(int numero) {
		ocorrencia = numero;
	}
}
