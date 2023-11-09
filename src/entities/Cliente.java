package entities;

public class Cliente {

    private String nome;
    private String numero;

    private String email;

    public Cliente(String nome, String numero, String email) {
        this.nome = nome;
        this.numero = numero;
        this.email = email;
    }

    public Cliente() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void adicionarCliente(Cliente cliente){

    }

    public void atualizarCliente(Cliente cliente){

    }

    public void deletarCliente(String email){

    }

    public void buscarCliente(String email){

    }



}
