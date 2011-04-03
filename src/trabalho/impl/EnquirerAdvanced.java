package trabalho.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import trabalho.banco.CriaTabela;
import trabalho.banco.InsereTabela;
import trabalho.inter.IBaseConhecimento;
import trabalho.inter.IDeclaracao;
import trabalho.inter.IEnquirer;
import trabalho.inter.IObjetoConhecimento;
import trabalho.inter.IResponder;

public class EnquirerAdvanced implements IEnquirer 
{
	private String[] animais;				// recebe o vetor listaNomes da baseConhecimento
	private String usuario, senha;			// recebe os valores para conexao do banco
	private Vector<String> propriedade;		// vetor que armazena todas as perguntas consultadas
	private Vector<String> pergDiferentes;	// vetor que armazena as perguntas distintas
	private Vector<String> listaAnimais;	// vetor que armazena os possiveis animais
	private Vector<String> pergFeitas;		// vetor que armazena as perguntas ja feitas
	
	/**
	 * construtor da classe, recebe os valores para conexao
	 * instancia classes, cria vetores, alem de criar um
	 * banco de dados e uma tabela
	 */
	public EnquirerAdvanced()
	{
		// neste local se insere os dados da conexao com o banco
		usuario = "root";
		senha = "123456"; //mudar senha de acordo com a conexao da maquina
		
		IBaseConhecimento bc = new BaseConhecimento();
		animais = bc.listaNomes();
		propriedade = new Vector<String>();
		listaAnimais = new Vector<String>();
		pergDiferentes = new Vector<String>();
		pergFeitas = new Vector<String>();
		
		//insere elementos da lista de nomes em um Vector
		for(int i = 0; i < animais.length; i++)
			listaAnimais.add(animais[i]);
		
		// criando um banco de dados	
		CriaTabela cria = new CriaTabela(usuario, senha);
		if(cria.geraTabela())
		{
			// insere os dados na tabela
			new InsereTabela(animais, usuario, senha);
		}
		else
			// se ja existe o banco, atualiza os dados para uma nova utilizacao
			cria.atualizaTabela();
	}
	
	/**
	 * proximaPergunta metodo que retorna qual a pergunta que aparece mais vezes 
	 * dentro de um vetor de perguntas dado como entrada
	 * @param Vector propriedades
	 * @param Vector perguntasDiferentes
	 * @return String pergunta
	 */
	private String proximaPergunta (Vector<String> propriedades, Vector<String> perguntasDiferentes)
	{
		String pergunta = new String(); // string de retorno
		int cont = 0;					// contador de iteracao
		int contMax = 0;				// contador maximo, ele armazena a qtde de vezes que repete a pergunta
		
		for(int i = 0; i < perguntasDiferentes.size(); i++)
		{
			cont = 0;
			for(int j = 0; j < propriedades.size(); j++)
			{
				
				// se houver repeticao da string nos 2 vetores soma o contador
				if(propriedades.get(j).equals(perguntasDiferentes.get(i)))
				{
					cont++;
					// se o contador achar uma string que repete mais, atualiza
					if(cont > contMax)
					{
						pergunta = propriedades.get(j);
						contMax = cont;
					}
				}
			}
		}
		return pergunta;
	}
	
	/**
	 * connect, tem a funcao de realizar as perguntas para um usuario
	 * e escolher a melhor opcao de resposta dentro de uma base de dados determinada
	 * @param IResponder responder  
	 */
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
	        
	        // faz uma consulta de perguntas distintas
	        ResultSet listaPergDiferentes = stmt.executeQuery("SELECT DISTINCT(PERGUNTA) FROM BASE.GERAL G ORDER BY G.`PERGUNTA`");
	        
	        // insere o retorno da consulta em um vetor de String
	        boolean temConteudo = listaPergDiferentes.next();
            while (temConteudo)
            {
            	pergDiferentes.add(listaPergDiferentes.getString("PERGUNTA"));
                temConteudo = listaPergDiferentes.next();
            }
            
	        // enquanto nao encontrar o animal procurado
	        while(listaAnimais.size() > 1)
	        {
	          /* faz uma consulta que retorna todas as perguntas que ainda nao foram feitas e existem nos animais
		         que ainda restam como possibilidade de ser o animal procurado*/
	            ResultSet listaPerguntas = stmt.executeQuery("SELECT PERGUNTA FROM BASE.GERAL G " +
	            		"WHERE G.`CHECKED` = 1 ORDER BY G.`PERGUNTA`");
	            
	            // zero o vetor propriedade
	            if(propriedade.size() > 0)propriedade.removeAllElements();
	            // insere o retorno da consulta no vetor de String propriedade
	            temConteudo = listaPerguntas.next();
	            while (temConteudo)
	            {
	                propriedade.add(listaPerguntas.getString("PERGUNTA"));
	                temConteudo = listaPerguntas.next();
	            }
	            
	            // aqui testa o caso em que sobra mais de um animal sem perguntas a ser feitas
	            if(propriedade.size() == 0)
	            {
	            	
            		IDeclaracao decl = null;
            		IObjetoConhecimento obj;
            		IBaseConhecimento bc = new BaseConhecimento();
            		//System.out.println(listaAnimais.toString());
            		
            		
            		
            		while(listaAnimais.size() > 1)
            		{
            			//System.out.println(pergFeitas.size());
		            	for(int i = 0; i < listaAnimais.size(); i++)
			            {
		            		
		            		boolean achou = false;
		            		obj = bc.recuperaObjeto(listaAnimais.get(i));
		            		decl = obj.primeira();
		            		
		            		while(decl != null && !achou)
		            		{
		            			// se a ultima pergunta feita tiver na base deste animal, achou = true
		            			if(pergFeitas.lastElement().equalsIgnoreCase(decl.getPropriedade()))
		            					achou = true;
		            			decl = obj.proxima();
		            		}
		            		
		            		if(achou)
		            		{
		            			//System.out.println(pergFeitas.lastElement());
		            			//System.out.println(listaAnimais.get(i));
		            			// se achou remove o elemento da lista de animais possiveis
		            			listaAnimais.removeElementAt(i);
		            		}
			            }
		            	// elimino do vetor perguntas feitas esta pergunta
            			pergFeitas.removeElementAt(pergFeitas.size() - 1);
            		}
	            	break;
	            }           
	            
	            // chama o metodo que retorna a proxima pergunta
	            String pergunta = proximaPergunta(propriedade, pergDiferentes);
	            pergFeitas.add(pergunta);
	            // chama o responder
	            String resposta = responder.ask(pergunta);
	            // insere a resposta no banco
	            
	            //System.out.println(pergunta + " " + resposta);
	            stmt.executeUpdate("UPDATE BASE.GERAL SET RESPONDER='"+ resposta+"' " +
	            		"WHERE PERGUNTA='"+pergunta+"'");
	            if(resposta.equalsIgnoreCase("sim") || resposta.equalsIgnoreCase("nao"))
	            {            	
		            // faz uma consulta que retorna quais animais ainda podem ser o animal procurado
		            ResultSet listaAnimaisPossiveis = stmt.executeQuery("SELECT DISTINCT(G.`ANIMAIS`) FROM BASE.GERAL G " +
		            		"WHERE G.`PERGUNTA` = '"+pergunta+"' AND G.`RESPOSTA` = '"+resposta+"' AND CHECKED=1" );
		            
		            // zero o vetor de animais
	            	if(listaAnimais.size() > 0)listaAnimais.removeAllElements();
		            // insere o retorno da consulta no vetor de String Animais
		            temConteudo = listaAnimaisPossiveis.next();
		            while (temConteudo)
		            {
		            	listaAnimais.add(listaAnimaisPossiveis.getString("ANIMAIS"));
		                temConteudo = listaAnimaisPossiveis.next();
		            }
		            
		            // seta o valor FALSE na coluna CHECK, para todos os animais
		            stmt.executeUpdate("UPDATE GERAL SET CHECKED=0");
		            for(int i = 0; i < listaAnimais.size(); i++)
		            {
		            	// depois disso seta TRUE para todos animais que ainda tem possibilidade
		            	stmt.executeUpdate("UPDATE GERAL SET CHECKED=1 WHERE ANIMAIS = '"+listaAnimais.get(i)+"'");
		            	listaAnimais.iterator();
		            }
		            // seta o valor FALSE na coluna CHECK, para todas as perguntas ja feitas destes animais
		            for(int i = 0; i < pergFeitas.size(); i++)
		            {
		            	stmt.executeUpdate("UPDATE GERAL SET CHECKED=0 WHERE PERGUNTA = '"+pergFeitas.get(i)+"'");
		            	listaAnimais.iterator();
		            }
	            }
	            else 
	            {
	            	// seta o valor FALSE na coluna CHECK, para todas as perguntas ja feitas
		            for(int i = 0; i < pergFeitas.size(); i++)
		            {
		            	stmt.executeUpdate("UPDATE GERAL SET CHECKED=0 WHERE PERGUNTA = '"+pergFeitas.get(i)+"'");
		            	listaAnimais.iterator();
		            }
	            }
	            //System.out.println(listaAnimais.toString());
	        }
	        
	        // passa o resultado para o responder
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
