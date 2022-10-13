/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.udesc.ceavi.dsd.model;

/**
 *
 * @author davib
 */
public class Car extends Thread {
    
    private boolean alive;
    private Road road;
    
    public Car() {
        this.alive = true;
    }
    
    public void kill() {
        this.alive = false;
    }
}
