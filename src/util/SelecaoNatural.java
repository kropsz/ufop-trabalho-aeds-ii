package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SelecaoNatural {
    public static <T extends Comparable<T> & Serializable> void gerarParticoesOrdenadas(String caminhoArquivo, String caminhoParticoes) {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
        List<T> memoriaTemporaria = new ArrayList<>();
        int numParticao = 0;
        int limiteRegistros = 100;

        ArrayList<T> items = (ArrayList<T>) in.readObject();
        memoriaTemporaria.addAll(items);

        while (!memoriaTemporaria.isEmpty()) {
            MergeSort.<T>sort(memoriaTemporaria, 0, memoriaTemporaria.size() - 1);

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminhoParticoes + "/particao" + numParticao + ".dat"))) {
                T ultimoItem = null;
                int contadorRegistros = 0;

                while (!memoriaTemporaria.isEmpty() && contadorRegistros < limiteRegistros && (ultimoItem == null || ultimoItem.compareTo(memoriaTemporaria.get(0)) <= 0)) {
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

    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
}
}
