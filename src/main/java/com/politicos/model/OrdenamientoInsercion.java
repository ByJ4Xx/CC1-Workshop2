/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politicos.model;
import java.util.Objects;

/**
 * Implementación de la {@link EstrategiaOrdenamiento} utilizando el algoritmo de Inserción (Insertion Sort).
 * Este algoritmo construye la lista ordenada final un elemento a la vez, tomando elementos
 * de la lista de entrada y colocándolos en su posición correcta dentro de una sublista ya ordenada.
 * Es eficiente para listas pequeñas o casi ordenadas.
 *
 * <p>Esta implementación modifica los enlaces {@code siguiente} de los {@link Nodo}s para reordenar la lista.</p>
 *
 * <p>Complejidad Temporal: O(n^2) en peor y caso promedio, O(n) en mejor caso (ya ordenada).</p>
 * <p>Complejidad Espacial: O(1) (ordenación in situ).</p>
 *
 * @param <T> El tipo de elementos en la lista, debe ser {@link Comparable}.
 * @author devapps
 * @version 1.1
 */
public class OrdenamientoInsercion<T extends Comparable<T>> implements EstrategiaOrdenamiento<T> {

    /** Referencia a la cabeza de la sublista ordenada que se está construyendo. */
    private Nodo<T> cabezaOrdenada;

    /**
     * Ordena la lista dada usando el algoritmo de Inserción, re-enlazando nodos.
     *
     * @param lista La lista {@link ListaEnlazadaSimple} a ordenar. No debe ser null.
     * @throws NullPointerException si {@code lista} es null.
     * @throws ClassCastException si los elementos no son {@code Comparable}.
     */
    private long comparaciones;
    private long intercambios;
    private long tiempoEjecucion;

    public long getComparaciones() { return comparaciones; }
    public long getIntercambios() { return intercambios; }
    public long getTiempoEjecucion() { return tiempoEjecucion; }

    @Override
    public ResultadoOrdenamiento ordenar(ListaEnlazadaSimple<T> lista) {
        comparaciones = 0;
        intercambios = 0;

        Nodo<T> actualOriginal = lista.getCabeza();
        this.cabezaOrdenada = null;

        while (actualOriginal != null) {
            comparaciones++;
            Nodo<T> siguienteOriginal = actualOriginal.getSiguiente();
            insertarEnOrden(actualOriginal);
            actualOriginal = siguienteOriginal;
        }

        lista.setCabeza(this.cabezaOrdenada);
        return new ResultadoOrdenamiento(getTiempoEjecucion(),getComparaciones(), getIntercambios());
    }

    @Override
    public ResultadoOrdenamiento ordenar(ListaEnlazadaDoble<T> lista) {
        comparaciones = 0;
        intercambios = 0;

        NodoDoble<T> cabezaOrdenada = null;
        NodoDoble<T> actual = lista.getCabeza();

        while (actual != null) {
            comparaciones++;
            NodoDoble<T> siguiente = actual.getSiguiente();
            
            if (cabezaOrdenada == null || cabezaOrdenada.getDato().compareTo(actual.getDato()) >= 0) {
                actual.setAnterior(null);
                actual.setSiguiente(cabezaOrdenada);
                if (cabezaOrdenada != null) {
                    cabezaOrdenada.setAnterior(actual);
                }
                cabezaOrdenada = actual;
                intercambios++;
            } else {
                NodoDoble<T> temp = cabezaOrdenada;
                while (temp.getSiguiente() != null && temp.getSiguiente().getDato().compareTo(actual.getDato()) < 0) {
                    comparaciones++;
                    temp = temp.getSiguiente();
                }
                actual.setSiguiente(temp.getSiguiente());
                if (temp.getSiguiente() != null) {
                    temp.getSiguiente().setAnterior(actual);
                }
                temp.setSiguiente(actual);
                actual.setAnterior(temp);
                intercambios++;
            }
            actual = siguiente;
        }

        lista.setCabeza(cabezaOrdenada);
        return new ResultadoOrdenamiento(getTiempoEjecucion(),getComparaciones(), getIntercambios());
    }

    @Override
    public ResultadoOrdenamiento ordenar(ListaEnlazadaSimpleCircular<T> lista) {
        comparaciones = 0;
        intercambios = 0;

        Nodo<T> cabezaOrdenada = null;
        Nodo<T> actual = lista.getCabeza();
        int tamanno = lista.getTamanno();
        
        for (int i = 0; i < tamanno; i++) {
            comparaciones++;
            Nodo<T> siguiente = actual.getSiguiente();
            
            if (cabezaOrdenada == null || cabezaOrdenada.getDato().compareTo(actual.getDato()) >= 0) {
                actual.setSiguiente(cabezaOrdenada);
                cabezaOrdenada = actual;
                intercambios++;
            } else {
                Nodo<T> temp = cabezaOrdenada;
                while (temp.getSiguiente() != null && 
                       temp.getSiguiente().getDato().compareTo(actual.getDato()) < 0) {
                    comparaciones++;
                    temp = temp.getSiguiente();
                }
                actual.setSiguiente(temp.getSiguiente());
                temp.setSiguiente(actual);
                intercambios++;
            }
            actual = siguiente;
            
        }

        Nodo<T> nuevoUltimo = cabezaOrdenada;
        while (nuevoUltimo.getSiguiente() != null) {
            nuevoUltimo = nuevoUltimo.getSiguiente();
        }
        nuevoUltimo.setSiguiente(cabezaOrdenada);
        
        lista.setCabeza(cabezaOrdenada);
        return new ResultadoOrdenamiento(getTiempoEjecucion(),getComparaciones(), getIntercambios());
    }

    private void insertarEnOrden(Nodo<T> nodoAInsertar) {
        if (this.cabezaOrdenada == null ||
            this.cabezaOrdenada.getDato().compareTo(nodoAInsertar.getDato()) >= 0) {
            nodoAInsertar.setSiguiente(this.cabezaOrdenada);
            this.cabezaOrdenada = nodoAInsertar;
            intercambios++;
        } else {
            Nodo<T> actualOrdenado = this.cabezaOrdenada;
            while (actualOrdenado.getSiguiente() != null &&
                   actualOrdenado.getSiguiente().getDato().compareTo(nodoAInsertar.getDato()) < 0) {
                comparaciones++;
                actualOrdenado = actualOrdenado.getSiguiente();
            }
            nodoAInsertar.setSiguiente(actualOrdenado.getSiguiente());
            actualOrdenado.setSiguiente(nodoAInsertar);
            intercambios++;
        }
    }
}
