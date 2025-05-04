/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politicos.model;

/**
 *
 * @author USUARIO
 */
public class ResultadoOrdenamiento {
    public double tiempo;
    public long comparaciones;
    public long intercambios;

    public ResultadoOrdenamiento(double tiempo, long comparaciones, long intercambios) {
        this.tiempo = tiempo;
        this.comparaciones = comparaciones;
        this.intercambios = intercambios;
    }
}
