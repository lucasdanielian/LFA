package minimizacao;

/**
 * Classe Transicao.
 * 
 * Representa cada transição feita no AFD (delta)
 * 
 * @author Raydson
 * @author Lucas
 * @author William
 * 
 */
class Transicao {
    /**
     * Atributos:
     * * origem - estado de origem
     * * terminal - símbolo do alfabeto lido
     * * destino - estado destino após a transição
     */
    String origem;
    char terminal;
    String destino;
    
    /**
     * Construtor Padrão.
     * 
     * Apenas inicializa os atributos
     * Os valores úteis serão postos pelos métodos set
     */
    public Transicao(){
        origem = "";
        terminal = 'N';
        destino = "";
}
    
    public void setOrigem(String origem){
        this.origem = origem;
    }
    
    public void setTerminal(char terminal){
        this.terminal = terminal;
    }
    
    public void setDestino(String destino){
        this.destino = destino;
    }
    
    public String getOrigem(){
        return origem;
    }
    
    public char getTerminal(){
        return terminal;
    }
    
    public String getDestino(){
        return destino;
    }
}
