import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import entities.Aluguel;
import entities.Cliente;
import entities.Filme;
import enums.Status;
import util.ArvoreBinariaVencedores;
import util.MergeSort;
import util.SelecaoNatural;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final String caminhoClientes = "src/resources/clientes.dat";
        final String caminhoFilmes = "src/resources/filmes.dat";
        final String caminhoAluguel = "src/resources/alugueis.dat";
        final String caminhoArquivoLog = "src/resources/log.dat";
        final String particoesFilmes = "src/resources/particao-filme";
        final String particoesClientes = "src/resources/particao-cliente";

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
            System.out.println("9 - Imprimir filmes ordenados pelo MergeSort");
            System.out.println("10 - Imprimir clientes ordenados pelo MergeSort");
            System.out.println("11 - Seleção Natural Filmes");
            System.out.println("12 - Seleção Natural Clientes");
            System.out.println("13 - Arvore Binária de Vencedores Filmes");
            System.out.println("14 - Arvore Binária de Vencedores Clientes");
            System.out.println("15 - Sair");
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
                    Set<Long> idsGerados = new HashSet<>();
                    Random rand = new Random();
                    for (int i = 1; i <= 100; i++) {
                        long id = 1 + rand.nextInt(100);
                        while (idsGerados.contains(id)) {
                            id = 1 + rand.nextInt(100);
                        }
                        idsGerados.add(id);
                        Cliente clienteBase = new Cliente(
                                id,
                                "Cliente" + i,
                                "0" + i,
                                i + "@example.com");

                        Cliente.salvarCliente(clienteBase, caminhoClientes);
                    }

                    Set<Long> idsGeradosFilme = new HashSet<>();
                    for (int j = 1; j <= 500; j++) {
                        long id = 1 + rand.nextInt(500);
                        while (idsGeradosFilme.contains(id)) {
                            id = 1 + rand.nextInt(500);
                        }
                        idsGeradosFilme.add(id);
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
                    System.out.println("Bases de 500 clientes e 500 filmes criadas!");
                    break;

                case 9:

                    ordenaFilmes(filmes, caminhoFilmes, caminhoArquivoLog);
                    System.out.println("Filmes ordenados!");
                    for (Filme filme : filmes) {
                        System.out.println(filme.toString());
                    }
                    break;

                case 10:
                    ordenaClientes(clientes, caminhoClientes, caminhoArquivoLog);
                    System.out.println("Clientes ordenados!");
                    for (Cliente cliente : clientes) {
                        System.out.println(cliente.toString());
                    }
                    break;
                case 11:
                    SelecaoNatural.gerarParticoesOrdenadas(caminhoFilmes, particoesFilmes, caminhoArquivoLog, 100, "Filme");
                     break;
                case 12:
                    SelecaoNatural.gerarParticoesOrdenadas(caminhoClientes, particoesClientes, caminhoArquivoLog, 20, "Cliente");
                    break;
                case 13:
                    arvoreBinariaVencedoresFilme(particoesFilmes, caminhoArquivoLog);
                    break;
                case 14:
                    arvoreBinariaVencedoresCliente(particoesClientes, caminhoArquivoLog);
                    break;
                case 15:
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
                }
            }
        }
        System.out.println("Filme não encontrado!");
    }
    
    public static void ordenaFilmes(List<Filme> filmes, String caminhoFilmes, String caminhoArquivoLog) {
        Long tempoInicial = System.nanoTime();
        MergeSort.sort(filmes, 0, filmes.size() - 1);
        Long tempoFinal = System.nanoTime();
        salvarTempoExecucao(tempoInicial, tempoFinal, caminhoArquivoLog, "MergeSort Filme");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminhoFilmes))) {
            for (Filme filme : filmes) {
                out.writeObject(filme);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ordenaClientes(List<Cliente> clientes, String caminhoClientes, String caminhoArquivoLog){
        Long tempoInicial = System.nanoTime();
        MergeSort.sort(clientes, 0, clientes.size() - 1);
        Long tempoFinal = System.nanoTime();
        salvarTempoExecucao(tempoInicial, tempoFinal, caminhoArquivoLog, "MergeSort Cliente");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminhoClientes))) {
            for (Cliente cliente : clientes) {
                out.writeObject(cliente);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void arvoreBinariaVencedoresFilme(String particaoFilmes, String caminhoArquivoLog) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o número de partições: ");
        int numeroParticoes = scanner.nextInt();
        ArvoreBinariaVencedores<Filme> arvore = new ArvoreBinariaVencedores<>();
        Long tempoInicial = System.nanoTime();
        arvore.preencherArvoreComParticao(particaoFilmes);
        arvore.criarArquivoVencedores(particaoFilmes, numeroParticoes, "src/resources/vencedores-filmes.dat");
        Long tempoFinal = System.nanoTime();
        salvarTempoExecucao(tempoInicial, tempoFinal, caminhoArquivoLog, "Árvore de vencedores de filme");
        arvore.imprimirVencedores("src/resources/vencedores-filmes.dat");
    }

    public static void arvoreBinariaVencedoresCliente(String particaoCliente, String caminhoArquivoLog){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o número de partições: ");
        int numeroParticoes = scanner.nextInt();
        ArvoreBinariaVencedores<Cliente> arvore = new ArvoreBinariaVencedores<>();
        Long tempoInicial = System.nanoTime();
        arvore.preencherArvoreComParticao(particaoCliente);
        arvore.criarArquivoVencedores(particaoCliente, numeroParticoes, "src/resources/vencedores-clientes.dat");
        Long tempoFinal = System.nanoTime();
        salvarTempoExecucao(tempoInicial, tempoFinal, caminhoArquivoLog, "Árvore de vencedores de cliente");
        arvore.imprimirVencedores("src/resources/vencedores-clientes.dat");
    }

    public static void salvarTempoExecucao(Long tempoInicial, Long tempoFinal,
        String caminhoLog, String tipo) {
        double tempoTotal = 0;
        tempoTotal = (tempoFinal - tempoInicial) / 1000000000.0;
        DecimalFormat df = new DecimalFormat("#.##########");
        String tempoTotalString = df.format(tempoTotal);
        String tempoExecucao = "\n---------------\n" + tipo + ": " + "\n" +
                "Contagem de Tempo: " + tempoTotalString + " segundos" + "\n---------------";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoLog, true))) {
            oos.writeObject(tempoExecucao);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

