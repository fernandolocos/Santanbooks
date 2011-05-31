package pt.c03ensaios.naxxramas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import pt.c01interfaces.s01chaveid.s01base.inter.IStatistics;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Pergunta;

/**
 * Creates statistics about an Enquirer.
 * 
 * @author Danilo Carvalho Grael<dancgr@gmail.com> - 101961
 * @author Flavia Pisani<flavia.pisani@gmail.com> - 104941
 */
public class Razuvious implements IStatistics, IRazuvious, Serializable {
    private static final long serialVersionUID = 3099098903233922530L;
    private ArrayList<Pergunta> perguntas;

    public Razuvious() {
        this.perguntas = new ArrayList<Pergunta>();
    }

    @Override
    public void addInfo(int numeroPergunta, String pergunta, String resposta) {
        Pergunta p = null;
        boolean naoExiste = true;

        Iterator<Pergunta> it = this.perguntas.iterator();
        while (it.hasNext()) {
            p = it.next();
            if (p.getPergunta().equals(pergunta)) {
                naoExiste = false;
                p.incQuantidadeVezes();
                break;
            }
        }

        if (naoExiste) {
            p = new Pergunta(pergunta, resposta);
        }

        perguntas.add(numeroPergunta, p);
    }

    @Override
    public String toString() {
        StringBuffer resposta = new StringBuffer();

        Iterator<Pergunta> it = this.perguntas.iterator();
        while (it.hasNext()) {
            resposta.append(it.next().toString());
            resposta.append("\n");
        }

        resposta.append("Total de perguntas: ");
        resposta.append(perguntas.size());

        return resposta.toString();
    }

    @Override
    public boolean repetiu() {
        boolean repetiu = false;

        Iterator<Pergunta> it = perguntas.iterator();
        while (it.hasNext()) {
            if (it.next().repetiu()) {
                repetiu = true;
                break;
            }
        }

        return repetiu;
    }

    @Override
    public int totalPerguntas() {
        return perguntas.size();
    }

    @Override
    public int perguntasAnimal(Collection<Pergunta> perguntas) {
        Iterator<Pergunta> it = perguntas.iterator();
        int perguntasAnimal = 0;

        while (it.hasNext()) {
            String p = it.next().getPergunta();
            Iterator<Pergunta> it2 = this.perguntas.iterator();

            while (it2.hasNext()) {
                if (it2.next().getPergunta().equals(p)) {
                    ++perguntasAnimal;
                    break;
                }
            }
        }
        
        return perguntasAnimal;
    }
}
