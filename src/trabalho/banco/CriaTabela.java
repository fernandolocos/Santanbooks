package trabalho.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CriaTabela 
{
	private String usuario;
	private String senha;
	
	public CriaTabela(String usuario, String senha)
    {
		this.usuario = usuario;
		this.senha = senha;
    }
	
	public boolean geraTabela()
    {  
		boolean criou = true;
		try 
		{
			//Carrega driver do MySQL  
	        Class.forName("com.mysql.jdbc.Driver");  
	        //Abre conexao com o banco  
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost?user="+usuario+"&"+"password="+senha);  
	        // cria um objeto de comandos SQL para a base
	        Statement stmt = conn.createStatement();  
	        //se nao existe, cria o banco de dados
	        stmt.executeUpdate("CREATE DATABASE BASE");  
	       
        	//cria tabela 
        	stmt.executeUpdate("CREATE TABLE `BASE`.`GERAL`(`ORDEM` INTEGER " +
        			"UNSIGNED NOT NULL AUTO_INCREMENT, " +
        			"`ANIMAIS` VARCHAR(45) NOT NULL, " +
        			"`PERGUNTA` VARCHAR(45) NOT NULL, " +
        			"`RESPOSTA` VARCHAR(10) NOT NULL, " +
        			"`CHECKED` BOOLEAN NOT NULL DEFAULT 1, " +
        			"PRIMARY KEY (`ORDEM`))"
        	);	
	        
	        // fecha o stmt
	        stmt.close();

            // fecha conexao
	        conn.close();
	        
		} catch (ClassNotFoundException erro) {
            criou = false;
        } catch (SQLException erro) {
        	criou = false;
        }
        return criou;
    }  
	
	public void atualizaTabela()
	{
		try
		{
			//Carrega driver do MySQL  
	        Class.forName("com.mysql.jdbc.Driver");  
	        //Abre conexao com o banco  
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost?user="+usuario+"&"+"password="+senha);  
	        // cria um objeto de comandos SQL para a base
	        Statement stmt = conn.createStatement();
	        
	        stmt.executeUpdate("UPDATE BASE.GERAL SET CHECKED=1");	
	        
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