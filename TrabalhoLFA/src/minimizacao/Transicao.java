/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minimizacao;

/**
 *
 * @author Lucas
 */
public class Transicao {
    String origem;
    String terminal;
    String destino;
    
    public Transicao(){
        origem = null;
        terminal = null;
        destino = null;
    }
    
    public void setOrigem(String origem){
        this.origem = origem;
    }
    
    public void setTerminal(String terminal){
        this.terminal = terminal;
    }
    
    public void setDestino(String destino){
        this.destino = destino;
    }
    
    public String getOrigem(){
        return origem;
    }
    
    public String getTerminal(){
        return terminal;
    }
    
    public String getDestino(){
        return destino;
    }
    
    
}
