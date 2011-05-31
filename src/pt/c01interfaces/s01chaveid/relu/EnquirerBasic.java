package pt.c01interfaces.s01chaveid.relu;


import java.util.LinkedList;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquirerBasic implements IEnquirer {

    
    LinkedList<IAnimal> allAnimals;
    private static IBaseConhecimento bc = new BaseConhecimento();
    
    
    public EnquirerBasic() {
        allAnimals = geraLista(bc.listaNomes());
    }
    
    private static LinkedList<IAnimal> geraLista (String[] vetor)
    {
        LinkedList<IAnimal> lista = new LinkedList<IAnimal>();
        for (int i=0;i<vetor.length;i++)
            lista.add(new Animal(vetor[i], bc));
        return lista;
    }

    public void connect(IResponder responder) {
        
        int index;
        
        while(allAnimals.size() > 1){
            
            IAnimal atual = allAnimals.getFirst();
            //System.out.println(atual.getNome());
            for(int i=0; i< allAnimals.size();i++){
                System.out.println(allAnimals.get(i).getNome());        
            }
            System.out.println();
            
            String propriedade = atual.getPerguntas().getFirst();
            String valor = atual.getRespostas().getFirst();
            String respostaCerta = responder.ask(propriedade);
            
            
            if(respostaCerta.equalsIgnoreCase(valor)){ /*checa se a primeira pergunta do primeiro animal 
                da lista confere com a respectiva resposta*/
                atual.getPerguntas().remove(0);//e ja remove a pergunta
                atual.getRespostas().remove(0);//e a resposta do primeiro animal
                
                for(index = 1 ;index<allAnimals.size();index++ ){//enquanto nao ler todos os animais, vai indo de um em um
                    atual = allAnimals.get(index);
                    if(atual.getPerguntas().contains(propriedade) && 
                    atual.getRespostas().get(atual.getPerguntas().indexOf(propriedade)).equalsIgnoreCase(valor))
                    {//se o animal tiver a perguta E a resposta corresponder
                        atual.getPerguntas().remove(atual.getPerguntas().indexOf(propriedade));//remove a pergunta
                        atual.getRespostas().remove(atual.getRespostas().indexOf(valor));//remove a resposta
                    } else { // se nao tiver a pergunta OU a resposta nao corresponder
                        allAnimals.remove(atual);
                        index -=1; //ajusta o indice para que o proximo animal seja comparado corretamente
                    }
                }//fim do for
                
            } else if(respostaCerta.equalsIgnoreCase("nao sei")){//se a pergunta do primeiro animal nao ocorre no correto
                
                allAnimals.remove(atual);//removo o primeiro
                
                for(index = 0 ;index<allAnimals.size();index++ ){
                    atual = allAnimals.get(index);
                    if(atual.getPerguntas().contains(propriedade)){//e todas as ocorrencias da pergunta
                        allAnimals.remove(allAnimals.get(index));
                        index-=1;
                    }
                }
                
            } else {//se a resposta do primeiro animal nao for a desejada
                allAnimals.remove(atual);//remove o animal atual
                
                for(index = 0 ;index<allAnimals.size();index++ ){//faz a mesma rotina descrita acima
                    atual = allAnimals.get(index);
                    if(atual.getPerguntas().contains(propriedade) && 
                        atual.getRespostas().get(atual.getPerguntas().indexOf(propriedade)).equalsIgnoreCase(respostaCerta))
                    {
                        atual.getPerguntas().remove(atual.getPerguntas().indexOf(propriedade));
                        atual.getRespostas().remove(atual.getRespostas().indexOf(respostaCerta));
                    
                    } else {
                        allAnimals.remove(allAnimals.get(index));
                        index -=1;
                    }
                }//fim do for
                
            }
            
        }//fim do while
                        
        //Neste ponto soh sobrou um animal na lista, e este eh o animal correto.
        
        if(responder.finalAnswer(allAnimals.getFirst().getNome()))
            System.out.println("acertou " + allAnimals.getFirst().getNome());
        else System.out.println("errou " + allAnimals.getFirst().getNome());
        

    }

}


