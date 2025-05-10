/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politicos.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiConsumer;

/**
 *
 * @author USUARIO
 */
public class GeneradorListas {
    private static final Random random = new Random();
    private static final int ID_BASE = 1000;
    ListaEnlazadaBase<Politico> lista;
    
    public GeneradorListas() {   
    }
    public void ejecutar(
        int n,
        double tasaCrecimiento,
        EstadisticasOrdenamiento estadisticasSimpleAleatorio,
        EstadisticasOrdenamiento estadisticasDobleAleatorio,
        EstadisticasOrdenamiento estadisticasCircularAleatorio,
        EstadisticasOrdenamiento estadisticasSimpleOrdenado,
        EstadisticasOrdenamiento estadisticasDobleOrdenado,
        EstadisticasOrdenamiento estadisticasCircularOrdenado,
        EstadisticasOrdenamiento estadisticasSimpleInverso,
        EstadisticasOrdenamiento estadisticasDobleInverso,
        EstadisticasOrdenamiento estadisticasCircularInverso
    ) {
        generador("Aleatorio", this::generarAleatorio, n, tasaCrecimiento,
            estadisticasSimpleAleatorio, estadisticasDobleAleatorio, estadisticasCircularAleatorio);

        generador("Ordenado", this::generarOrdenado, n, tasaCrecimiento,
            estadisticasSimpleOrdenado, estadisticasDobleOrdenado, estadisticasCircularOrdenado);

        generador("Inverso", this::generarInverso, n, tasaCrecimiento,
            estadisticasSimpleInverso, estadisticasDobleInverso, estadisticasCircularInverso);
    }

    private void generador(
        String tipo,
        BiConsumer<List<Politico>, Integer> generador,
        int n,
        double tasaCrecimiento,
        EstadisticasOrdenamiento estadisticasSimple,
        EstadisticasOrdenamiento estadisticasDoble,
        EstadisticasOrdenamiento estadisticasCircular
    ) {
        try {
            while (true) {
                System.out.println("\n\t======= " + tipo.toUpperCase() + " - n = " + n + " =======");

                // Paso 1: Generar datos base
                List<Politico> datos = new ArrayList<>();
                generador.accept(datos, n);

                // Paso 2: Lista Simple
                ListaEnlazadaSimple<Politico> listaSimple = new ListaEnlazadaSimple<>();
                cargarDatos(listaSimple, datos);
                listaSimple.imprimir();
                System.out.println("\t\t==== Lista Simple ====");
                ordenar(listaSimple, estadisticasSimple);
                limpiar(listaSimple);

                // Paso 3: Lista Doble
                ListaEnlazadaDoble<Politico> listaDoble = new ListaEnlazadaDoble<>();
                cargarDatos(listaDoble, datos);
                listaDoble.imprimir();
                System.out.println("\t\t==== Lista Doble ====");
                ordenar(listaDoble, estadisticasDoble);
                limpiar(listaDoble);

                // Paso 4: Lista Circular
                ListaEnlazadaSimpleCircular<Politico> listaCircular = new ListaEnlazadaSimpleCircular<>();
                cargarDatos(listaCircular, datos);
                listaCircular.imprimir();
                System.out.println("\t\t==== Lista Circular ====");
                ordenar(listaCircular, estadisticasCircular);
                limpiar(listaCircular);

                datos = null;
                System.gc();

                // Siguiente iteración
                n = (int) Math.round(n * tasaCrecimiento);
                // Descomentar el siguiente condicional si desea hacer pruebas o ver las estadisticas mas rapido
                /*
                if (n > 100000) {
                    throw new OutOfMemoryError();
                }
                */
            }
        } catch (OutOfMemoryError e) {
            System.err.println("Se ha agotado la memoria interna con tipo: " + tipo);
        }
    }

    private void cargarDatos(ListaEnlazadaBase<Politico> lista, List<Politico> datos) {
        for (Politico p : datos) {
            lista.insertarAlFinal(new Politico(p.getId(), p.getDinero(), p.getFechaNacimiento()));
        }
    }

    protected void generarAleatorio(List<Politico> datos, int n) {
        for (int i = 0; i < n; i++) {
            int dinero = 100 + random.nextInt(1_000_000);
            LocalDate fecha = LocalDate.of(
                random.nextInt(60) + 1940,
                random.nextInt(12) + 1,
                random.nextInt(28) + 1
            );
            datos.add(new Politico(ID_BASE + i, dinero, fecha));
        }
    }

    protected void generarInverso(List<Politico> datos, int n) {
        int dineroBase = 100 + random.nextInt(5000);
        for (int i = 0; i < n; i++) {
            LocalDate fecha = LocalDate.of(
                random.nextInt(60) + 1940,
                random.nextInt(12) + 1,
                random.nextInt(28) + 1
            );
            datos.add(0, new Politico(ID_BASE + i, dineroBase, fecha)); // insertar al inicio
            dineroBase += random.nextInt(5000);
        }
    }

    protected void generarOrdenado(List<Politico> datos, int n) {
        int dineroBase = 100 + random.nextInt(5000);
        for (int i = 0; i < n; i++) {
            LocalDate fecha = LocalDate.of(
                random.nextInt(60) + 1940,
                random.nextInt(12) + 1,
                random.nextInt(28) + 1
            );
            datos.add(new Politico(ID_BASE + i, dineroBase, fecha));
            dineroBase += random.nextInt(5000);
        }
    }

    protected void limpiar(ListaEnlazadaBase<Politico> lista) {
        lista = null;
        System.gc();
    }

    protected void ordenar(ListaEnlazadaBase<Politico> lista, EstadisticasOrdenamiento estadisticas) {
        GestorOrdenamientos.ejecutar("Burbuja", new OrdenamientoBurbuja<>(), lista, estadisticas);
        GestorOrdenamientos.ejecutar("Inserción", new OrdenamientoInsercion<>(), lista, estadisticas);
        GestorOrdenamientos.ejecutar("MergeSort", new OrdenamientoMerge<>(), lista, estadisticas);
        GestorOrdenamientos.ejecutar("QuickSort", new OrdenamientoQuickSort<>(), lista, estadisticas);
    }
}
