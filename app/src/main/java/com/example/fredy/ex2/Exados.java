package com.example.fredy.ex2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Exados extends AppCompatActivity {
    //Declaración de variables globales
    Paint pincel = new Paint();
    float circCoordX = 0, circCoordY= 0;
    String mensaje="Sin Evento";
    Path ruta = new Path();
    int ancho, alto;
    int puntos,contador=1;
    int puntosj1,puntosj2;
    String turno="Jugador 1";

    //Primero debemos hacer una vista, seguidamente una clase donde irá el canvas y el pincel declarado anteriormente
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vista vista = new Vista(this);
        setContentView(vista);
        //Fragmento de código evento Touch
        setContentView(R.layout.activity_exados);
        LinearLayout layoutP = (LinearLayout) findViewById(R.id.activity_exados);
        Vista areaDibujo = new Vista (this);
        layoutP.addView(areaDibujo);

    }
    class Vista extends View {

        public Vista(Context context) {

            super(context);
        }

        public void onDraw(Canvas canvas) {

            canvas.drawRGB(25, 25, 25);

            //Obtenemos el ancho y alto de la pantalla a usar
            ancho = canvas.getWidth();
            alto = canvas.getHeight();

            //Dibujado de las guías
            pincel.setARGB(255,255,0,0);
            //eje x
            canvas.drawLine(0,alto/2,ancho - 1,alto/2,pincel);

            //eje y
            canvas.drawLine(ancho/2,0,ancho/2,alto-1,pincel);

            //Dibujado de los círculos
            pincel.setARGB(255,0,127,255);
            pincel.setStyle(Paint.Style.STROKE);
            int radio = ancho/2/5;

            //Pintado de los círculos por medio de array
            int [] color = Exados.this.getResources().getIntArray(R.array.colores);


            for (int cont=0, i=0;cont<= 5; i++ , cont++) {
                int radioActual=cont*radio;
                canvas.drawCircle(ancho / 2, alto / 2,radioActual, pincel);
                pincel.setColor(color[i]);}

            //Dibujado del texto por medio de array

            String [] texto ={
                    "","20","40","60","80","100",
            };
            pincel.setARGB(255,255,255,255);
            pincel.setStyle(Paint.Style.STROKE);

            for (int cont2=1;cont2 <= 5; cont2++) {

                canvas.drawText(texto[cont2], ancho / 2-(ancho/60), (cont2+2) * radio, pincel);
            }

            //Fragmento de código evento Touch para dibujar y ver mensajes de coordenas X,Y
            pincel.setAntiAlias(true);
            pincel.setColor(Color.CYAN);
            pincel.setStyle(Paint.Style.FILL);
            canvas.drawCircle(circCoordX,circCoordY, 20,pincel);
            pincel.setStyle(Paint.Style.STROKE);
            pincel.setTextSize(15);

            canvas.drawText("Jugador 1 =" + puntosj1 + "    " + "Jugador 2 =" + puntosj2, 0, this.getMeasuredHeight() - 25, pincel);
            canvas.drawText("Turno:   " + turno, 0, this.getMeasuredHeight()-alto+57,pincel);
            canvas.drawPath(ruta,pincel);
        }

        //Método para saber que turno es y saber quien es el ganador
        public void Turnos (int contador,int puntos) {


            if (contador%2==0){
                puntosj1=puntosj1+puntos;
                turno="Jugador 2";

            }else {
                puntosj2=puntosj2+puntos;
                turno="Jugador 1";
            }
            if(contador==21){
                if(puntosj1>puntosj2){
                    Toast.makeText(Exados.this, "GANA JUGADOR 1", Toast.LENGTH_LONG).show();
                    }
                if(puntosj2>puntosj1){
                    Toast.makeText(Exados.this, "GANA JUGADOR 2", Toast.LENGTH_LONG).show();
                }
                if(puntosj1==puntosj2) {
                    Toast.makeText(Exados.this, "EMPATE", Toast.LENGTH_LONG).show();
                }

            }
        }

        @Override
        public boolean onTouchEvent (MotionEvent evento) {

            circCoordX = evento.getX();
            circCoordY = evento.getY();


            if (evento.getAction() == MotionEvent.ACTION_DOWN){

            if(contador<=20) {
                //Fórmula para encontrar el ancho disponible y visible de los circulos
                double tocar = Math.sqrt(Math.pow(circCoordX - (ancho / 2), 2) + Math.pow(circCoordY - (alto / 2), 2));
                //Ciclos para hacer la suma de los puntos (el último if es en caso de darle afuera de los círculos)
                if (tocar < 50) {

                   puntos=100;
                }
                if (tocar > 50 & tocar < 100) {

                    puntos=80;
                }
                if (tocar > 100 & tocar < 150) {

                    puntos =60;
                }
                if (tocar > 150 & tocar < 200) {

                    puntos = 40;
                }
                if (tocar > 200 & tocar < 250) {

                    puntos = 20;
                }
                if (tocar > 250) {

                    puntos =  0;
                }
                //Contador en caso de llegar 20 se detiene, este mismo se usa en el método Turnos
                contador++;
                Turnos(contador,puntos);

            }
                //Importante poner en caso de no hacerlo seguira marcando los touch y aumentando el puntaje
                this.invalidate();
                return true;

            }

            //Para dejar de recibir valores mediante el touch
            return false;

        }

    }

}