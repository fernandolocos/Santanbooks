package pt.c01interfaces.s01chaveid.fejao.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CriaTabela 
{
	private String usuario; // usuario da conexao com o banco de dados
	private String senha;	// senha da conexao com o banco de dados
	
	/**
	 * construtor da classe
	 * @param usuario
	 * @param senha
	 */
	public CriaTabela(String usuario, String senha)
    {
		this.usuario = usuario;
		this.senha = senha;
    }
	
	/**
	 * geraTabela metodo que cria o banco de dados e a tabela
	 * @return boolean criou => verifica se a tabela foi criada
	 */
	public boolean geraTabela()
    {  
		boolean criou = true;
		try 
		{
			// Carrega driver do MySQL  
	        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");  
	        // Abre conexao com o banco  
	        Connection conn = DriverManager.getConnection("jdbc:derby:pt\\c01interfaces\\s01chaveid\\fejao\\banco\\BASE;create=true", "", "");  
	        // cria um objeto de comandos SQL para a base
	        Statement stmt = conn.createStatement();  
	        // cria o banco de dados
	        //stmt.executeUpdate("CREATE DATABASE BASE");  
	       
        	// cria tabela 
        	stmt.executeUpdate("CREATE TABLE BASE.GERAL(ORDEM INTEGER " +
        			"NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
        			"ANIMAIS VARCHAR(255) NOT NULL, " +
        			"PERGUNTA VARCHAR(255) NOT NULL, " +
        			"RESPOSTA VARCHAR(3) NOT NULL, " +
        			"CHECKED INTEGER NOT NULL DEFAULT 1, " +
        			"RESPONDER VARCHAR(7) DEFAULT 'NULL', " +
        			"PRIMARY KEY (ORDEM))"
        	);	
	        
	        // fecha o stmt
	        stmt.close();

            // fecha conexao
	        conn.close();
	        
		} catch (ClassNotFoundException erro) {
            criou = false;
        } catch (SQLException erro) {
        	erro.printStackTrace();
        	criou = false;
        }
        return criou;
    }  
	
	/**
	 * atualizaTabela, metodo que e acionado caso ja exista tabela criada.
	 * E o responsavel por 'limpar' dados contidos da ultima execucao.
	 */
	public void atualizaTabela()
	{
		try
		{
			//Carrega driver do MySQL  
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");  
	        // Abre conexao com o banco  
	        Connection conn = DriverManager.getConnection("jdbc:derby:pt\\c01interfaces\\s01chaveid\\fejao\\banco\\BASE", "", "");  
	        // cria um objeto de comandos SQL para a base
	        Statement stmt = conn.createStatement();
	        System.out.println("atualiza");
	        stmt.executeUpdate("UPDATE BASE.GERAL SET CHECKED=1");
	        stmt.executeUpdate("UPDATE BASE.GERAL SET RESPONDER=NULL");	
	        
	        // fecha o stmt
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