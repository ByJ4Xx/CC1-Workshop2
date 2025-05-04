/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politicos.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author USUARIO
 */
public class EstadisticasOrdenamiento {
    
    // Clave: "Tamaño_Algoritmo", por ejemplo: "1000_QuickSort"
    private Map<String, ResultadoOrdenamiento> acumulados = new HashMap<>();
    private Map<String, Integer> conteos = new HashMap<>();

    public synchronized void agregarResultado(int tamanio, String algoritmo, ResultadoOrdenamiento resultado) {
        String clave = generarClave(tamanio, algoritmo);

        acumulados.putIfAbsent(clave, new ResultadoOrdenamiento(0, 0, 0));
        conteos.putIfAbsent(clave, 0);

        ResultadoOrdenamiento acumulado = acumulados.get(clave);
        acumulado.tiempo += resultado.tiempo;
        acumulado.comparaciones += resultado.comparaciones;
        acumulado.intercambios += resultado.intercambios;

        conteos.put(clave, conteos.get(clave) + 1);
    }

    private String generarClave(int tamanio, String algoritmo) {
        return tamanio + "_" + algoritmo;
    }

    public ResultadoOrdenamiento obtenerPromedio(int tamanio, String algoritmo) {
        String clave = generarClave(tamanio, algoritmo);
        ResultadoOrdenamiento acumulado = acumulados.get(clave);
        int conteo = conteos.getOrDefault(clave, 1); // evitar división por 0

        return new ResultadoOrdenamiento(
            acumulado.tiempo / conteo,
            acumulado.comparaciones / conteo,
            acumulado.intercambios / conteo
        );
    }
    
    public Map<String, ResultadoOrdenamiento> getPromedios() {
        Map<String, ResultadoOrdenamiento> promedios = new HashMap<>();
        Map<String, Integer> totalConteos = new HashMap<>();
        Map<String, ResultadoOrdenamiento> acumuladosGlobales = new HashMap<>();

        for (String clave : acumulados.keySet()) {
            String[] partes = clave.split("_");
            String algoritmo = partes[1];
            
            ResultadoOrdenamiento parcial = acumulados.get(clave);
            int conteo = conteos.get(clave);

            acumuladosGlobales.putIfAbsent(algoritmo, new ResultadoOrdenamiento(0, 0, 0));
            totalConteos.putIfAbsent(algoritmo, 0);

            ResultadoOrdenamiento global = acumuladosGlobales.get(algoritmo);
            global.tiempo += parcial.tiempo;
            global.comparaciones += parcial.comparaciones;
            global.intercambios += parcial.intercambios;

            totalConteos.put(algoritmo, totalConteos.get(algoritmo) + conteo);
        }

        for (String algoritmo : acumuladosGlobales.keySet()) {
            ResultadoOrdenamiento global = acumuladosGlobales.get(algoritmo);
            int total = totalConteos.getOrDefault(algoritmo, 1);
            promedios.put(algoritmo, new ResultadoOrdenamiento(
                global.tiempo / total,
                global.comparaciones / total,
                global.intercambios / total
            ));
        }

        return promedios;
    }

    public Map<Integer, Map<String, ResultadoOrdenamiento>> getDatos() {
        Map<Integer, Map<String, ResultadoOrdenamiento>> resultadoFinal = new HashMap<>();

        for (String clave : acumulados.keySet()) {
            String[] partes = clave.split("_");
            int tamanio = Integer.parseInt(partes[0]);
            String algoritmo = partes[1];

            ResultadoOrdenamiento promedio = obtenerPromedio(tamanio, algoritmo);

            resultadoFinal
                .computeIfAbsent(tamanio, k -> new HashMap<>())
                .put(algoritmo, promedio);
        }

        return resultadoFinal;
    }

    public void imprimirPromedios() {
        Map<Integer, Map<String, ResultadoOrdenamiento>> promedios = getDatos();

        for (Integer tamanio : promedios.keySet()) {
            System.out.println("Tamaño de arreglo: " + tamanio);
            for (Map.Entry<String, ResultadoOrdenamiento> entry : promedios.get(tamanio).entrySet()) {
                String algoritmo = entry.getKey();
                ResultadoOrdenamiento promedio = entry.getValue();
                System.out.printf("  [%s] Promedio -> Tiempo: %.2f ms, Comparaciones: %d, Intercambios: %d%n",
                    algoritmo, promedio.tiempo, promedio.comparaciones, promedio.intercambios);
            }
            System.out.println();
        }
    }
}
