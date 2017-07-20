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
    int origem;
    char terminal;
    int destino;
    
    
    public Transicao(){
        origem = -1;
        terminal = 'N';
        destino = -1;
}
    
    public void setOrigem(int origem){
        this.origem = origem;
    }
    
    public void setTerminal(char terminal){
        this.terminal = terminal;
    }
    
    public void setDestino(int destino){
        this.destino = destino;
    }
    
    public int getOrigem(){
        return origem;
    }
    
    public char getTerminal(){
        return terminal;
    }
    
    public int getDestino(){
        return destino;
    }
}
