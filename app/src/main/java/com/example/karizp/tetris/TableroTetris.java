package com.example.karizp.tetris;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by karizp on 06/03/2018.
 */

public class TableroTetris {
    private int row=22;
    private int col=14;
    private boolean gameOver;
    private int[][] matrizBlock;
    private ArrayList<PiezaTetris> piezasJuego;

    private PiezaTetris piezaActual;

    TableroTetris(){
        this.gameOver = false;
        this.matrizBlock = new int[row][col];

        for(int i=0;i<row;i++)
        {
            for(int j=0;j<col;j++)
            {
                matrizBlock[i][j]=0;
            }
        }

        //Creacion de piezas
        int[][] cuadradoForm={{1,1},{1,1}};
        int[][][] cuadradoRot={{{1,1},{1,1}},{{1,1},{1,1}},{{1,1},{1,1}},{{1,1},{1,1}}};

        PiezaTetris cuadrado = new PiezaTetris(cuadradoForm,cuadradoRot,4);

        int[][] LForm={{1,0},
                       {1,0},
                       {1,1}};
        int[][][] LRot={{{1,0}, {1,0}, {1,1}},{{1,1,1},{1,0,0}},{{1,1},{0,1},{0,1}},{{0,0,1},{1,1,1}}};

        PiezaTetris L = new PiezaTetris(LForm,LRot,3);

        int[][] JForm={{0,1},
                       {0,1},
                       {1,1}};
        int[][][] JRot={{{0,1}, {0,1}, {1,1}},{{1,0,0},{1,1,1}},{{1,1},{1,0},{1,0}},{{1,1,1},{0,0,1}}};

        PiezaTetris J = new PiezaTetris(JForm,JRot,6);

        int[][] ZForm={{1,1,0},
                       {0,1,1}};
        int[][][] ZRot={{{1,1,0},{0,1,1}},{{0,1},{1,1},{1,0}}};

        PiezaTetris Z= new PiezaTetris(ZForm,ZRot,7);

        int[][] SForm={{0,1,1},
                       {1,1,0}};
        int[][][] SRot={{{0,1,1},{1,1,0}},{{1,0},{1,1},{0,1}}};

        PiezaTetris S= new PiezaTetris(SForm,SRot,5);

        int[][] TForm={{1,1,1},
                       {0,1,0}};
        int[][][] TRot={{{1,1,1},{0,1,0}},{{0,1},{1,1},{0,1}},{{0,1,0},{1,1,1}},{{1,0},{1,1},{1,0}}};

        PiezaTetris T= new PiezaTetris(TForm,TRot,1);

        int[][] IForm={{1},
                       {1},
                       {1},
                       {1}};
        int[][][] IRot={{{1},{1},{1},{1}},{{1,1,1,1}}};

        PiezaTetris I= new PiezaTetris(IForm,IRot,2);


        piezasJuego=new ArrayList<>();
        piezasJuego.add(cuadrado);
        piezasJuego.add(L);
        piezasJuego.add(J);
        piezasJuego.add(Z);
        piezasJuego.add(S);
        piezasJuego.add(T);
        piezasJuego.add(I);


    }


    public void generarPieza()
    {
        Random r = new Random();
        int i = r.nextInt(7 - 1) + 1;
        piezaActual=(PiezaTetris) piezasJuego.get(i).clone(); //aqui podria ser clone
        piezaActual.setLanded(false);

        for(int j=0;j<piezaActual.getForm().length;j++)
        {
            for(int k=0;k<piezaActual.getForm()[j].length;k++)
            {
                if(piezaActual.getForm()[j][k]!=0)
                {
                    int row=piezaActual.getTopLeft()[0];
                    int col=piezaActual.getTopLeft()[1];

                    matrizBlock[row+j][col+k]=piezaActual.getColor();
                }
            }
        }
    }

    public void quitarPieza()
    {
        for(int j=0;j<piezaActual.getForm().length;j++)
        {
            for(int k=0;k<piezaActual.getForm()[j].length;k++)
            {
                if(piezaActual.getForm()[j][k]!=0)
                {
                    int row=piezaActual.getTopLeft()[0];
                    int col=piezaActual.getTopLeft()[1];

                    matrizBlock[row+j][col+k]=0;
                }
            }
        }
    }

    public void pintarPieza()
    {
        for(int j=0;j<piezaActual.getForm().length;j++)
        {
            for(int k=0;k<piezaActual.getForm()[j].length;k++)
            {
                if(piezaActual.getForm()[j][k]!=0)
                {
                    int row=piezaActual.getTopLeft()[0];
                    int col=piezaActual.getTopLeft()[1];

                    matrizBlock[row+j][col+k]=piezaActual.getColor();
                }
            }
        }
    }

    public void piezaCayendo() {
        int[] newPos = piezaActual.getTopLeft().clone();
        newPos[0]++;
        piezaActual.setPotential(newPos);
        boolean spaceTaken = false;

        quitarPieza();

        int num=0;
        //comprueba si el espacio donde se va a mover esta ocupado
        for (int i = 0; i < piezaActual.getActualform().length; i++) {
            for (int j = 0; j < piezaActual.getActualform()[i].length; j++) {
                if (piezaActual.getActualform()[i][j] != 0) {
                    num = i + piezaActual.getPotential()[0];
                    Log.i("tablero", " " + num);
                    if (matrizBlock[i + piezaActual.getPotential()[0]][j + piezaActual.getPotential()[1]] != 0) {
                        spaceTaken = true;
                        break;
                    }else if(num==21)
                    {
                        piezaActual.setTopLeft(piezaActual.getPotential().clone());
                        spaceTaken = true;
                    }
                }
            }
        }
        Log.i("tablero", " " + spaceTaken);
        if (!spaceTaken) {
            piezaActual.setTopLeft(piezaActual.getPotential().clone());
        }
        else
        {
            piezaActual.setLanded(true);
        }
        pintarPieza();
    }


    public ArrayList<PiezaTetris> getPiezasJuego() {
        return piezasJuego;
    }

    public void setPiezasJuego(ArrayList<PiezaTetris> piezasJuego) {
        this.piezasJuego = piezasJuego;
    }

    public PiezaTetris getPiezaActual() {
        return piezaActual;
    }

    public void setPiezaActual(PiezaTetris piezaActual) {
        this.piezaActual = piezaActual;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int[][] getMatrizBlock() {
        return matrizBlock;
    }

    public void setMatrizBlock(int[][] matrizBlock) {
        this.matrizBlock = matrizBlock;
    }
}
