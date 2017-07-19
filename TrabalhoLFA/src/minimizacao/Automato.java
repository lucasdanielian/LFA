package minimizacao;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author raydson
 */
public class Automato {
    private ArrayList<Estado> estados;
    private ArrayList<String> estadosIniciais;
    private ArrayList<String> estadosFinais;
    private ArrayList<String> alfabeto;
    private ArrayList<Transicao> transicoes;
    private ArrayList<Coluna> matriz;
    
    
    private Indice indiceAux;
    private boolean igualAux;
    private ArrayList<Indice> propagacaoAux;
    private String motivoAux;
    
    public Automato(String nomeArquivo) {
        estados = new ArrayList<>();
        estadosIniciais = new ArrayList<>();
        estadosFinais = new ArrayList<>();
        alfabeto = new ArrayList<>();
        transicoes = new ArrayList<>();
        matriz = new ArrayList<Coluna>();
        
        try{
            System.out.println("\n\n abla \t\t top\n\n");
            
            ioArquivo io = new ioArquivo();
            BufferedReader automato = io.lerArquivo(nomeArquivo);
            String linha = automato.readLine();
            System.out.println(linha);
            
            
            System.out.println("\n\n abla \t\t top\n\n");
            
            linha = automato.readLine();
            if(linha.charAt(0) == '{'){
                linha = linha.replace("{", "");
                linha = linha.replace("}", "");
                String[] novosEstados = linha.split(","); 
                for(int i=0; i<novosEstados.length; i++){
                    Estado novoEstado = new Estado();
                    novoEstado.setEstado(novosEstados[i]);
                    estados.add(novoEstado);
                }
            }
            else {
                Estado novoEstado = new Estado();
                novoEstado.setEstado(linha);
                estados.add(novoEstado);
            }
            
            linha = automato.readLine();
            if(linha.charAt(0) == '{'){
                linha = linha.replace("{", "");
                linha = linha.replace("}", "");
                String[] novoAlfabeto = linha.split(",");
                for(int i=0; i<novoAlfabeto.length; i++){
                    alfabeto.add(novoAlfabeto[i]);
                }
            }
            else{
                alfabeto.add(linha);
            }
            
            linha = automato.readLine();
            if(linha.charAt(0) == '{')
                linha = automato.readLine();
            
            while(linha.charAt(linha.length() - 1) == ','){
                
                Transicao novaTransicao = new Transicao();
                linha = linha.replace("(", "");
                linha = linha.replace(")", "");
                linha = linha.replace("->",",");
                System.out.println(linha);
                String[] aux = linha.split(",");
                novaTransicao.setOrigem(aux[0]);
                novaTransicao.setTerminal(aux[1]);
                novaTransicao.setDestino(aux[2]);
                transicoes.add(novaTransicao);
                linha = automato.readLine();
            }
            Transicao novaTransicao = new Transicao();
            linha.replace("(", "");
            linha.replace(")", "");
            linha.replace("->",",");
            String[] aux = linha.split(",");
            novaTransicao.setOrigem(aux[0]);
            novaTransicao.setTerminal(aux[1]);
            novaTransicao.setDestino(aux[2]);
            transicoes.add(novaTransicao);
            
            linha = automato.readLine();
            if(linha.charAt(0) == '}'){
                linha = automato.readLine();                
            }
            
            if(linha.charAt(0) == '{'){
                linha = linha.replace("{", "");
                linha = linha.replace("}", "");
                String[] iniciais = linha.split(",");
                for(int i=0; i<iniciais.length; i++){
                    for(int j=0; j<estados.size(); j++){
                        if(iniciais[i].equals(estados.get(j).getEstado())){
                            estados.get(j).setInicial();
                        }
                    }
                    //estadosFinais.add(finais[i]);
                }
            }
            else{
                for(int j=0; j<estados.size(); j++){
                        if(linha.equals(estados.get(j).getEstado())){
                            estados.get(j).setInicial();
                        }
                    }
            }      
            
            linha = automato.readLine();
            if(linha.charAt(0) == '{'){
                linha = linha.replace("{", "");
                linha = linha.replace("}", "");
                String[] finais = linha.split(",");
                for(int i=0; i<finais.length; i++){
                    for(int j=0; j<estados.size(); j++){
                        if(finais[i].equals(estados.get(j).getEstado())){
                            estados.get(j).setFinal();
                        }
                    }
                    //estadosFinais.add(finais[i]);
                }
            }
            else{
                for(int j=0; j<estados.size(); j++){
                        if(linha.equals(estados.get(j).getEstado())){
                            estados.get(j).setFinal();
                        }
                    }
            }
            
            
        }
        catch(Exception e){
            System.out.println("Erro ao ler o arquivo :: " + e);
        }
    }
    
    public void imprimePraMim(){
        System.out.print("Estados -> " );
        for(Estado aux : estados){
           System.out.println(aux.getEstado());
        }
        
        System.out.print("Afabeto -> " );
        for(String aux : alfabeto){
           System.out.println(aux);
        }
        
        System.out.print("Transições -> " );
        for(Transicao aux : transicoes){
           System.out.println(aux.getOrigem() + "," + aux.getTerminal() + "->" + aux.getDestino());
        }
        
        System.out.print("Estados Iniciais -> " );
        for(String aux : estadosIniciais){
           System.out.println(aux);
        }
        
        System.out.print("Estados Finais-> " );
        for(String aux : estadosFinais){
           System.out.println(aux);
        }
    }
    
    public void formatarTabela() {
        
        // cria a matriz para calcular o AFD minimizado.
        

        // insere todas as linha da matriz - combinações [i, j].
        for (int i = 0; i < estados.size(); i++) {
                for (int j = i + 1; j < estados.size(); j++) {
                        indiceAux = new Indice (i,j);
                        igualAux = true;
                        propagacaoAux = new ArrayList<Indice>();
                        motivoAux = "nao tem motivo";
                        Coluna coluna = new Coluna(indiceAux,igualAux,propagacaoAux,motivoAux);
                        matriz.add(coluna);
                }
        }
       
    }
    
    public void minimizar(){ // resolve o problema de fato
        for(Coluna coluna : matriz){
            String indice = coluna.indice.toString();
            indice = indice.replace("[", "");
            indice = indice.replace("]", "");
            String[] aux = indice.split(","); // cada indice do vetor fica com um dos estados envolvidos
            
            aux[0] = "q" + aux[0];
            aux[1] = "q" + aux[1];
            
            
            Estado init = new Estado();
            Estado dest = new Estado();
            
            for(Estado estado : estados){
                if(estado.getEstado().equals(aux[0])){
                    init = estado;
                }
                if(estado.getEstado().equals(aux[1])){
                    dest = estado;
                }
            }
            
            init.setFinal();
            
            if(init.getIsFinal() == false && dest.getIsFinal() == true){ // nao e possivel simplicar
                coluna.setMotivo("Nao final / final");
            }
            else{
                if(init.getIsFinal() == true && dest.getIsFinal() == false){ // nao é possivel simplificar
                    coluna.setMotivo("Final/ Nao final");
                }
                else{
                    //william inserir sua matriz aqui;
                    
                }
            }
            
           
           
            
        }
        
        try{
            FileWriter arq = new FileWriter("resultado.txt");
            arq.write("INDICE\t\tD[i,j] = \t\t S[i,j] = \t\t MOTIVO \n");
            
            for(Coluna coluna :matriz){
                arq.write(coluna.getIndice().toString() + "\t\t" + coluna.getIgual() + "\t\t\t" + "\t\t\t" + coluna.getMotivo() + "\n");
            }
            arq.close();
        }
        catch(Exception e){
            
        }
        
    }
}
