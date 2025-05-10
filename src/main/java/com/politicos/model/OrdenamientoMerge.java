/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politicos.model;
import java.util.Objects;

/**
 * Implementación de la {@link EstrategiaOrdenamiento} utilizando el algoritmo Merge Sort (ordenación por mezcla).
 * Es un algoritmo eficiente de tipo "divide y vencerás", particularmente adecuado para listas enlazadas
 * ya que no requiere acceso aleatorio. Divide la lista recursivamente, ordena las sublistas y
 * luego las fusiona (merge) de manera ordenada.
 *
 * <p>Esta implementación modifica los enlaces {@code siguiente} de los {@link Nodo}s.</p>
 *
 * <p>Complejidad Temporal: O(n log n) en todos los casos (peor, promedio, mejor).</p>
 * <p>Complejidad Espacial: O(log n) debido a la pila de recursión (puede ser O(n) en algunas implementaciones iterativas).</p>
 *
 * @param <T> El tipo de elementos en la lista, debe ser {@link Comparable}.
 * @author devapps
 * @version 1.1
 */
public class OrdenamientoMerge<T extends Comparable<T>> implements EstrategiaOrdenamiento<T> {

    /**
     * Ordena la lista dada usando el algoritmo Merge Sort, re-enlazando nodos.
     *
     * @param lista La lista {@link ListaEnlazadaSimple} a ordenar. No debe ser null.
     * @throws NullPointerException si {@code lista} es null.
     * @throws ClassCastException si los elementos no son {@code Comparable}.
     */
    private long comparaciones;
    private long intercambios;
    private long tiempoEjecucion;

    /**
     * Inicializa los contadores antes de comenzar el ordenamiento.
     */
    private void inicializarContadores() {
        comparaciones = 0;
        intercambios = 0;
    }

    public long getComparaciones() { return comparaciones; }
    public long getIntercambios() { return intercambios; }

    // --- MergeSort para Lista Enlazada Simple ---
    @Override
    public ResultadoOrdenamiento ordenar(ListaEnlazadaSimple<T> lista) {
        inicializarContadores();
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

        long inicio = System.nanoTime();
        Nodo<T> cabeza = lista.getCabeza();
        cabeza = mergeSortSimple(cabeza);
        lista.setCabeza(cabeza);
        long fin = System.nanoTime();
        tiempoEjecucion = fin - inicio;
        return new ResultadoOrdenamiento(
            tiempoEjecucion / 1_000_000.0,
            comparaciones,
            intercambios
        );
    }

    private Nodo<T> mergeSortSimple(Nodo<T> cabeza) {
        if (cabeza == null || cabeza.getSiguiente() == null) {
            return cabeza;
        }

        // Dividir la lista en dos mitades
        Nodo<T> mitad = obtenerMitadSimple(cabeza);
        Nodo<T> siguienteMitad = mitad.getSiguiente();
        mitad.setSiguiente(null);

        // Ordenar recursivamente cada mitad
        Nodo<T> izquierda = mergeSortSimple(cabeza);
        Nodo<T> derecha = mergeSortSimple(siguienteMitad);

        // Fusionar las mitades ordenadas
        return fusionarSimple(izquierda, derecha);
    }

    private Nodo<T> fusionarSimple(Nodo<T> a, Nodo<T> b) {
        Nodo<T> dummy = new Nodo<>(null);
        Nodo<T> tail = dummy;

        while (a != null && b != null) {
            comparaciones++;
            if (a.getDato().compareTo(b.getDato()) <= 0) {
                tail.setSiguiente(a);
                a = a.getSiguiente();
            } else {
                tail.setSiguiente(b);
                b = b.getSiguiente();
            }
            tail = tail.getSiguiente();
            intercambios++;
        }

        // Agregar el resto de la lista que no está vacía
        tail.setSiguiente((a != null) ? a : b);

        return dummy.getSiguiente();
    }

    private Nodo<T> obtenerMitadSimple(Nodo<T> cabeza) {
        if (cabeza == null) {
            return cabeza;
        }

        Nodo<T> lento = cabeza;
        Nodo<T> rapido = cabeza.getSiguiente();

        while (rapido != null && rapido.getSiguiente() != null) {
            lento = lento.getSiguiente();
            rapido = rapido.getSiguiente().getSiguiente();
        }

        return lento;
    }

    // --- MergeSort para Lista Enlazada Doble ---
    @Override
    public ResultadoOrdenamiento ordenar(ListaEnlazadaDoble<T> lista) {
        inicializarContadores();
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

        long inicio = System.nanoTime();
        NodoDoble<T> cabeza = lista.getCabeza();
        cabeza = mergeSortDoble(cabeza);
        lista.setCabeza(cabeza);
        
        // Actualizar la cola después de ordenar
        if (cabeza != null) {
            NodoDoble<T> cola = cabeza;
            while (cola.getSiguiente() != null) {
                cola = cola.getSiguiente();
            }
            lista.cola = cola;
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

    private NodoDoble<T> mergeSortDoble(NodoDoble<T> cabeza) {
        if (cabeza == null || cabeza.getSiguiente() == null) {
            return cabeza;
        }

        // Dividir la lista en dos mitades
        NodoDoble<T> mitad = obtenerMitadDoble(cabeza);
        NodoDoble<T> siguienteMitad = mitad.getSiguiente();
        mitad.setSiguiente(null);
        if (siguienteMitad != null) {
            siguienteMitad.setAnterior(null);
        }

        // Ordenar recursivamente cada mitad
        NodoDoble<T> izquierda = mergeSortDoble(cabeza);
        NodoDoble<T> derecha = mergeSortDoble(siguienteMitad);

        // Fusionar las mitades ordenadas
        return fusionarDoble(izquierda, derecha);
    }

    private NodoDoble<T> fusionarDoble(NodoDoble<T> a, NodoDoble<T> b) {
        NodoDoble<T> dummy = new NodoDoble<>(null);
        NodoDoble<T> tail = dummy;

        while (a != null && b != null) {
            comparaciones++;
            if (a.getDato().compareTo(b.getDato()) <= 0) {
                tail.setSiguiente(a);
                a.setAnterior(tail);
                a = a.getSiguiente();
            } else {
                tail.setSiguiente(b);
                b.setAnterior(tail);
                b = b.getSiguiente();
            }
            tail = tail.getSiguiente();
            intercambios++;
        }

        NodoDoble<T> restante = (a != null) ? a : b;
        while (restante != null) {
            tail.setSiguiente(restante);
            restante.setAnterior(tail);
            tail = restante;
            restante = restante.getSiguiente();
            intercambios++;
        }

        NodoDoble<T> cabezaOrdenada = dummy.getSiguiente();
        if (cabezaOrdenada != null) {
            cabezaOrdenada.setAnterior(null); // Asegurar que el primer nodo no tenga anterior
        }

        return cabezaOrdenada;
    }

    private NodoDoble<T> obtenerMitadDoble(NodoDoble<T> cabeza) {
        if (cabeza == null) {
            return cabeza;
        }

        NodoDoble<T> lento = cabeza;
        NodoDoble<T> rapido = cabeza.getSiguiente();

        while (rapido != null && rapido.getSiguiente() != null) {
            lento = lento.getSiguiente();
            rapido = rapido.getSiguiente().getSiguiente();
        }

        return lento;
    }

    // --- MergeSort para Lista Enlazada Simple Circular ---
    @Override
    public ResultadoOrdenamiento ordenar(ListaEnlazadaSimpleCircular<T> lista) {
        inicializarContadores();
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

        long inicio = System.nanoTime();
        
        // Convertir temporalmente la lista circular en simple
        Nodo<T> cabeza = lista.getCabeza();
        Nodo<T> ultimo = lista.ultimo;
        if (ultimo != null) {
            ultimo.setSiguiente(null); // Romper la circularidad temporalmente
        }

        // Ordenar la lista como si fuera simple
        cabeza = mergeSortSimple(cabeza);
        
        // Reconectar la lista circular
        if (cabeza != null) {
            ultimo = cabeza;
            while (ultimo.getSiguiente() != null) {
                ultimo = ultimo.getSiguiente();
            }
            ultimo.setSiguiente(cabeza);
            lista.ultimo = ultimo;
        } else {
            lista.ultimo = null;
        }
        
        long fin = System.nanoTime();
        tiempoEjecucion = fin - inicio;
        return new ResultadoOrdenamiento(
            tiempoEjecucion / 1_000_000.0,
            comparaciones,
            intercambios
        );
    }
}
