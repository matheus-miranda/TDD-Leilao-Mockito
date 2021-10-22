package br.com.alura.leilao.ui;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.recyclerview.widget.RecyclerView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.alura.leilao.database.dao.UsuarioDAO;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter;

@RunWith(MockitoJUnitRunner.class)
public class AtualizadorDeUsuarioTest {

    @Mock
    private UsuarioDAO dao;
    @Mock
    private ListaUsuarioAdapter adapter;
    @Mock
    private RecyclerView recyclerView;

    @Test
    public void deve_AtualizarListaDeUsuario_QuandoSalvarUsuario() {
        AtualizadorDeUsuario atualizadorDeUsuario = new AtualizadorDeUsuario(dao, adapter, recyclerView);
        Usuario joao = new Usuario("Joao");
        when(dao.salva(joao)).thenReturn(new Usuario(1, "Joao"));
        when(adapter.getItemCount()).thenReturn(1);

        atualizadorDeUsuario.salva(joao);

        verify(dao).salva(new Usuario("Joao"));
        verify(adapter).adiciona(new Usuario(1, "Joao"));
        verify(recyclerView).smoothScrollToPosition(0);
    }
}