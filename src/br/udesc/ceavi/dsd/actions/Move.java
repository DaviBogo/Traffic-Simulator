/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.udesc.ceavi.dsd.actions;

import br.udesc.ceavi.dsd.model.road.Road;

/**
 *
 * @author davib
 */
public class Move implements ActionInterface {

    private Road beggining;
    private Road destination;

    public Move(Road beggining, Road destination) {
        this.beggining = beggining;
        this.destination = destination;
    }

    @Override
    public void execute() {
        destination.move(beggining.getCar());
        beggining.setCar(null);
        beggining.repaint();
        destination.repaint();
        beggining.release();
    }

  
}
