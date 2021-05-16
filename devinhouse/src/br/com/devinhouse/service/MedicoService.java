package br.com.devinhouse.service;

import java.util.ArrayList;
import java.util.List;

import br.com.devinhouse.conexao.Conexao;
import br.com.devinhouse.dao.MedicoDAO;
import br.com.devinhouse.dto.Medico;

public class MedicoService {

    private MedicoDAO dao;
    private Conexao conexao;

    public MedicoService() {
        dao = new MedicoDAO();
        conexao = new Conexao();
    }

    public String inserirMedico(Medico medico) {
        String mensagemDeRetorno;

        dao.setMedico(medico);
        dao.setConexao(conexao);

        try {
            conexao.iniciarConexao();

            if (dao.inserir()) {
                conexao.confirmaTransacao();
                mensagemDeRetorno = "Medico inserido com sucesso!";
            } else {
                conexao.cancelaTransacao();
                mensagemDeRetorno = "Não foi possível inserir o medico!";
            }

            conexao.encerrarConexao();
            return mensagemDeRetorno;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String atualizarMedico(Medico medico) {
        String mensagemDeRetorno;

        dao.setMedico(medico);
        dao.setConexao(conexao);

        try {
            conexao.iniciarConexao();

            if (dao.atualizar()) {
                conexao.confirmaTransacao();
                mensagemDeRetorno = "Medico atualizado com sucesso!";
            } else {
                conexao.cancelaTransacao();
                mensagemDeRetorno = "Não foi possível atualizar o medico!";
            }

            conexao.encerrarConexao();
            return mensagemDeRetorno;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<Medico> retornaListaDeMedicos() {
        List<Medico> lista = new ArrayList<Medico>();

        dao.setConexao(conexao);

        try {
            conexao.iniciarConexao();

            lista = dao.getListaDeMedicos();

            conexao.encerrarConexao();

            return lista;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String deletarMedico(Medico medico) {
        String mensagemDeRetorno;

        dao.setMedico(medico);
        dao.setConexao(conexao);

        try {
            conexao.iniciarConexao();

            if (dao.deletar()) {
                conexao.confirmaTransacao();
                mensagemDeRetorno = "Medico deletado com sucesso!";
            } else {
                conexao.cancelaTransacao();
                mensagemDeRetorno = "Não foi possível deletar o medico!";
            }

            conexao.encerrarConexao();
            return mensagemDeRetorno;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public Medico retornaMedicoComMaisAtendimento() {
        Medico medicoDaLista = new Medico();

        dao.setConexao(conexao);

        try {
            conexao.iniciarConexao();

            medicoDaLista = dao.getMedicoComMaisAtendimento();

            conexao.encerrarConexao();

            return medicoDaLista;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
