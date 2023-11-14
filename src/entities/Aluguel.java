package entities;

import util.CalculaTempoECusto;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Aluguel implements CalculaTempoECusto {

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
        this.valor = valor;
    }

    public Aluguel(){

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

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void atualizarAluguel(Aluguel aluguel){

    }

    public void calcularValorTotal(Aluguel aluguel){

    }

    private static List<Aluguel> carregarAlugueisDoArquivoTexto(String caminhoArquivo) {
        List<Aluguel> alugueis = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] atributos = linha.split(";");


                Aluguel aluguel = new Aluguel();
                aluguel.setId(Long.parseLong(atributos[0]));

                alugueis.add(aluguel);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return alugueis;
    }

    public Aluguel buscaSequencial(List<Aluguel> alugueis, Long id, String caminho) {
        Long tempoInicial = System.currentTimeMillis();
        Integer contador = 0;
        for (Aluguel aluguel : alugueis) {
            contador ++;
            if (aluguel.getId().equals(id)) {
                Long tempoFinal = System.currentTimeMillis();
                salvarTempoExecucao(tempoInicial, tempoFinal, contador, caminho);
                return aluguel;
            }
        }

        return null;
    }

    public void salvaAluguelNoArquivo(Aluguel aluguel, String caminho){
        try (BufferedWriter br = new BufferedWriter(new FileWriter(caminho))){
            String linha = aluguel.getId() + ";" +
                           aluguel.getFilmes() + ";" +
                           aluguel.getDataAluguel() + ";" +
                           aluguel.getDataDevolucao() + ";" +
                           aluguel.getValor();
            br.write(linha);
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

     public Aluguel buscaBinaria(long chave, List<Aluguel> alugueis, String caminho) {
        Long tempoInicial = System.currentTimeMillis();
        Integer contador = 0;
        Aluguel aluguel = null;

        Integer inicio = 0;
        Integer fim = alugueis.size() - 1;

        while (inicio <= fim && (aluguel == null || aluguel.getId() != chave)) {
            Integer meio = (inicio + fim) / 2;
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
            Long tempoFinal = System.currentTimeMillis();
            salvarTempoExecucao(tempoInicial, tempoFinal, contador, caminho);
            return aluguel;
        } else {
            return null;
        }
    }

    @Override
    public void salvarTempoExecucao(Long tempoInicial, Long tempoFinal, int contador, String caminho) {
        Long tempoTotal = tempoFinal - tempoInicial;
        try (BufferedWriter br = new BufferedWriter(new FileWriter(caminho))){
            String linha = "Tempo total: " + tempoTotal + "\n" +
                           "Numero de comparações: " + contador;
            br.write(linha);

    } catch (IOException e) {
            e.printStackTrace();
    }
    }

   
}
