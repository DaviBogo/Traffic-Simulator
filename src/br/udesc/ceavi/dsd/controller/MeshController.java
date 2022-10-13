/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.udesc.ceavi.dsd.controller;

import br.udesc.ceavi.dsd.model.Road;

/**
 *
 * @author davib
 */
public class MeshController {
    
    private int[][] matrix;
    private Road[][] roadMatrix;

    public MeshController(int[][] matrix) {
        this.matrix = matrix;
        this.roadMatrix = new Road[matrix.length][matrix[0].length];
    }
    
    
}
