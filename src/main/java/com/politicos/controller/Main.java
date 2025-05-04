/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politicos.controller;

import javax.swing.SwingUtilities;

/**
 *
 * @author USUARIO
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller();
            controller.run();
            //VistaGUI vista = new VistaGUI(controller);
            //vista.mostrar();
        });
    }
}
