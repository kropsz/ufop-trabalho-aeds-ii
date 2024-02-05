package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import entities.Filme;
import enums.Status;

public class GerenciadorDeFilmes {
    private RandomAccessFile file;
    private int tamanho;

    public GerenciadorDeFilmes(int tamanho) {
        this.tamanho = tamanho;
        try {
            file = new RandomAccessFile("src/resources/filmesHash.dat", "rw");
            file.setLength(tamanho * 8); // Cada entrada na tabela de hash é um ponteiro de 8 bytes
            for (int i = 0; i < tamanho; i++) {
                file.writeLong(0); // Inicializa cada entrada com um ponteiro nulo
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int hash(Long id) {
        return (int) (id % tamanho);
    }

    public void povoarArquivoComFilmes(int quantidade) {
        for (long i = 1; i <= quantidade; i++) {
            Filme filme = new Filme();
            filme.setId(i);
            filme.setTitulo("Filme " + i);
            filme.setDiretor("Diretor " + i);
            filme.setAnoDeLancamento(2000 + (int) i);
            filme.setGenero("Genero " + i);
            filme.setClassificacao((int) i % 5 + 1); // Classificação entre 1 e 5
            filme.setStatus(i % 2 == 0 ? Status.DISPONIVEL : Status.ALUGADO); // Status alternando entre ATIVO e INATIVO
            inserir(filme);
        }
        System.out.println("Base Criada com sucesso");
    }

    public Filme buscar(Long id) {
        try {
            int index = hash(id);
            file.seek(index * 8);
            long pointer = file.readLong();
            while (pointer != 0) {
                file.seek(pointer);
                long nextPointer = file.readLong();
                long movieId = file.readLong();
                if (movieId == id) {
                    String titulo = file.readUTF();
                    String diretor = file.readUTF();
                    int anoDeLancamento = file.readInt();
                    String genero = file.readUTF();
                    int classificacao = file.readInt();
                    Status status = Status.valueOf(file.readUTF());
                    Filme filme = new Filme();
                    filme.setId(id);
                    filme.setTitulo(titulo);
                    filme.setDiretor(diretor);
                    filme.setAnoDeLancamento(anoDeLancamento);
                    filme.setGenero(genero);
                    filme.setClassificacao(classificacao);
                    filme.setStatus(status);
                    return filme;
                }
                pointer = nextPointer;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void inserir(Filme filme) {
        try {
            int index = hash(filme.getId());
            file.seek(index * 8);
            long pointer = file.readLong();
            file.seek(file.length());
            long newPointer = file.getFilePointer();
            file.writeLong(pointer); // O próximo filme na lista
            file.writeLong(filme.getId());
            file.writeUTF(filme.getTitulo());
            file.writeUTF(filme.getDiretor());
            file.writeInt(filme.getAnoDeLancamento());
            file.writeUTF(filme.getGenero());
            file.writeInt(filme.getClassificacao());
            file.writeUTF(filme.getStatus().toString());
            file.seek(index * 8);
            file.writeLong(newPointer); // Atualiza o ponteiro na tabela de hash
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void remover(Long id) {
        try {
            int index = hash(id);
            file.seek(index * 8);
            long pointer = file.readLong();
            long previousPointer = -1;
            while (pointer != 0) {
                file.seek(pointer);
                long nextPointer = file.readLong();
                long movieId = file.readLong();
                if (movieId == id) {
                    if (previousPointer == -1) { // O filme está no início da lista
                        file.seek(index * 8);
                        file.writeLong(nextPointer);
                    } else { // O filme está no meio ou no final da lista
                        file.seek(previousPointer);
                        file.writeLong(nextPointer);
                    }
                    break;
                }
                previousPointer = pointer;
                pointer = nextPointer;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Filme> lerTodosOsFilmes() {
    List<Filme> filmes = new ArrayList<>();
    try {
        file.seek(0);
        while (file.getFilePointer() < file.length()) {
            long pointer = file.readLong();
            while (pointer != 0) {
                file.seek(pointer);
                long nextPointer = file.readLong();
                long id = file.readLong();
                String titulo = file.readUTF();
                String diretor = file.readUTF();
                int anoDeLancamento = file.readInt();
                String genero = file.readUTF();
                int classificacao = file.readInt();
                String statusString = file.readUTF();
                Status status;
                try {
                    status = Status.valueOf(statusString);
                } catch (IllegalArgumentException e) {
                    status = Status.DISPONIVEL; 
                 }
                Filme filme = new Filme();
                filme.setId(id);
                filme.setTitulo(titulo);
                filme.setDiretor(diretor);
                filme.setAnoDeLancamento(anoDeLancamento);
                filme.setGenero(genero);
                filme.setClassificacao(classificacao);
                filme.setStatus(status);
                filmes.add(filme);
                pointer = nextPointer;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return filmes;
}
}