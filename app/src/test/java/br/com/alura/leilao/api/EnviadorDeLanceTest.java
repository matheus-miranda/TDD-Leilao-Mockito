package br.com.alura.leilao.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLancesException;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.dialog.AvisoDialogManager;

@RunWith(MockitoJUnitRunner.class)
public class EnviadorDeLanceTest {

    private EnviadorDeLance enviadorDeLance;

    @Mock
    private LeilaoWebClient client;
    @Mock
    private EnviadorDeLance.LanceProcessadoListener listener;
    @Mock
    private AvisoDialogManager aviso;
    @Mock
    private Leilao leilao;

    @Test
    public void deve_MostrarMensagemDeFalha_QuandoLanceForMenorQueUltimoLance() {
        enviadorDeLance = new EnviadorDeLance(client, listener, aviso);
        Leilao leilao = new Leilao("Console");
        Lance joao = new Lance(new Usuario("Joao"), 200.0);

        leilao.propoe(joao);
        enviadorDeLance.envia(leilao, new Lance(new Usuario("Joana"), 100.0));

        verify(aviso).mostraAvisoLanceMenorQueUltimoLance();
    }

    @Test
    public void deve_MostrarMensagemDeFalha_QuandoUsuarioDerMaisDeCincoLances() {
        enviadorDeLance = new EnviadorDeLance(client, listener, aviso);
        doThrow(UsuarioJaDeuCincoLancesException.class)
                .when(leilao).propoe(any(Lance.class));

        enviadorDeLance.envia(leilao, new Lance(new Usuario("Joao"), 200.0));

        verify(aviso).mostraAvisoUsuarioJaDeuCincoLances();
    }

    @Test
    public void deve_MostrarMensagemDeFalha_QuandoOUsuarioDoUltimoLanceDerNovoLance() {
        enviadorDeLance = new EnviadorDeLance(client, listener, aviso);
        doThrow(LanceSeguidoDoMesmoUsuarioException.class).when(leilao).propoe(any(Lance.class));

        enviadorDeLance.envia(leilao, new Lance(new Usuario("Joao"), 200.0));

        verify(aviso).mostraAvisoLanceSeguidoDoMesmoUsuario();
        // Verifica que após chamar a exceção o propoe nao é mais chamado
        verify(client, never()).propoe(any(Lance.class), anyLong(), any(RespostaListener.class));
    }

    @Test
    public void deve_MostraMensagemDeFalha_QuandoFalharEnvioDeLanceParaAPI() {
        enviadorDeLance = new EnviadorDeLance(client, listener, aviso);
        doAnswer(invocation -> {
            RespostaListener<Void> argument = invocation.getArgument(2);
            argument.falha("");
            return null;
        }).when(client).propoe(any(Lance.class), anyLong(), any(RespostaListener.class));

        enviadorDeLance.envia(leilao, new Lance(new Usuario("Joao"), 200.0));

        verify(aviso).mostraToastFalhaNoEnvio();
        verify(listener, never()).processado(new Leilao("Computador"));
    }

    @Test
    public void deve_NotificarLanceProcessado_QuandoEnviarLanceParaAPIComSucesso() {

    }

}