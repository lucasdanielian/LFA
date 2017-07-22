/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minimizacao;

/**
 *
 * @author raydson
 */
class Transicao {
    String origem;
    char terminal;
    String destino;
    
    
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
