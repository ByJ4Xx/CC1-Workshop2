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
    private long tiempoInicio;
    private long tiempoFin;

    /**
     * Inicializa los contadores antes de comenzar el ordenamiento.
     */
    private void inicializarContadores() {
        comparaciones = 0;
        intercambios = 0;
        tiempoInicio = 0;
        tiempoFin = 0;
    }

    public long getComparaciones() { return comparaciones; }
    public long getIntercambios() { return intercambios; }
    public long getTiempoEjecucion() { 
        return tiempoFin-tiempoInicio; 
    }

    // --- MergeSort para Lista Enlazada Simple ---
    @Override
    public ResultadoOrdenamiento ordenar(ListaEnlazadaSimple<T> lista) {
        inicializarContadores();
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

        tiempoInicio = System.nanoTime();
        Nodo<T> cabeza = lista.getCabeza();
        cabeza = mergeSortSimple(cabeza);
        lista.setCabeza(cabeza);
        tiempoFin = System.nanoTime();
        return new ResultadoOrdenamiento(getTiempoEjecucion(),getComparaciones(), getIntercambios());
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
        Nodo<T> resultado = null;

        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }

        comparaciones++;
        if (a.getDato().compareTo(b.getDato()) <= 0) {
            resultado = a;
            resultado.setSiguiente(fusionarSimple(a.getSiguiente(), b));
        } else {
            resultado = b;
            resultado.setSiguiente(fusionarSimple(a, b.getSiguiente()));
        }
        intercambios++;
        return resultado;
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

        tiempoInicio = System.nanoTime();
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
        tiempoFin = System.nanoTime();
        return new ResultadoOrdenamiento(getTiempoEjecucion(),getComparaciones(), getIntercambios());
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
        NodoDoble<T> resultado = null;

        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }

        comparaciones++;
        if (a.getDato().compareTo(b.getDato()) <= 0) {
            resultado = a;
            resultado.setSiguiente(fusionarDoble(a.getSiguiente(), b));
            if (resultado.getSiguiente() != null) {
                resultado.getSiguiente().setAnterior(resultado);
            }
        } else {
            resultado = b;
            resultado.setSiguiente(fusionarDoble(a, b.getSiguiente()));
            if (resultado.getSiguiente() != null) {
                resultado.getSiguiente().setAnterior(resultado);
            }
        }
        intercambios++;
        return resultado;
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

        tiempoInicio = System.nanoTime();
        
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
        
        tiempoFin = System.nanoTime();
        return new ResultadoOrdenamiento(getTiempoEjecucion(),getComparaciones(), getIntercambios());
    }

    /**
     * Imprime las estadísticas del ordenamiento.
     */
    private void imprimirEstadisticas() {
        System.out.printf("Comparaciones: %d | Fusiones: %d | Tiempo: %.3f ms%n",
                comparaciones, intercambios, (tiempoFin - tiempoInicio) / 1_000_000.0);
    }
}
