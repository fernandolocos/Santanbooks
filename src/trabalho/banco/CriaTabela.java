package trabalho.banco;

import java.sql.*;

public class CriaTabela 
{
	public CriaTabela(String usuario, String senha)
    {
		geraTabela(usuario, senha);
    }
	
	public static void geraTabela(String usuario, String senha)
    {  
		int criouBd = 0;
		
		try 
		{
			//Carrega driver do MySQL  
	        Class.forName("com.mysql.jdbc.Driver");  
	        //Abre conexao com o banco  
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost?user="+usuario+"&"+"password="+senha);  
	        // cria um objeto de comandos SQL para a base
	        Statement stmt = conn.createStatement();  
	        //se nao existe, cria o banco de dados
	        criouBd = stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS BASE");  
	        if(criouBd != 0)
	        {
	        	//cria tabela 
	        	stmt.executeUpdate("CREATE TABLE `BASE`.`GERAL`(`ORDEM` INTEGER " +
	        			"UNSIGNED NOT NULL AUTO_INCREMENT, " +
	        			"`ANIMAIS` VARCHAR(45) NOT NULL, " +
	        			"`PERGUNTA` VARCHAR(45) NOT NULL, " +
	        			"`RESPOSTA` VARCHAR(10) NOT NULL, " +
	        			"`CHECK` BOOLEAN NOT NULL DEFAULT 1, " +
	        			"PRIMARY KEY (`ORDEM`))"
	        	);	
	        }
	        else
	        {
	        	System.out.println("Ja existe o banco");
	        	//aqui sera setado true na coluna check
	        }
	        
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