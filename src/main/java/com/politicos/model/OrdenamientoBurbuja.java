/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politicos.model;
import java.util.Objects;

/**
 * Implementación de la {@link EstrategiaOrdenamiento} utilizando el algoritmo Burbuja (Bubble Sort).
 * Este algoritmo itera repetidamente sobre la lista, comparando elementos adyacentes
 * e intercambiándolos si están en el orden incorrecto. Es simple pero ineficiente para grandes listas.
 *
 * <p><b>Nota Importante:</b> Esta implementación específica intercambia los <strong>datos</strong>
 * contenidos en los nodos, no los nodos en sí. Por lo tanto, requiere que la clase {@link Nodo}
 * posea un método {@code setDato(T)}.</p>
 *
 * <p>Complejidad Temporal: O(n^2) en peor y caso promedio, O(n) en mejor caso (ya ordenada).</p>
 * <p>Complejidad Espacial: O(1).</p>
 *
 * @param <T> El tipo de elementos en la lista, debe ser {@link Comparable}.
 * @author devapps
 * @version 1.1
 */
public class OrdenamientoBurbuja<T extends Comparable<T>> implements EstrategiaOrdenamiento<T> {
    
    private long comparaciones;
    private long intercambios;
    private long tiempoEjecucion;

    public long getComparaciones() { return comparaciones; }
    public long getIntercambios() { return intercambios; }
    public long getTiempoEjecucion() { return tiempoEjecucion; }
    /**
     * Ordena la lista dada usando el algoritmo de Burbuja mediante intercambio de datos.
     *
     * @param lista La lista {@link ListaEnlazadaSimple} a ordenar. No debe ser null.
     * @throws NullPointerException si {@code lista} es null.
     * @throws ClassCastException si los elementos no son {@code Comparable}.
     * @throws UnsupportedOperationException si {@link Nodo#setDato(Object)} no está disponible (implícito).
     */

    @Override
    public ResultadoOrdenamiento ordenar(ListaEnlazadaSimple<T> lista) {
        Objects.requireNonNull(lista);
        int comparaciones = 0, intercambios = 0;
        long inicio = System.nanoTime();

        int n = lista.getTamanno();
        boolean intercambio;

        for (int i = 0; i < n - 1; i++) {
            Nodo<T> actual = lista.getCabeza();
            intercambio = false;

            for (int j = 0; j < n - i - 1; j++) {
                comparaciones++;
                Nodo<T> siguiente = actual.getSiguiente();
                if (actual.getDato().compareTo(siguiente.getDato()) > 0) {
                    T temp = actual.getDato();
                    actual.setDato(siguiente.getDato());
                    siguiente.setDato(temp);
                    intercambios++;
                    intercambio = true;
                }
                actual = siguiente;
            }
            if (!intercambio) break;
        }
        long fin = System.nanoTime();
        return new ResultadoOrdenamiento(getTiempoEjecucion(),getComparaciones(), getIntercambios());
    }
    @Override
    public ResultadoOrdenamiento ordenar(ListaEnlazadaDoble<T> lista) {
        Objects.requireNonNull(lista);
        int comparaciones = 0, intercambios = 0;
        long inicio = System.nanoTime();

        int n = lista.getTamanno();
        boolean intercambio;

        for (int i = 0; i < n - 1; i++) {
            NodoDoble<T> actual = lista.getCabeza();
            intercambio = false;

            for (int j = 0; j < n - i - 1; j++) {
                comparaciones++;
                NodoDoble<T> siguiente = actual.getSiguiente();
                if (actual.getDato().compareTo(siguiente.getDato()) > 0) {
                    T temp = actual.getDato();
                    actual.setDato(siguiente.getDato());
                    siguiente.setDato(temp);
                    intercambios++;
                    intercambio = true;
                }
                actual = siguiente;
            }
            if (!intercambio) break;
        }

        long fin = System.nanoTime();
        return new ResultadoOrdenamiento(getTiempoEjecucion(),getComparaciones(), getIntercambios());
    }
    @Override
    public ResultadoOrdenamiento ordenar(ListaEnlazadaSimpleCircular<T> lista) {
        Objects.requireNonNull(lista);
        int comparaciones = 0, intercambios = 0;
        long inicio = System.nanoTime();

        int n = lista.getTamanno();
        boolean intercambio;

        for (int i = 0; i < n - 1; i++) {
            Nodo<T> actual = lista.getCabeza();
            intercambio = false;

            for (int j = 0; j < n - i - 1; j++) {
                Nodo<T> siguiente = actual.getSiguiente();
                comparaciones++;
                if (actual.getDato().compareTo(siguiente.getDato()) > 0) {
                    T temp = actual.getDato();
                    actual.setDato(siguiente.getDato());
                    siguiente.setDato(temp);
                    intercambios++;
                    intercambio = true;
                }
                actual = siguiente;
            }
            if (!intercambio) break;
        }

        long fin = System.nanoTime();
        return new ResultadoOrdenamiento(getTiempoEjecucion(),getComparaciones(), getIntercambios());
    }
}

