package pt.c01interfaces.s01chaveid.naxxramas;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.Queue;

import pt.c01interfaces.s01chaveid.s01base.inter.*;
import pt.c01interfaces.s01chaveid.s01base.impl.*;

/**
 * Classe GrafoPerguntasAnimais
 * 
 * 	Gera uma matriz esparsa com os dados de uma base de conhecimento,
 * permitindo analisá-la do ponto de vista de grafo.
 * @see IBaseConhecimento
 * 
 * 	Tem a funcionalidade de busca em largura, que determina um animal que
 * está se tentando descobrir através de um objeto de IResponder.
 *  @see IResponder
 * 
 * @author Danilo Carvalho Grael - 101961 <dancgr@gmail.com>
 * @author Flavia Pisani - 104941 <flavia.pisani@gmail.com>
 *
 */
public class GrafoPerguntasAnimais {
	private IBaseConhecimento base;
	private LinkedList<Animal> animais;
	private LinkedList<Pergunta> perguntas;
	private enum TipoElemento { ANIMAL, PERGUNTA }
	
	/**
	 * Construtor da Classe
	 * 
	 * Gera toda a matriz esparsa usando uma IBaseConhecimento.
	 */
	public GrafoPerguntasAnimais() {
		this.base = new BaseConhecimento();
		this.animais = new LinkedList<Animal>();
		this.perguntas = new LinkedList<Pergunta>();	
		
		String nomes[] = this.base.listaNomes();
		int iAnimais = 0;
		
		for (String nome : nomes) {
			IObjetoConhecimento oc = this.base.recuperaObjeto(nome);
			IDeclaracao decl = oc.primeira();
			
			Animal animal = new Animal(nome, iAnimais++);
			this.animais.add(animal);
			int indiceAnimal = this.animais.size() - 1;

			while (decl != null) {
				String pergunta = decl.getPropriedade();
				String resposta = decl.getValor();
				
				Pergunta p = new Pergunta(pergunta);
				int indicePergunta = this.perguntas.indexOf(p);
				if (indicePergunta == -1) {
				    indicePergunta = this.perguntas.size();
				    p.setIndice(indicePergunta);
				    this.perguntas.add(p);
				}

				p = this.perguntas.get(indicePergunta);
				Animal a = this.animais.get(indiceAnimal);
				Elemento novo = new Elemento(TipoElemento.PERGUNTA, resposta, p, a);
				Iterator<Elemento> ie = p.getLista().iterator();
				int i;
				for (i = 0; ie.hasNext() && ie.next().getColuna().getIndice() < novo.getColuna().getIndice(); ++i);
				p.getLista().add(i, novo);
				ie = a.getLista().iterator();
                for (i = 0; ie.hasNext() && ie.next().getLinha().getIndice() < novo.getLinha().getIndice(); ++i);
                a.getLista().add(i, novo);
				decl = oc.proxima();
			}
		}
	}

	/**
	 * Método de Busca em Largura no grafo que através de um IResponder, advinha o animal desconhecido.
	 * 
	 * @param IResponder r
	 * @return String Nome do animal advinhado.
	 */
	public String bfsForAnimal(IResponder r) {
		// Fila para a pesquisa em largura.
	    Queue<Elemento> fila = new LinkedList<Elemento>();
	    
	    Iterator<Pergunta> ip = this.perguntas.iterator();
	    boolean encontrou = false;
	    String animalEncontrado = null;
	    
	    // Itera pelos elementos das perguntas até achar uma que se aplica ao animal procurado.
	    while (ip.hasNext() && fila.isEmpty()) {
	        Pergunta p = ip.next();
	        Iterator<Elemento> ie = p.getLista().iterator();
	        String resposta = p.getResposta();
	        boolean naoSei = resposta == null ?
	                true : resposta.equalsIgnoreCase("nao sei");

	        while (ie.hasNext()) {
	            Elemento e = ie.next();
	            if (e.getColuna().getPossibilidade()) {	                
	                if (resposta == null) {
    	                resposta = r.ask(p.getPergunta());
    	                p.setResposta(resposta);
    	                naoSei = resposta.equalsIgnoreCase("nao sei");
	                }
    	            if (naoSei || !e.getDado().equalsIgnoreCase(resposta)) {
    	                e.getColuna().setPossibilidade(false);
    	            }
    	            else {
    	                fila.offer(e);
    	            }
	            }
	        }
	    }

        Pergunta perguntaAnterior = fila.isEmpty() ? null : fila.peek().getLinha();

        // Enquanto a fila nao estiver vazia, continua a procura.
        while (!fila.isEmpty() && !encontrou) {
            Elemento e = fila.poll();
            Animal a = e.getColuna();
            // Se o elemento for uma pergunta, continua o processamento.
            if (e.getTipo() == TipoElemento.PERGUNTA) {
                int indice = a.getLista().indexOf(e) + 1;
                // Enquanto ainda houverem perguntas.
                if (indice < a.getLista().size()) {
                    Elemento prox = a.getLista().get(indice);
                    Pergunta p = prox.getLinha();
                    perguntaAnterior = p;
                    String resposta = p.getResposta();
                    // Se a pergunta ainda nao foi feita, faz a pergunta.
                    if (resposta == null) {
                        resposta = r.ask(p.getPergunta());
                        p.setResposta(resposta);
                    }
                    // Se a resposta da pergunta for a esperada, insere o proximo elemento na fila.
                    if (prox.getDado().equalsIgnoreCase(resposta)) {
                        fila.offer(prox);
                    }
                }
                else {
                	// Se acabaram todas as perguntas, oferece um animal como resposta.
                    fila.offer(new Elemento(TipoElemento.ANIMAL, null, null, a));
                }
            }
            /* Caso contrário, ele determina se o nó atual é a resposta final ou se o animal
               atual deve ser eliminado. */
            else {
                if (fila.isEmpty()) {
                	// Se a fila estiver vazia, o animal atual só pode ser a resposta final.
                    animalEncontrado = a.getAnimal();
                    encontrou = true;
                }
                else {
                    /* Se há uma pergunta que o animal procurado satisfaz no
                       mesmo nível que o elemento do possível animal, então este
                       animal não é o correto. */
                    Elemento aux = a.getLista().getLast();
                    if (!aux.getLinha().equals(perguntaAnterior)) {
                        a.setPossibilidade(false);
                    }
                }
            }
        }

        // Zera a estrutura de dados para uso em futuras buscas e retorna o animal encontrado.
        this.limparMatriz();
        return animalEncontrado; 
	}

	/**
	 * Método de limpar Matriz.
	 * 
	 * Desfaz todas as alterações que são consequência de uma busca na matriz, deixando-a preparada
	 * para mais uma busca.
	 */
	private void limparMatriz() {
	    Iterator<Pergunta> ip = this.perguntas.iterator();
	    while (ip.hasNext()) {
	        ip.next().setResposta(null);
	    }
	    
	    Iterator<Animal> ia = this.animais.iterator();
	    while (ia.hasNext()) {
	        ia.next().setPossibilidade(true);
	    }
	}

	@Override
	public String toString() {
	    Iterator<Pergunta> ip = this.perguntas.iterator();
	    String s = "PERGUNTAS:\n";
	    while (ip.hasNext()) {
	        Pergunta p = ip.next();
	        s += "(" + p.getIndice() + ") " + p.getPergunta() + "?\n";
	        Iterator<Elemento> ie = p.getLista().iterator();
	        while (ie.hasNext()) {
	            Elemento e = ie.next();
	            s += e.getColuna().getAnimal() + " " + e.getDado() + "\n";
	        }
	        s += "\n";
	    }

        Iterator<Animal> ia = this.animais.iterator();
        s += "---------------------------------------\nANIMAIS:\n";
        while (ia.hasNext()) {
            Animal a = ia.next();
            s += a.getAnimal() + ":\n";
            Iterator<Elemento> ie = a.getLista().iterator();
            while (ie.hasNext()) {
                Elemento e = ie.next();
                s += "(" + e.getLinha().getIndice() + ") " + e.getLinha().getPergunta() + "? " + e.getDado() + "\n";
            }
            s += "\n";
        }

	    return s;
	}

  	private class Elemento {
        private String dado;
        private Pergunta linha;
        private Animal coluna;
        private TipoElemento tipo;
        
        public Elemento(TipoElemento tipo, String dado, Pergunta linha, Animal coluna) {
            this.dado = dado;
            this.linha = linha;
            this.coluna = coluna;
            this.tipo = tipo;
        }
        
        public String getDado() {
            return this.dado;
        }
        
        public Pergunta getLinha() {
            return this.linha;
        }
        
        public TipoElemento getTipo() {
            return this.tipo;
        }
        
        public Animal getColuna() {
            return this.coluna;
        }
        
        public boolean equals(Object o) {
            Elemento e = (Elemento)o;
            return this.dado.equalsIgnoreCase(e.dado)
                   && this.linha.equals(e.linha)
                   & this.coluna.equals(e.coluna);
        }
        
        public String toString() {
            if (this.coluna != null && this.linha != null)
                return this.coluna.getAnimal() + ", " + this.getLinha().getPergunta() + "? " + this.getDado();
            else
                return this.dado;
        }
    }

	private class Pergunta {
        private String pergunta;
        private String resposta;
        private int indice;
        private LinkedList<Elemento> lista;
        
        public Pergunta(String pergunta) {
            this.lista = new LinkedList<Elemento>();
            this.pergunta = pergunta;
            this.resposta = null;
            this.indice = -1;
        }
        
        public void setIndice(int indice) {
            this.indice = indice;
        }
        
        public int getIndice() {
            return this.indice;
        }

        public LinkedList<Elemento> getLista() {
            return this.lista;
        }
        
        public String getPergunta() {
            return this.pergunta;
        }
        
        public void setResposta(String resposta) {
            this.resposta = resposta;
        }
        
        public String getResposta() {
            return this.resposta;
        }
       
        public boolean equals(Object o) {
            Pergunta p = (Pergunta)o;
            return this.pergunta.equalsIgnoreCase(p.pergunta)
                && ((this.resposta == null && p.resposta == null)
                || this.resposta.equalsIgnoreCase(p.resposta));
        }
    }

    private class Animal {
        private String animal;
        private int indice;
        private boolean possibilidade;
        private LinkedList<Elemento> lista;
        
        public Animal(String animal, int indice) {
            this.lista = new LinkedList<Elemento>();
            this.animal = animal;
            this.indice = indice;
            this.possibilidade = true;
        }
        
        public int getIndice() {
            return this.indice;
        }
        
        public LinkedList<Elemento> getLista() {
            return this.lista;
        }
        
        public String getAnimal() {
            return this.animal;
        }
        
        public boolean getPossibilidade() {
            return this.possibilidade;
        }
        
        public void setPossibilidade(boolean possibilidade) {
            this.possibilidade = possibilidade;
        }
        
        public boolean equals(Object o) {
            Animal a = (Animal)o;
            return this.animal.equalsIgnoreCase(a.animal);
        }
    }
}