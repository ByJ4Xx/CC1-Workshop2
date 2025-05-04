/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politicos.model;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Implementación de una lista doblemente enlazada genérica.
 * Cada nodo mantiene referencias al nodo anterior y al siguiente, permitiendo
 * recorrido bidireccional y operaciones eficientes (O(1)) de inserción/eliminación
 * en ambos extremos (cabeza y cola).
 *
 * @param <T> El tipo de elementos almacenados en la lista.
 * @see NodoDoble
 * @author devapps
 * @version 1.2
 */
public class ListaEnlazadaDoble<T> implements ListaEnlazadaBase <T>{

    /** Referencia al primer nodo de la lista (cabeza). {@code null} si la lista está vacía. */
    public NodoDoble<T> cabeza;
    /** Referencia al último nodo de la lista (cola). {@code null} si la lista está vacía. */
    public NodoDoble<T> cola;
    /** Número actual de elementos en la lista. */
    private int tamanno;

    /**
     * Construye una lista doblemente enlazada vacía.
     */
    public ListaEnlazadaDoble() {
        this.cabeza = null;
        this.cola = null;
        this.tamanno = 0;
    }

    // --- Información Básica ---

    /**
     * Devuelve el número de elementos en la lista.
     * @return El tamaño actual de la lista.
     */
    public int getTamanno() {
        return tamanno;
    }

    /**
     * Comprueba si la lista está vacía.
     * @return {@code true} si la lista no tiene elementos, {@code false} en caso contrario.
     */
    public boolean estaVacia() {
        return tamanno == 0;
    }
    
    /**
    * Devuelve el nodo cabeza (el primer nodo) de la lista.
    * @return El nodo cabeza, o {@code null} si la lista está vacía.
    */
    public NodoDoble<T> getCabeza() {
        return this.cabeza;
    }
    
    public void setCabeza(NodoDoble<T> nuevaCabeza) {
        this.cabeza = nuevaCabeza;

        // Si la nueva cabeza es null, la lista está vacía
        if (this.cabeza == null) {
            this.cola = null;
            this.tamanno = 0;
            return;
        }
    }

    // --- Métodos de Inserción ---

    /**
     * Inserta un elemento al principio de la lista (nueva cabeza).
     * Operación de tiempo constante O(1).
     * @param dato El dato a insertar.
     */
    public void insertarAlInicio(T dato) {
        NodoDoble<T> nuevoNodo = new NodoDoble<>(dato, null, this.cabeza); // Ant: null, Sig: cabeza antigua
        if (estaVacia()) {
            // Si es el primer nodo, es cabeza y cola
            this.cabeza = nuevoNodo;
            this.cola = nuevoNodo;
        } else {
            // La cabeza antigua ahora apunta hacia atrás al nuevo nodo
            this.cabeza.setAnterior(nuevoNodo);
            // El nuevo nodo es la nueva cabeza
            this.cabeza = nuevoNodo;
        }
        this.tamanno++;
    }

    /**
     * Inserta un elemento al final de la lista (nueva cola).
     * Operación de tiempo constante O(1).
     * @param dato El dato a insertar.
     */
    public void insertarAlFinal(T dato) {
        if (estaVacia()) {
            insertarAlInicio(dato); // Caso base: insertar en lista vacía
            return;
        }
        // El nuevo nodo apunta hacia atrás a la cola antigua, y no tiene siguiente
        NodoDoble<T> nuevoNodo = new NodoDoble<>(dato, this.cola, null);
        // La cola antigua ahora apunta hacia adelante al nuevo nodo
        this.cola.setSiguiente(nuevoNodo);
        // El nuevo nodo se convierte en la nueva cola
        this.cola = nuevoNodo;
        this.tamanno++;
    }

    /**
     * Alias conveniente para {@link #insertarAlFinal(Object)}.
     * @param dato El dato a agregar al final.
     */
    public void agregar(T dato) {
        insertarAlFinal(dato);
    }

    /**
     * Inserta un nuevo elemento {@code datoNuevo} inmediatamente después de la primera
     * ocurrencia del nodo que contiene {@code datoExistente}.
     * Si {@code datoExistente} no se encuentra, la lista no se modifica.
     * La búsqueda es O(n).
     *
     * @param datoExistente El dato del nodo referencia. Se compara usando {@code equals()}.
     * @param datoNuevo El dato a insertar.
     * @return {@code true} si la inserción fue exitosa, {@code false} si {@code datoExistente} no fue encontrado.
     */
    public boolean insertarDespuesDe(T datoExistente, T datoNuevo) {
        NodoDoble<T> nodoExistente = buscarNodo(datoExistente);
        if (nodoExistente == null) {
            return false; // Nodo referencia no encontrado
        }

        // Si se inserta después de la cola, es como insertar al final
        if (nodoExistente == this.cola) {
            insertarAlFinal(datoNuevo);
        } else {
            // El nodo que actualmente sigue a nodoExistente
            NodoDoble<T> nodoSiguienteOriginal = nodoExistente.getSiguiente();
            // Crear el nuevo nodo, enlazándolo correctamente
            NodoDoble<T> nuevoNodo = new NodoDoble<>(datoNuevo, nodoExistente, nodoSiguienteOriginal);
            // Actualizar punteros de los vecinos
            nodoExistente.setSiguiente(nuevoNodo);
            // nodoSiguienteOriginal no puede ser null aquí porque nodoExistente no era la cola
            nodoSiguienteOriginal.setAnterior(nuevoNodo);
            this.tamanno++;
        }
        return true;
    }

    /**
     * Inserta un nuevo elemento {@code datoNuevo} inmediatamente antes de la primera
     * ocurrencia del nodo que contiene {@code datoExistente}.
     * Si {@code datoExistente} es la cabeza, equivale a {@link #insertarAlInicio(Object)}.
     * Si {@code datoExistente} no se encuentra, la lista no se modifica.
     * La búsqueda es O(n).
     *
     * @param datoExistente El dato del nodo referencia. Se compara usando {@code equals()}.
     * @param datoNuevo El dato a insertar.
     * @return {@code true} si la inserción fue exitosa, {@code false} si {@code datoExistente} no fue encontrado.
     */
    public boolean insertarAntesDe(T datoExistente, T datoNuevo) {
        NodoDoble<T> nodoExistente = buscarNodo(datoExistente);
        if (nodoExistente == null) {
            return false; // Nodo referencia no encontrado
        }

        // Si se inserta antes de la cabeza, es como insertar al inicio
        if (nodoExistente == this.cabeza) {
            insertarAlInicio(datoNuevo);
        } else {
            // El nodo que actualmente precede a nodoExistente
            NodoDoble<T> nodoAnteriorOriginal = nodoExistente.getAnterior();
            // Crear el nuevo nodo, enlazándolo correctamente
            NodoDoble<T> nuevoNodo = new NodoDoble<>(datoNuevo, nodoAnteriorOriginal, nodoExistente);
            // Actualizar punteros de los vecinos
            // nodoAnteriorOriginal no puede ser null aquí porque nodoExistente no era la cabeza
            nodoAnteriorOriginal.setSiguiente(nuevoNodo);
            nodoExistente.setAnterior(nuevoNodo);
            this.tamanno++;
        }
        return true;
    }

    // --- Métodos de Eliminación ---

    /**
     * Elimina y devuelve el elemento al principio de la lista (cabeza).
     * Operación de tiempo constante O(1).
     *
     * @return El dato del elemento eliminado.
     * @throws NoSuchElementException si la lista está vacía.
     */
    public T eliminarAlInicio() {
        if (estaVacia()) {
            throw new NoSuchElementException("No se puede eliminar de una lista doble vacía.");
        }
        T datoEliminado = this.cabeza.getDato();
        NodoDoble<T> antiguaCabeza = this.cabeza;

        this.cabeza = this.cabeza.getSiguiente(); // Avanza la cabeza
        this.tamanno--;

        if (this.cabeza != null) {
            this.cabeza.setAnterior(null); // La nueva cabeza no tiene anterior
        } else {
            // Si la cabeza es null, la lista quedó vacía, la cola también debe ser null
            this.cola = null;
        }

        // Opcional: limpiar punteros del nodo eliminado para ayudar al GC
        antiguaCabeza.setSiguiente(null);

        return datoEliminado;
    }

    /**
     * Elimina y devuelve el elemento al final de la lista (cola).
     * Operación de tiempo constante O(1).
     *
     * @return El dato del elemento eliminado.
     * @throws NoSuchElementException si la lista está vacía.
     */
    public T eliminarAlFinal() {
        if (estaVacia()) {
            throw new NoSuchElementException("No se puede eliminar de una lista doble vacía.");
        }
        // Si solo hay un elemento, es igual que eliminar al inicio
        if (this.tamanno == 1) {
            return eliminarAlInicio();
        }

        T datoEliminado = this.cola.getDato();
        NodoDoble<T> antiguaCola = this.cola;

        this.cola = this.cola.getAnterior(); // Retrocede la cola
        this.cola.setSiguiente(null);       // La nueva cola no tiene siguiente
        this.tamanno--;

        // Opcional: limpiar punteros del nodo eliminado
        antiguaCola.setAnterior(null);

        return datoEliminado;
    }

    /**
     * Elimina la primera ocurrencia del elemento especificado {@code dato} de la lista.
     * Utiliza {@code equals()} para la comparación. La búsqueda es O(n).
     *
     * @param dato El dato del elemento a eliminar.
     * @return {@code true} si el elemento fue encontrado y eliminado, {@code false} en caso contrario.
     */
    public boolean eliminar(T dato) {
        NodoDoble<T> nodoAEliminar = buscarNodo(dato);
        if (nodoAEliminar == null) {
            return false; // No encontrado
        }
        // Si se encontró, eliminarlo usando el método auxiliar
        eliminarNodo(nodoAEliminar);
        return true;
    }

    /**
     * Elimina y devuelve el elemento que se encuentra inmediatamente después de la primera
     * ocurrencia del nodo que contiene {@code datoExistente}.
     * Si {@code datoExistente} no se encuentra, o si es la cola, no se elimina nada.
     * La búsqueda es O(n).
     *
     * @param datoExistente El dato del nodo referencia. Se compara usando {@code equals()}.
     * @return El dato del nodo eliminado, o {@code null} si no se pudo realizar la eliminación.
     */
    public T eliminarDespuesDe(T datoExistente) {
        NodoDoble<T> nodoExistente = buscarNodo(datoExistente);
        // No se puede eliminar si no se encuentra, o si es la cola, o si su siguiente es null (redundante con cola)
        if (nodoExistente == null || nodoExistente.getSiguiente() == null) {
            return null;
        }
        // El nodo a eliminar es el siguiente
        NodoDoble<T> nodoAEliminar = nodoExistente.getSiguiente();
        eliminarNodo(nodoAEliminar); // Usa el auxiliar que maneja todos los casos
        return nodoAEliminar.getDato();
    }

    /**
     * Elimina y devuelve el elemento que se encuentra inmediatamente antes de la primera
     * ocurrencia del nodo que contiene {@code datoExistente}.
     * Si {@code datoExistente} no se encuentra, o si es la cabeza, no se elimina nada.
     * La búsqueda es O(n).
     *
     * @param datoExistente El dato del nodo referencia. Se compara usando {@code equals()}.
     * @return El dato del nodo eliminado, o {@code null} si no se pudo realizar la eliminación.
     */
    public T eliminarAntesDe(T datoExistente) {
        NodoDoble<T> nodoExistente = buscarNodo(datoExistente);
        // No se puede eliminar si no se encuentra, o si es la cabeza, o si su anterior es null (redundante con cabeza)
        if (nodoExistente == null || nodoExistente.getAnterior() == null) {
            return null;
        }
        // El nodo a eliminar es el anterior
        NodoDoble<T> nodoAEliminar = nodoExistente.getAnterior();
        eliminarNodo(nodoAEliminar); // Usa el auxiliar
        return nodoAEliminar.getDato();
    }

    // --- Otras Utilidades ---

    /**
     * Elimina todos los elementos de la lista, dejándola vacía.
     * Anula las referencias cabeza y cola, y establece el tamaño a 0.
     */
    public void borrarLista() {
        // Podríamos iterar y anular todos los punteros, pero para Java,
        // simplemente perder la referencia a la cabeza y cola es suficiente
        // para que el GC actúe eventualmente.
        this.cabeza = null;
        this.cola = null;
        this.tamanno = 0;
    }

    /**
     * Crea y devuelve una copia superficial (shallow copy) de esta lista.
     * Se crean nuevos nodos {@link NodoDoble}, pero contienen referencias a los mismos
     * objetos de datos que la lista original.
     *
     * @return Una nueva instancia de {@code ListaEnlazadaDoble} con los mismos datos.
     */
    public ListaEnlazadaDoble<T> clonarLista() {
        ListaEnlazadaDoble<T> clon = new ListaEnlazadaDoble<>();
        NodoDoble<T> actual = this.cabeza;
        while (actual != null) {
            clon.insertarAlFinal(actual.getDato()); // Usar operación eficiente
            actual = actual.getSiguiente();
        }
        return clon;
    }

    /**
     * Imprime una representación textual de la lista en la consola estándar,
     * desde la cabeza hasta la cola.
     * Muestra los elementos separados por " &lt;-&gt; ", indicando HEAD y TAIL.
     */
    public void imprimir() {
        if (estaVacia()) {
            System.out.println("Lista Doble Vacía");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("HEAD <-> ");
        NodoDoble<T> actual = this.cabeza;
        while (actual != null) {
            sb.append(actual); // Usa NodoDoble.toString() -> dato.toString()
            actual = actual.getSiguiente();
            if (actual != null) {
                sb.append(" <-> ");
            }
        }
        sb.append(" <-> TAIL");
        System.out.println(sb.toString());
    }

    /**
     * Imprime una representación textual de la lista en la consola estándar,
     * desde la cola hasta la cabeza, demostrando el enlace inverso.
     * Muestra los elementos separados por " &lt;-&gt; ", indicando TAIL y HEAD.
     */
    public void imprimirReverso() {
        if (estaVacia()) {
            System.out.println("Lista Doble Vacía");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("TAIL <-> ");
        NodoDoble<T> actual = this.cola;
        while (actual != null) {
            sb.append(actual); // Usa NodoDoble.toString()
            actual = actual.getAnterior(); // Moverse hacia atrás
            if (actual != null) {
                sb.append(" <-> ");
            }
        }
        sb.append(" <-> HEAD");
        System.out.println(sb.toString());
    }

    // --- Métodos Auxiliares Privados ---

    /**
     * Busca el primer nodo en la lista que contiene el {@code datoBusqueda}.
     * Utiliza {@code Objects.equals()} para manejar {@code null} de forma segura.
     *
     * @param datoBusqueda El dato a buscar.
     * @return El {@link NodoDoble} que contiene el dato, o {@code null} si no se encuentra.
     */
    private NodoDoble<T> buscarNodo(T datoBusqueda) {
        NodoDoble<T> actual = this.cabeza;
        while (actual != null) {
            if (Objects.equals(actual.getDato(), datoBusqueda)) {
                return actual;
            }
            actual = actual.getSiguiente();
        }
        return null; // No encontrado
    }

    /**
     * Elimina el nodo especificado de la lista, actualizando correctamente
     * los enlaces de sus vecinos (si los tiene) y las referencias cabeza/cola
     * y el tamaño de la lista si es necesario.
     * Este método centraliza la lógica de eliminación de nodos.
     *
     * @param nodoAEliminar El nodo que se va a quitar de la lista (no debe ser null).
     */
    private void eliminarNodo(NodoDoble<T> nodoAEliminar) {
        // No verificar null aquí, se asume que viene de una búsqueda válida.

        // Identificar los vecinos
        NodoDoble<T> nodoAnterior = nodoAEliminar.getAnterior();
        NodoDoble<T> nodoSiguiente = nodoAEliminar.getSiguiente();

        // --- Actualizar enlaces de los vecinos ---
        if (nodoAnterior == null) {
            // Se está eliminando la cabeza
            this.cabeza = nodoSiguiente;
        } else {
            // El anterior ahora apunta al siguiente del nodo eliminado
            nodoAnterior.setSiguiente(nodoSiguiente);
        }

        if (nodoSiguiente == null) {
            // Se está eliminando la cola
            this.cola = nodoAnterior;
        } else {
            // El siguiente ahora apunta al anterior del nodo eliminado
            nodoSiguiente.setAnterior(nodoAnterior);
        }

        // --- Actualizar tamaño ---
        this.tamanno--;

        // --- Opcional: limpiar punteros del nodo eliminado ---
        nodoAEliminar.setAnterior(null);
        nodoAEliminar.setSiguiente(null);
        // nodoAEliminar.setDato(null); // No es estrictamente necesario
    }
}
