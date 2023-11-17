import java.util.ArrayList;
import java.util.List;

import entities.Aluguel;
import entities.Cliente;
import entities.Filme;

public class Main {
    public static void main(String[] args) {
        final String caminhoClientes = "src/resources/clientes.dat";
        final String caminhoFilmes = "src/resources/filmes.dat";
        final String caminhoAluguel = "src/resources/alugueis.dat";
        final String caminhoArquivoLog = "src/resources/log.dat";

        List<Filme> filmes = new ArrayList<>();
        List<Cliente> clientes = new ArrayList<>();
        List<Aluguel> alugueis = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            filmes.add(new Filme((long) i, "Filme " + i, "Diretor " + i, 2000 + i, "Genero " + i, i, null));
            clientes.add(new Cliente("Cliente " + i, "Numero " + i, "Email " + i));
            alugueis.add(new Aluguel((long) i, filmes, clientes.get(i), null, null, null));
        }
        Filme.criaBaseFilmes(filmes, caminhoFilmes);
        // List<Filme> aux = Filme.lerFilmesDat(caminhoFilmes);
        // System.out.println(aux.toString());

        //Filme teste = Filme.buscaSequencial(filmes, (long) 99, caminhoFilmes, caminhoArquivoLog);
        //System.out.println(teste.toString());
        //System.out.println();
        Filme teste2 = Filme.buscaBinaria((long) 99, filmes, caminhoFilmes, caminhoArquivoLog);
        System.out.println(teste2.toString());

    }
}