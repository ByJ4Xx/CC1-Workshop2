/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politicos.model;
import java.util.Objects;

/**
 * Implementación de la {@link EstrategiaOrdenamiento} utilizando el algoritmo Quick Sort (ordenación rápida).
 * Es un algoritmo eficiente de tipo "divide y vencerás". Selecciona un elemento como pivote
 * y particiona la lista de forma que los elementos menores que el pivote queden a un lado
 * y los mayores al otro, luego ordena recursivamente las sublistas.
 *
 * <p><b>Nota Importante:</b> Esta implementación específica utiliza el <strong>último elemento</strong>
 * de la (sub)lista como pivote e intercambia los <strong>datos</strong> contenidos en los nodos,
 * no los nodos en sí. Por lo tanto, requiere que la clase {@link Nodo} posea un método {@code setDato(T)}.</p>
 * <p>QuickSort en listas enlazadas puede ser menos eficiente que en arrays debido a la dificultad
 * de acceso aleatorio y la complejidad de la partición.</p>
 *
 * <p>Complejidad Temporal: O(n log n) en promedio, O(n^2) en el peor caso (ej. lista ya ordenada).</p>
 * <p>Complejidad Espacial: O(log n) en promedio (pila recursión), O(n) en peor caso.</p>
 *
 * @param <T> El tipo de elementos en la lista, debe ser {@link Comparable}.
 * @author devapps
 * @version 1.1
 */
public class OrdenamientoQuickSort<T extends Comparable<T>> implements EstrategiaOrdenamiento<T> {

    /**
     * Ordena la lista dada usando el algoritmo Quick Sort, usando el último nodo como pivote
     * y realizando intercambio de datos.
     *
     * @param lista La lista {@link ListaEnlazadaSimple} a ordenar. No debe ser null.
     * @throws NullPointerException si {@code lista} es null.
     * @throws ClassCastException si los elementos no son {@code Comparable}.
     * @throws UnsupportedOperationException si {@link Nodo#setDato(Object)} no está disponible (implícito).
     */
    private long comparaciones;
    private long intercambios;
    private long tiempoEjecucion;

    public long getComparaciones() { return comparaciones; }
    public long getIntercambios()  { return intercambios; }
    public long getTiempoEjecucion() { return tiempoEjecucion; }
    
    private void inicializarContadores() {
        comparaciones = 0;
        intercambios = 0;
    }

    /**
     * Inicializa los contadores antes de comenzar el ordenamiento.
     */

    // --- QuickSort para Lista Enlazada Simple ---
    @Override
    public ResultadoOrdenamiento ordenar(ListaEnlazadaSimple<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");
        inicializarContadores();
        long inicio = System.nanoTime();
        Nodo<T> cabeza = lista.getCabeza();
        Nodo<T> cola = encontrarColaSimple(cabeza);

        quickSortRecursivoSimple(cabeza, cola);

        lista.setCabeza(cabeza);
        long fin = System.nanoTime();
        tiempoEjecucion = fin - inicio;
        return new ResultadoOrdenamiento(
            tiempoEjecucion / 1_000_000.0,
            comparaciones,
            intercambios
        );
    }

    private Nodo<T> encontrarColaSimple(Nodo<T> nodo) {
        if (nodo == null) {
            return null;
        }
        while (nodo.getSiguiente() != null) {
            nodo = nodo.getSiguiente();
        }
        return nodo;
    }

    private void quickSortRecursivoSimple(Nodo<T> cabezaSubLista, Nodo<T> colaSubLista) {
        if (cabezaSubLista == null || colaSubLista == null || cabezaSubLista == colaSubLista || cabezaSubLista == colaSubLista.getSiguiente()) {
            return;
        }

        Nodo<T>[] resultadoParticion = particionarSimple(cabezaSubLista, colaSubLista);
        Nodo<T> nodoPivoteFinal = resultadoParticion[0];
        Nodo<T> nodoAntesPivote = resultadoParticion[1];

        if (nodoAntesPivote != null && nodoPivoteFinal != cabezaSubLista) {
            quickSortRecursivoSimple(cabezaSubLista, nodoAntesPivote);
        }

        if (nodoPivoteFinal != null && nodoPivoteFinal != colaSubLista) {
            quickSortRecursivoSimple(nodoPivoteFinal.getSiguiente(), colaSubLista);
        }
    }

    private Nodo<T>[] particionarSimple(Nodo<T> cabeza, Nodo<T> cola) {
        // Elegir pivote aleatorio para evitar peor caso
        Nodo<T> pivote = obtenerPivoteAleatorio(cabeza, cola);
        intercambiarDatos(pivote, cola); // mover pivote al final

        T valorPivote = cola.getDato();
        Nodo<T> i = null;
        Nodo<T> actual = cabeza;

        while (actual != cola) {
            comparaciones++;
            if (actual.getDato().compareTo(valorPivote) < 0) {
                i = (i == null) ? cabeza : i.getSiguiente();
                intercambiarDatos(actual, i);
                intercambios++;
            }
            actual = actual.getSiguiente();
        }

        i = (i == null) ? cabeza : i.getSiguiente();
        intercambiarDatos(cola, i);
        intercambios++;

        Nodo<T> nodoAntesPivote = null;
        if (i != cabeza) {
            Nodo<T> buscador = cabeza;
            while (buscador != null && buscador.getSiguiente() != i) {
                buscador = buscador.getSiguiente();
            }
            nodoAntesPivote = buscador;
        }

        @SuppressWarnings("unchecked")
        Nodo<T>[] resultado = (Nodo<T>[]) new Nodo<?>[2];
        resultado[0] = i;               // pivote final
        resultado[1] = nodoAntesPivote; // antes del pivote
        return resultado;
    }
    
    private Nodo<T> obtenerPivoteAleatorio(Nodo<T> inicio, Nodo<T> fin) {
    int longitud = 0;
    Nodo<T> actual = inicio;
    while (actual != fin.getSiguiente()) {
        longitud++;
        actual = actual.getSiguiente();
    }
    int paso = (int) (Math.random() * longitud);
    actual = inicio;
    for (int i = 0; i < paso; i++) {
        actual = actual.getSiguiente();
    }
    return actual;
}

    // --- QuickSort para Lista Enlazada Doble ---
    @Override
    public ResultadoOrdenamiento ordenar(ListaEnlazadaDoble<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");
        inicializarContadores();
        long inicio = System.nanoTime();
        NodoDoble<T> cabeza = lista.getCabeza();
        NodoDoble<T> cola = lista.cola;

        quickSortRecursivoDoble(cabeza, cola);

        // Actualizar la cabeza y cola de la lista
        lista.setCabeza(cabeza);
        if (cabeza != null) {
            lista.cola = encontrarColaDoble(cabeza);
        } else {
            lista.cola = null;
        }
        long fin = System.nanoTime();
        tiempoEjecucion = fin - inicio;
        return new ResultadoOrdenamiento(
            tiempoEjecucion / 1_000_000.0,
            comparaciones,
            intercambios
        );
    }

    private NodoDoble<T> encontrarColaDoble(NodoDoble<T> nodo) {
        if (nodo == null) {
            return null;
        }
        while (nodo.getSiguiente() != null) {
            nodo = nodo.getSiguiente();
        }
        return nodo;
    }

    private ResultadoOrdenamiento quickSortRecursivoDoble(NodoDoble<T> cabezaSubLista, NodoDoble<T> colaSubLista) {

        NodoDoble<T>[] resultadoParticion = particionarDoble(cabezaSubLista, colaSubLista);
        NodoDoble<T> nodoPivoteFinal = resultadoParticion[0];
        NodoDoble<T> nodoAntesPivote = resultadoParticion[1];

        if (nodoAntesPivote != null && nodoPivoteFinal != cabezaSubLista) {
            quickSortRecursivoDoble(cabezaSubLista, nodoAntesPivote);
        }

        if (nodoPivoteFinal != null && nodoPivoteFinal != colaSubLista) {
            quickSortRecursivoDoble(nodoPivoteFinal.getSiguiente(), colaSubLista);
        }
        return new ResultadoOrdenamiento(getTiempoEjecucion(),getComparaciones(), getIntercambios());
    }

    private NodoDoble<T>[] particionarDoble(NodoDoble<T> cabeza, NodoDoble<T> cola) {
        NodoDoble<T> pivote = obtenerPivoteAleatorioDoble(cabeza, cola);
        intercambiarDatosDoble(pivote, cola);  // Mover el pivote al final

        T valorPivote = cola.getDato();
        NodoDoble<T> i = cabeza.getAnterior();
        NodoDoble<T> actual = cabeza;

        while (actual != cola) {
            comparaciones++;
            if (actual.getDato().compareTo(valorPivote) < 0) {
                i = (i == null) ? cabeza : i.getSiguiente();
                intercambiarDatosDoble(actual, i);
                intercambios++;
            }
            actual = actual.getSiguiente();
        }

        i = (i == null) ? cabeza : i.getSiguiente();
        intercambiarDatosDoble(cola, i);
        intercambios++;

        NodoDoble<T> nodoAntesPivote = (i != cabeza) ? i.getAnterior() : null;

        @SuppressWarnings("unchecked")
        NodoDoble<T>[] resultado = (NodoDoble<T>[]) new NodoDoble<?>[2];
        resultado[0] = i;
        resultado[1] = nodoAntesPivote;
        return resultado;
    }
    
    private NodoDoble<T> obtenerPivoteAleatorioDoble(NodoDoble<T> inicio, NodoDoble<T> fin) {
        int longitud = 0;
        NodoDoble<T> actual = inicio;
        while (actual != fin.getSiguiente()) {
            longitud++;
            actual = actual.getSiguiente();
        }
        int paso = (int) (Math.random() * longitud);
        actual = inicio;
        for (int i = 0; i < paso; i++) {
            actual = actual.getSiguiente();
        }
        return actual;
    }
    
    private void intercambiarDatosDoble(NodoDoble<T> a, NodoDoble<T> b) {
        T temp = a.getDato();
        a.setDato(b.getDato());
        b.setDato(temp);
    }


    @Override
    public ResultadoOrdenamiento ordenar(ListaEnlazadaSimpleCircular<T> lista) {
        Objects.requireNonNull(lista, "La lista circular no puede ser null.");
        inicializarContadores();
        long inicio = System.nanoTime();

        // 1) Romper la circularidad
        Nodo<T> head = lista.getCabeza();
        Nodo<T> tail = head;
        while (tail.getSiguiente() != head) {
            tail = tail.getSiguiente();
        }
        tail.setSiguiente(null);

        // 2) QuickSort sobre lista lineal
        quickSortRecursivoSimple(head, tail);

        // 3) Restaurar circularidad
        Nodo<T> newTail = head;
        while (newTail.getSiguiente() != null) {
            newTail = newTail.getSiguiente();
        }
        newTail.setSiguiente(head);
        lista.ultimo = newTail;
        
        long fin = System.nanoTime();
        tiempoEjecucion = fin - inicio;
        return new ResultadoOrdenamiento(
            tiempoEjecucion / 1_000_000.0,
            comparaciones,
            intercambios
        );
    }
    /**
     * Método auxiliar para intercambiar datos entre dos nodos.
     */
    private void intercambiarDatos(Nodo<T> a, Nodo<T> b) {
        T temp = a.getDato();
        a.setDato(b.getDato());
        b.setDato(temp);
    }

    /**
     * Método auxiliar para intercambiar datos entre dos nodos dobles.
     */
    private void intercambiarDatos(NodoDoble<T> a, NodoDoble<T> b) {
        T temp = a.getDato();
        a.setDato(b.getDato());
        b.setDato(temp);
    }
}
