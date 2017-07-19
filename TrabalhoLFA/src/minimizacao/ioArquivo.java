package minimizacao;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author raydson
 */
public class ioArquivo {

    public ioArquivo() {
        
    }
    public BufferedReader lerArquivo(String nomeArquivo) throws FileNotFoundException{
        BufferedReader automato = new BufferedReader(new FileReader(nomeArquivo + ".txt"));
    return automato;
    }
    
    public void escreverArquivo(String nomeArquivo) throws IOException{
        FileWriter automato = new FileWriter(nomeArquivo + ".txt");
    }
}
