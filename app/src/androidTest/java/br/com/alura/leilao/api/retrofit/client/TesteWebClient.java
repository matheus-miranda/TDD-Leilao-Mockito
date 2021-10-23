package br.com.alura.leilao.api.retrofit.client;

import java.io.IOException;

import br.com.alura.leilao.api.retrofit.TesteRetrofitInicializador;
import br.com.alura.leilao.api.retrofit.service.TestService;
import br.com.alura.leilao.model.Leilao;
import retrofit2.Call;
import retrofit2.Response;

public class TesteWebClient extends WebClient {

    private final TestService service;

    public TesteWebClient() {
        this.service = new TesteRetrofitInicializador().getTestService();
    }

    public Leilao salva(Leilao leilao) throws IOException {
        Call<Leilao> call = service.salva(leilao);
        Response<Leilao> resposta = call.execute();
        if (temDados(resposta)) {
            return resposta.body();
        }
        return null;
    }

    public boolean limpaBanco() throws IOException {
        Call<Void> call = service.limpaBanco();
        Response<Void> resposta = call.execute();
        return resposta.isSuccessful();
    }
}
