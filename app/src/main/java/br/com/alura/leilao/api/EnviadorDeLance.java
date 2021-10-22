package br.com.alura.leilao.api;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.exception.LanceMenorQueUltimoLanceException;
import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLancesException;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.dialog.AvisoDialogManager;

public class EnviadorDeLance {

    private final LeilaoWebClient client;
    private final LanceProcessadoListener listener;
    private final AvisoDialogManager aviso;


    public EnviadorDeLance(LeilaoWebClient client,
                           LanceProcessadoListener listener,
                           AvisoDialogManager aviso) {
        this.client = client;
        this.listener = listener;
        this.aviso = aviso;
    }

    public void envia(final Leilao leilao, Lance lance) {
        try {
            leilao.propoe(lance);
            client.propoe(lance, leilao.getId(), new RespostaListener<Void>() {
                @Override
                public void sucesso(Void resposta) {
                    listener.processado(leilao);
                }

                @Override
                public void falha(String mensagem) {
                    aviso.mostraToastFalhaNoEnvio();
                }
            });
        } catch (LanceMenorQueUltimoLanceException exception) {
            aviso.mostraAvisoLanceMenorQueUltimoLance();
        } catch (LanceSeguidoDoMesmoUsuarioException exception) {
            aviso.mostraAvisoLanceSeguidoDoMesmoUsuario();
        } catch (UsuarioJaDeuCincoLancesException exception) {
            aviso.mostraAvisoUsuarioJaDeuCincoLances();
        }
    }

    public interface LanceProcessadoListener {
        void processado(Leilao leilao);
    }

}
