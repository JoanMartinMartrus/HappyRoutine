package com.example.happyroutine

import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.juang.jplot.PlotPlanitoXY


class Estadisticas_weight : AppCompatActivity() {

    var plot: PlotPlanitoXY ?=null
    var pantalla: LinearLayout?=null
    var context: Context?=null

    var X : FloatArray =FloatArray(4)
    var Y : FloatArray =FloatArray(4)

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas_weight)
        context=this;
        pantalla= findViewById(R.id.pantalla)

        X[0]=3.4f;Y[0]=2.5f;
        X[1]=11.3f;Y[1]=6.6f;
        X[2]=12.4f;Y[2]=7.6f;
        X[3]=20.9f;Y[3]=10.4f;

        plot = PlotPlanitoXY(context,"WEIGHT GRAPHIC","DAYS","WEIGHT");
        plot!!.SetSerie1(X,Y,"graph 1",5,true);// el 5 es el tamaño de punto "true" es para unir los puntos
        //con una linea
        /*antes de mostrar el grafico en pantalla(LinearLayout) deben de ir todos los ajustes "Set" del grafico.
        Todos los metodos publicos que ayudan a personalizar el grafico se describen cada uno en la siguiente sección */

        /*
        //agregando imagem.png al fondo de la cuadricula que esta en la carpeta "drawable" del proyecto.
        Drawable myDrawable = getResources().getDrawable(R.drawable.fneon);//debe cambiarse "fneon" por tu imagen
        Bitmap myFondo = ((BitmapDrawable) myDrawable).getBitmap();
        plot.SetImagFondo1(myFondo);
        */

        plot!!.SetHD(true); //ajustamos la calidad hd que suaviza bordes del grafico. por default esta desactivado
        plot!!.SetTouch(true);// activa el touch sobre el grafico no es necesario colocarlo ya que por default esta activado
        // this.pantalla.addView(plot!!.rootView)
    }
}