package com.example.karizp.tetris;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by karizp on 06/03/2018.
 */

public class TableroTetris {
    private int row=22;
    private int col=12;
    private boolean gameOver;
    private int[][] matrizBlock;
    private ArrayList<PiezaTetris> piezasJuego;

    private PiezaTetris piezaActual;
    private int formaActual;
    private int rotActual;
    private int puntuacion;

    TableroTetris(){
        this.gameOver = false;
        this.matrizBlock = new int[row][col];
        puntuacion=0;

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
        int[][][] ZRot={{{1,1,0},{0,1,1}},{{0,1},{1,1},{1,0}},{{1,1,0},{0,1,1}},{{0,1},{1,1},{1,0}}};

        PiezaTetris Z= new PiezaTetris(ZForm,ZRot,7);

        int[][] SForm={{0,1,1},
                       {1,1,0}};
        int[][][] SRot={{{0,1,1},{1,1,0}},{{1,0},{1,1},{0,1}},{{0,1,1},{1,1,0}},{{1,0},{1,1},{0,1}}};

        PiezaTetris S= new PiezaTetris(SForm,SRot,5);

        int[][] TForm={{1,1,1},
                       {0,1,0}};
        int[][][] TRot={{{1,1,1},{0,1,0}},{{0,1},{1,1},{0,1}},{{0,1,0},{1,1,1}},{{1,0},{1,1},{1,0}}};

        PiezaTetris T= new PiezaTetris(TForm,TRot,1);

        int[][] IForm={{1},
                       {1},
                       {1},
                       {1}};
        int[][][] IRot={{{1},{1},{1},{1}},{{1,1,1,1}},{{1},{1},{1},{1}},{{1,1,1,1}}};

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
        int i = r.nextInt(7 ) ;


        Log.w("Forma"," "+i);

        piezaActual=(PiezaTetris) piezasJuego.get(i).clone(); //aqui podria ser clone
        piezaActual.setLanded(false);
        rotActual=0;
        formaActual=i;

        for(int j=0;j<piezaActual.getForm().length;j++)
        {
            for(int k=0;k<piezaActual.getForm()[j].length;k++)
            {
                if(piezaActual.getForm()[j][k]!=0)
                {
                    int row=piezaActual.getTopLeft()[0];
                    int col=piezaActual.getTopLeft()[1];

                    if(matrizBlock[row+j][col+k]==0)
                        matrizBlock[row+j][col+k]=piezaActual.getColor();
                    else {
                        gameOver = true;
                        break;
                    }
                }
            }
            if(gameOver)
                break;
        }
    }


    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void quitarPieza()
    {
        for(int j=0;j<piezaActual.getActualform().length;j++)
        {
            for(int k=0;k<piezaActual.getActualform()[j].length;k++)
            {
                if(piezaActual.getActualform()[j][k]!=0)
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
        for(int j=0;j<piezaActual.getActualform().length;j++)
        {
            for(int k=0;k<piezaActual.getActualform()[j].length;k++)
            {
                if(piezaActual.getActualform()[j][k]!=0)
                {
                    int row=piezaActual.getTopLeft()[0];
                    int col=piezaActual.getTopLeft()[1];

                    matrizBlock[row+j][col+k]=piezaActual.getColor();
                }
            }
        }

    }

    public void FillLine()
    {
        boolean isFilled=true;
        int i;
        for(i=row-1;i>=0;i--)
        {
            for (int j = 0; j < col; j++) {
                if (matrizBlock[i][j] == 0) {
                    isFilled = false;
                    break;
                } else {
                    isFilled = true;
                }
            }
            if (isFilled)
            {
                break;
            }
        }

        if(isFilled)
        {
            puntuacion+=1400;
            clearLine(i);
            FillLine();
        }

    }

    public void clearLine(int i)
    {
        //clear line
        for(int j=0;j<col;j++)
        {
            matrizBlock[i][j]=0;
        }
        //paint line
        if(i!=0) {
            for (int j = i - 1; j >= 0; j--) {
                for (int k = 0; k < col; k++) {
                    if (matrizBlock[j][k] != 0) {
                        if (matrizBlock[j+1][k]==0)
                        {
                            matrizBlock[j+1][k]=matrizBlock[j][k];
                            matrizBlock[j][k]=0;
                        }
                    }
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

        int num=newPos[0]+piezaActual.getActualform().length;
        if (!(num==row+1)) {
        //comprueba si el espacio donde se va a mover esta ocupado
        for (int i = 0; i < piezaActual.getActualform().length; i++) {
            for (int j = 0; j < piezaActual.getActualform()[i].length; j++) {
                if (piezaActual.getActualform()[i][j] != 0) {
                    num = i + piezaActual.getPotential()[0];
                    if (matrizBlock[i + piezaActual.getPotential()[0]][j + piezaActual.getPotential()[1]] != 0) {
                        spaceTaken = true;
                        break;
                    } else if (num == row-1) {
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
    }

    public void rotar()
    {
        boolean spaceTaken=false;
        int[] topLeft = piezaActual.getTopLeft();
        int[][] potentialShape=null;
        switch(rotActual){
            case 0:
                potentialShape =  piezasJuego.get(formaActual).getRotations()[1];
                break;
            case 1:
                potentialShape =  piezasJuego.get(formaActual).getRotations()[2];
                break;
            case 2:
                potentialShape =  piezasJuego.get(formaActual).getRotations()[3];
                break;
            case 3:
                potentialShape =  piezasJuego.get(formaActual).getRotations()[0];
                break;
        }

        quitarPieza();
        for (int i=0; i<potentialShape.length;i++)
        {
            for(int j=0; j<potentialShape[i].length;j++)
            {
                if(potentialShape[i][j]!=0)
                {
                    if(i+topLeft[0]<row-1 && j+topLeft[1]<col-1)
                    {
                        if (matrizBlock[i + topLeft[0]][j + topLeft[1]] != 0) {
                            spaceTaken = true;
                            break;
                        }
                    }
                    else
                    {
                        spaceTaken=true;
                    }
                }
            }
        }

        if(!spaceTaken)
        {
            piezaActual.setActualform(potentialShape);
            rotActual++;
            if(rotActual==4)
                rotActual=0;
        }
        pintarPieza();
        Log.w("Figura","Pinte Figura rotada");

    }

    public void moverPiezaDer()
    {
        int[] newPos = piezaActual.getTopLeft().clone();
        newPos[1]++;
        piezaActual.setPotential(newPos);
        boolean spaceTaken = false;
        int num;

        num=newPos[1]+piezaActual.getActualform()[0].length;
        if (!(num==col+1)) {

            quitarPieza();

            for (int i = 0; i < piezaActual.getActualform().length; i++) {
                for (int j = 0; j < piezaActual.getActualform()[i].length; j++) {
                    if (piezaActual.getActualform()[i][j] != 0) {
                        num = j + piezaActual.getPotential()[0];
                        if (matrizBlock[i + piezaActual.getPotential()[0]][j + piezaActual.getPotential()[1]] != 0) {
                            spaceTaken = true;
                            break;
                        } else if (num == col-2) {
                            piezaActual.setTopLeft(piezaActual.getPotential().clone());
                            spaceTaken = true;
                        }
                    }
                }
            }
            if (!spaceTaken) {
                piezaActual.setTopLeft(piezaActual.getPotential().clone());
            }
            pintarPieza();
        }
    }

    public void moverPiezaIzq()
    {
        int[] newPos = piezaActual.getTopLeft().clone();
        newPos[1]--;
        piezaActual.setPotential(newPos);
        boolean spaceTaken = false;
        int num;

        num=newPos[1];
        if (!(num==-1)) {

            quitarPieza();

            for (int i = 0; i < piezaActual.getActualform().length; i++) {
                for (int j = 0; j < piezaActual.getActualform()[i].length; j++) {
                    if (piezaActual.getActualform()[i][j] != 0) {

                        if (matrizBlock[i + piezaActual.getPotential()[0]][j + piezaActual.getPotential()[1]] != 0) {
                            spaceTaken = true;
                            break;
                        }
                    }
                }
            }
            if (!spaceTaken) {
                piezaActual.setTopLeft(piezaActual.getPotential().clone());
            }
            pintarPieza();
        }
    }

    public void moverPiezaAbajo()
    {
        int[] newPos = piezaActual.getTopLeft().clone();

        boolean spaceTaken = false,foundRow=false;
        int num,rowFree;

        num=newPos[0]+piezaActual.getActualform().length+1;

        quitarPieza();

        rowFree=row-piezaActual.getActualform().length;

        for(int i=rowFree; i>=0;i--)
        {
            newPos[0]=i;
            piezaActual.setPotential(newPos);
            for (int j = 0; j < piezaActual.getActualform().length; j++)
            {
                for (int k = 0; k < piezaActual.getActualform()[j].length; k++)
                {
                    if (piezaActual.getActualform()[j][k] != 0) {

                        if (matrizBlock[j + piezaActual.getPotential()[0]][k + piezaActual.getPotential()[1]] != 0)
                        {
                            spaceTaken=true;
                            break;
                        }
                        else
                        {
                            spaceTaken=false;
                        }
                    }
                }
                if(spaceTaken)
                    break;
            }
            if(!spaceTaken)
            {
                piezaActual.setLanded(true);
                piezaActual.setTopLeft(piezaActual.getPotential().clone());
                break;
            }
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
