package trabalho.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import trabalho.banco.CriaTabela;
import trabalho.banco.InsereTabela;
import trabalho.impl.BaseConhecimento;
import trabalho.inter.IBaseConhecimento;
import trabalho.inter.IEnquirer;
import trabalho.inter.IResponder;

public class EnquirerAdvanced implements IEnquirer 
{
	private String[] animais;
	private String usuario, senha;
	private Vector<String> propriedade;
	private Vector<String> listaAnimais;
	
	public EnquirerAdvanced()
	{
		// neste local se insere os dados da conexao com o banco
		usuario = "root";
		senha = "26071946";
		
		IBaseConhecimento bc = new BaseConhecimento();
		animais = bc.listaNomes();
		propriedade = new Vector<String>();
		listaAnimais = new Vector<String>();
		
		//insere elementos da lista de nomes em um Vector
		for(int i = 0; i < animais.length; i++)
			listaAnimais.add(animais[i]);
		
		// criando um banco de dados e uma tabela	
		new CriaTabela(usuario, senha);
		// insere os dados na tabela
		new InsereTabela(animais, usuario, senha);
	}
	
	/**
	 * proximaPergunta metodo que retorna qual a pergunta que aparece mais vezes 
	 * dentro de um vetor de perguntas dado como entrada
	 * @param Vector propriedades
	 * @return String pergunta
	 */
	public String proximaPergunta (Vector<String> propriedades)
	{
		/*String pergunta = new String();
		for(int i = 0; i < propriedades.size(); i++)
		{
			//if(propriedades.contains(decl.getPropriedade())
		}
		return pergunta;*/
		return propriedades.firstElement();
	}
	
	public void connect(IResponder responder) 
	{    
		try 
        {		
        	//Carrega driver do MySQL  
	        Class.forName("com.mysql.jdbc.Driver");  
	        //Abre conexao com o banco  
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/BASE?user="+usuario+"&"+"password="+senha);  
	        // cria um objeto de comandos SQL para a base
	        Statement stmt = conn.createStatement();
	        
	        // enquanto nao encontrar o animal procurado
	        while(listaAnimais.size() > 1)
	        {
	        	// limpa os dados dos vetores auxiliares para as novas consultas
	        	if(propriedade.size() > 0) propriedade.removeAllElements();
	        	if(listaAnimais.size() > 0)listaAnimais.removeAllElements();
	        	
		        /* faz uma consulta que retorna todas as perguntas que ainda nao foram feitas e existem nos animais
		         que ainda restam como possibilidade de ser o animal procurado*/
	            ResultSet listaPerguntas = stmt.executeQuery("SELECT PERGUNTA FROM BASE.GERAL G " +
	            		"WHERE G.`CHECKED` = 1 ORDER BY G.`PERGUNTA`");
	
	            // insere o retorno da consulta em um vetor de String
	            boolean temConteudo = listaPerguntas.next();
	            while (temConteudo)
	            {
	                propriedade.add(listaPerguntas.getString("PERGUNTA"));
	                temConteudo = listaPerguntas.next();
	            }
	            
	            System.out.println(propriedade.toString());
	            
	            // chama o metodo que retorna a proxima pergunta
	            String pergunta = proximaPergunta(propriedade);
	            // chama o responder
	            String resposta = responder.ask(pergunta);
	            
	            // faz uma consulta que retorna quais animais ainda podem ser o animal procurado
	            ResultSet listaAnimaisPossiveis = stmt.executeQuery("SELECT G.`ANIMAIS` FROM BASE.GERAL G " +
	            		"WHERE G.`PERGUNTA` = '"+pergunta+"' AND G.`RESPOSTA` = '"+resposta+"'");
	            
	            // insere o retorno da consulta em um vetor de String
	            temConteudo = listaAnimaisPossiveis.next();
	            while (temConteudo)
	            {
	            	listaAnimais.add(listaAnimaisPossiveis.getString("ANIMAIS"));
	                temConteudo = listaAnimaisPossiveis.next();
	            }
	            
	            // seta o valor FALSE na coluna CHECK, para os animais e perguntas ja eliminadas
	            stmt.executeUpdate("UPDATE GERAL SET CHECKED=0");
	            for(int i = 0; i < listaAnimais.size(); i++)
	            {
	            	stmt.executeUpdate("UPDATE GERAL SET CHECKED=1 WHERE ANIMAIS = '"+listaAnimais.get(i)+
	            			"' AND PERGUNTA != '"+pergunta+"'");
	            	listaAnimais.iterator();
	            }
	        }
	        
            System.out.println(listaAnimais.toString());
            responder.finalAnswer(listaAnimais.toString());
            
            // fecha stmt
            stmt.close();

            // fecha conexao
            conn.close();

        } catch (ClassNotFoundException erro) {
            System.out.println(erro.getMessage());
        } catch (SQLException erro) {
            System.out.println("Erro no SQL: " + erro.getMessage());
        }
	}
}
