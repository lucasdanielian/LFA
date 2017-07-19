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
    private ArrayList<String> estados;
    private ArrayList<String> estadosIniciais;
    private ArrayList<String> estadosFinais;
    private ArrayList<String> alfabeto;
    private ArrayList<String> transicoes;
    private ArrayList<Coluna> matriz;
    
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
                    estados.add(novosEstados[i]);
                }
            }
            else {
                estados.add(linha);
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
                transicoes.add(linha.replace(",", ""));
                linha = automato.readLine();
            }
            transicoes.add(linha);
            
            linha = automato.readLine();
            if(linha.charAt(0) == '}'){
                linha = automato.readLine();                
            }
            
            if(linha.charAt(0) == '{'){
                linha = linha.replace("{", "");
                linha = linha.replace("}", "");
                String[] iniciais = linha.split(",");
                for(int i=0; i<iniciais.length; i++){
                    estadosIniciais.add(iniciais[i]);
                }
            }
            else{
                estadosIniciais.add(linha);
            }            
            
            linha = automato.readLine();
            if(linha.charAt(0) == '{'){
                linha = linha.replace("{", "");
                linha = linha.replace("}", "");
                String[] finais = linha.split(",");
                for(int i=0; i<finais.length; i++){
                    estadosFinais.add(finais[i]);
                }
            }
            else{
                estadosFinais.add(linha);
            }
        }
        catch(Exception e){
            System.out.println("Erro ao ler o arquivo :: " + e);
        }
    }
    
    public void imprimePraMim(){
        System.out.print("Estados -> " );
        for(String aux : estados){
           System.out.println(aux);
        }
        
        System.out.print("Afabeto -> " );
        for(String aux : alfabeto){
           System.out.println(aux);
        }
        
        System.out.print("Transições -> " );
        for(String aux : transicoes){
           System.out.println(aux);
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
        Indice indiceAux;
        boolean igualAux;
        ArrayList<Indice> propagacaoAux = new ArrayList<Indice>();
        ArrayList<String> motivoAux = new ArrayList<String>();


        // insere todas as linha da matriz - combinações [i, j].
        for (int i = 0; i < estados.size(); i++) {
                for (int j = i + 1; j < estados.size(); j++) {
                        indiceAux = new Indice (i,j);
                        igualAux = true;
                        propagacaoAux = null;
                        motivoAux = null;
                        Coluna coluna = new Coluna(indiceAux,igualAux,propagacaoAux,motivoAux);
                        matriz.add(coluna);
                }
        }
        
        try{
            FileWriter arq = new FileWriter("resultado.txt");
            arq.write("INDICE\tD[i,j] = \t S[i,j] = \t MOTIVO \n");
            
            for(Coluna coluna :matriz){
                arq.write(coluna.getIndice().toString() + "\t" + coluna.igual + "\t" + "\n");
            }
            arq.close();
        }
        catch(Exception e){
            
        }
    }
}
