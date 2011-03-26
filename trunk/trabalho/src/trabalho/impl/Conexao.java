package trabalho.impl;

import java.sql.*;

public class Conexao {  
    
    public static String status = "";  
    private static Connection con = null;  
  
    public static Connection getConnection() {  
        try {  
            Class.forName("com.mysql.jdbc.Driver").newInstance();  
            String url = "jdbc:derby:contabilidade;user=root;password=alyson";  
            con = DriverManager.getConnection(url);  
            status = "Conexão Aberta";  
  
        } catch (SQLException e) {  
            status = e.getMessage();  
        } catch (ClassNotFoundException e) {  
            status = e.getMessage();  
        } catch (Exception e) {  
            status = e.getMessage();  
        }  
        return con;  
    }  
  
    public String closeConnection(Connection c) {  
        try {  
            c.close();  
            status = "Conexao Encerrada";  
        } catch(SQLException e) {  
            status = "Encerramento Falho: " + e.getMessage();  
        }  
        return status;  
    }  
  
}  