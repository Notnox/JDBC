package br.com.devinhouse.service;

import java.util.ArrayList;
import java.util.List;

import br.com.devinhouse.conexao.Conexao;
import br.com.devinhouse.dao.AtendimentoDAO;
import br.com.devinhouse.dto.Atendimento;

public class AtendimentoService {

    private AtendimentoDAO dao;
    private Conexao conexao;

    public AtendimentoService() {
        dao = new AtendimentoDAO();
        conexao = new Conexao();
    }

    public String inserirAtendimento(Atendimento atendimento) {
        String mensagemDeRetorno;

        dao.setAtendimento(atendimento);
        dao.setConexao(conexao);

        try {
            conexao.iniciarConexao();

            if (dao.inserir()) {
                conexao.confirmaTransacao();
                mensagemDeRetorno = "Atendimento inserido com sucesso!";
            } else {
                conexao.cancelaTransacao();
                mensagemDeRetorno = "Não foi possível inserir o atendimento!";
            }

            conexao.encerrarConexao();
            return mensagemDeRetorno;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String atualizarAtendimento(Atendimento atendimento) {
        String mensagemDeRetorno;

        dao.setAtendimento(atendimento);
        dao.setConexao(conexao);

        try {
            conexao.iniciarConexao();

            if (dao.atualizar()) {
                conexao.confirmaTransacao();
                mensagemDeRetorno = "Atendimento atualizado com sucesso!";
            } else {
                conexao.cancelaTransacao();
                mensagemDeRetorno = "Não foi possível atualizar o atendimento!";
            }

            conexao.encerrarConexao();
            return mensagemDeRetorno;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<Atendimento> retornaListaDeAtendimentos() {
        List<Atendimento> lista = new ArrayList<Atendimento>();

        dao.setConexao(conexao);

        try {
            conexao.iniciarConexao();

            lista = dao.getListaDeAtendimentos();

            conexao.encerrarConexao();

            return lista;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Atendimento> retornaListaDeAtendimentosComPacientesAtivos() {
        List<Atendimento> lista = new ArrayList<Atendimento>();

        dao.setConexao(conexao);

        try {
            conexao.iniciarConexao();

            lista = dao.getListaDeAtendimentosComPacientesAtivos();

            conexao.encerrarConexao();

            return lista;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String deletarAtendimento(Atendimento atendimento) {
        String mensagemDeRetorno;

        dao.setAtendimento(atendimento);
        dao.setConexao(conexao);

        try {
            conexao.iniciarConexao();

            if (dao.deletar()) {
                conexao.confirmaTransacao();
                mensagemDeRetorno = "Atendimento deletado com sucesso!";
            } else {
                conexao.cancelaTransacao();
                mensagemDeRetorno = "Não foi possível deletar o atendimento!";
            }

            conexao.encerrarConexao();
            return mensagemDeRetorno;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
