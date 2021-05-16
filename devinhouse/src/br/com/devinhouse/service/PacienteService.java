package br.com.devinhouse.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.devinhouse.conexao.Conexao;
import br.com.devinhouse.dao.PacienteDAO;
import br.com.devinhouse.dto.Paciente;

public class PacienteService {

    private PacienteDAO dao;
    private Conexao conexao;

    public PacienteService() {
        dao = new PacienteDAO();
        conexao = new Conexao();
    }

    public String inserirPaciente(Paciente paciente) {
        String mensagemDeRetorno;

        dao.setPaciente(paciente);
        dao.setConexao(conexao);

        try {
            conexao.iniciarConexao();

            if (dao.inserir()) {
                conexao.confirmaTransacao();
                mensagemDeRetorno = "Paciente inserido com sucesso!";
            } else {
                conexao.cancelaTransacao();
                mensagemDeRetorno = "Não foi possível inserir o paciente!";
            }

            conexao.encerrarConexao();
            return mensagemDeRetorno;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String inserirPacienteComAtendimento(Paciente paciente, int medico, String descricao, double valor, Date data) {
        String mensagemDeRetorno;

        dao.setPaciente(paciente);
        dao.setConexao(conexao);

        try {
            conexao.iniciarConexao();

            if (dao.inserirComAtendimento(medico, descricao, valor, data)) {
                conexao.confirmaTransacao();
                mensagemDeRetorno = "Paciente inserido com sucesso!";
            } else {
                conexao.cancelaTransacao();
                mensagemDeRetorno = "Não foi possível inserir o paciente!";
            }

            conexao.encerrarConexao();
            return mensagemDeRetorno;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String atualizarPaciente(Paciente paciente) {
        String mensagemDeRetorno;

        dao.setPaciente(paciente);
        dao.setConexao(conexao);

        try {
            conexao.iniciarConexao();

            if (dao.atualizar()) {
                conexao.confirmaTransacao();
                mensagemDeRetorno = "Paciente atualizado com sucesso!";
            } else {
                conexao.cancelaTransacao();
                mensagemDeRetorno = "Não foi possível atualizar o paciente!";
            }

            conexao.encerrarConexao();
            return mensagemDeRetorno;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<Paciente> retornaListaDePacientes() {
        List<Paciente> lista = new ArrayList<Paciente>();

        dao.setConexao(conexao);

        try {
            conexao.iniciarConexao();

            lista = dao.getListaDePacientes();

            conexao.encerrarConexao();

            return lista;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String deletarPaciente(Paciente paciente) {
        String mensagemDeRetorno;

        dao.setPaciente(paciente);
        dao.setConexao(conexao);

        try {
            conexao.iniciarConexao();

            if (dao.deletar()) {
                conexao.confirmaTransacao();
                mensagemDeRetorno = "Paciente deletado com sucesso!";
            } else {
                conexao.cancelaTransacao();
                mensagemDeRetorno = "Não foi possível deletar o paciente!";
            }

            conexao.encerrarConexao();
            return mensagemDeRetorno;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
