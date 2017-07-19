/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minimizacao;

/**
 *
 * @author aluno
 */
public class Estado {
    private String estado;
    private boolean isFinal;
    private boolean isInicial;
    
    public Estado(){
        estado = null;
        isInicial = false;
        isFinal = false;
    }
    
    public void setEstado(String estado){
        this.estado = estado;
    }
    
    public void setInicial(){
        this.isFinal = true;
    }
    
    public void setFinal(){
        this.isFinal = true;
    }
    
    public String getEstado(){
        return estado;
    }
}


