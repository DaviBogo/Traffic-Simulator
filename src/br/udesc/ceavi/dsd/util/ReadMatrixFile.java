package br.udesc.ceavi.dsd.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author davib
 */
public class ReadMatrixFile {

    private int[][] matrix;
    private String path;

    public ReadMatrixFile(String path) throws FileNotFoundException, Exception {
        this.path = path;
        readFile();
    }

    public int[][] getMatrix() throws Exception {
        if (matrix == null) {
            throw new Exception("Matriz não Instaciada");
        }
        return matrix;
    }

    private void readFile() throws FileNotFoundException, Exception {
        Scanner scanner = new Scanner(new File(path));

        try {
            int row = Integer.parseInt(scanner.nextLine());
            int column = Integer.parseInt(scanner.nextLine());
            matrix = new int[column][row];
        } catch (NumberFormatException e) {
            throw new Exception("Arquivo Sem Formatação Adequada");
        }

        int linhaIndex = 0;

        while (scanner.hasNext()) {
            String l = scanner.nextLine();
            String[] lv = l.split("	");

            for (int i = 0; i < lv.length; i++) {
                matrix[i][linhaIndex] = Integer.parseInt(lv[i]);
            }
            linhaIndex++;
        }

        scanner.close();
    }

}
