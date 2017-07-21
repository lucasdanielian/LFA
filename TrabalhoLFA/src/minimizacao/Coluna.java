/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minimizacao;

import java.util.ArrayList;

/**
 *
 * @author aluno
 */
public class Coluna {
    public Indice indice;
    public boolean igual;
    public ArrayList<Indice> propagacao;
    public String motivo;
    
    public boolean marcado;
    
    public Coluna(Indice indice, boolean igual, ArrayList<Indice> propagacao, String motivo){
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

