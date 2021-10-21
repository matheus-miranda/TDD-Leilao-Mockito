package br.com.alura.leilao.ui.recyclerview.adapter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.alura.leilao.model.Leilao;

@RunWith(MockitoJUnitRunner.class)
public class ListaLeilaoAdapterTest {

    @Mock
    private Context context;
    @Spy
    private ListaLeilaoAdapter adapter = new ListaLeilaoAdapter(context);

    @Test
    public void deve_AtualizarListaDeLeiloes_QuandoReceberListaDeLeiloes() {
        doNothing().when(adapter).atualizaLista();

        adapter.atualiza(new ArrayList<>(Arrays.asList(
                new Leilao("computador"),
                new Leilao("console")
        )));

        int quantidateLeiloesDevolvidas = adapter.getItemCount();

        verify(adapter).atualizaLista();
        assertThat(quantidateLeiloesDevolvidas, is(2));
    }
}