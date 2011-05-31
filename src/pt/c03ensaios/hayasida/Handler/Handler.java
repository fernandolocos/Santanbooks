package pt.c03ensaios.hayasida.Handler;

import java.util.Vector;
import anima.annotation.Component;
import anima.component.base.ComponentBase;
import pt.c03ensaios.hayasida.FileTxtHandler.IFileTxtHandler;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

@Component(id = "<http://purl.org/dcc/pt.c03ensaios.hayasida.Handler.Handler>", provides = "<http://purl.org/dcc/pt.c03ensaios.hayasida.Handler.IHandler>")

public class Handler extends ComponentBase implements IHandler {
	
	private IFileTxtHandler fileHandler = null;
	private String dir;
	
	public Handler(){
		this.setDir("C:\\");
		if(!this.createIFileTxtHandler()) this.fileHandler = null;
	}
	
	public Handler(String dir){
		this.setDir(dir);
		if(!this.createIFileTxtHandler()) this.fileHandler = null;
	}
	
	/**
	 * Conectar a um componente FileTxtHandler.
	 * @return true se a operação foi bem sucedida, false, caso contrário.
	 */
	public boolean createIFileTxtHandler(){
		boolean retorno = false;
		try {
            IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();
            IFileTxtHandler fileHandler = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.hayasida.FileTxtHandler.FileTxtHandler>");
            this.connect(fileHandler);
            
            retorno = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
	}
	
	/**
	 * Configura o diretório onde o Handler irá manipular os arquivos.
	 * @param dir String com o caminho do diretório.
	 */
	public void setDir(String dir){
		this.dir = dir;
		if(this.fileHandler!= null) fileHandler.setDir(dir);
	}
	
	/**
	 * Retorna o diretório do objeto.
	 */
	public String getDir(){
		return this.dir;
	}
	
	/**
	 * Conecta um IFileTxtHandler ao objeto.
	 * @param Um objeto IFileTxtHandler.
	 */
	public void connect(IFileTxtHandler provider){
		if(provider != null){
			this.fileHandler = provider;
			this.fileHandler.setDir(this.dir);
		}
	}
		
	/**
	 * Retorna um Vector com as perguntas de um arquivo.
	 * @param file String com o nome do arquivo (sem extensão) de onde serão extraídas as perguntas.
	 * @return Um Vector<String> com as perguntas.
	 */
	public Vector<String> getPerguntas(String file){
		Vector<String> perguntas = new Vector<String>();		
		try{
			Vector<String> text = this.fileHandler.open(file);;
			boolean cont = true;
			for(int i = 0; i < text.size() && cont; i++){
				try{
					String pergunta = text.get(i).substring(0, text.get(i).indexOf(',') );
					if(pergunta.endsWith("\"") && pergunta.startsWith("\"")){
						pergunta = pergunta.replace('\"', ' ').trim();
						perguntas.add(pergunta);
					}
				} catch(Exception e){
					System.out.println("Erro: arquivo <" + file + ".txt> nao esta no formato certo.");
					cont = false;
				}
			}
		} catch(Exception e){
			System.out.println("Erro no arquivo <" + file + ".txt>: " + e.getMessage());
		}
		return perguntas;
	}
	
	/**
	 * Retorna um Vector com as respostas de um arquivo.
	 * @param file Arquivo de onde serão extraídas as respotas.
	 * @return Um Vector<String> com as respotas.
	 */
	public Vector<String> getRespostas(String file){
		Vector<String> respostas = new Vector<String>();
		try {
			Vector<String> text = this.fileHandler.open(file);
			boolean cont = true;
			for(int i = 0; i < text.size() && cont; i++){
				try{
					String resposta = text.get(i).substring( text.get(i).indexOf(',') );
					String respostaFilt = resposta.substring( resposta.indexOf('\"') );
					if(respostaFilt.endsWith("\"") && respostaFilt.startsWith("\"")){
						respostaFilt = respostaFilt.replace('\"', ' ').trim();
						respostas.add(respostaFilt);		
					}
				} catch(Exception e){
					System.out.println("Erro: arquivo <" + file + ".txt> nao esta no formato certo.");
					cont = false;
				}
			}
		} catch (Exception e) {
			System.out.println("Erro no arquivo <" + file + ".txt>: " + e.getMessage());
		}
		return respostas;
	}
	
	/**
	 * Salva em um arquivo .txt um conjunto de perguntas e respostas no formato "<pergunta>", "<resposta>"
	 * @param filename String com o nome do arquivo sem extensão.
	 * @param perguntas Um Vector<String> com uma pergunta por item.
	 * @param respostas Um Vector<String> com uma resposta (para a pergunta de mesmo índice) por item.
	 */
	public void save(String filename, Vector<String> perguntas, Vector<String> respostas){
		Vector<String> text = new Vector<String>();
		if(perguntas.size() == respostas.size() && perguntas.size() != 0){
			for(int i = 0; i < perguntas.size(); i++){
				text.add("\"" + perguntas.get(i) + "\", \"" + respostas.get(i) + "\"");
			}
		} else {
			System.out.println("Erro na gravacao do arquivo.");
		}
		
		this.fileHandler.save(text, filename);
	}
	
	/**
	 * Verifica se uma pergunta existe em algum arquivo.
	 * @param pergunta Uma String contendo a pergunta (sem aspas).
	 * @return true se a pergunta existe em pelo menos um arquivo; false, caso contrário.
	 */
	public boolean exists(String pergunta){
		boolean exsts = false;
		
		if((this.filterFilesByPergunta(pergunta).size()) > 0) exsts = true;
		
		return exsts;
	}
	
	/**
	 * Verifica se uma pergunta com uma resposta específica existe em algum arquivo.
	 * @param pergunta Uma String contendo a pergunta (sem aspas).
	 * @param resposta Uma String contendo a respota (sem aspas).
	 * @return true se o par existe em pelo menos um arquivo; false, caso contrário.
	 */
	public boolean exists(String pergunta, String resposta){
		boolean exsts = false;
		
		if((this.filterFilesByPergunta(pergunta, resposta).size()) > 0) exsts = true;
		
		return exsts;
	}
	
	/**
	 * Retorna um Vector com os nomes de todos os arquivos que contém a pergunta.
	 * @param pergunta Uma String contendo a pergunta (sem aspas).
	 * @return Um Vector<String> com os nomes dos arquivos sem extensão.
	 */
	public Vector<String> filterFilesByPergunta(String pergunta){
		Vector<String> files = new Vector<String>();
		
		Vector<String> filesNames = this.fileHandler.listTxtFilesNames();
		
		for(int i = 0; i < filesNames.size(); i++){
			
			Vector<String> prg = this.getPerguntas(filesNames.get(i));
						
			if(prg.contains(pergunta)){
				files.add(filesNames.get(i));
			}
			
		}		
		
		return files;
	}

	/**
	 * Retorna um Vector com os nomes de todos os arquivos que contém a pergunta com a resposta específica.
	 * @param pergunta Uma String contendo a pergunta (sem aspas).
	 * @param resposta Uma String contendo a respota (sem aspas).
	 * @return Um Vector<String> com os nomes dos arquivos sem extensão.
	 */
	public Vector<String> filterFilesByPergunta(String pergunta, String resposta){
		Vector<String> files = new Vector<String>();
		
		Vector<String> filesNames = this.fileHandler.listTxtFilesNames();
		
		for(int i = 0; i < filesNames.size(); i++){
			
			Vector<String> prg = this.getPerguntas(filesNames.get(i)), rsp = this.getRespostas(filesNames.get(i));
						
			if(prg.contains(pergunta)){
				if(rsp.get(prg.indexOf(pergunta)).equalsIgnoreCase(resposta)){
					files.add(filesNames.get(i));					
				}
			}
			
		}		
		
		return files;
	}
	
	/**
	 * Verifica se há algum arquivo com um conjunto de pares de perguntas e respostas.
	 * @param perguntas Um Vector<String> com uma pergunta por item.
	 * @param respostas Um Vector<String> com uma resposta (para a pergunta de mesmo índice) por item.
	 * @return true se o conjunto existe em pelo menos um arquivo; false, caso contrário.
	 */
	public boolean exists(Vector<String> perguntas, Vector<String> respostas){
		boolean exists = false;
		Vector<String> filesNames = this.fileHandler.listTxtFilesNames();
		
		for(int i = 0; i < filesNames.size(); i++){
			
			Vector<String> prg = this.getPerguntas(filesNames.get(i)), rsp = this.getRespostas(filesNames.get(i));
						
			if(prg.containsAll(perguntas) && perguntas.size() == respostas.size()){
				boolean hasSameAnswers = true;
				for(int j = 0; j < perguntas.size() && hasSameAnswers == true; j++){
					if(!respostas.get(perguntas.indexOf(prg.get(j))).equalsIgnoreCase(rsp.get(j))) hasSameAnswers = false;
				}
				if(hasSameAnswers) exists = true;
			}
			
		}		
		return exists;
	}
}
