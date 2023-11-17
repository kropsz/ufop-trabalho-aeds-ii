package entities;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import enums.Status;

public class Filme implements Serializable {

    private Long id;
    private String titulo;
    private String diretor;
    private Integer anoDeLancamento;
    private String genero;
    private Integer classificacao;
    public Status status;

    public Filme(Long id, String titulo, String diretor, Integer anoDeLancamento, String genero, Integer classificacao,
            Status status) {
        this.id = id;
        this.titulo = titulo;
        this.diretor = diretor;
        this.anoDeLancamento = anoDeLancamento;
        this.genero = genero;
        this.classificacao = classificacao;
        this.status = Status.DISPONIVEL;
    }

    public Filme() {

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public Integer getAnoDeLancamento() {
        return anoDeLancamento;
    }

    public void setAnoDeLancamento(Integer anoDeLancamento) {
        this.anoDeLancamento = anoDeLancamento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Integer classificacao) {
        this.classificacao = classificacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addNovoFilme(Filme filme, String caminho) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            oos.writeObject(filme);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void criaBaseFilmes(List<Filme> filmes, String caminho) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            for (Filme filme : filmes) {
                oos.writeObject(filme);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Filme buscarFilme(Long id, String caminho) {
        Filme filme = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            while ((filme = (Filme) ois.readObject()) != null) {
                if (filme.getId().equals(id)) {
                    return filme;
                }
            }
        } catch (EOFException e) {

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Filme> lerFilmes(String caminho) {
        List<Filme> filmes = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            Filme filme;
            while ((filme = (Filme) ois.readObject()) != null) {
                filmes.add(filme);
            }
        } catch (EOFException e) {

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return filmes;
    }

    public static Filme buscaSequencial(List<Filme> filmes, Long id, String caminho, String caminhoLog) {
        Long tempoInicial = System.nanoTime();
        Integer contador = 0;
        for (Filme filme : filmes) {
            contador++;
            if (filme.getId().equals(id)) {
                Long tempoFinal = System.nanoTime();
                salvarTempoExecucao(tempoInicial, tempoFinal, contador, caminho, caminhoLog);
                return filme;
            }
        }

        return null;
    }

    public static Filme buscaBinaria(long chave, List<Filme> filmes, String caminho, String caminhoLog) {
        ordenaLista(filmes);
        Long tempoInicial = System.currentTimeMillis();
        int contador = 0;
        Filme filme = null;

        int inicio = 0;
        int fim = filmes.size() - 1;

        while (inicio <= fim && (filme == null || filme.getId() != chave)) {
            int meio = (inicio + fim) / 2;
            filme = filmes.get(meio);

            contador++;

            if (filme != null) {
                Long id = filme.getId();
                if (id > chave) {
                    fim = meio - 1;
                } else {
                    inicio = meio + 1;
                }
            }
        }

        Long tempoFinal = System.currentTimeMillis();
        salvarTempoExecucao(tempoInicial, tempoFinal, contador, caminho, caminhoLog);

        return filme;
    }

    private static void ordenaLista(List<Filme> filmes) {
        filmes.sort((filme1, filme2) -> filme1.getId().compareTo(filme2.getId()));
    }

    @Override
    public String toString() {
        return "Filme [anoDeLancamento=" + anoDeLancamento + ", classificacao=" + classificacao + ", diretor="
                + diretor + ", genero=" + genero + ", id=" + id + ", status=" + status + ", titulo=" + titulo + "]";
    }

    public static void salvarTempoExecucao(Long tempoInicial, Long tempoFinal, int contador, String caminho,
        String caminhoLog) {
        double tempoTotal = 0;
        tempoTotal = (tempoFinal - tempoInicial) / 1000000000.0;
        DecimalFormat df = new DecimalFormat("#.##########");
        String tempoTotalString = df.format(tempoTotal);
        String contadorString = Integer.toString(contador);
        String tempoExecucao = "Comparações: " + contadorString + "\n" +
                               "Contagem de Tempo: " + tempoTotalString + " segundos" + "\n";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoLog, true))) {
            oos.writeObject(tempoExecucao);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public void statusFilme(Filme filme) {
    // }
}