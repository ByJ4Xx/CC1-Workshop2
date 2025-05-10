/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politicos.controller;

import com.politicos.model.GeneradorListas;

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
        // Generar listas y ordenar

        GeneradorListas generador = new GeneradorListas();
        generador.ejecutar(10, 1.5, estadisticasAleatorioSimple, estadisticasAleatorioDoble, estadisticasAleatorioCircular, estadisticasOrdenadoSimple, estadisticasOrdenadoDoble, estadisticasOrdenadoCircular, estadisticasInversoSimple, estadisticasInversoDoble, estadisticasInversoCircular);
    }
}
