package com.politicos.model;

public interface ListaEnlazadaBase<T> {
    void insertarAlFinal(T dato);
    void insertarAlInicio(T dato);  // Asegúrate de implementarlo en todas las listas
    void imprimir();
    ListaEnlazadaBase clonarLista();
}
