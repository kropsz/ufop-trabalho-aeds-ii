package util;

import java.util.*;
import java.io.*;

public class ArvoreBinariaVencedores<T extends Comparable<T> & Serializable> {
    private PriorityQueue<T> arvore;

    public ArvoreBinariaVencedores() {
        this.arvore = new PriorityQueue<>();
    }

    public void inserir(T elemento) {
        arvore.add(elemento);
    }

    public T remover() {
        return arvore.poll();
    }

    public T vencedor() {
        return arvore.peek();
    }

    public boolean vazia() {
        return arvore.isEmpty();
    }

    public void preencherArvoreComParticao(String nomeArquivo) { //le os elementos do arquivo e coloca na arvore   
        List<T> elementos = lerDadosDaParticao(nomeArquivo);
        for (T elemento : elementos) {
            inserir(elemento);
        }
    }

    private List<T> lerDadosDaParticao(String nomeArquivo) { //le os dados de uma particao e retorna uma lista com os elementos	
        List<T> elementos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            while (true) {
                try {
                    T elemento = (T) ois.readObject();
                    elementos.add(elemento);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return elementos;
    }

    public void criarArquivoVencedores(String diretorio, int numeroParticoes, String nomeArquivoVencedores) { //cria o arquivo com os vencedores
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivoVencedores))) {
            for (int i = 0; i < numeroParticoes; i++) {
                preencherArvoreComParticao(diretorio + "/particao" + i + ".dat");
            }

            while (!arvore.isEmpty()) {
                T vencedor = vencedor();
                oos.writeObject(vencedor);
                remover();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void imprimirVencedores(String nomeArquivoVencedores) { //imprime 
        List<T> vencedores = lerDadosDaParticao(nomeArquivoVencedores);
        for (T vencedor : vencedores) {
            System.out.println(vencedor);
        }
    }

}