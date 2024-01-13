import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import entities.Aluguel;
import entities.Cliente;
import entities.Filme;
import enums.Status;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final String caminhoClientes = "src/resources/clientes.dat";
        final String caminhoFilmes = "src/resources/filmes.dat";
        final String caminhoAluguel = "src/resources/alugueis.dat";
        final String caminhoArquivoLog = "src/resources/log.dat";

        List<Filme> filmes = new ArrayList<>();
        List<Cliente> clientes = new ArrayList<>();
        List<Aluguel> alugueis = new ArrayList<>();

        while (true) {
            filmes = Filme.lerFilmes(caminhoFilmes);
            clientes = Cliente.lerClientes(caminhoClientes);
            alugueis = Aluguel.lerAlugueis(caminhoAluguel);
            System.out.println("----------------------------------------------");
            System.out.println("--   Bem-Vindo a Locadora de filmes  --");
            System.out.println("----------------------------------------------");
            System.out.println();
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Cadastrar Filme");
            System.out.println("2 - Cadastrar Cliente");
            System.out.println("3 - Alugar Filme");
            System.out.println("4 - Devolver Filme");
            System.out.println("5 - Buscar Filme");
            System.out.println("6 - Buscar Cliente");
            System.out.println("7 - Buscar Aluguel");
            System.out.println("8 - Preencher Base inicial");
            System.out.println("9 - Sair");
            System.out.println();
            System.out.println("Digite a opção desejada: ");
            System.out.println();
            System.out.println("------------------------------------------------");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1:
                    criaFilme(caminhoFilmes);
                    break;
                case 2:
                    criaCliente(caminhoClientes);
                    break;
                case 3:
                    criaAluguel(caminhoAluguel, caminhoFilmes, caminhoArquivoLog, caminhoClientes, clientes, filmes,
                            alugueis);
                    break;
                case 4:
                    devolveAluguel(caminhoAluguel, caminhoFilmes, caminhoArquivoLog, caminhoClientes, clientes, filmes,
                            alugueis);
                    break;
                case 5:
                    buscaFilme(caminhoArquivoLog, filmes);
                    break;
                case 6:
                    buscaCliente(caminhoArquivoLog, clientes);
                    break;
                case 7:
                    buscaAluguel(caminhoArquivoLog, alugueis);
                    break;
                case 8:
                    Random rand = new Random();
                    for (int i = 1; i <= 1000; i++) {
                        long id = 1 + rand.nextInt(1000); 
                        Cliente clienteBase = new Cliente(
                                id,
                                "Cliente" + i,
                                "0" + i,
                                i + "@example.com");

                        Cliente.salvarCliente(clienteBase, caminhoClientes);
                    }

                    // CRIA BASE DE 500 FILMES
                    for (int j = 1; j <= 500; j++) {
                        long id = 1 + rand.nextInt(500); 
                        Filme filmeBase = new Filme(
                                id,
                                "Filme" + j,
                                "Diretor" + j,
                                2000 + j,
                                "Gênero" + j,
                                j % 5 + 1,
                                Status.DISPONIVEL);

                        Filme.salvaFilme(filmeBase, caminhoFilmes);
                    }
                    System.out.println("Bases de 1000 clientes e 500 filmes criadas!");
                    break;

                case 9:
                    System.out.println("Saindo...");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        }

    }

    public static void criaFilme(String caminhoFilmes) {
        Scanner scanner = new Scanner(System.in);
        List<Filme> filmes = new ArrayList<>();
        filmes = Filme.lerFilmes(caminhoFilmes);
        Long idFilme = (long) 0;
        for (int i = 0; i < filmes.size(); i++) {
            if (filmes.get(i).getId() > idFilme) {
                idFilme = filmes.get(i).getId();
            }
        }
        idFilme++;
        System.out.println("Digite o nome do filme: ");
        String nomeFilme = scanner.next();
        scanner.nextLine();
        System.out.println("Digite o nome do diretor: ");
        String diretor = scanner.next();
        scanner.nextLine();
        System.out.println("Digite o ano de lançamento: ");
        int anoLancamento = scanner.nextInt();
        System.out.println("Digite o gênero: ");
        String genero = scanner.next();
        System.out.println("Digite a classificação indicativa: ");
        int classificacaoIndicativa = scanner.nextInt();
        Filme filme = new Filme(idFilme, nomeFilme, diretor, anoLancamento, genero, classificacaoIndicativa,
                Status.DISPONIVEL);
        Filme.salvaFilme(filme, caminhoFilmes);
        System.out.println(filme.toString());

    }

    public static void criaCliente(String caminhoClientes) {
        Scanner scanner = new Scanner(System.in);
        List<Cliente> clientes = new ArrayList<>();
        clientes = Cliente.lerClientes(caminhoClientes);
        Long idCliente = (long) 0;
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() > idCliente) {
                idCliente = clientes.get(i).getId();
            }
        }
        idCliente++;
        System.out.println("Digite o nome do cliente: ");
        String nomeCliente = scanner.next();
        scanner.nextLine();
        System.out.println("Digite o telefone: ");
        String telefone = scanner.next();
        System.out.println("Digite o email: ");
        String email = scanner.next();
        Cliente cliente = new Cliente(idCliente, nomeCliente, telefone, email);
        Cliente.salvarCliente(cliente, caminhoClientes);
        System.out.println(cliente.toString());

    }

    public static void criaAluguel(String caminhoAluguel, String caminhoFilmes, String caminhoArquivoLog,
            String caminhoClientes, List<Cliente> clientes, List<Filme> filmes, List<Aluguel> alugueis) {
        Scanner scanner = new Scanner(System.in);
        Long idNewAluguel = (long) 0;

        for (int i = 0; i < alugueis.size(); i++) {
            if (alugueis.get(i).getId() > idNewAluguel) {
                idNewAluguel = alugueis.get(i).getId();
            }
        }

        idNewAluguel++;
        List<Filme> filmesAluguel = new ArrayList<>();

        while (true) {
            Long idAux = (long) 0;

            System.out.println("Informe o filme que deseja alugar: ");
            String nomeFilme = scanner.next();

            for (int i = 0; i < filmes.size(); i++) {
                if (filmes.get(i).getTitulo().equals(nomeFilme)) {
                    idAux = filmes.get(i).getId();
                }
            }

            Filme filmeAluguel = Filme.buscaBinaria(idAux, filmes, caminhoArquivoLog);

            if (filmeAluguel == null) {
                System.out.println("Filme não encontrado!");
                break;
            }

            if (filmeAluguel.getStatus() == Status.ALUGADO) {
                System.out.println("Filme indisponível!");
            } else {
                filmeAluguel.setStatus(Status.ALUGADO);
                Filme.atualizarFilme(filmeAluguel, caminhoFilmes);
                filmesAluguel.add(filmeAluguel);
                scanner.nextLine();
                System.out.println("Deseja alugar outro filme? (S/N)");
                String opcaoAlugar = scanner.next();

                if (opcaoAlugar.equals("N")) {
                    break; 
                }
            }
        }

        System.out.println("Digite o email do cliente: ");
        String emailBuscado = scanner.next();
        Long idClienteBusca = (long) 0;

        try {
            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getEmail().equals(emailBuscado)) {
                    idClienteBusca = clientes.get(i).getId();
                }
            }
        } catch (Exception e) {
            System.out.println("Cliente não encontrado!");
        }

        Cliente clienteAluguel = Cliente.buscaBinaria(idClienteBusca, clientes, caminhoArquivoLog);
        Date dataAluguel = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Date dataDevolucao = Date.from(LocalDateTime.now().plusDays(7).atZone(ZoneId.systemDefault()).toInstant());
        Aluguel aluguel = new Aluguel(idNewAluguel, filmesAluguel, clienteAluguel, dataAluguel, dataDevolucao, null);
        Aluguel.salvaAluguelNoArquivo(aluguel, caminhoAluguel);
        System.out.println(aluguel.toString());
    }

    public static void devolveAluguel(String caminhoAluguel, String caminhoFilmes, String caminhoArquivoLog,
            String caminhoClientes, List<Cliente> clientes, List<Filme> filmes, List<Aluguel> alugueis) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o id do aluguel: ");
        Long idAluguel = scanner.nextLong();
        Aluguel aluguelDevolvido = Aluguel.buscaBinaria(idAluguel, alugueis, caminhoArquivoLog);
        if (aluguelDevolvido == null) {
            System.out.println("Aluguel não encontrado!");
        } else {
            for (Filme filmeDevolvido : aluguelDevolvido.getFilmes()) {
                filmeDevolvido.setStatus(Status.DISPONIVEL);
                Filme.atualizarFilme(filmeDevolvido, caminhoFilmes);
            }

            System.out.println("Filmes devolvidos e agora estão disponíveis.");
        }
    }

    public static void buscaCliente(String caminhoArquivoLog, List<Cliente> clientes) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o email do cliente: ");
        String emailBuscado = scanner.next();
        Long idClienteBusca = (long) 0;
        try {
            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getEmail().equals(emailBuscado)) {
                    idClienteBusca = clientes.get(i).getId();
                }
            }
        } catch (Exception e) {
            System.out.println("Cliente não encontrado!");

        }
        Cliente clienteBusca = Cliente.buscaBinaria(idClienteBusca, clientes, caminhoArquivoLog);
        Cliente.buscaSequencial(clientes, idClienteBusca, caminhoArquivoLog);
        System.out.println(clienteBusca.toString());
    }

    public static void buscaAluguel(String caminhoArquivoLog, List<Aluguel> alugueis) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o id do aluguel: ");
        Long idAluguelBusca = scanner.nextLong();
        Aluguel aluguelBusca = Aluguel.buscaBinaria(idAluguelBusca, alugueis, caminhoArquivoLog);
        Aluguel.buscaSequencial(alugueis, idAluguelBusca, caminhoArquivoLog);
        if (aluguelBusca == null) {
            System.out.println("Aluguel não encontrado!");

        } else {
            System.out.println(aluguelBusca.toString());
        }

    }

    public static void buscaFilme(String caminhoArquivoLog, List<Filme> filmes) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do filme: ");
        String nomeFilmeBusca = scanner.next();
        Long idFilmeBusca = (long) 0;
        for (int i = 0; i < filmes.size(); i++) {
            if (filmes.get(i).getTitulo().equals(nomeFilmeBusca)) {
                idFilmeBusca = filmes.get(i).getId();
                Filme filmeBusca = Filme.buscaBinaria(idFilmeBusca, filmes, caminhoArquivoLog);
                Filme.buscaSequencial(filmes, idFilmeBusca, caminhoArquivoLog);
                if (filmeBusca != null) {
                    System.out.println("Filme encontrado: " + "\n" + filmeBusca.toString());
                    break;
                } else {
                    System.out.println("Filme não encontrado!");
                }
            }
            else{
                System.out.println("Filme não encontrado!");
                break;
            }
        }
    }
}
