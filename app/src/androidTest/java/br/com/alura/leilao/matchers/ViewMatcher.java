package br.com.alura.leilao.matchers;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import br.com.alura.leilao.R;
import br.com.alura.leilao.formatter.FormatadorDeMoeda;

public class ViewMatcher {

    public static Matcher<? super View> apareceLeilao(int posicao, String descricaoEsperada, double maiorLanceEsperado) {

        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            final FormatadorDeMoeda formatador = new FormatadorDeMoeda();
            private final String maiorLanceFormatadoEsperado = formatador.formata(maiorLanceEsperado);

            @Override
            public void describeTo(Description description) {
                description.appendText("View com descricao").appendValue(descricaoEsperada)
                        .appendText(", maior lance ").appendValue(formatador.formata(maiorLanceEsperado))
                        .appendText(" na posicao ").appendValue(posicao);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                RecyclerView.ViewHolder viewHolderDevolvido = item.findViewHolderForAdapterPosition(posicao);
                if (viewHolderDevolvido == null) {
                    throw new IndexOutOfBoundsException("View holder na posicao " + posicao + " nao foi encontrado.");
                }

                View viewDoViewHolder = viewHolderDevolvido.itemView;
                boolean temDescricaoEsperada = verificaDescricaoEsperada(viewDoViewHolder);

                boolean temMaiorLanceEsperado = verificaMaiorLanceEsperado(viewDoViewHolder);

                return temDescricaoEsperada && temMaiorLanceEsperado;
            }

            private boolean verificaMaiorLanceEsperado(View viewDoViewHolder) {
                TextView textViewMaiorLance = viewDoViewHolder.findViewById(R.id.item_leilao_maior_lance);
                boolean temMaiorLanceEsperado = textViewMaiorLance.toString().equals(formatador.formata(maiorLanceEsperado));
                return temMaiorLanceEsperado;
            }

            private boolean verificaDescricaoEsperada(View viewDoViewHolder) {
                TextView textViewDescricao = viewDoViewHolder.findViewById(R.id.item_leilao_descricao);
                boolean temDescricaoEsperada = textViewDescricao.toString().equals(descricaoEsperada);
                return temDescricaoEsperada;
            }
        };
    }
}
