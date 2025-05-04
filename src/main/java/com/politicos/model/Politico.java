/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politicos.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

/**
 * Representa a un político con sus atributos básicos.Implementa {@link Comparable}<{@link Politico}> para definir la ordenación natural por ID.
 * 
 * @author Samuel
 * @version 1.0
 */
public class Politico implements Comparable<Politico> {

    /** Formateador estándar para mostrar fechas de forma localizada y legible. */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

    /** Nombre completo del político */
    //private String nombre;
    /** Edad actual */
    //private int edad;
    /** Identificador único */
    private int id;
    /** Patrimonio económico estimado */
    private double dinero;
    /** Fecha de nacimiento */
    private LocalDate fechaNacimiento;

    /**
     * Construye una nueva instancia de Político.
     *
     * @param nombre Nombre completo. No debe ser null o vacío.
     * @param edad Edad actual (no negativa).
     * @param id Identificador único (no negativo).
     * @param dinero Patrimonio económico estimado.
     * @param fechaNacimiento Fecha de nacimiento (no debe ser null).
     * @throws NullPointerException si {@code nombre} o {@code fechaNacimiento} son null.
     * @throws IllegalArgumentException si {@code edad}, {@code peso} o {@code id} son negativos.
     */
    public Politico(int id, double dinero, LocalDate fechaNacimiento) {
        setId(id);
        this.dinero = dinero;
        setFechaNacimiento(fechaNacimiento);
    }

    // --- Getters ---
    //public String getNombre() { return nombre; }
    //public int getEdad() { return edad; }
    //public double getPeso() { return peso; }
    public int getId() { return id; }
    public double getDinero() { return dinero; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }

    // --- Setters ---
    /*
    public void setNombre(String nombre) {
        Objects.requireNonNull(nombre, "El nombre no puede ser null.");
        if (nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public void setEdad(int edad) {
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa.");
        }
        this.edad = edad;
    }

    public void setPeso(double peso) {
        if (peso < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo.");
        }
        this.peso = peso;
    }
    */
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El ID no puede ser negativo.");
        }
        this.id = id;
    }

    public void setDinero(double dinero) { this.dinero = dinero; }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = Objects.requireNonNull(fechaNacimiento, 
                              "La fecha de nacimiento no puede ser null.");
    }

    // --- Overrides ---

    /**
     * Devuelve una representación textual formateada del Politico.
     * @return Una cadena descriptiva del Politico.
     */
    @Override
    public String toString() {
        String fechaStr = fechaNacimiento.format(DATE_FORMATTER);
        return String.format("Político: ID: %d | Nacimiento: %s | Patrimonio: %.2f €", id, fechaStr, dinero);
    }

    /**
     * Compara este Politico con otro objeto para determinar igualdad.
     * @param o El objeto a comparar.
     * @return {@code true} si tienen el mismo ID, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Politico político = (Politico) o;
        return this.id == político.id;
    }

    /**
     * Genera un código hash para este Politico.
     * @return El código hash calculado basado en el ID.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Compara este Politico con otro basándose en su ID (orden numérico).
     *
     * @param other El otro Politico a comparar (no debe ser null).
     * @return Un entero negativo, cero o positivo si este ID es menor,
     *         igual o mayor que el de {@code other}.
     * @throws NullPointerException si {@code other} es null.
     */
    @Override
    public int compareTo(Politico other) {
        Objects.requireNonNull(other, "No se puede comparar con un Político null.");
        return Double.compare(this.dinero, other.dinero);
    }
}
