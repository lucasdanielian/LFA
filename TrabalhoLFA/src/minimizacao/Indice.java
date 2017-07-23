package minimizacao;

/**
 * Classe Indice.
 
 * Representa um par [i,j] de estados, que é utilizado na primeira coluna na
 * tabela de minimização
 * 
 * @author Raydson
 * @author Lucas
 * @author William
 * 
 */
public class Indice {
    /**
     * Atributos:
     * * i, j - número do estado qi e qj
     * * linhaTabela - indexador para a linha na tabela que está o índice [i,j]
     * * cont - estático, para fazer indexação
     */
    public int i;
    public int j;
    public int linhaTabela;
    public static int cont = 0;
    
    /**
     * Construtor.
     * 
     * @param i - estado qi
     * @param j - estado qj
     */
    public Indice(int i, int j) {
            this.i = i;
            this.j = j;
            this.linhaTabela = cont;
            cont++;
    }
    
    /**
     * Construtor auxiliar.
     * @param i - estado qi
     * @param j - estado qj
     * @param noIncrement - para indicar que é auxiliar, não modifica a indexação
     */
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



