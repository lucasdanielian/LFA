package executavel;

import minimizacao.Automato;

/**
 * Classe principal para executar a minimização do AFD.
 * 
 * Cria um novo objeto autômato, que será construído a partir do arquivo de
 * entrada especificado.
 * Por fim, aplica o algoritmo de minimização, escreve a tabela final em um
 * arquivo e o novo autômato em outro arquivo.
 * 
 * @author Raydson
 * @author Lucas
 * @author William
 */
public class principalLFA {
    public static void main(String[] args){
        Automato automato = new Automato(args[0]);
        automato.minimizar(args[1], args[2]);
    }
}
