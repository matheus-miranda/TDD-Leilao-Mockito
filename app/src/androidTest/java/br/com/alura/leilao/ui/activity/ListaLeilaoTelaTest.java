package br.com.alura.leilao.ui.activity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.model.Leilao;

public class ListaLeilaoTelaTest {

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> activity = new ActivityTestRule<>(ListaLeilaoActivity.class, true, false);

    @Test
    public void deveAparecerUmLeilao_QuandoCarregarUmLeilaoNaApi() throws IOException {
        LeilaoWebClient webClient = new LeilaoWebClient();

        boolean bancoNaoFoiLimpo = !webClient.limpaBanco();
        if (bancoNaoFoiLimpo) {
            Assert.fail("Banco de dados nao foi limpo");
        }

        Leilao carroSalvo = webClient.salva(new Leilao("Carro"));
        if (carroSalvo == null) {
            Assert.fail("Leilao nao foi salvo");
        }
        activity.launchActivity(new Intent());

        onView(withText("Casa")).check(matches(isDisplayed()));
    }
}