package executavel;

import minimizacao.Automato;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author raydson
 */
public class principalLFA {
    public static void main(String[] args){
        Automato automato = new Automato("lfa");
        //System.out.println("vai imprimir");
        //automato.imprimePraMim();
        automato.formatarTabela();
        automato.minimizar();
    }
}