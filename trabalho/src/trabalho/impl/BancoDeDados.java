package trabalho.impl;

import java.sql.*;

public class BancoDeDados 
{ 
    public static void main(String[] args) throws ClassNotFoundException, SQLException 
    {  
        //Carrega driver do MySQL  
        Class.forName("com.mysql.jdbc.Driver");  
        //Abre conex�o com o banco, n�o estou conectando a nenhuma base em espec�fico  
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost?user=usuario&password=senha");  
        //Crio um Statement  
        Statement stmt = conn.createStatement();  
        System.out.println("Come�o");  
        //Toda opera��o que n�o for consulta, use executeUpdate, s� assim  
        //voc� poder� alterar algo no banco de dados  
        int i = stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS TESTECREATE");  
        System.out.println("Resultado: "+i);// Se criar uma nova base de dados, o retorno ser� 1  
        System.out.println("Final");  
    }  
}
