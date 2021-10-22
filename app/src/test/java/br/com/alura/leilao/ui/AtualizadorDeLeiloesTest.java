package br.com.alura.leilao.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter;

@RunWith(MockitoJUnitRunner.class)
public class AtualizadorDeLeiloesTest {

    @Mock
    private ListaLeilaoAdapter adapter;
    @Mock
    private LeilaoWebClient client;
    @Mock
    private AtualizadorDeLeiloes.ErroCarregaLeiloesListener listener;

    @Test
    public void deve_AtualizarListaDeLeiloes_QuandoBuscarLeiloesDaApi() {
        AtualizadorDeLeiloes atualizadorDeLeiloes = new AtualizadorDeLeiloes();
        doAnswer(invocation -> {
            RespostaListener<List<Leilao>> argument = invocation.getArgument(0);
            argument.sucesso(new ArrayList<>(Arrays.asList(
                    new Leilao("computador"),
                    new Leilao("console")
            )));
            return null;
        }).when(client).todos(ArgumentMatchers.any(RespostaListener.class));

        atualizadorDeLeiloes.buscaLeiloes(adapter, client, listener);

        verify(client).todos(any(RespostaListener.class));
        verify(adapter).atualiza(new ArrayList<>(Arrays.asList(
                new Leilao("computador"),
                new Leilao("console")
        )));
    }

    @Test
    public void deve_ApresentarMensagemDeFalha_QuandoFalharABuscaDeLeiloes() {
        AtualizadorDeLeiloes atualizador = new AtualizadorDeLeiloes();
        doAnswer(invocation -> {
            RespostaListener<List<Leilao>> argument = invocation.getArgument(0);
            argument.falha(anyString());
            return null;
        }).when(client).todos(any(RespostaListener.class));

        atualizador.buscaLeiloes(adapter, client, listener);

        verify(listener).erroAoCarregar(anyString());
    }
}