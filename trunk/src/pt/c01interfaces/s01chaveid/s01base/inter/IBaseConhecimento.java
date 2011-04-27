package pt.c01interfaces.s01chaveid.s01base.inter;


public interface IBaseConhecimento
{
    public String[] listaNomes();
	public IObjetoConhecimento recuperaObjeto(String nome);
}