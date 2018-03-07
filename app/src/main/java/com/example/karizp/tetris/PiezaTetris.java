package com.example.karizp.tetris;

import java.util.Random;

/**
 * Created by karizp on 06/03/2018.
 */

public class PiezaTetris {
    private int[][] form;
    private int[][] actualform;
    private int color;
    private int[][][] rotations;
    //la posicion se toma del bloque superior izquierdo de la figura
    private int[] topLeft=new int[2];
    private int[] potential=new int[2];

    public PiezaTetris(int[][] form, int[][][]rotations,int color)
    {
        this.form=form;
        this.rotations=rotations;
        actualform=form;
        topLeft[0]=0;
        topLeft[1]=6;
        this.color=color;

    }

    public void pintarPieza(int color)
    {
        for(int i=0;i<actualform.length;i++)
        {
            for(int j=0;i<actualform[i].length;j++)
            {
                if(actualform[i][j]!=0)
                {
                    actualform[i][j]=color;
                }
            }
        }
    }

    public int[][] getActualform() {
        return actualform;
    }

    public void setActualform(int[][] actualform) {
        this.actualform = actualform;
    }

    public int[] getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(int[] topLeft) {
        this.topLeft = topLeft;
    }

    public int[] getPotential() {
        return potential;
    }

    public void setPotential(int[] potential) {
        this.potential = potential;
    }

    public int[][] getForm() {
        return form;
    }

    public void setForm(int[][] form) {
        this.form = form;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int[][][] getRotations() {
        return rotations;
    }

    public void setRotations(int[][][] rotations) {
        this.rotations = rotations;
    }
}
