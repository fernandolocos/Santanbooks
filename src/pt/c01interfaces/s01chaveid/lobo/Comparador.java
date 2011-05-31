package pt.c01interfaces.s01chaveid.lobo;

import java.util.Comparator;

/**
 * 
 * @author Rodrigo Lobo da Costa - RA 104012
 * @author Fabio Beranizo Fontes Lopes - RA 104864
 *
 */
public class Comparador implements Comparator<Question>
{
	//comparador para ser usado no sort da lista
	public int compare(Question o1, Question o2)
	{
		return o2.preferencia() - o1.preferencia();
	}
}
