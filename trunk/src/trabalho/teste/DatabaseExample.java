package trabalho.teste;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import trabalho.banco.Database;


public class DatabaseExample
{

    public static void main(String args[])
    {
        try {
            Database bd = Database.getInstance();
        
            Connection dbConexao = bd.getConnection();

            executaConsulta(dbConexao);
            
            bd.releaseConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void executaConsulta(Connection conexao)
    {

        try {
            Statement comando = conexao.createStatement();

            ResultSet resultado = comando.executeQuery(
                "SELECT * FROM cadastro");

            boolean temConteudo = resultado.next();
            while (temConteudo)
            {
            	int ra = resultado.getInt("ra");
            	String nome = resultado.getString("nome"),
                curso = resultado.getString("curso");
                
                System.out.println(ra + "; " + nome + "; " + curso);
                temConteudo = resultado.next();
            }

            comando.close();
        } catch (SQLException erro) {
            erro.printStackTrace();
        }
    }

}