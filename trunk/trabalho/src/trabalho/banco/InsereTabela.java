package trabalho.impl;

import java.sql.*;

import trabalho.inter.IBaseConhecimento;
import trabalho.inter.IDeclaracao;
import trabalho.inter.IObjetoConhecimento;

public class InsereTabela
{
	private IObjetoConhecimento obj;
	private IBaseConhecimento bc;
	
	public InsereTabela(String[] listaNomes)
    {
		bc = new BaseConhecimento();
		insere("root", "26071946", listaNomes);
    }
    
    public void insere(String usuario, String senha, String[] listaNomes)
    {

        try 
        {
        	//Carrega driver do MySQL  
	        Class.forName("com.mysql.jdbc.Driver");  
	        //Abre conexao com o banco  
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/BASE?user="+usuario+"&"+"password="+senha);  
	        // cria um objeto de comandos SQL para a base
	        Statement stmt = conn.createStatement();
	        
	        IDeclaracao decl = null;
	        
	        // criando um vetor de objetos com todos os animais
			for(int i = 0; i < listaNomes.length; i++)
			{
				obj = bc.recuperaObjeto(listaNomes[i]);
				decl = obj.primeira();
				
				while(decl != null)
				{
		            // aciona metodo que executa comando SQL					
					stmt.executeUpdate("INSERT INTO TABELA (ANIMAIS, PERGUNTA, RESPOSTA) VALUES ('"+listaNomes[i]+"', '"+
							decl.getPropriedade()+"', '"+decl.getValor()+"')");
		            
		            decl = obj.proxima();
				}
			}

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