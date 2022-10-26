/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.udesc.ceavi.dsd.view;

/**
 *
 * @author davib
 */
public interface MainFrameObserver {
    
    public void notifyCarNumber(int carNumber);

    public void notifySimulationOver();
            
}
