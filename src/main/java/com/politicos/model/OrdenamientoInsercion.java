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

    private Nodo<T> cabezaOrdenada;
    private long comparaciones;
    private long intercambios;
    private long tiempoEjecucion;

    private void inicializarContadores() {
        comparaciones = 0;
        intercambios = 0;
    }

    @Override
    public ResultadoOrdenamiento ordenar(ListaEnlazadaSimple<T> lista) {
        Objects.requireNonNull(lista, "La lista no puede ser null.");

        inicializarContadores();
        long inicioNano = System.nanoTime();

        cabezaOrdenada = null;
        Nodo<T> actualOriginal = lista.getCabeza();
        while (actualOriginal != null) {
            comparaciones++;
            Nodo<T> siguienteOriginal = actualOriginal.getSiguiente();
            actualOriginal.setSiguiente(null);
            insertarEnOrden(actualOriginal);
            actualOriginal = siguienteOriginal;
        }

        lista.setCabeza(cabezaOrdenada);
        tiempoEjecucion = System.nanoTime() - inicioNano;
        return new ResultadoOrdenamiento(
            tiempoEjecucion / 1_000_000.0,
            comparaciones,
            intercambios
        );
    }

    @Override
    public ResultadoOrdenamiento ordenar(ListaEnlazadaDoble<T> lista) {
        Objects.requireNonNull(lista, "La lista no puede ser null.");

        inicializarContadores();
        long inicioNano = System.nanoTime();

        NodoDoble<T> cabezaOrdDoble = null;
        NodoDoble<T> actual = lista.getCabeza();
        while (actual != null) {
            comparaciones++;
            NodoDoble<T> siguiente = actual.getSiguiente();
            // Desconectar
            actual.setAnterior(null);
            actual.setSiguiente(null);

            // Insertar en cabezaOrdDoble
            if (cabezaOrdDoble == null ||
                cabezaOrdDoble.getDato().compareTo(actual.getDato()) >= 0)
            {
                actual.setSiguiente(cabezaOrdDoble);
                if (cabezaOrdDoble != null) cabezaOrdDoble.setAnterior(actual);
                cabezaOrdDoble = actual;
                intercambios++;
            } else {
                NodoDoble<T> temp = cabezaOrdDoble;
                while (temp.getSiguiente() != null &&
                       temp.getSiguiente().getDato().compareTo(actual.getDato()) < 0)
                {
                    comparaciones++;
                    temp = temp.getSiguiente();
                }
                actual.setSiguiente(temp.getSiguiente());
                if (temp.getSiguiente() != null) temp.getSiguiente().setAnterior(actual);
                temp.setSiguiente(actual);
                actual.setAnterior(temp);
                intercambios++;
            }

            actual = siguiente;
        }

        lista.setCabeza(cabezaOrdDoble);
        tiempoEjecucion = System.nanoTime() - inicioNano;
        return new ResultadoOrdenamiento(
            tiempoEjecucion / 1_000_000.0,
            comparaciones,
            intercambios
        );
    }

    @Override
    public ResultadoOrdenamiento ordenar(ListaEnlazadaSimpleCircular<T> lista) {
        Objects.requireNonNull(lista, "La lista circular no puede ser null.");

        inicializarContadores();
        long inicioNano = System.nanoTime();

        // Si 0 o 1 elemento, nada que hacer
        if (lista.getTamanno() <= 1) {
            tiempoEjecucion = System.nanoTime() - inicioNano;
            return new ResultadoOrdenamiento(
                tiempoEjecucion / 1_000_000.0,
                comparaciones,
                intercambios
            );
        }

        // 1) Romper ciclo
        Nodo<T> head = lista.getCabeza();
        Nodo<T> tail = lista.ultimo;
        tail.setSiguiente(null);

        // 2) Insertion Sort lineal
        cabezaOrdenada = null;
        Nodo<T> curr = head;
        while (curr != null) {
            comparaciones++;
            Nodo<T> next = curr.getSiguiente();
            curr.setSiguiente(null);
            insertarEnOrden(curr);
            curr = next;
        }

        // 3) Restaurar ciclo
        Nodo<T> nuevoHead = cabezaOrdenada;
        Nodo<T> nuevoTail = nuevoHead;
        while (nuevoTail.getSiguiente() != null) {
            nuevoTail = nuevoTail.getSiguiente();
        }
        nuevoTail.setSiguiente(nuevoHead);
        lista.ultimo = nuevoTail;

        tiempoEjecucion = System.nanoTime() - inicioNano;
        return new ResultadoOrdenamiento(
            tiempoEjecucion / 1_000_000.0,
            comparaciones,
            intercambios
        );
    }

    private void insertarEnOrden(Nodo<T> nodoAInsertar) {
        if (cabezaOrdenada == null ||
            cabezaOrdenada.getDato().compareTo(nodoAInsertar.getDato()) >= 0)
        {
            nodoAInsertar.setSiguiente(cabezaOrdenada);
            cabezaOrdenada = nodoAInsertar;
            intercambios++;
        } else {
            Nodo<T> actualOrdenado = cabezaOrdenada;
            while (actualOrdenado.getSiguiente() != null &&
                   actualOrdenado.getSiguiente().getDato().compareTo(nodoAInsertar.getDato()) < 0)
            {
                comparaciones++;
                actualOrdenado = actualOrdenado.getSiguiente();
            }
            nodoAInsertar.setSiguiente(actualOrdenado.getSiguiente());
            actualOrdenado.setSiguiente(nodoAInsertar);
            intercambios++;
        }
    }
}

