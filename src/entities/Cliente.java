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

public class Cliente implements Serializable {

    private Long id;
    private String nome;
    private String numero;
    private String email;

    public Cliente(Long id, String nome, String numero, String email) {
        this.id = id;
        this.nome = nome;
        this.numero = numero;
        this.email = email;
    }

    public Cliente() {
    }

     public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void salvarCliente(Cliente cliente, String caminho) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho, true))) {
            oos.writeObject(cliente);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void criaBaseClientes(List<Cliente> clientes, String caminho) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            for (Cliente cliente : clientes) {
                oos.writeObject(cliente);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> lerClientes(String caminho) {
        List<Cliente> clientes = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            Cliente cliente;
            while ((cliente = (Cliente) ois.readObject()) != null) {
                clientes.add(cliente);
            }
        } catch (EOFException e) {

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public static Cliente buscaSequencial(List<Cliente> clientes, Long id, String caminho, String caminhoLog) {
        Long tempoInicial = System.nanoTime();
        Integer contador = 0;
        for (Cliente cliente : clientes) {
            contador++;
            if (cliente.getId().equals(id)) {
                Long tempoFinal = System.nanoTime();
                salvarTempoExecucao(tempoInicial, tempoFinal, contador, caminho, caminhoLog);
                return cliente;
            }
        }
        return null;
    }

    public static Cliente buscaBinaria(Long id, List<Cliente> clientes, String caminho, String caminhoLog) {
        ordenaLista(clientes);
        Long tempoInicial = System.nanoTime();
        int contador = 0;
        Cliente cliente = null;

        int inicio = 0;
        int fim = clientes.size() - 1;

        while (inicio <= fim && (cliente == null || !cliente.getId().equals(id))) {
            int meio = (inicio + fim) / 2;
            cliente = clientes.get(meio);

            contador++;

            if (cliente != null) {
                Long idCliente = cliente.getId();
                if (idCliente.compareTo(id) > 0) {
                    fim = meio - 1;
                } else {
                    inicio = meio + 1;
                }
            }
        }

        Long tempoFinal = System.nanoTime();
        salvarTempoExecucao(tempoInicial, tempoFinal, contador, caminho, caminhoLog);

        return cliente;
    }

    private static void ordenaLista(List<Cliente> clientes) {
        clientes.sort((cliente1, cliente2) -> cliente1.getId().compareTo(cliente2.getId()));
    }

    @Override
    public String toString() {
        return "Cliente id=" + id + ", Nome=" + nome + ", telefone="
                + numero + ", email=" + email;
    }

    public static void salvarTempoExecucao(Long tempoInicial, Long tempoFinal, int contador, String caminho,
        String caminhoLog) {
        double tempoTotal = 0;
        tempoTotal = (tempoFinal - tempoInicial) / 1000000000.0;
        DecimalFormat df = new DecimalFormat("#.#########");
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
    
    // public void atualizarCliente(Cliente cliente) {
    // }

    // public void deletarCliente(String email) {
    // }
}
