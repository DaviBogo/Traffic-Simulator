/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package br.udesc.ceavi.dsd;

import br.udesc.ceavi.dsd.view.MainFrame;
import java.awt.EventQueue;

/**
 *
 * @author davib
 */
public class TrafficSimulator {

    public static void main(String[] args) {
                EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
    
}
