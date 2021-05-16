package br.com.devinhouse.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.devinhouse.conexao.Conexao;
import br.com.devinhouse.dto.Atendimento;
import br.com.devinhouse.interfaces.IGerenciamentoDAO;

public class AtendimentoDAO implements IGerenciamentoDAO {

    private Atendimento atendimento;
    private Conexao conexao;

    public Conexao getConexao() {
        return conexao;
    }

    public void setConexao(Conexao conexao) {
        this.conexao = conexao;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

    @Override
    public boolean inserir() {
        try {
            PreparedStatement pst = conexao.getConexao().prepareStatement(
                    "INSERT INTO atendimento VALUES(?, ?, ?, ?, ?, ?, ?)");

            pst.setInt(1, atendimento.getCodigo());
            pst.setInt(2, atendimento.getPaciente());
            pst.setString(3, atendimento.getDescricao());
            pst.setInt(4, atendimento.getMedico());
            pst.setString(5, atendimento.getSituacao());
            pst.setDouble(6, atendimento.getValor());
            pst.setDate(7, atendimento.getData());

            pst.executeUpdate();

            pst.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean atualizar() {
        try {
            PreparedStatement pst = conexao.getConexao().prepareStatement(
                    "UPDATE atendimento SET paciente = ?, descricao = ?, medico = ?, situacao = ?, valor = ?, data = ? WHERE codigo = ?");

            pst.setInt(1, atendimento.getPaciente());
            pst.setString(2, atendimento.getDescricao());
            pst.setInt(3, atendimento.getMedico());
            pst.setString(4, atendimento.getSituacao());
            pst.setDouble(5, atendimento.getValor());
            pst.setDate(6, atendimento.getData());
            pst.setInt(7, atendimento.getCodigo());

            pst.executeUpdate();

            pst.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletar() {
        try {

            PreparedStatement pst = conexao.getConexao().prepareStatement("DELETE FROM atendimento WHERE codigo = ?");

            pst.setInt(1, atendimento.getCodigo());

            pst.executeUpdate();

            pst.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Atendimento> getListaDeAtendimentos() {
        try {
            PreparedStatement pst = conexao.getConexao().prepareStatement("SELECT * FROM atendimento");

            ResultSet result = pst.executeQuery();

            List<Atendimento> lista = new ArrayList<Atendimento>();

            while (result.next()) {
                Atendimento atendimentoDaLista = new Atendimento();

                atendimentoDaLista.setPaciente(result.getInt("paciente"));
                atendimentoDaLista.setDescricao(result.getString("descricao"));
                atendimentoDaLista.setMedico(result.getInt("medico"));
                atendimentoDaLista.setSituacao(result.getString("situacao"));
                atendimentoDaLista.setValor(result.getDouble("valor"));
                atendimentoDaLista.setData(result.getDate("data"));

                lista.add(atendimentoDaLista);
            }

            result.close();
            pst.close();
            return lista;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Atendimento> getListaDeAtendimentosComPacientesAtivos() {
        try {
            PreparedStatement pst = conexao.getConexao().prepareStatement("SELECT paciente, medico FROM atendimento where atendimento.situacao != ?");

            pst.setString(1, "Anulada");

            ResultSet result = pst.executeQuery();

            List<Atendimento> lista = new ArrayList<Atendimento>();

            while (result.next()) {
                Atendimento atendimentoDaLista = new Atendimento();

                atendimentoDaLista.setPaciente(result.getInt("paciente"));
                atendimentoDaLista.setMedico(result.getInt("medico"));
                atendimentoDaLista.setSituacao(result.getString("situacao"));

                lista.add(atendimentoDaLista);
            }

            result.close();
            pst.close();
            return lista;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
