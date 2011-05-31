package pt.c03ensaios.proj01;

import java.io.*;
import java.util.Scanner;

import pt.c03ensaios.debolacha.inter.IAnimalList;
import pt.c03ensaios.linnaeus.IAnimalsDatabase;
import pt.c03ensaios.tochinhas2.impl.IQuestions;

import anima.component.IRequires;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

public class TesteDoComponente {

	public static void main(String args[]) {
		
		// Declarações
		Scanner entrada = new Scanner(System.in);
		String resposta = "";

        IGlobalFactory factory = null;
		try {
			factory = ComponentContextFactory.createGlobalFactory();
		} catch (ContextException e) {
			e.printStackTrace();
		} catch (FactoryException e) {
			e.printStackTrace();
		}
            
        IAnimalListReceptacle meuManipulador = factory.createInstance(
            		"<http://purl.org/dcc/pt.c03ensaios.proj01.TextFilesManager>");  
        
        IQuestions minhasQuestoes = factory.createInstance(
        		"<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.Questions>");

        IAnimalsDatabase db = factory.createInstance(
                "<http://purl.org/dcc/pt.c03ensaios.linnaeus.AnimalsDatabase>");
        
        IRequires<IQuestions> managerConectado = meuManipulador.queryReceptacle(
        		"<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.IQuestions>");
        
        IRequires<IAnimalsDatabase> questoesConectadas = minhasQuestoes.queryReceptacle(
    		"<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalsDatabase>");
        
        IAnimalList lista = factory.createInstance(
        		"<http://purl.org/dcc/pt.c03ensaios.debolacha.impl.AnimalList>");
        		
        questoesConectadas.connect(db);
        managerConectado.connect(minhasQuestoes);
        meuManipulador.connect(lista);
        
        ITextFilesManager txtManipulador = (ITextFilesManager) meuManipulador;
            
		System.out.println("Base de dados: ");

		// Exibição arquivos da pasta
		String[] arquivos = txtManipulador.listaDeArquivos();
		for (int i = 0; i < arquivos.length; i++)
			System.out.println(arquivos[i]);

		int acao = -1;
		while (acao != TextFilesManager.SAIR) {
			System.out.print(TextFilesManager.SEPARADOR + TextFilesManager.MENU + TextFilesManager.PEDIDODAOPCAO);
			acao = Integer.parseInt(entrada.nextLine());

			switch (acao) {
			case TextFilesManager.ADICIONAR:
				System.out.println("Nome do arquivo: ");
				String nomeArquivo = entrada.nextLine();

				System.out.println("Digite os dados e SAVE para salvar:\n");
				resposta = entrada.nextLine();

				String texto = "";

				if (!resposta.equals(TextFilesManager.MARCADORDEFIM)) {
					texto = resposta;
					resposta = entrada.nextLine();

					while (!resposta.equals(TextFilesManager.MARCADORDEFIM)) {
						texto += "\n" + resposta;
						resposta = entrada.nextLine();
					}
				}

				try {
					if (txtManipulador.temIgual(texto) || txtManipulador.temComMesmoNome(nomeArquivo))					
						System.out.println("TEM!!!");
					else
					{
						txtManipulador.criaArquivo(nomeArquivo);
						txtManipulador.insereConteudo(texto);
						System.out.println(TextFilesManager.SUCESSODECRIACAO);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					txtManipulador.setPasta(new File(
							"pt/c01interfaces/s01chaveid/s01base/bd/"));
				} catch (Exception e) {
					e.printStackTrace();
				}

				break;

			case TextFilesManager.EDITAR:
				arquivos = txtManipulador.listaDeArquivos();
				for (int i = 0; i < arquivos.length; i++)
					System.out.println(i+1 + " - " + arquivos[i]);
				
				int indice = -1;

				try {
					indice = Integer.parseInt(entrada.nextLine());
					txtManipulador.setArquivo(new File(txtManipulador.getPasta() + "/" + arquivos[indice-1]));
					System.out.println(txtManipulador.pegaConteudo());
				} catch (Exception e) {
					e.printStackTrace();
				}

				System.out.println("Digite os dados e SAVE para salvar:\n");
				resposta = entrada.nextLine();

				texto = "";

				if (!resposta.equals(TextFilesManager.MARCADORDEFIM)) {
					texto = resposta;
					resposta = entrada.nextLine();

					while (!resposta.equals(TextFilesManager.MARCADORDEFIM)) {
						texto += "\n" + resposta;
						resposta = entrada.nextLine();
					}
				}
				
				try {
					if (txtManipulador.temIgual(texto))					
						System.out.println("TEM!!!");
					else
					{
						txtManipulador.insereConteudo(texto);
						System.out.println(TextFilesManager.SUCESSODECRIACAO);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				try {
					txtManipulador.insereConteudo(texto);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				try {
					txtManipulador.setPasta(new File(
							"pt/c01interfaces/s01chaveid/s01base/bd/"));
				} catch (Exception e) {
					e.printStackTrace();
				}

				break;

			case TextFilesManager.DELETAR:
				arquivos = txtManipulador.listaDeArquivos();
				for (int i = 0; i < arquivos.length; i++)
					System.out.println(i + " - " + arquivos[i]);
				
				try {
					txtManipulador.setArquivo(arquivos[Integer.parseInt(entrada.nextLine())-1]);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (txtManipulador.apaga())					
					System.out.println(TextFilesManager.SUCESSODEDELECAO);

				break;
			}
		} 
	} 
}
