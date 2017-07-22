package minimizacao;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

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
    private ArrayList<String> alfabeto;
    private ArrayList<Transicao> transicoes;
    private ArrayList<Coluna> matriz;
    
    private Indice indiceAux;
    private boolean igualAux;
    private ArrayList<Indice> propagacaoAux;
    private String motivoAux;
    
    private Integer[][] matrizDeTransicao;

    
    public Automato(String nomeArquivo) {
        estados = new ArrayList<>();
        alfabeto = new ArrayList<>();
        transicoes = new ArrayList<>();
        matriz = new ArrayList<>();
        
        try{
            BufferedReader automato = new BufferedReader(new FileReader(nomeArquivo + ".txt"));
            String linha = automato.readLine();
            
            linha = automato.readLine();
            if(linha.charAt(0) == '{'){
                linha = linha.replace("{", "");
                linha = linha.replace("}", "");
                String[] novosEstados = linha.split(","); 
                for (String novosEstado : novosEstados) {
                    Estado novoEstado = new Estado();
                    novoEstado.setEstado(novosEstado);
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
                alfabeto.addAll(Arrays.asList(novoAlfabeto));
            }
            else{
                alfabeto.add(linha);
            }
            
            linha = automato.readLine();
            if(linha.contains("{")){
                linha = automato.readLine();
            }
            
            while(linha.charAt(linha.length() - 1) == ','){
                
                Transicao novaTransicao = new Transicao();
                linha = linha.replace("(", "");
                linha = linha.replace(")", "");
                linha = linha.replace("->",",");
                String[] aux = linha.split(",");
                novaTransicao.setOrigem(aux[0]);
                novaTransicao.setTerminal(aux[1].charAt(0));
                novaTransicao.setDestino(aux[2]);
                transicoes.add(novaTransicao);
                linha = automato.readLine();
            }
            Transicao novaTransicao = new Transicao();
            linha = linha.replace("(", "");
            linha = linha.replace(")", "");
            linha = linha.replace("->",",");
            String[] aux = linha.split(",");
            novaTransicao.setOrigem(aux[0]);
            novaTransicao.setTerminal(aux[1].charAt(0));
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
                for (int i=0; i < iniciais.length; i++) {
                    for (int j = 0; j<estados.size(); j++) {
                        if (iniciais[i].equals(estados.get(j).getEstado())) {
                            estados.get(j).setInicial();
                        }
                    }
                }
            }
            else{
                linha = linha.replace(",", "");
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
        for(Estado aux : estados){
            if(aux.getIsInicial())
        System.out.println(aux.getEstado());
        }
        
        System.out.print("Estados Finais-> " );
        for(Estado aux : estados){
            if(aux.getIsFinal())
            System.out.println(aux.getEstado());
        }
    }
    
    public void formatarTabela() {
        
        matrizDeTransicao = new Integer[estados.size()][alfabeto.size()];
        for(int i = 0; i < estados.size(); i++){
            for(int j = 0; j < alfabeto.size(); j++){
                matrizDeTransicao[i][j] = -1;
            }
        }
        
        for(int i = 0; i < estados.size(); i++){
            for(Transicao transicao : transicoes){
                String origem = transicao.getOrigem();
                origem = origem.replace("q", "");
                String destino = transicao.getDestino();
                destino = destino.replace("q", "");
                int letra = Character.getNumericValue(transicao.getTerminal());
                // 'A' e 'a' é igual a 10, não diferencia maius. de minusc.
                matrizDeTransicao[Integer.parseInt(origem)][letra-10] = Integer.parseInt(destino);
            }
        }

        // insere todas as linha da matriz - combinações [i, j].
        for (int i = 0; i < estados.size(); i++) {
                for (int j = i + 1; j < estados.size(); j++) {
                    indiceAux = new Indice (i,j);
                    igualAux = true;
                    propagacaoAux = new ArrayList<>();
                    motivoAux = "-"; // não tem motivo
                    Coluna coluna = new Coluna(indiceAux, igualAux, propagacaoAux, motivoAux);
                    matriz.add(coluna);
                }
        }
       
    }
    
    public void minimizar(){ // resolve o problema de fato
        // passo 2 do algoritmo do Terra - pág. 145
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
            
            if(init.getIsFinal() == false && dest.getIsFinal() == true){ // nao e possivel simplificar
                coluna.setMotivo("final/nao-final");
                coluna.setNotIgual();
                coluna.setMarcado();
                //aqui deve ser inserida a questao da propagação - não precisa!
                //propagar(coluna);
            }
            else if(init.getIsFinal() == true && dest.getIsFinal() == false){ // nao é possivel simplificar
                coluna.setMotivo("final/nao-final");
                coluna.setNotIgual();
                coluna.setMarcado();
                //aqui deve ser inserida a qestao da propagação - não precisa!
                //propagar(coluna);
            }
            
            /*
            
            else {
                // insere propagação 
                int i = coluna.indice.i;
                int j = coluna.indice.j;
                
                for (int simbolo = 0; simbolo < alfabeto.size(); simbolo++) {
                    int dest_i = matrizDeTransicao[i][simbolo];
                    int dest_j = matrizDeTransicao[j][simbolo];

                    // ordena, sempre i < j:
                    if (dest_i > dest_j) {
                        int auxSwap = dest_i;
                        dest_i = dest_j;
                        dest_j = auxSwap;
                    }
                    
                    //Indice indexAux = new Indice(dest_i, dest_j, false);
                    
                    for (Coluna C : matriz) {
                        if (C.indice.i == dest_i && C.indice.j == dest_j && !C.getMarcado()) {
                            C.propagacao.add(coluna.indice);
                            break;
                        }
                    }
                    
                }                    
            }*/
        }  
        
        
        // passo 3 do algortimo
        for(Coluna coluna : matriz) {
            if (coluna.getMarcado()) {
                continue;
            }
            
            int i = coluna.indice.i;
            int j = coluna.indice.j;

            Estado dest1 = new Estado();
            Estado dest2 = new Estado();
            
            for (int simbolo = 0; simbolo < alfabeto.size(); simbolo++) {
                int dest_i = matrizDeTransicao[i][simbolo];
                int dest_j = matrizDeTransicao[j][simbolo];
                
                // ordena, sempre i < j:
                if (dest_i > dest_j) {
                    int aux = dest_i;
                    dest_i = dest_j;
                    dest_j = aux;
                }
                
                String aux1 = "q" + Integer.toString(dest_i);
                String aux2 = "q" + Integer.toString(dest_j);   

                for(Estado estado : estados){
                    if(estado.getEstado().equals(aux1)){
                        dest1 = estado;
                    }
                    if(estado.getEstado().equals(aux2)){
                        dest2 = estado;
                    }
                }
                
                if(dest1.getIsFinal() == false && dest2.getIsFinal() == true ||
                        dest1.getIsFinal() == true && dest2.getIsFinal() == false) {
                    String indiceProp = "[" + Integer.toString(dest_i) + "," + Integer.toString(dest_j) + "]";
                    coluna.setMotivo(alfabeto.get(simbolo) + indiceProp);
                    coluna.setNotIgual();
                    //aqui deve ser inserida a questao da propagação
                    propagar(coluna); // método recursivo
                }
                else  {
                    // insere propagação                     
                    
                    for (Coluna C : matriz) {
                        if (C.indice.i == dest_i && C.indice.j == dest_j && !C.marcado &&
                                C.indice.i != coluna.indice.i && C.indice.j != coluna.indice.j) {
                            if(!C.propagacao.contains(coluna.indice))
                                C.propagacao.add(coluna.indice);
                            break;
                        }
                    }
                }
                

            }
        }  
                
        
        try{
            FileWriter arq = new FileWriter("resultado.txt");
            arq.write("INDICE\t\tD[i,j] =\t\tS[i,j] = \t\tMOTIVO\n");
            
            for(Coluna coluna :matriz){
                //arq.write(coluna.getIndice().toString() + "\t\t" + coluna.getIgual() + "\t\t\t{ " + " }\t\t\t" + coluna.getMotivo() + "\n");
                arq.write(coluna.getIndice().toString() + "\t\t" + coluna.getIgual() + "\t");
                
                for(int i=0; i < (4 - coluna.propagacao.size()); i++){
                    arq.write("    ");
                }
                
                arq.write("{ ");
                
                for (Indice Idx : coluna.propagacao) {
                    arq.write(Idx.toString() + " ");
                }
                
                arq.write("}\t\t\t" + coluna.getMotivo() + "\n");
            }
            arq.close();
        }
        catch(Exception e){
            
        }
        //Não to dando conta
        String e1 = "";
        for(Coluna colunaux : matriz){
            if(colunaux.getIgual()){
                for (Estado estado : estados){
                    if(estado.getEstado().contains("q" + colunaux.indice.i)){
                        if(!estado.getEstado().contains("q" + colunaux.indice.j)){
                            if(estado.getIsInicial()){
                                estados.get(colunaux.indice.j).setInicial();
                                estados.get(colunaux.indice.i).setInicial();
                            }
                            estado.setEstado(estado.getEstado() + "q" + colunaux.indice.j);
                            e1 = "q" + colunaux.indice.j;
                        }
                    }
                       
                }
                
                int id = -1;
                for(int i=0; i < estados.size(); i++){
                    if(estados.get(i).getEstado().equals(e1))
                        id = i;
                }
                if(id != -1){
                    estados.remove(id);
                }
                
                for(Transicao t : transicoes){
                    if(t.getOrigem().contains("q" + colunaux.indice.i)){
                        if(!t.getOrigem().contains("q" + colunaux.indice.j)){
                            t.setOrigem(t.getOrigem() + "q" + colunaux.indice.j);
                            e1 = "q" + colunaux.indice.j;
                        }
                    }
                }
                
                id = -1;
                for(int i=0; i < transicoes.size(); i++){
                    if(transicoes.get(i).getOrigem().equals(e1))
                        id = i;
                }
                if(id != -1){
                    transicoes.remove(id);
                }

                for(Transicao t : transicoes){
                    if(t.getDestino().contains("q" + colunaux.indice.i)){
                        if(!t.getDestino().contains("q" + colunaux.indice.j)){
                            t.setDestino(t.getDestino() + "q" + colunaux.indice.j);
                            e1 = "q" + colunaux.indice.j;
                        }
                    }
                }
                
                id = -1;
                for(int i=0; i < transicoes.size(); i++){
                    if(transicoes.get(i).getOrigem().equals(e1))
                        id = i;
                }
                if(id != -1){
                    transicoes.remove(id);
                }
                
                for(Transicao t : transicoes){
                    if(t.getOrigem().contains("q" + colunaux.indice.j)){
                        if(!t.getOrigem().contains("q" + colunaux.indice.i)){
                            t.setOrigem("q" + colunaux.indice.i + t.getOrigem());
                            e1 = "q" + colunaux.indice.i;
                        }
                    }
                }
                
                id = -1;
                for(int i=0; i < transicoes.size(); i++){
                    if(transicoes.get(i).getOrigem().equals(e1))
                        id = i;
                }
                if(id != -1){
                    transicoes.remove(id);
                }
                
                for(Transicao t : transicoes){
                    if(t.getDestino().contains("q" + colunaux.indice.j)){
                        if(!t.getDestino().contains("q" + colunaux.indice.i)){
                            t.setDestino("q" + colunaux.indice.i + t.getDestino());
                            e1 = "q" + colunaux.indice.i;
                        }
                    }
                }
                
                id = -1;
                for(int i=0; i < transicoes.size(); i++){
                    if(transicoes.get(i).getOrigem().equals(e1))
                        id = i;
                }
                if(id != -1){
                    transicoes.remove(id);
                }
            }
        }
            
        
        try{
            FileWriter arq = new FileWriter("afd minimizado.txt");
            arq.write("(\n    {");
            
            for(Estado e : estados){
                arq.write(e.getEstado() + ",");
            }
            
            arq.write("},\n    {");
            
            for(String letras : alfabeto){
                arq.write(letras + ",");
            }
            
            arq.write("},\n    {\n");
            
            for(Transicao t : transicoes){
                arq.write("        (" + t.getOrigem() + "," + t.terminal + "->" + 
                        t.getDestino() + "),\n");
            }
            for(Estado aux : estados){
                if(aux.getIsInicial())
                    arq.write("    },\n    " + aux.getEstado() + ",\n    {");
            }
            
            for(Estado aux : estados){
                if(aux.getIsFinal())
                    arq.write(aux.getEstado() + ",");
            }
            
            arq.write("}\n)");
            
            arq.close();
        }
        catch(Exception e){
            
        }
        
    }
    
    public void propagar(Coluna coluna){
        ArrayList<Indice> propagacao = coluna.getPropagacao();
        for (Indice propagar : propagacao) {
            Coluna x = matriz.get(propagar.linhaTabela);
            x.setNotIgual();
            x.setMotivo("propagacao" + coluna.indice.toString());
            propagar(x);
        }
    }
}
