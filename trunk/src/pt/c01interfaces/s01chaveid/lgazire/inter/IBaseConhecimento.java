package pt.c01interfaces.s01chaveid.lgazire.inter;

import java.util.ArrayList;


public interface IBaseConhecimento
{
    public String[] listaNomes();
    public IObjetoConhecimento recuperaObjeto(String nome);
}