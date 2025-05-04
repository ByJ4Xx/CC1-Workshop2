/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politicos.model;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Implementación de una lista enlazada simple y circular genérica.
 * En esta estructura, el último nodo apunta de vuelta al primer nodo (cabeza).
 * Se utiliza una única referencia externa, {@code ultimo}, que apunta al último nodo,
 * lo que permite acceso O(1) a la cabeza ({@code ultimo.getSiguiente()}) y
 * operaciones O(1) de inserción al inicio y al final.
 * Utiliza la clase {@link Nodo}.
 *
 * @param <T> El tipo de elementos almacenados en la lista.
 * @see Nodo
 * @author devapps
 * @version 1.2
 */
public class ListaEnlazadaSimpleCircular<T> implements ListaEnlazadaBase<T>{

    /**
     * Referencia al último nodo de la lista circular.
     * Si la lista está vacía, es {@code null}.
     * Si la lista tiene un solo elemento, {@code ultimo} apunta a ese elemento,
     * y ese elemento apunta a sí mismo.
     * En una lista con más elementos, {@code ultimo.getSiguiente()} devuelve la cabeza.
     */
    public Nodo<T> ultimo;
    /** Número actual de elementos en la lista. */
    private int tamanno;

    /**
     * Construye una lista enlazada simple circular vacía.
     */
    public ListaEnlazadaSimpleCircular() {
        this.ultimo = null;
        this.tamanno = 0;
    }

    // --- Información Básica y Acceso Interno ---

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
     * Obtiene el nodo cabeza de la lista circular (el primer elemento).
     * El acceso es O(1) a través de la referencia {@code ultimo}.
     * @return El {@link Nodo} cabeza, o {@code null} si la lista está vacía.
     */
    public Nodo<T> getCabeza() {
        // Si no está vacía, la cabeza es el siguiente del último nodo.
        return estaVacia() ? null : this.ultimo.getSiguiente();
    }
    
    public void setCabeza(Nodo<T> nuevaCabeza) {
        if (estaVacia()) {
            this.ultimo = nuevaCabeza;
            if (this.ultimo != null) {
                this.ultimo.setSiguiente(this.ultimo); // Se apunta a sí mismo
            }
        } else {
            // Enlazar el nuevo nodo al principio
            nuevaCabeza.setSiguiente(this.ultimo.getSiguiente());
            this.ultimo.setSiguiente(nuevaCabeza);
        }
        recalcularUltimo();
    }
    
    public void recalcularUltimo() {
        if (estaVacia()) return;

        Nodo<T> actual = this.ultimo.getSiguiente();
        while (actual.getSiguiente() != this.ultimo.getSiguiente()) {
            actual = actual.getSiguiente();
        }
        this.ultimo = actual;
    }

    // --- Métodos de Inserción ---

    /**
     * Inserta un elemento al principio de la lista (se convierte en la nueva cabeza).
     * Operación de tiempo constante O(1).
     * @param dato El dato a insertar.
     */
    public void insertarAlInicio(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (estaVacia()) {
            // Primer nodo: es el último y apunta a sí mismo
            this.ultimo = nuevoNodo;
            this.ultimo.setSiguiente(this.ultimo);
        } else {
            // Nuevo nodo apunta a la antigua cabeza
            nuevoNodo.setSiguiente(this.ultimo.getSiguiente());
            // El último nodo existente ahora apunta al nuevo nodo (que es la nueva cabeza)
            this.ultimo.setSiguiente(nuevoNodo);
        }
        this.tamanno++;
    }

    /**
     * Inserta un elemento al final de la lista (se convierte en el nuevo {@code ultimo}).
     * Operación de tiempo constante O(1).
     * @param dato El dato a insertar.
     */
    public void insertarAlFinal(T dato) {
        // Estrategia: insertar al inicio y luego hacer que el nuevo nodo sea el 'ultimo'.
        insertarAlInicio(dato);
        // Después de insertarAlInicio, el nuevo nodo es la cabeza (ultimo.getSiguiente()).
        // Si la lista ahora tiene más de un elemento, debemos actualizar 'ultimo'
        // para que apunte a este nuevo nodo que acabamos de insertar.
        if (this.tamanno > 1) { // No necesario si era el primer nodo
            this.ultimo = this.ultimo.getSiguiente();
        }
        // Si era el primer nodo, 'ultimo' ya apuntaba a él desde insertarAlInicio.
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
        Nodo<T> nodoExistente = buscarNodo(datoExistente);
        if (nodoExistente == null) {
            return false; // Nodo referencia no encontrado
        }

        Nodo<T> nuevoNodo = new Nodo<>(datoNuevo);
        // Enlazar el nuevo nodo después del existente
        nuevoNodo.setSiguiente(nodoExistente.getSiguiente());
        nodoExistente.setSiguiente(nuevoNodo);

        // Si insertamos después del que era el último nodo, el nuevo nodo es el nuevo último
        if (nodoExistente == this.ultimo) {
            this.ultimo = nuevoNodo;
        }
        this.tamanno++;
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
        if (estaVacia()) {
            return false;
        }

        // Comprobar si se inserta antes de la cabeza
        Nodo<T> cabeza = getCabeza();
        if (Objects.equals(cabeza.getDato(), datoExistente)) {
            insertarAlInicio(datoNuevo);
            return true;
        }

        // Buscar el nodo *anterior* al que contiene datoExistente
        Nodo<T> nodoAnterior = buscarNodoAnterior(datoExistente);
        if (nodoAnterior == null) {
            // No se encontró (y no era la cabeza)
            return false;
        }

        // Insertar después de nodoAnterior
        Nodo<T> nuevoNodo = new Nodo<>(datoNuevo, nodoAnterior.getSiguiente());
        nodoAnterior.setSiguiente(nuevoNodo);
        this.tamanno++;
        return true;
    }

    // --- Métodos de Eliminación ---

    /**
     * Elimina y devuelve el elemento al principio de la lista (la cabeza).
     * Operación de tiempo constante O(1).
     *
     * @return El dato del elemento eliminado.
     * @throws NoSuchElementException si la lista está vacía.
     */
    public T eliminarAlInicio() {
        if (estaVacia()) {
            throw new NoSuchElementException("No se puede eliminar de una lista circular vacía.");
        }

        Nodo<T> cabeza = getCabeza(); // El nodo a eliminar
        T datoEliminado = cabeza.getDato();

        if (this.ultimo == cabeza) { // Si solo había un elemento
            this.ultimo = null; // La lista queda vacía
        } else {
            // El último nodo salta la cabeza antigua y apunta a la nueva cabeza
            this.ultimo.setSiguiente(cabeza.getSiguiente());
        }
        this.tamanno--;
        // cabeza.setSiguiente(null); // Opcional: ayudar GC
        return datoEliminado;
    }

    /**
     * Elimina y devuelve el elemento al final de la lista (el {@code ultimo}).
     * Requiere recorrer la lista para encontrar el penúltimo nodo, por lo que es O(n).
     *
     * @return El dato del elemento eliminado.
     * @throws NoSuchElementException si la lista está vacía.
     */
    public T eliminarAlFinal() {
        if (estaVacia()) {
            throw new NoSuchElementException("No se puede eliminar de una lista circular vacía.");
        }
        if (this.tamanno == 1) { // Caso especial
            return eliminarAlInicio();
        }

        // Encontrar el nodo penúltimo (el que apunta al 'ultimo')
        Nodo<T> penultimo = buscarNodoAnterior(this.ultimo.getDato());
        // Este penultimo no debería ser null si tamanno > 1

        T datoEliminado = this.ultimo.getDato();
        // El penúltimo ahora apunta a la cabeza (saltando el antiguo 'ultimo')
        penultimo.setSiguiente(this.ultimo.getSiguiente());
        // El penúltimo se convierte en el nuevo 'ultimo'
        this.ultimo = penultimo;
        this.tamanno--;
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
        if (estaVacia()) {
            return false;
        }

        // Primero, verificar si el dato está en la cabeza
        Nodo<T> cabeza = getCabeza();
        if (Objects.equals(cabeza.getDato(), dato)) {
            eliminarAlInicio();
            return true;
        }

        // Si no está en la cabeza, buscar el nodo anterior
        Nodo<T> nodoAnterior = buscarNodoAnterior(dato);
        if (nodoAnterior == null) {
            // No se encontró (y no era la cabeza)
            return false;
        }

        // Si se encontró el anterior, su siguiente es el nodo a eliminar
        Nodo<T> nodoAEliminar = nodoAnterior.getSiguiente();
        // Hacer que el anterior salte sobre el nodo a eliminar
        nodoAnterior.setSiguiente(nodoAEliminar.getSiguiente());

        // Si el nodo eliminado era el 'ultimo', actualizar la referencia 'ultimo'
        if (nodoAEliminar == this.ultimo) {
            this.ultimo = nodoAnterior;
        }
        this.tamanno--;
        return true;
    }

    /**
     * Elimina y devuelve el elemento que se encuentra inmediatamente después de la primera
     * ocurrencia del nodo que contiene {@code datoExistente}.
     * Si {@code datoExistente} no se encuentra, o si es el único nodo, no se elimina nada.
     * La búsqueda es O(n).
     *
     * @param datoExistente El dato del nodo referencia. Se compara usando {@code equals()}.
     * @return El dato del nodo eliminado, o {@code null} si no se pudo realizar la eliminación.
     */
    public T eliminarDespuesDe(T datoExistente) {
        Nodo<T> nodoExistente = buscarNodo(datoExistente);

        // No se puede eliminar si no se encuentra o si es el único nodo
        if (nodoExistente == null || this.tamanno <= 1) {
            return null;
        }

        Nodo<T> nodoAEliminar = nodoExistente.getSiguiente();
        T datoEliminado = nodoAEliminar.getDato();

        // Hacer que nodoExistente salte sobre nodoAEliminar
        nodoExistente.setSiguiente(nodoAEliminar.getSiguiente());

        // Si el nodo eliminado era el 'ultimo', nodoExistente pasa a ser el nuevo 'ultimo'
        if (nodoAEliminar == this.ultimo) {
            this.ultimo = nodoExistente;
        }
        this.tamanno--;
        return datoEliminado;
    }

    /**
     * Elimina y devuelve el elemento que se encuentra inmediatamente antes de la primera
     * ocurrencia del nodo que contiene {@code datoExistente}.
     * Requiere al menos dos nodos. Si {@code datoExistente} es la cabeza, se elimina el último nodo.
     * La búsqueda es O(n).
     *
     * @param datoExistente El dato del nodo referencia. Se compara usando {@code equals()}.
     * @return El dato del nodo eliminado, o {@code null} si no se pudo realizar la eliminación.
     */
    public T eliminarAntesDe(T datoExistente) {
        if (this.tamanno <= 1) { // Imposible eliminar antes con 0 o 1 nodo
            return null;
        }

        // Caso especial: eliminar antes de la cabeza es eliminar el último nodo
        if (Objects.equals(getCabeza().getDato(), datoExistente)) {
            return eliminarAlFinal();
        }

        // Buscar el nodo X tal que X -> (nodo_a_eliminar) -> (nodo_con_datoExistente)
        // Empezamos desde la cabeza, buscando la secuencia.
        Nodo<T> nodoAnteriorAlPrevio = this.ultimo; // Empezar en el que precede a la cabeza
        for (int i = 0; i < this.tamanno; i++) {
            Nodo<T> nodoPrevio = nodoAnteriorAlPrevio.getSiguiente(); // Posible nodo a eliminar
            Nodo<T> nodoActual = nodoPrevio.getSiguiente();           // Nodo donde buscar datoExistente

            if (Objects.equals(nodoActual.getDato(), datoExistente)) {
                // Encontrado! Hay que eliminar nodoPrevio.
                // Sabemos que nodoPrevio no es el último (porque nodoActual no es la cabeza).
                // ¿Podría ser nodoPrevio la cabeza? Sí.
                if (nodoPrevio == getCabeza()) {
                    return eliminarAlInicio(); // Equivalente
                } else {
                    // Eliminar nodoPrevio (que está en medio)
                    T datoEliminado = nodoPrevio.getDato();
                    nodoAnteriorAlPrevio.setSiguiente(nodoActual); // Bypass
                    this.tamanno--;
                    // Si nodoPrevio era 'ultimo', ya fue manejado arriba.
                    // Si nodoPrevio era cabeza, ya fue manejado.
                    return datoEliminado;
                }
            }
            // Avanzar el puntero 'anterior' para la siguiente iteración
            nodoAnteriorAlPrevio = nodoAnteriorAlPrevio.getSiguiente();
        }

        // Si el bucle termina, no se encontró la secuencia
        return null;
    }

    // --- Otras Utilidades ---

    /**
     * Elimina todos los elementos de la lista, dejándola vacía.
     * Anula la referencia {@code ultimo} y establece el tamaño a 0.
     */
    public void borrarLista() {
        this.ultimo = null;
        this.tamanno = 0;
    }

    /**
     * Crea y devuelve una copia superficial (shallow copy) de esta lista circular.
     * Se crean nuevos nodos {@link Nodo}, pero contienen referencias a los mismos
     * objetos de datos que la lista original.
     *
     * @return Una nueva instancia de {@code ListaEnlazadaSimpleCircular} con los mismos datos.
     */
    public ListaEnlazadaSimpleCircular<T> clonarLista() {
        ListaEnlazadaSimpleCircular<T> clon = new ListaEnlazadaSimpleCircular<>();
        if (estaVacia()) {
            return clon;
        }
        Nodo<T> actual = getCabeza();
        for (int i = 0; i < this.tamanno; i++) {
            clon.insertarAlFinal(actual.getDato()); // Usar la inserción eficiente
            actual = actual.getSiguiente();
        }
        return clon;
    }

    /**
     * Imprime una representación textual de la lista circular en la consola estándar.
     * Muestra los elementos desde la cabeza, indicando la conexión final a la cabeza.
     */
    public void imprimir() {
        if (estaVacia()) {
            System.out.println("Lista Circular Vacía");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("HEAD -> ");
        Nodo<T> actual = getCabeza();
        for (int i = 0; i < this.tamanno; i++) {
            sb.append(actual); // Usa Nodo.toString()
            actual = actual.getSiguiente();
            if (i < this.tamanno - 1) {
                sb.append(" -> ");
            }
        }
        sb.append(" -> (HEAD)"); // Indica circularidad
        System.out.println(sb.toString());
    }

    // --- Métodos Auxiliares Privados ---

    /**
     * Busca el primer nodo en la lista que contiene el {@code datoBusqueda}.
     * Utiliza {@code Objects.equals()} para manejar {@code null}.
     * Recorre la lista circular una vez.
     *
     * @param datoBusqueda El dato a buscar.
     * @return El {@link Nodo} que contiene el dato, o {@code null} si no se encuentra.
     */
    private Nodo<T> buscarNodo(T datoBusqueda) {
        if (estaVacia()) {
            return null;
        }
        Nodo<T> actual = getCabeza();
        for (int i = 0; i < this.tamanno; i++) {
            if (Objects.equals(actual.getDato(), datoBusqueda)) {
                return actual; // Encontrado
            }
            actual = actual.getSiguiente(); // Avanza
        }
        return null; // No encontrado después de una vuelta completa
    }

    /**
     * Busca el nodo que precede inmediatamente al primer nodo que contiene {@code datoBusqueda}.
     * Utiliza {@code Objects.equals()} para manejar {@code null}.
     * Recorre la lista circular una vez.
     *
     * @param datoBusqueda El dato contenido en el nodo objetivo (el nodo *después* del que buscamos).
     * @return El nodo predecesor, o {@code null} si {@code datoBusqueda} no se encuentra o está en la cabeza (no tiene predecesor único en este contexto).
     */
    private Nodo<T> buscarNodoAnterior(T datoBusqueda) {
        if (estaVacia() || this.tamanno == 1) {
            return null; // No hay nodo anterior si 0 o 1 elemento
        }

        Nodo<T> actual = this.ultimo; // Empezar desde el último nodo
        for (int i = 0; i < this.tamanno; i++) {
            // Comprobar si el *siguiente* de 'actual' contiene el dato
            if (Objects.equals(actual.getSiguiente().getDato(), datoBusqueda)) {
                // Si el siguiente es la cabeza, y estamos buscando el anterior a la cabeza,
                // devolvemos 'ultimo'. En otros casos, devolvemos 'actual'.
                 if (actual.getSiguiente() == getCabeza() && Objects.equals(getCabeza().getDato(), datoBusqueda)) {
                    // Estamos buscando el anterior a la cabeza
                    // En la definición estricta, el anterior es 'ultimo'.
                    // Pero si buscamos para *insertar antes* de la cabeza, es un caso especial.
                    // Si buscamos para *eliminar* (el nodo ANTES de la cabeza), eliminamos 'ultimo'.
                    // Devolver 'actual' (que es ultimo en este caso) es consistente.
                    // return this.ultimo; // O simplemente 'actual'
                 }
                 // Devolver el nodo actual, ya que su siguiente tiene el dato
                 return actual;
            }
            actual = actual.getSiguiente(); // Avanzar
        }
        return null; // No se encontró el dato en el nodo siguiente después de una vuelta
    }
}
