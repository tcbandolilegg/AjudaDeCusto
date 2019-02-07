package br.com.ascenderideias.www.ajudadecusto;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class Principal extends AppCompatActivity {
    private final String PREFS_NAME;
    public float v_litro;
    public boolean calcular;
    EditText origem;
    EditText destino;
    EditText distancia;
    EditText valor_litro;
    EditText autonomia;
    float ajuda_custo;
    EditText m_ajuda_custo;
    float v1, v2;
    private View.OnClickListener meusBotoes = new View.OnClickListener() {
        public void onClick(View v) {
            float v1, v2;

            v1 = Float.parseFloat(distancia.getText().toString());
            v2 = Float.parseFloat(autonomia.getText().toString());

            if (v.getId() == R.id.bt_sair) {
                finish();
            } else {

                if (v_litro == 0) {
                    Toast.makeText(getBaseContext(), "Precisa escolher um Combustivel", Toast.LENGTH_SHORT).show();
                    calcular = false;
                } else {
                    calcular = true;
                }

                if (v1 == 0) {
                    Toast.makeText(getBaseContext(), "Precisa informar a distancia em Km", Toast.LENGTH_SHORT).show();
                    calcular = false;
                } else {
                    calcular = true;
                }

                if (v2 == 0) {
                    Toast.makeText(getBaseContext(), "Precisa informar a autonimia do veiculo", Toast.LENGTH_SHORT).show();
                    calcular = false;
                } else {
                    calcular = true;
                }

                if (calcular) {
                    switch (v.getId()) {
                        case R.id.bt_ida:
                            Toast.makeText(getBaseContext(), "Calculando", Toast.LENGTH_SHORT).show();
                            ajuda_custo = (v1 / v2) * v_litro;
                            m_ajuda_custo.setText(String.valueOf(ajuda_custo));
                            break;
                        case R.id.bt_idaevolta:
                            Toast.makeText(getBaseContext(), "Calculando", Toast.LENGTH_SHORT).show();
                            ajuda_custo = 2 * (v1 / v2) * v_litro;
                            m_ajuda_custo.setText(String.valueOf(ajuda_custo));
                            break;
                        case R.id.bt_sair:
                            finish();
                            break;
                        default:
                            break;
                    }
                }

            }


        }
    };

    public Principal() {
        PREFS_NAME = "CombPrecos";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        setTitle("Ajuda de Custo");
        //Bom mas por onde começar? Primeiro acesse o link https://console.developers.google.com/project e crie um projeto para o seu app, que ira utilizar o Google Maps Android API:

        //https://medium.com/android-dev-br/google-maps-api-android-por-onde-come%C3%A7ar-f7c6f71f3467
        //float[] results = new float[1];
        //Location.distanceBetween(oldPosition.latitude, oldPosition.longitude,
        //                newPosition.latitude, newPosition.longitude, results);

        //TODO usar MAPS
        origem = findViewById(R.id.get_cidorigem);  //TODO usar MAPS
        destino = findViewById(R.id.get_ciddestino);  //TODO usar MAPS
        distancia = findViewById(R.id.get_distkm);//TODO calcular com MAPS
        valor_litro = findViewById(R.id.v_vallitro); // é trazido da shared pref conforme a escolha do combustivel
        autonomia = findViewById(R.id.get_autonomia); // TODO criar bd de carros e autonomias conforme combustivel
        m_ajuda_custo = findViewById(R.id.calculo_ajudacusto);
        calcular = false;

        Button ida = findViewById(R.id.bt_ida);
        Button idaevolta = findViewById(R.id.bt_idaevolta);
        Button sair = findViewById(R.id.bt_sair);

        ida.setOnClickListener(meusBotoes);
        idaevolta.setOnClickListener(meusBotoes);
        sair.setOnClickListener(meusBotoes);


        final Spinner escolheComustivel = findViewById(R.id.spn_combustivel);
        ArrayAdapter<CharSequence> vinculo =
                ArrayAdapter.createFromResource(this, R.array.tipo_combustivel,
                        android.R.layout.simple_spinner_dropdown_item);
        escolheComustivel.setAdapter(vinculo);
        AdapterView.OnItemSelectedListener combescolhido = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = escolheComustivel.getSelectedItem().toString();
                SharedPreferences sharedPref;
                sharedPref = getSharedPreferences(PREFS_NAME, 0);
                v_litro = 0;
                switch (item) {
                    case "Gasolina":
                        v_litro = sharedPref.getFloat("val_gasolina", 0.0f);
                        calcular = true;
                        break;
                    case "Alcool":
                        v_litro = sharedPref.getFloat("val_alcool", 0.0f);
                        calcular = true;
                        break;
                    case "Diesel":
                        v_litro = sharedPref.getFloat("val_diesel", 0.0f);
                        calcular = true;
                        break;
                    case "Gnv":
                        v_litro = sharedPref.getFloat("val_gnv", 0.0f);
                        calcular = true;
                        break;
                    default:
                        v_litro = 0;
                        calcular = false;
                        break;
                }
                valor_litro.setText(String.valueOf(v_litro));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getBaseContext(), "Precisa escolher um Combustivel não entrou no switch", Toast.LENGTH_SHORT).show();
            }
        };
        escolheComustivel.setOnItemSelectedListener(combescolhido);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_compartilhar:
                Toast.makeText(this, "Fazer maneira de compartilhar", Toast.LENGTH_LONG).show(); // TODO IMPLEMENTAR O CAMPARTILHAR
                return true;
            case R.id.menu_faleconosco:
                Toast.makeText(this, "enviar e-mail ouo msn pelo whats", Toast.LENGTH_LONG).show(); // TODO IMPLEMENTAR envio de e-mail
                return true;
            case R.id.menu_atualiza:
                Toast.makeText(this, "Atualizar Preços", Toast.LENGTH_LONG).show(); // TODO IMPLEMENTAR O chamar novamente a activity de atualizacao
                return true;
            default:
                return super.onOptionsItemSelected(item);
            /// novo

        }
    }
}