package br.ufmg.coltec.vagalumeapi;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VagalumeService {
    @GET("search.php")
    Call <MusicaDados> searchLetra(@Query("art") String artista,
                                   @Query("mus") String musica);
}
