/** 
 * Interface for a Handler Component that has basic operations to manage .txt files with questions and answers in the format "<question>", "<answer>". 
 * @author Roberto K. Hayasida Junior
 */

package pt.c03ensaios.hayasida.Handler;

import java.util.Vector;
import pt.c03ensaios.hayasida.FileTxtHandler.IFileTxtHandlerReceptacle;
import anima.annotation.ComponentInterface;
import anima.component.ISupports;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.hayasida.Handler.IHandler>")

public interface IHandler extends IFileTxtHandlerReceptacle, ISupports {
	void setDir(String dir);
	String getDir();
	Vector<String> getPerguntas(String file);
	Vector<String> getRespostas(String file);
	void save(String filename, Vector<String> perguntas, Vector<String> respostas);
	boolean exists(String pergunta);
	boolean exists(String pergunta, String resposta);
	boolean exists(Vector<String> perguntas, Vector<String> respostas);
	Vector<String> filterFilesByPergunta(String pergunta);
	Vector<String> filterFilesByPergunta(String pergunta, String resposta);
}
