package pt.c03ensaios.hayasida.FileTxtHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Vector;
import anima.annotation.Component;
import anima.component.base.ComponentBase;

@Component(id="<http://purl.org/dcc/pt.c03ensaios.hayasida.FileTxtHandler.FileTxtHandler>", provides="<http://purl.org/dcc/pt.c03ensaios.hayasida.FileTxtHandler.IFileTxtHandler>")

public class FileTxtHandler extends ComponentBase implements IFileTxtHandler{
	
	private String dir;
	
	public FileTxtHandler(){
		this.setDir("C:\\");
	}
	
	public FileTxtHandler(String dir){
		this.setDir(dir);
	}

	/**
	 * Permite configurar o diretório em que o objeto manipulará os arquivos.
	 * @param dir String com o caminho completo para o diretório dos arquivos.
	 */
	public void setDir(String dir) {
		this.dir = dir;		
	}
	
	/**
	 * Retorna o diretório atual do objeto.
	 */
	public String getDir(){
		return this.dir;
	}
	
	/**
	 * Abre um arquivo e retorna o texto em um Vector de Strings.
	 * @param filename String com o nome do arquivo sem extensão.
	 * @return Um Vector<String> com uma String para cada linha de texto do arquivo.
	 */
	public Vector<String> open(String filename){
		Vector<String> text = new Vector<String>();
		Vector<String> files = this.listTxtFilesNames();
		if(files.contains(filename)){
			int ch;
		    File file = new File(this.dir + filename + ".txt");
		    StringBuffer strContent = new StringBuffer("");
		    FileInputStream fis = null;
		    
		    try {
		    	fis = new FileInputStream(file);
		    	while ((ch = fis.read()) != -1){
	    			if(ch == '\n'){
	    				text.add(strContent.toString().trim());
	    				strContent.delete(0, strContent.length());
	    			} else {
	    				strContent.append((char) ch);
	    			}
		    	}
				if(strContent.length() > 0) text.add(strContent.toString().trim());
		    	fis.close();
		    } catch (Exception e) {
		    	System.err.println("Error: " + e.getMessage());;
		    }
		}
	    return text;
	}
	
	/**
	 * Salva em um arquivo um o texto contido em um Vector de Strings.
	 * @param filename String com o nome do arquivo sem extensão.
	 * @param text Um Vector<String> contendo o texto, uma String por linha.
	 */
	public void save(Vector<String> text, String filename){
		File file = new File(this.dir);
		boolean ok = false;
		
		if(!file.exists()){
			if(file.mkdir()){
				ok = true;
			}
		} else {
			ok = true;
		}
		
		if(ok){
			try{
				FileWriter fstream = new FileWriter(this.dir + filename + ".txt");
		        BufferedWriter out = new BufferedWriter(fstream);
		        int size = text.size();
		        for(int i = 0; i < size; i++){
			        out.write(text.get(i));
			        if(i != (size - 1)){ out.newLine(); }
		        }
		        out.close();
		    } catch (Exception e){
		    	System.err.println("Error: " + e.getMessage());
		    }				
		}
	}

	/**
	 * Retorna todos os arquivos .txt do diretório.
	 * @return Um Vector<Files> com os arquivos.
	 */
	public Vector<File> listTxtFiles(){
		File dir = new File(this.dir);
		File files[] = dir.listFiles();
		Vector<File> filesFiltered = new Vector<File>();
		
		for(int i = 0; i < files.length; i++){
			if(files[i].isFile()){
				if(files[i].getName().endsWith(".txt")){
					filesFiltered.add(files[i]);
				}				
			}			
		}
		
		return filesFiltered;
	}
	
	/**
	 * Retorna os nomes de todos os arquivos .txt do diretório.
	 * @return Um Vector<Strings> com os nomes sem extensão.
	 */
	public Vector<String> listTxtFilesNames(){
		Vector<File> files = this.listTxtFiles();
		Vector<String> names  = new Vector<String>();
		
		for(int i = 0; i < files.size(); i++){
			names.add(files.get(i).getName().substring(0, files.get(i).getName().length() - 4));
		}
		
		return names;
	}
	

}
