package br.com.devinhouse.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.devinhouse.conexao.Conexao;
import br.com.devinhouse.dto.Medico;
import br.com.devinhouse.interfaces.IGerenciamentoDAO;

public class MedicoDAO implements IGerenciamentoDAO {

    private Medico medico;
    private Conexao conexao;

    public Conexao getConexao() {
        return conexao;
    }

    public void setConexao(Conexao conexao) {
        this.conexao = conexao;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    @Override
    public boolean inserir() {
        try {
            PreparedStatement pst = conexao.getConexao()
                    .prepareStatement("INSERT INTO medico (nome, crm) VALUES(?, ?)");

            pst.setString(1, medico.getNome());
            pst.setString(2, medico.getCrm());

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
            PreparedStatement pst = conexao.getConexao()
                    .prepareStatement("UPDATE medico SET nome = ?, crm = ? WHERE codigo = ?");

            pst.setString(1, medico.getNome());
            pst.setString(2, medico.getCrm());
            pst.setInt(3, medico.getCodigo());

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

            PreparedStatement pst = conexao.getConexao()
                    .prepareStatement("UPDATE atendimento SET medico = ?, situacao = ? WHERE medico = ?");

            pst.setInt(1, 0);
            pst.setString(2, "Anulada");
            pst.setInt(3, medico.getCodigo());

            pst.executeUpdate();

            pst = conexao.getConexao().prepareStatement("DELETE FROM medico WHERE codigo = ?");

            pst.setInt(1, medico.getCodigo());

            pst.executeUpdate();

            pst.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Medico> getListaDeMedicos() {
        try {
            PreparedStatement pst = conexao.getConexao().prepareStatement("SELECT * FROM medico");

            ResultSet result = pst.executeQuery();

            List<Medico> lista = new ArrayList<Medico>();

            while (result.next()) {
                Medico medicoDaLista = new Medico();

                medicoDaLista.setCodigo(result.getInt("codigo"));
                medicoDaLista.setNome(result.getString("nome"));
                medicoDaLista.setCrm(result.getString("crm"));

                lista.add(medicoDaLista);
            }

            result.close();
            pst.close();
            return lista;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Medico getMedicoComMaisAtendimento() {
        try {
            PreparedStatement pst = conexao.getConexao().prepareStatement(
                    "SELECT medico, COUNT(medico) FROM atendimento GROUP BY medico HAVING COUNT (medico)=( SELECT MAX(mycount) FROM ( SELECT medico, COUNT(medico) mycount FROM atendimento GROUP BY medico) as conta)",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet result = pst.executeQuery();
            result.next();

            Medico medicoDaLista = new Medico();

            medicoDaLista.setCodigo(result.getInt("medico"));

            result.close();
            pst.close();
            return medicoDaLista;
        } catch (

        SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
