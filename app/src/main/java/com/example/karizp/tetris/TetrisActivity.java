package com.example.karizp.tetris;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;


/**
 * Created by karizp on 05/03/2018.
 */

public class TetrisActivity  extends AppCompatActivity {

    private GridLayout gridTablero;
    private TableroTetris juegoTetris;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);

        final Handler handler = new Handler();

        juegoTetris=new TableroTetris();
        createTable();
        juegoTetris.generarPieza();
        updateTable();

        Runnable run = new Runnable() {
            @Override
            public void run() {


                if(juegoTetris.getPiezaActual().isLanded())
                {
                    juegoTetris.generarPieza();
                    Log.i("tablero", " se creo nueva pieza");
                }
                else
                {
                    juegoTetris.piezaCayendo();
                }

                updateTable();

                handler.postDelayed(this,1000);
            }
        };

        handler.post(run);

        //MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.tetris_song);
        //mediaPlayer.start();

    }

    public void createTable()
    {
        gridTablero = (GridLayout) findViewById(R.id.tablero);
        int row=gridTablero.getRowCount();
        int col=gridTablero.getColumnCount();

        for(int i=0;i<row;i++)
        {
            for (int j=0;j<col;j++)
            {
                ImageView block = new ImageView(this);
                block.setLayoutParams(new GridView.LayoutParams(55,55));
                block.setImageResource(R.drawable.block_gris);
                gridTablero.addView(block);
            }
        }

    }

    void updateTable()
    {
        gridTablero = (GridLayout) findViewById(R.id.tablero);
        int row=22;
        int col=14;

        for(int i=0;i<row;i++)
        {
            for (int j = 0; j < col; j++)
            {
                    int[][] matriz = juegoTetris.getMatrizBlock();

                    int color=matriz[i][j];
                    ImageView image = (ImageView) gridTablero.getChildAt(i*14+j);
                    Log.i("Hijos", " "+gridTablero.getChildCount());

                    if(image!=null)
                    {
                        switch (color) {
                            //gris
                            case 0:
                                image.setImageResource(R.drawable.block_gris);
                                break;
                            //amarillo
                            case 1:
                                image.setImageResource(R.drawable.block_amarillo);
                                break;
                            //naraja
                            case 2:

                                image.setImageResource(R.drawable.block_naranja);
                                break;
                            //rojo
                            case 3:
                                image.setImageResource(R.drawable.block_rojo);
                                break;
                            //morado
                            case 4:
                                image.setImageResource(R.drawable.block_morado);
                                break;
                            //azul
                            case 5:
                                image.setImageResource(R.drawable.block_azul);
                                break;
                            //celeste
                            case 6:
                                image.setImageResource(R.drawable.block_celeste);
                                break;
                            //verde
                            case 7:
                                image.setImageResource(R.drawable.block_verde);
                                break;
                        }
                    }
            }
        }
    }


}
