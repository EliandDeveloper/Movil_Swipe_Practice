package com.example.tema_2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.view.MotionEvent;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private EditText editTextAmount;
    private TextView textViewResult;
    private RelativeLayout mainLayout;
    float initialX, initialY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editTextAmount = findViewById(R.id.editTextAmount);
        textViewResult = findViewById(R.id.textViewResult);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View rootView = findViewById(R.id.textViewSwipe);
        rootView.setOnTouchListener(this);

        View imgGradient = findViewById(R.id.imgGradient);
        imgGradient.setOnTouchListener(this);

        mainLayout = findViewById(R.id.main);
    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {

        float finalX = motionEvent.getX();
        float finalY = motionEvent.getY();

        int action = motionEvent.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                // Registra las coordenadas iniciales al tocar la pantalla
                initialX = finalX;
                initialY = finalY;

                return true;

            case MotionEvent.ACTION_UP:
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                // Calcula la diferencia entre las coordenadas iniciales y finales
                float deltaX = finalX - initialX;
                float deltaY = finalY - initialY;

                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    if (deltaX > 0 && v.getId() == R.id.textViewSwipe) {
                        // Movimiento de izquierda a derecha
                        Toast.makeText(context, "Izquierda a derecha", duration).show();

                        finish();

                    } else if (v.getId() == R.id.textViewSwipe) {
                        // Movimiento de derecha a izquierda
                        Toast.makeText(context, "Derecha a izquierda", duration).show();

                        editTextAmount.setText("");
                        textViewResult.setText("");
                    }
                } else {
                    if (deltaY > 0 && v.getId() == R.id.imgGradient) {

                        // Movimiento de arriba abajo
                        Toast.makeText(context, "Arriba abajo", duration).show();

                        // Calcular la transparencia en función del desplazamiento vertical
                        float transparency = Math.abs(deltaY) / mainLayout.getHeight();
                        // Cambiar el color de fondo del layout principal con un degradado entre blanco y negro
                        mainLayout.setBackgroundColor(Color.argb((int) (255 * transparency), 0, 0, 0));

                    } else if(v.getId() == R.id.imgGradient) {

                        // Movimiento de abajo arriba
                        Toast.makeText(context, "Abajo arriba", duration).show();

                        // Calcular la transparencia en función del desplazamiento vertical
                        float transparency = Math.abs(deltaY) / mainLayout.getHeight();
                        // Cambiar el color de fondo del layout principal con un degradado entre blanco y negro
                        mainLayout.setBackgroundColor(Color.argb((int) (255 * transparency), 255, 255, 255));
                    }
                }
                return true;
        }
        return super.onTouchEvent(motionEvent);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            calculateValues();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void calculateValues() {
        String cantidadToString = editTextAmount.getText().toString();
        if (!cantidadToString.isEmpty()) {
            int cantidadEntera = Integer.parseInt(cantidadToString);
            int cien = cantidadEntera / 100;
            cantidadEntera %= 100;
            int cincuenta = cantidadEntera / 50;
            cantidadEntera %= 50;
            int vente = cantidadEntera / 20;
            cantidadEntera %= 20;
            int diez = cantidadEntera / 10;
            cantidadEntera %= 10;
            int cinco = cantidadEntera / 5;
            cantidadEntera %= 5;
            int centavos = cantidadEntera;

            String resultado = "Billetes de 100: " + cien + "\n" +
                    "Billetes de 50: " + cincuenta + "\n" +
                    "Billetes de 20: " + vente + "\n" +
                    "Monedas de 10: " + diez + "\n" +
                    "Monedas de 5: " + cinco + "\n" +
                    "Centavos: " + centavos;

            textViewResult.setText(resultado);
        }
    }

}