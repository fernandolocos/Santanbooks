package pt.c03ensaios.naxxramas;

import java.util.Collection;

import pt.c01interfaces.s01chaveid.s01base.inter.IStatistics;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Pergunta;

/**
 * Provides a method to count how many questions an animal has.
 * 
 * @author Danilo Carvalho Grael<dancgr@gmail.com> - 101961
 * @author Flavia Pisani<flavia.pisani@gmail.com> - 104941
 */
public interface IRazuvious extends IStatistics {
    public int perguntasAnimal(Collection<Pergunta> perguntas);
}
