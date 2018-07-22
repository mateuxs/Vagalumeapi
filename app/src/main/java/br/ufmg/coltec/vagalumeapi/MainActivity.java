package br.ufmg.coltec.vagalumeapi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final VagalumeService service = new RetrofitConfig().getVagalumeService(); //setando o retrofit

        Button busca = (Button) findViewById(R.id.buscar);
        busca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView artistaView = findViewById(R.id.artista);
                TextView musicaView = findViewById(R.id.musica);
                String art = artistaView.getText().toString(); //o que o usuário digitou
                String mus = musicaView.getText().toString();

                //calling
                Call<MusicaDados> musicaDadosCall = service.searchLetra(art, mus);

                //quando houver resposta...
                musicaDadosCall.enqueue(new Callback<MusicaDados>() {
                    @Override
                    public void onResponse(Call<MusicaDados> call, Response<MusicaDados> response) {
                        try {
                            MusicaDados dadosMusica = response.body(); //obtém resposta
                            ArrayList<Musica> mus = dadosMusica.getMusica(); //a api nos retorna o array Musica, com apenas uma música
                            Musica nossaMusica = mus.get(0);  //na posição 0 está a musica que queremos
                            String letra = nossaMusica.getText(); //na string Text está a letra

                            TextView letraView = findViewById(R.id.letra);
                            letraView.setText(letra);
                        }catch (Exception e){
                            TextView letraView = findViewById(R.id.letra);
                            letraView.setText("Nenhuma música encontrada");
                        }
                    }

                    @Override
                    public void onFailure(Call<MusicaDados> call, Throwable t) {
                        TextView letraView = findViewById(R.id.letra);
                        letraView.setText("Falha na requisição.");

                    }
                });
            }
        });

    }
}
