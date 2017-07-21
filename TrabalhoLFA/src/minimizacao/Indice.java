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
public class Indice {
    public int i;
    public int j;
    public int linhaTabela;
    public static int cont = 0;

    public Indice(int i, int j) {
            this.i = i;
            this.j = j;
            this.linhaTabela = cont;
            cont++;
    }
    
    public Indice(int i, int j, boolean noIncrement) {
        // para nao afetar contagem, indice auxiliar
            this.i = i;
            this.j = j;
            this.linhaTabela = -1;
    }
    
    public void setIndice(int iNovo, int jNovo){
        this.i = iNovo;
        this.j = jNovo;
    }
    
    public String toString(){
        String nova = "[" + i + ","+ j +"]";
        return nova;
    }
}



