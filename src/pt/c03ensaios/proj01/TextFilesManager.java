package pt.c03ensaios.proj01;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

import pt.c03ensaios.tochinhas2.impl.*;
import pt.c03ensaios.debolacha.inter.*;
import anima.annotation.Component;
import anima.component.IRequires;
import anima.component.base.ComponentBase;

/**
 * Simulates a text files editor.
 * 
 * @author Guilherme Colucci Pereira
 * 
 */
@Component(id = "<http://purl.org/dcc/pt.c03ensaios.proj01.TextFilesManager>",
		   provides = { "<http://purl.org/dcc/pt.c03ensaios.proj01.ITextFilesManager>" },
		   requires = { "<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.IQuestions>"})
		   
public class TextFilesManager extends ComponentBase implements
		ITextFilesManager, IRequires<IQuestions>, IAnimalListReceptacle {

	public static final String SEPARADOR = "\n\n-----\n";
	public static final String MARCADORDEFIM = "SAVE";
	public static final String MENU = "Acoes possiveis:\n\n"
			+ "1 - Adicionar um novo arquivo\n" + "2 - Editar um arquivo\n"
			+ "3 - Deletar um arquivo\n" + "4 - Sair\n\n";
	public static final String PEDIDODAOPCAO = "Escolha o numero da acao correspondente: ";
	public static final String SUCESSODECRIACAO = "Seu arquivo foi criado com sucesso!";
	public static final String SUCESSODEDELECAO = "Seu arquivo foi deletado com sucesso!";
	public static final String SUCESSODEDICAO = "Seu arquivo foi editado com sucesso!";
	public static final int ADICIONAR = 1;
	public static final int EDITAR = 2;
	public static final int DELETAR = 3;
	public static final int SAIR = 4;
	
	private File arquivo;
	private File pasta;
	private IQuestions manipuladorDeQuestoes;
	private IAnimalList manipuladorDeLista;
	
	private class Propriedade
	{
		private String pergunta;
		private String resposta;
		
		private Propriedade(String p, String r)
		{
			this.pergunta = p;
			this.resposta = r;
		}
		
		private Propriedade()
		{
			this (null, null);
		}
		
		private String getPergunta()
		{
			return pergunta;
		}
		
		private String getResposta()
		{
			return resposta;
		}
	}

	/**
	 * Default class constructor. It sets the pasta attribute with a File
	 * instance, which represents a physical folder on the disk. It also sets
	 * the arquivo attribute with a null value.
	 * 
	 * @param caminho
	 *            Represents the virtual folder path.
	 */

	public TextFilesManager(String caminho) {
		this.pasta = new File(caminho);
		this.arquivo = null;
	}

	/**
	 * Default class constructor. It sets the pasta attribute with a File
	 * instance, which represents a physical folder on the disk. It also sets
	 * the arquivo attribute with a null value.
	 * 
	 * @param caminho
	 *            Represents the virtual folder path.
	 */

	public TextFilesManager() {
		this("pt/c01interfaces/s01chaveid/s01base/bd/");
	}

	public void setArquivo(File ma){
		this.arquivo = ma;
	}

	public void setArquivo(String ma){
		this.setArquivo(new File(this.getPasta() + "/" + ma));
	}

	public void setPasta(File mp) {
		this.pasta = mp;
	}

	public File getArquivo() {
		return this.arquivo;
	}

	public File getPasta() {
		return this.pasta;
	}

	public String[] listaDeArquivos() {
		System.out.println(this.pasta.getAbsolutePath());
		return this.pasta.list();
	}

	public boolean criaArquivo(String nome) throws IOException {
		if (!this.pasta.exists())
			return false;

		File novoArquivo = new File(this.pasta + "/" + nome);

		novoArquivo.createNewFile();

		this.arquivo = novoArquivo;

		return true;
	}

	public void insereConteudo(String conteudo) throws IOException {
		FileWriter escritor = null; 
		escritor = new FileWriter(this.arquivo); 
		escritor.write(conteudo); 
		escritor.close();
	}

	public boolean apaga() {
		return this.arquivo.delete();
	}

	public String pegaConteudo() throws IOException {
		BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
		String content = "", aux = "";

		content = leitor.readLine();
		while ((aux = leitor.readLine()) != null)
			content += "\n" + aux;

		return content;
	}
	
	/**
	 * Connects both components.
	 * 
	 * @param questoes
	 *            The component that will be connected
	 */
	public void connect(IQuestions questoes)
	{
		this.manipuladorDeQuestoes = questoes;
	}
	
	/**
	 * Connects both components.
	 * 
	 * @param myList
	 *            The component that will be connected
	 */
	public void connect(IAnimalList myList)
	{
		this.manipuladorDeLista = myList;
	}
	
	private Propriedade[] formataPropriedades(String conteudo)
	{
		conteudo = conteudo.replace("\"", "");
		StringTokenizer quebraLinhas = new StringTokenizer(conteudo, "\n");
		String linha = "";
		Propriedade[] retorno = new Propriedade[quebraLinhas.countTokens()];

		for (int i = 0; quebraLinhas.hasMoreTokens(); i++)
		{
			linha = quebraLinhas.nextToken();
			StringTokenizer quebraPerguntas = new StringTokenizer(linha, ",");
			String pergunta = quebraPerguntas.nextToken(), resposta = quebraPerguntas.nextToken();
			retorno[i] = new Propriedade(pergunta, resposta);
		}
		
		return retorno;
	}
	
	public boolean temIgual(String conteudo) throws IOException {
		Propriedade[] novoAnimal = this.formataPropriedades(conteudo);
		
		// Pega todos os animais com que contém a pergunta e resposta iguais à
		// primeira propriedade do animal
		this.manipuladorDeLista.setList(vectorToArray(
				this.manipuladorDeQuestoes.baseFiltration(
						novoAnimal[0].getPergunta(), 
						novoAnimal[0].getResposta())));
				
		for(int i = 1; i < novoAnimal.length; i++)
			this.manipuladorDeLista = manipuladorDeLista.intersec(
					this.manipuladorDeQuestoes.baseFiltration(
							novoAnimal[i].getPergunta(), 
							novoAnimal[i].getResposta()));
						
		return this.manipuladorDeLista.size() > 0;
	}
	
	private String[] vectorToArray(Vector<String> v)
	{
		String[] retorno = new String[v.size()];
		
		for (int i = 0; i < v.size(); i++)
			retorno[i] = v.get(i);

		return retorno;
	}
	
	public boolean temComMesmoNome(String arquivo) {
		return new File(this.pasta + "/" + arquivo).exists();
	}
}
