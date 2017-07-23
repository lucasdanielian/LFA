package minimizacao;

/**
 * Classe Estado. 
 * 
 * Representa um estado qi do AFD.
 * 
 * @author Raydson
 * @author Lucas
 * @author William
 */
public class Estado {
    /**
     * Atributos:
     * * estado - nome "q0" "q1" etc
     * * isFinal - se é ou não estado final
     * * isInicial - se é ou não estado inicial
     */
    private String estado;
    private boolean isFinal;
    private boolean isInicial;
    
    /**
     * Construtor padrão.
     * Coloca os valores iniciais sem nenhum significado para o programa
     * Valores reais serão postos pelos métodos set
     */
    public Estado(){
        estado = null;
        isInicial = false;
        isFinal = false;
    }
    
    public void setEstado(String estado){
        this.estado = estado;
    }
    
    public void setInicial(){
        this.isInicial = true;
    }
    
    public void setFinal(){
        this.isFinal = true;
    }
    
    public String getEstado(){
        return estado;
    }
    
    public boolean getIsInicial(){
        return isInicial;
    }
    
    public boolean getIsFinal(){
        return isFinal;
    }
}


