package pt.c03ensaios.proj01;

import java.io.*;

import anima.annotation.*;
import anima.component.*;

/**
 * Provides tools to a text files editor.
 * 
 * @author Guilherme Colucci Pereira
 * 
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.proj01.ITextFilesManager>")
public interface ITextFilesManager extends ISupports
{
	/**
	 * Sets the arquivo attribute with a File value.
	 * 
	 * @param ma
	 *            Represents a file on the disk.
	 * @throws Exception
	 *             if the ma parameter does not represent a file.
	 */
	public void setArquivo(File ma);
	
	/**
	 * Sets the arquivo attribute with a String value.
	 * 
	 * @param ma
	 *            Represents the path of a file on the disk.
	 *             if the ma parameter does not represent the path for a file.
	 */
	public void setArquivo(String ma);
	
	/**
	 * Sets the pasta attribute with a File value.
	 * 
	 * @param mp
	 *            Represents the path of a folder on the disk.
	 */
	public void setPasta(File mp);

	/**
	 * Gets the value of the arquivo attribute.
	 * 
	 * @return Returns the value of the arquivo attribute.
	 */
	public File getArquivo();
	
	/**
	 * Gets the value of the pasta attribute.
	 * 
	 * @return Returns the value of the pasta attribute.
	 */
	public File getPasta();
	

	/**
	 * Gets the list of files and subdirectories of the folder represented by
	 * the pasta attribute.
	 * 
	 * @return Returns the list of files and subdirectories of the folder
	 *         reprsented by the pasta attribute.
	 */
	public String[] listaDeArquivos();
	
	/**
	 * Creates a new physical file.
	 * 
	 * @param nome
	 *            The name of the file which will be created.
	 * @return If the creation was well succeeded or not.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	public boolean criaArquivo(String nome) throws IOException;
	
	/**
	 * Inserts a text inside the file represented by the arquivo attribute.
	 * 
	 * @param conteudo
	 *            The content which is going to be inserted.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	public void insereConteudo(String conteudo) throws IOException;
	
	/**
	 * Deletes the file represented by the arquivo attribute.
	 * 
	 * @return If the deletion was well succeeded.
	 */
	public boolean apaga();
	
	/**
	 * Gets the content of the file.
	 * 
	 * @return The content within the file arquivo.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	public String pegaConteudo() throws IOException;
	
	/**
	 * Verifies if there already is a file in the folder with the same content
	 * as conteudo.
	 * 
	 * @param conteudo
	 *            The content that will be compared.
	 * @return If there is a file with the same content.
	 * @throws IOException
	 *             If there is an I/O error occurs.
	 */
	public boolean temIgual(String conteudo) throws IOException ;
	
	/**
	 * Verifies if there already is a file in the folder with the same name.
	 * 
	 * @param arquivo
	 *            the file name which will be compared.
	 * @return If there is a file with the same name.
	 */
	public boolean temComMesmoNome(String arquivo);
}
