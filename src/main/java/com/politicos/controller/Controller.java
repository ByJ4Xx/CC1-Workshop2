/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politicos.controller;

import com.politicos.model.GeneradorListasAleatorias;

/**
 *
 * @author USUARIO
 */
import com.politicos.model.*;

public class Controller {
    private EstadisticasOrdenamiento estadisticasAleatorioSimple;
    private EstadisticasOrdenamiento estadisticasAleatorioDoble;
    private EstadisticasOrdenamiento estadisticasAleatorioCircular;
    private EstadisticasOrdenamiento estadisticasOrdenadoSimple;
    private EstadisticasOrdenamiento estadisticasOrdenadoDoble;
    private EstadisticasOrdenamiento estadisticasOrdenadoCircular;
    private EstadisticasOrdenamiento estadisticasInversoSimple;
    private EstadisticasOrdenamiento estadisticasInversoDoble;
    private EstadisticasOrdenamiento estadisticasInversoCircular;
    
    public void run() {
        estadisticasAleatorioSimple = new EstadisticasOrdenamiento();
        estadisticasAleatorioDoble = new EstadisticasOrdenamiento();
        estadisticasAleatorioCircular = new EstadisticasOrdenamiento();
        estadisticasOrdenadoSimple = new EstadisticasOrdenamiento();
        estadisticasOrdenadoDoble = new EstadisticasOrdenamiento();
        estadisticasOrdenadoCircular = new EstadisticasOrdenamiento();
        estadisticasInversoSimple = new EstadisticasOrdenamiento();
        estadisticasInversoDoble = new EstadisticasOrdenamiento();
        estadisticasInversoCircular = new EstadisticasOrdenamiento();
        // 1. Generar listas ALEATORIAS (simple, doble, circular)
        System.out.println("\t\t\t==== LISTAS ALEATORIAS ====");
        GeneradorListasAleatorias generadorAleatorio = new GeneradorListasAleatorias();
        generadorAleatorio.generarListas(100, 1.5, estadisticasAleatorioSimple, estadisticasAleatorioDoble, estadisticasAleatorioCircular);
        System.out.println("\t\t\t==== LISTAS Inversas ====");
        GeneradorListasInversas generadorInverso = new GeneradorListasInversas();
        generadorInverso.generarListas(100, 1.5,estadisticasInversoSimple, estadisticasInversoDoble, estadisticasInversoCircular);
        System.out.println("\t\t\t==== LISTAS Ordenadas ====");
        GeneradorListasOrdenadas generadorOrdenado = new GeneradorListasOrdenadas();
        generadorOrdenado.generarListas(100, 1.5, estadisticasOrdenadoSimple, estadisticasOrdenadoDoble, estadisticasOrdenadoCircular);
    }
}
