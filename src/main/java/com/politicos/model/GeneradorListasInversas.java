package com.politicos.model;

import java.time.LocalDate;
import java.util.Random;

public class GeneradorListasInversas {
    private static final Random random = new Random();
    private static final int ID_BASE = 1000;
    ListaEnlazadaBase<Politico> lista;
    
    public GeneradorListasInversas() {   
    }
    public void generarListas(int n, double tasaCrecimiento, EstadisticasOrdenamiento estadisticasSimple, EstadisticasOrdenamiento estadisticasDoble, EstadisticasOrdenamiento estadisticasCircular) {
        lista = new ListaEnlazadaSimple<>();
        System.out.println("\t\t==== Lista Simple ====");
        generador(n, tasaCrecimiento, lista, estadisticasSimple);
        limpiar(lista);
        lista = new ListaEnlazadaDoble<>();
        System.out.println("\t\t==== Lista Doble ====");
        generador(n, tasaCrecimiento, lista, estadisticasDoble);
        limpiar(lista);
        System.out.println("\t\t==== Lista Circular ====");
        lista = new ListaEnlazadaSimpleCircular();
        generador(n, tasaCrecimiento, lista, estadisticasCircular);
        limpiar(lista);
    }
    
    public void generador(int n, double tasaCrecimiento, ListaEnlazadaBase<Politico> lista, EstadisticasOrdenamiento estadisticas) {
        try {
            while (true) {
                generarInverso(lista, n);
                //Borrar despues
                lista.imprimir();
                // Llamamos a los metodos de ordenamiento
                GestorOrdenamientos.ejecutar("Burbuja", new OrdenamientoBurbuja<>(), lista, estadisticas);
                GestorOrdenamientos.ejecutar("Insersión", new OrdenamientoInsercion<>(), lista, estadisticas);
                GestorOrdenamientos.ejecutar("MergeSort", new OrdenamientoMerge<>(), lista, estadisticas);
                GestorOrdenamientos.ejecutar("QuickSort", new OrdenamientoQuickSort<>(), lista, estadisticas);
                //Eliminamos la referecnia al arreglo y liberamos memoria
                n = (int) Math.round(n*tasaCrecimiento);
                if (n>5) {
                    throw new OutOfMemoryError();
                }
            }
        } catch (OutOfMemoryError e) {
            System.err.println("Se ha agotado la memoria interna");
        }
    }

    protected void generarInverso(ListaEnlazadaBase<Politico> lista, int n) {
        int dineroBase = 100 + random.nextInt(5000);
        for (int i = 0; i < n; i++) {
            LocalDate fecha = LocalDate.of(
                random.nextInt(60) + 1940,
                random.nextInt(12) + 1,
                random.nextInt(28) + 1
            );
            lista.insertarAlInicio(new Politico(ID_BASE + i, dineroBase, fecha)); // Clave: insertar al inicio
            dineroBase += random.nextInt(5000);
        }
    }
    protected void limpiar (ListaEnlazadaBase<Politico> lista) {
        lista = null;
        System.gc();
    }
}
