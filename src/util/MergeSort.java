package util;

import java.util.ArrayList;
import java.util.List;

public class MergeSort {
    public static <T extends Comparable<T>> void sort(List<T> items, int esquerda, int direita) {
        if (esquerda < direita) {
            int meio = (esquerda + direita) / 2;

            sort(items, esquerda, meio);
            sort(items, meio + 1, direita);

            merge(items, esquerda, meio, direita);
        }
    }

    private static <T extends Comparable<T>> void merge(List<T> items, int esquerda, int meio, int direita) {
        int n1 = meio - esquerda + 1;
        int n2 = direita - meio;

        List<T> L = new ArrayList<>();
        List<T> R = new ArrayList<>();

        for (int i = 0; i < n1; ++i)
            L.add(items.get(esquerda + i));
        for (int j = 0; j < n2; ++j)
            R.add(items.get(meio + 1 + j));

        int i = 0, j = 0;

        int k = esquerda;
        while (i < n1 && j < n2) {
            if (L.get(i).compareTo(R.get(j)) <= 0) {
                items.set(k, L.get(i));
                i++;
            } else {
                items.set(k, R.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            items.set(k, L.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            items.set(k, R.get(j));
            j++;
            k++;
        }
    }
}
