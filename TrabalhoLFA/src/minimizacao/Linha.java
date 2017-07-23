package minimizacao;

import java.util.ArrayList;

/**
 * Classe Linha.
 * 
 * Representa uma linha da tabela de minimização ("matriz").
 * 
 * @author Raydson
 * @author Lucas
 * @author William
 */
public class Linha {
    /**
     * Cada atributo aqui representa uma coluna nessa linha:
     * * indice - pares [i,j] dos estados
     * * igual - D[i,j], se os estados são iguais (true) ou não (false)
     * * propagacao - lista com os índices a propagar distinção caso estados não sejam iguais
     * * motivo - motivo pelo qual os estados i e j são diferentes
     * 
     */
    public Indice indice;
    public boolean igual;
    public ArrayList<Indice> propagacao;
    public String motivo;
    /**
     * Atributo marcado - indica que o algoritmo tem que pular essa linha se a
     * flag for true, marcação feita quando é detectado que i e j não são equivalente
     * no início da minimização (motivo final/não-final)
     */
    public boolean marcado;
    
    /**
     * Construtor, inicializa os valores de cada coluna na linha da matriz
     * @param indice - par de estados [i,j]
     * @param igual - D[i,j] se é igual (true) ou não (false)
     * @param propagacao - onde propagar se forem diferentes
     * @param motivo - final/não-final, propagação[i,j], simbolo-lido[i,j] ou -
     */
    public Linha(Indice indice, boolean igual, ArrayList<Indice> propagacao, String motivo){
        this.indice = indice;
        this.igual = igual;
        this.propagacao = propagacao;
        this.motivo = motivo;
        this.marcado = false;
    }
    
    public Indice getIndice(){
        return this.indice;
    }
    
    public boolean getIgual(){
        return this.igual;
    }
    
    public ArrayList<Indice> getPropagacao(){
        return this.propagacao;
    }
    
    public String getMotivo(){
        return motivo;
    }
    
    public boolean getMarcado() {
        return this.marcado;
    }
    
    public void setMotivo(String motivo){
        this.motivo = motivo;
    }
    
    public void setNotIgual(){
        this.igual = false;
    }
    
    public void setMarcado() {
        this.marcado = true;
    }
}

