package br.com.alura.leilao.api.retrofit;

import br.com.alura.leilao.api.retrofit.service.TestService;

public class TesteRetrofitInicializador extends RetrofitInicializador {

    public TestService getTestService() {
        return retrofit.create(TestService.class);
    }
}
