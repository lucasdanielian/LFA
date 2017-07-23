package minimizacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Automato {
    /**
     * Atributos básicos:
     * * estados - armazena todos os estados do autômato em um ArrayList do objeto
     * * alfabeto - armazena todos os símbolos do alfabeto em um ArrayList de String
     * * transicoes - armazena todas as transições possíveis do autômato
     * * matriz - armazena a tabela de minimização em que será aplicado o algortimo
     */
    private ArrayList<Estado> estados;
    private ArrayList<String> alfabeto;
    private ArrayList<Transicao> transicoes;
    private ArrayList<Linha> matriz;
    /**
     * Atributos auxiliares:
     * * indiceAux - 
     * * igualAux -
     * * propagacaoAux -
     * * motivoAux -
     */
    private Indice indiceAux;
    private boolean igualAux;
    private ArrayList<Indice> propagacaoAux;
    private String motivoAux;
    /**
     * Atributo:
     * * matrizDeTransicao - estrutura de dados que armazena todas as transições 
     * do autômato em uma matriz, para que a aplicação da função de transição
     * (delta) seja rápida e simplificada, pois basta acessar uma célula da 
     * matriz para se obter o estado de destino a partir da linha (estado de
     * origem) e da coluna (símbolo lido) da mesma
     * 
     * A estrutura da matriz é assim:
     * * cada linha i representa um estado "qi" do AFD
     * * cada coluna j representa um símbolo do alfabeto (mapeado em inteiro)
     * * cada célula representa o estado "q" de destino da transição "qi" lendo "j"
     * 
     */
    private Integer[][] matrizDeTransicao;
    
    /**
     * Construtor da classe autômato (AFD).
     * 
     * O construtor identifica cada uma dos elementos formais do arquivo e os insere
     * nos respectivos atributos básicos do objeto criado
     * 
     * @param nomeArquivo - nome do arquivo de entrada contendo o AFD original a
     * ser minimizado
     */
    public Automato(String nomeArquivo) {
        // Inicializa os atributos
        estados = new ArrayList<>();
        alfabeto = new ArrayList<>();
        transicoes = new ArrayList<>();
        matriz = new ArrayList<>();
        
        this.lerArquivo(nomeArquivo);
    }
    
    /**
     * Metodo lerArquivo.
     * 
     * Lê o arquivo todo e insere as iformações nos atributos de uma vez
     * não insere nada na "matriz" por enquanto, isso é feito em outro método
     * 
     * @param nomeArquivo - nome do arquivo de entrada contendo o AFD original a
     * ser minimizado
     */
    public void lerArquivo(String nomeArquivo){   
        try{
            BufferedReader automato = new BufferedReader(new FileReader(nomeArquivo + ".txt"));
            String linha = automato.readLine();
            
            linha = automato.readLine();
            while(linha.charAt(0) != '{'){
                linha = linha.substring(1);
            }
            linha = linha.replace("{", "");
            linha = linha.replace("}", "");
            String[] novosEstados = linha.split(","); 
            for (String novosEstado : novosEstados) {
                Estado novoEstado = new Estado();
                novoEstado.setEstado(novosEstado);
                estados.add(novoEstado);
            }
            
            linha = automato.readLine();
            while(linha.charAt(0) != '{'){
                linha = linha.substring(1);
            }
            linha = linha.replace("{", "");
            linha = linha.replace("}", "");
            String[] novoAlfabeto = linha.split(",");
            alfabeto.addAll(Arrays.asList(novoAlfabeto));

            linha = automato.readLine();
            if(linha.contains("{")){
                linha = automato.readLine();
            }
            
            while(!linha.contains("}")){
                
                while(linha.charAt(0) != '('){
                    linha = linha.substring(1);
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
            }

            linha = automato.readLine();
            while(linha.charAt(0) != 'q'){
                linha = linha.substring(1);
            }
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

            linha = automato.readLine();
            while(linha.charAt(0) != '{'){
                linha = linha.substring(1);
            }
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
        catch(Exception e){
            System.out.println("Erro ao ler o arquivo :: " + e);
        }
    }
    
    /**
     * Método que constrói a tabela de minimização.
     * 
     * Após o programa ler o arquivo e armazenar as informações do AFD,
     * este método faz com que o atributo "matriz" guarde a tabela de minimização
     * que será escrita em um dos arquivos de saída (e.g. Tabela.txt) como
     * também cria a matriz de transição que será utilizada como função de transição
     * no processo de minimização (delta)
     * Executa o passo 1 do algoritmo da apostila
     */
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
        
        // passo 1 do algoritmo do Terra - pág. 145
        // insere todas as linha da matriz - combinações [i, j]
        // marca todos como supostamente iguais
        for (int i = 0; i < estados.size(); i++) {
                for (int j = i + 1; j < estados.size(); j++) {
                    indiceAux = new Indice(i,j);
                    igualAux = true;
                    propagacaoAux = new ArrayList<>();
                    motivoAux = "-"; // não tem motivo
                    Linha linha = new Linha(indiceAux, igualAux, propagacaoAux, motivoAux);
                    matriz.add(linha);
                }
        }
       
    }
    
    /**
     * Resolve o problema de fato.
     * Todo o processo de minimização é feito aqui
     * Implementação baseada no algoritmo descrito na apostila
     * Executa os passos 2, 3 e 4.
     */
    public void minimizar(String arquivo1, String arquivo2){           
        this.formatarTabela(); // passo 1
        
        // passo 2 do algoritmo do Terra - pág. 145
        // para cada par de estados, se um for final e outro não, marca como
        // diferentes
        for(Linha linha : matriz){
            String indice = linha.indice.toString();
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
            
            if(init.getIsFinal() == false && dest.getIsFinal() == true){ 
                linha.setMotivo("final/nao-final");
                linha.setNotIgual();
                linha.setMarcado();
            }
            else if(init.getIsFinal() == true && dest.getIsFinal() == false){ 
                linha.setMotivo("final/nao-final");
                linha.setNotIgual();
                linha.setMarcado();
            }
        }  
        
        // passo 3 do algortimo do Terra - pág. 145
        // verifica se para cada par de estados lendo o mesmo símbolo caem em estados
        // com mesmo tipo (final/não-final), inserindo a propagação se forem iguais
        // ou propagando a distinção caso contrário
        for(Linha linha : matriz) {
            if (linha.getMarcado()) {
                continue;
            }
            
            int i = linha.indice.i;
            int j = linha.indice.j;

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
                    linha.setMotivo(alfabeto.get(simbolo) + indiceProp);
                    linha.setNotIgual();
                    //aqui deve ser inserida a questao da propagação
                    propagar(linha); // método recursivo
                }
                else  {
                    // insere propagação                     
                    
                    for (Linha C : matriz) {
                        if (C.indice.i == dest_i && C.indice.j == dest_j && !C.marcado &&
                                C.indice.i != linha.indice.i && C.indice.j != linha.indice.j) {
                            if(!C.propagacao.contains(linha.indice))
                                C.propagacao.add(linha.indice);
                            break;
                        }
                    }
                }
                

            }
        }  
                
        // escreve a tabela final de minimização no arquivo - TABELA
        try{
            FileWriter arq = new FileWriter(arquivo1 + ".txt");
            arq.write("INDICE\t\tD[i,j] =\t\tS[i,j] = \t\tMOTIVO\n");
            
            for(Linha linha : matriz){
                String D_ij = linha.getIgual() ? "0" : "1";
                arq.write(linha.getIndice().toString() + "\t\t" + D_ij + "\t");
                
                for(int i=0; i < (4 - linha.propagacao.size()); i++){
                    arq.write("    ");
                }
                
                arq.write("{ ");
                
                for (Indice Idx : linha.propagacao) {
                    arq.write(Idx.toString() + " ");
                }
                
                arq.write("}\t\t\t" + linha.getMotivo() + "\n");
            }
            arq.close();
        }
        catch(Exception e){
            System.out.println("Erro ao escrver no Arquivo : " + arquivo1 +
                    ".txt" + " Motivo : " + e);
        }
        
        //Função para juntar os estados iguais para escrever o automato minimizado
        String e1 = "";
        int id;
        for(Linha linhaux : matriz){
            if(linhaux.getIgual()){
                for (Estado estado : estados){
                    if(estado.getEstado().contains("q" + linhaux.indice.i)){
                        if(!estado.getEstado().contains("q" + linhaux.indice.j)){
                            if(estado.getIsInicial()){
                                estados.get(linhaux.indice.j).setInicial();
                                estados.get(linhaux.indice.i).setInicial();
                            }
                            estado.setEstado(estado.getEstado() + "q" + linhaux.indice.j);
                            e1 = "q" + linhaux.indice.j;
                        }
                    }
                       
                }
                
                id = -1;
                for(int i=0; i < estados.size(); i++){
                    if(estados.get(i).getEstado().equals(e1))
                        id = i;
                }
                if(id != -1){
                    estados.remove(id);
                }
                
                for(Transicao t : transicoes){
                    if(t.getOrigem().contains("q" + linhaux.indice.i)){
                        if(!t.getOrigem().contains("q" + linhaux.indice.j)){
                            t.setOrigem(t.getOrigem() + "q" + linhaux.indice.j);
                            e1 = "q" + linhaux.indice.j;
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
                    if(t.getDestino().contains("q" + linhaux.indice.i)){
                        if(!t.getDestino().contains("q" + linhaux.indice.j)){
                            t.setDestino(t.getDestino() + "q" + linhaux.indice.j);
                            e1 = "q" + linhaux.indice.j;
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
                    if(t.getOrigem().contains("q" + linhaux.indice.j)){
                        if(!t.getOrigem().contains("q" + linhaux.indice.i)){
                            t.setOrigem("q" + linhaux.indice.i + t.getOrigem());
                            e1 = "q" + linhaux.indice.i;
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
                    if(t.getDestino().contains("q" + linhaux.indice.j)){
                        if(!t.getDestino().contains("q" + linhaux.indice.i)){
                            t.setDestino("q" + linhaux.indice.i + t.getDestino());
                            e1 = "q" + linhaux.indice.i;
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
            
        // escreve o autômato minimizado resultante no arquivo - AFD NOVO
        try{
            FileWriter arq = new FileWriter(arquivo2 + ".txt");
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
            System.out.println("Erro ao escrver no Arquivo : " + arquivo1 +
                    ".txt" + " Motivo : " + e);
        }
        
    }
    
    /**
     * Método recursivo responsável por tratar a propagação de distinção na tabela.
     * 
     * @param linha - uma linha da tabela de minimização
     */
    public void propagar(Linha linha){
        ArrayList<Indice> propagacao = linha.getPropagacao();
        for (Indice propagar : propagacao) {
            Linha x = matriz.get(propagar.linhaTabela);
            x.setNotIgual();
            x.setMotivo("prop" + linha.indice.toString());
            propagar(x);
        }
    }
    
    // Debugger
    /*
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
    }*/
}
