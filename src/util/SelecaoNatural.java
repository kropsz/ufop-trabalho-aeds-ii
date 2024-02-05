package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SelecaoNatural {
        public static <T extends Comparable<T> & Serializable> void gerarParticoesOrdenadas(String caminhoArquivo,
                String caminhoParticoes, String caminhoLog, int limiteRegistros, String ent) {
            Long tempoInicial = System.nanoTime();
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
                List<T> memoriaTemporaria = new ArrayList<>();
                int numParticao = 0;

                ArrayList<T> items = (ArrayList<T>) in.readObject();
                memoriaTemporaria.addAll(items);

            while (!memoriaTemporaria.isEmpty()) {
                MergeSort.<T>sort(memoriaTemporaria, 0, memoriaTemporaria.size() - 1);

                try (ObjectOutputStream out = new ObjectOutputStream(
                        new FileOutputStream(caminhoParticoes + "/particao" + numParticao + ".dat"))) {
                    T ultimoItem = null;
                    int contadorRegistros = 0;

                    while (!memoriaTemporaria.isEmpty() && contadorRegistros < limiteRegistros
                            && (ultimoItem == null || ultimoItem.compareTo(memoriaTemporaria.get(0)) <= 0)) {
                        ultimoItem = memoriaTemporaria.remove(0);
                        out.writeObject(ultimoItem);
                        contadorRegistros++;

                        if (in.available() > 0) {
                            T novoItem = (T) in.readObject();
                            Comparator<T> comparator = Comparator.naturalOrder();
                            int pos = Collections.binarySearch(memoriaTemporaria, novoItem, comparator);
                            if (pos < 0) {
                                pos = -pos - 1;
                            }
                            memoriaTemporaria.add(pos, novoItem);
                        }
                    }
                }

                numParticao++;
            }
            System.out.println("Número de partições criadas: " + numParticao);
            Long tempoFinal = System.nanoTime();
            salvarTempoExecucao(tempoInicial, tempoFinal, caminhoLog, ent);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void salvarTempoExecucao(Long tempoInicial, Long tempoFinal,
            String caminhoLog, String ent) {
        double tempoTotal = 0;
        tempoTotal = (tempoFinal - tempoInicial) / 1000000000.0;
        DecimalFormat df = new DecimalFormat("#.##########");
        String tempoTotalString = df.format(tempoTotal);
        String tempoExecucao = "\n---------------\n" + "Seleção Natural " + ent + ": " + "\n" +
                "Contagem de Tempo: " + tempoTotalString + " segundos" + "\n---------------";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoLog, true))) {
            oos.writeObject(tempoExecucao);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
