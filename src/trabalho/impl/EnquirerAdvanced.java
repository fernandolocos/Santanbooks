package trabalho.impl;

import java.sql.*;
import java.util.*;
import trabalho.banco.CriaTabela;
import trabalho.banco.InsereTabela;
import trabalho.inter.IBaseConhecimento;
import trabalho.inter.IDeclaracao;
import trabalho.inter.IEnquirer;
import trabalho.inter.IObjetoConhecimento;
import trabalho.inter.IResponder;

public class EnquirerAdvanced implements IEnquirer 
{
	private String[] animais;
	private String usuario, senha;
	private Vector<String> propriedade;
	private Vector<Integer> contaPergunta;
	
	public EnquirerAdvanced()
	{
		// neste local se insere os dados da conexao com o banco
		usuario = "root";
		senha = "26071946";
		
		IBaseConhecimento bc = new BaseConhecimento();
		animais = bc.listaNomes();
		propriedade = new Vector<String>();
		
		// criando uma tabela no banco de dados	
		new CriaTabela(usuario, senha);
		// inserindo os dados nas tabelas
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
		String pergunta = new String();
		for(int i = 0; i < propriedades.size(); i++)
		{
			//if(propriedades.contains(decl.getPropriedade())
		}
		return pergunta;
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
	        
	        // aciona metodo que executa comando SQL
            ResultSet resultado = stmt.executeQuery("SELECT PERGUNTA FROM GERAL");

            // lista o conteudo da tabela no console
            boolean temConteudo = resultado.next();
            while (temConteudo)
            {
                propriedade.add(resultado.getString("PERGUNTA"));
                temConteudo = resultado.next();
            }
            
            String pergunta = proximaPergunta(propriedade);
            
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
