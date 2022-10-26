/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.udesc.ceavi.dsd.view;

/**
 *
 * @author davib
 */
public interface MatrixObserver {

    public void clearTableCell(int colunm, int row);

    public void printCar(int cor, int colunm, int row);

    public void repaint();
}
