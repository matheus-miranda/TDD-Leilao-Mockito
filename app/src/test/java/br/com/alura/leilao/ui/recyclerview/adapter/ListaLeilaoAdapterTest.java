package br.com.alura.leilao.ui.recyclerview.adapter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import android.content.Context;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.alura.leilao.model.Leilao;

public class ListaLeilaoAdapterTest {

    @Test
    public void deve_AtualizarListaDeLeiloes_QuandoReceberListaDeLeiloes() {
        Context context = Mockito.mock(Context.class);
        ListaLeilaoAdapter adapter = Mockito.spy(new ListaLeilaoAdapter(context));
        Mockito.doNothing().when(adapter).atualizaLista();

        adapter.atualiza(new ArrayList<>(Arrays.asList(
                new Leilao("computador"),
                new Leilao("console")
        )));

        int quantidateLeiloesDevolvidas = adapter.getItemCount();

        Mockito.verify(adapter).atualizaLista();
        assertThat(quantidateLeiloesDevolvidas, is(2));
    }
}