package entities;

import enums.Status;

public class Filme {

    private Long id;
    private String titulo;
    private String diretor;
    private Integer anoDeLancamento;
    private String genero;
    private Integer classificacao;

    public Status status;

    public Filme(Long id, String titulo, String diretor, Integer anoDeLancamento, String genero, Integer classificacao, Status status) {
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

    public void addNovoFilme(Filme filme){

    }

    public void atualizaFilme(Filme filme){

    }

    public void deletarFilme(Long id){

    }

    public void buscarFilme(Long id){

    }

    public void statusFilme(Filme filme){

    }
}
