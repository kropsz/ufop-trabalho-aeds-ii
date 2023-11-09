package entities;

import java.util.Date;
import java.util.List;

public class Aluguel {

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

    public void registarAluguel(Aluguel aluguel){

    }

    public void atualizarAluguel(Aluguel aluguel){

    }

    public void calcularValorTotal(Aluguel aluguel){

    }

    public void buscarAluguel(Long id){

    }

    public void buscarTodosAlugeis(){

    }
}
