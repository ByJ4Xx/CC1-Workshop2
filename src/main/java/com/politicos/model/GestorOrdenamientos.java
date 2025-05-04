/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politicos.model;

import java.util.Objects;

/**
 *
 * @author USUARIO
 */
public class GestorOrdenamientos {
    public static <T extends Comparable<T>> void ejecutar(
            String nombrePrueba,
            EstrategiaOrdenamiento<T> estrategia,
            ListaEnlazadaBase<T> listaOriginal,
            EstadisticasOrdenamiento estadisticas
    ) {
        Objects.requireNonNull(estrategia, "La estrategia no puede ser null");
        Objects.requireNonNull(listaOriginal, "La lista no puede ser null");

        System.out.println("\n--- Ordenando con " + nombrePrueba + " ---");

        try {
            if (listaOriginal instanceof ListaEnlazadaSimple) {
                ListaEnlazadaSimple<T> clon = (ListaEnlazadaSimple<T>) listaOriginal.clonarLista();
                estadisticas.agregarResultado(clon.getTamanno(), nombrePrueba, estrategia.ordenar(clon));
                System.out.println("Resultado:");
                clon.imprimir();
                clon = null;
                System.gc();
            } else if (listaOriginal instanceof ListaEnlazadaDoble) {
                ListaEnlazadaDoble<T> clon = (ListaEnlazadaDoble<T>) listaOriginal.clonarLista();
                estadisticas.agregarResultado(clon.getTamanno(), nombrePrueba, estrategia.ordenar(clon));
                clon.imprimir();
                clon = null;
                System.gc();
            } else if (listaOriginal instanceof ListaEnlazadaSimpleCircular) {
                ListaEnlazadaSimpleCircular<T> clon = (ListaEnlazadaSimpleCircular<T>) listaOriginal.clonarLista();
                estadisticas.agregarResultado(clon.getTamanno(), nombrePrueba, estrategia.ordenar(clon));
                clon.imprimir();
                clon = null;
                System.gc();
            } else {
                throw new IllegalArgumentException("Tipo de lista no soportado: " + listaOriginal.getClass().getSimpleName());
            }

        } catch (UnsupportedOperationException usoEx) {
            System.err.println("ERROR: La estrategia '" + nombrePrueba + "' no es compatible. " + usoEx.getMessage());
        } catch (Exception e) {
            System.err.println("ERROR inesperado en '" + nombrePrueba + "': " + e.getMessage());
            e.printStackTrace();
        }
    }
}
