package com.example.karizp.tetris;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


/**
 * Created by karizp on 05/03/2018.
 */

public class TetrisActivity  extends AppCompatActivity {

    private GridLayout gridTablero;
    private TableroTetris juegoTetris;
    private Thread t1 = null;
    private Thread t2 = null;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);

        final Handler handler = new Handler();
        final Handler handlerUpdate = new Handler();

        juegoTetris=new TableroTetris();
        createTable();
        juegoTetris.generarPieza();

        Runnable run = new Runnable() {
            @Override
            public void run() {

                if(!juegoTetris.isGameOver()) {
                    if (juegoTetris.getPiezaActual().isLanded()) {
                        juegoTetris.FillLine();
                        juegoTetris.generarPieza();
                    } else {
                        juegoTetris.piezaCayendo();
                    }

                    if (juegoTetris.isGameOver()) {
                        gameOver();
                    }


                    handler.postDelayed(this, 600);
                }
            }
        };

        handler.post(run);


        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                updateTable();
                handlerUpdate.postDelayed(this,100);
            }
        };
        handlerUpdate.post(r2);

        mediaPlayer = MediaPlayer.create(this, R.raw.tetris_song);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

    }

    public void gameOver()
    {
        LinearLayout lGame = (LinearLayout)findViewById(R.id.layoutGameOver);
        lGame.setVisibility(View.VISIBLE);
        mediaPlayer.stop();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(TetrisActivity.this, MainActivity.class);
                startActivity(i);
            }
        }, 3000);


    }



    public void onBtnMoveRight(View view)
    {
        juegoTetris.moverPiezaDer();
    }

    public void onBtnMoveLeft(View view)
    {
        juegoTetris.moverPiezaIzq();
    }

    public void onBtnMoveDown(View view)
    {
        juegoTetris.moverPiezaAbajo();
    }

    public void onBtnRotate(View view)
    {
        juegoTetris.rotar();
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
        TextView tv = (TextView)findViewById(R.id.txtPuntuacion);
        String text = "Puntuación "+juegoTetris.getPuntuacion();

        tv.setText(text);

        gridTablero = (GridLayout) findViewById(R.id.tablero);
        int row=gridTablero.getRowCount();
        int col=gridTablero.getColumnCount();

        for(int i=0;i<row;i++)
        {
            for (int j = 0; j < col; j++)
            {
                    int[][] matriz = juegoTetris.getMatrizBlock();

                    int color=matriz[i][j];
                    ImageView image = (ImageView) gridTablero.getChildAt(i*col+j);
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



