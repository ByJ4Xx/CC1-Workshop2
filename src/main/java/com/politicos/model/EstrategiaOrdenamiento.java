/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politicos.model;

/**
 /**
 * Interfaz funcional que define el contrato para las estrategias de ordenación
 * aplicables a una {@link ListaEnlazadaSimple}.
 * Utiliza el patrón Strategy para permitir intercambiar algoritmos de ordenación.
 *
 * @param <T> El tipo de elementos en la lista, se espera que implemente {@link Comparable}.
 * @author devapps
 * @version 1.1
 */
public interface EstrategiaOrdenamiento<T> {

    /**
     * Ordena la lista enlazada simple proporcionada "in situ" (modificando la estructura original).
     * Se asume que los elementos de tipo {@code T} son comparables entre sí.
     * La implementación específica del algoritmo puede modificar la referencia a la cabeza de la lista.
     *
     * @param lista La {@link ListaEnlazadaSimple} que será ordenada. No debe ser null.
     * @throws ClassCastException si los elementos de la lista no implementan {@link Comparable}
     *                            (aunque la verificación principal se hace antes de llamar a este método).
     * @throws NullPointerException si {@code lista} es null.
     */
    ResultadoOrdenamiento ordenar(ListaEnlazadaSimple<T> lista);
    ResultadoOrdenamiento ordenar(ListaEnlazadaDoble<T> lista);
    ResultadoOrdenamiento ordenar(ListaEnlazadaSimpleCircular<T> lista);
}