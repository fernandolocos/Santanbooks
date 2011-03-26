package trabalho.impl;

import java.sql.*;

public class BancoDeDados 
{ 
    public static void main(String[] args) throws ClassNotFoundException, SQLException 
    {  
        //Carrega driver do MySQL  
        Class.forName("com.mysql.jdbc.Driver");  
        //Abre conexão com o banco, não estou conectando a nenhuma base em específico  
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost?user=usuario&password=senha");  
        //Crio um Statement  
        Statement stmt = conn.createStatement();  
        System.out.println("Começo");  
        //Toda operação que não for consulta, use executeUpdate, só assim  
        //você poderá alterar algo no banco de dados  
        int i = stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS TESTECREATE");  
        System.out.println("Resultado: "+i);// Se criar uma nova base de dados, o retorno será 1  
        System.out.println("Final");  
    }  
}
