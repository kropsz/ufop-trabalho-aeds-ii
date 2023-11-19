package entities;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Aluguel implements Serializable {

    private Long id;
    private List<Filme> filmes;
    private Cliente cliente;
    private Date dataAluguel;
    private Date dataDevolucao;
    private Double valor;

    public Aluguel(Long id, List<Filme> filmes, Cliente cliente, Date dataAluguel, Date dataDevolucao, Double valor) {
        this.id = id;
        this.filmes = filmes;
        this.cliente = cliente;
        this.dataAluguel = dataAluguel;
        this.dataDevolucao = dataDevolucao;
        this.valor = setValor();
    }

    public Aluguel() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(List<Filme> filmes) {
        this.filmes = filmes;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getDataAluguel() {
        return dataAluguel;
    }

    public void setDataAluguel(Date dataAluguel) {
        this.dataAluguel = dataAluguel;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Double getValor() {
        return valor;
    }

    public double setValor() {
        int qtdFilmes = filmes.size();
        int precoFilme = 10;
        Double valor = 0.0;
        valor += (qtdFilmes * precoFilme);
        return valor;
    }

    public static List<Aluguel> lerAlugueis(String caminho) {
        List<Aluguel> alugueis = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            Aluguel aluguel;
            while ((aluguel = (Aluguel) ois.readObject()) != null) {
                alugueis.add(aluguel);
            }
        } catch (EOFException e) {

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return alugueis;
    }

    public static void salvaAluguelNoArquivo(Aluguel aluguel, String caminho) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            oos.writeObject(aluguel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Aluguel buscaSequencial(List<Aluguel> alugueis, Long id, String caminhoLog) {
        Long tempoInicial = System.nanoTime();
        Integer contador = 0;
        for (Aluguel aluguel : alugueis) {
            contador++;
            if (aluguel.getId().equals(id)) {
                Long tempoFinal = System.nanoTime();
                salvarTempoExecucao(tempoInicial, tempoFinal, contador, caminhoLog, "Sequencial");
                return aluguel;
            }
        }

        return null;
    }

    public static Aluguel buscaBinaria(long chave, List<Aluguel> alugueis, String caminhoLog) {
        ordenaLista(alugueis);
        Long tempoInicial = System.nanoTime();
        int contador = 0;
        Aluguel aluguel = null;

        int inicio = 0;
        int fim = alugueis.size() - 1;

        while (inicio <= fim && (aluguel == null || aluguel.getId() != chave)) {
            int meio = (inicio + fim) / 2;
            aluguel = alugueis.get(meio);

            contador++;

            if (aluguel != null) {
                Long id = aluguel.getId();
                if (id > chave) {
                    fim = meio - 1;
                } else {
                    inicio = meio + 1;
                }
            }
        }

        if (aluguel != null && aluguel.getId() == chave) {
            Long tempoFinal = System.nanoTime();
            salvarTempoExecucao(tempoInicial, tempoFinal, contador, caminhoLog, "Binária");
            return aluguel;
        } else {
            return null;
        }
    }

    private static void ordenaLista(List<Aluguel> alugueis) {
        alugueis.sort((a1, a2) -> a1.getId().compareTo(a2.getId()));
    }

    public static void salvarTempoExecucao(Long tempoInicial, Long tempoFinal, int contador,
        String caminhoLog, String tipo) {
        double tempoTotal = 0;
        tempoTotal = (tempoFinal - tempoInicial) / 1000000000.0;
        DecimalFormat df = new DecimalFormat("#.#########");
        String tempoTotalString = df.format(tempoTotal);
        String contadorString = Integer.toString(contador);
        String tempoExecucao = "Busca " + tipo + ": " + "\n" + "Comparações: " + contadorString + "\n" +
                "Contagem de Tempo: " + tempoTotalString + " segundos" + "\n";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoLog, true))) {
            oos.writeObject(tempoExecucao);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StringBuilder nomeFilmes(){
        StringBuilder nomesFilmes = new StringBuilder(" ");
        for(int i = 0; i < this.filmes.size(); i++){
            nomesFilmes.append(filmes.get(i).getTitulo()).append(", ");
        }
        nomesFilmes.delete(nomesFilmes.length() - 2, nomesFilmes.length());

        return  nomesFilmes;
    }
    
    @Override
    public String toString() {
        return  "Aluguel ID: " + id +"\n" +
                "Cliente: " + cliente.getNome() + "\n" +
                "Filmes: " + nomeFilmes() + "\n" +
                "Data do Aluguel: " + dataAluguel + "\n" +
                "Data de devolução: " + dataDevolucao + "\n" +
                "Valor: " + valor;
    }
}