package pt.c03ensaios.naxxramas;

import java.io.Serializable;

/**
 * Stores several statistics linked to an animal.
 * 
 * @author Danilo Carvalho Grael<dancgr@gmail.com> - 101961
 * @author Flavia Pisani<flavia.pisani@gmail.com> - 104941
 */
public class AnimalStatistics implements Serializable {
    private static final long serialVersionUID = -4653024259865958703L;
    private String flow;
    private long time, kbMemory, freeMemory, totalMemory;
    private int processors, askedQuestionsCount, animalQuestionsCount;
    private boolean repetiuPergunta;

    public AnimalStatistics(long time, String flow, boolean repetiuPergunta,
        long memory, long totalMemory, long freeMemory, int processors,
        int askedQuestionsCount, int animalQuestionsCount) {
        this.flow = flow;
        this.repetiuPergunta = repetiuPergunta;
        this.time = time;
        this.kbMemory = memory / 1024;
        this.totalMemory = totalMemory / 1048576;
        this.freeMemory = freeMemory / 1048576;
        this.processors = processors;
        this.askedQuestionsCount = askedQuestionsCount;
        this.animalQuestionsCount = animalQuestionsCount;
    }
    
    public String getFlow() {
        return this.flow;
    }

    public boolean repetiuPergunta() {
        return this.repetiuPergunta;
    }

    public double getMicroTime() {
        double b = time/(1000);
        return b;
    }
    
    public int getProcessors() {
        return this.processors;
    }
    
    public long getKbMemory() {
        return this.kbMemory;
    }
    
    public long getTotalMemory() {
        return this.totalMemory;
    }
    
    public long getFreeMemory() {
        return this.freeMemory;
    }

    public int getAskedQuestionsCount() {
        return this.askedQuestionsCount;
    }
    
    public int getAnimalQuestionsCount() {
        return this.animalQuestionsCount;
    }
}
