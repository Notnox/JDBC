package br.com.devinhouse.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.devinhouse.conexao.Conexao;
import br.com.devinhouse.dto.Atendimento;
import br.com.devinhouse.dto.Paciente;
import br.com.devinhouse.interfaces.IGerenciamentoDAO;
import br.com.devinhouse.service.AtendimentoService;

public class PacienteDAO implements IGerenciamentoDAO {

    private Paciente paciente;
    private Conexao conexao;

    public Conexao getConexao() {
        return conexao;
    }

    public void setConexao(Conexao conexao) {
        this.conexao = conexao;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public boolean inserir() {
        try {
            PreparedStatement pst = conexao.getConexao()
                    .prepareStatement("INSERT INTO paciente (nome, nascimento, email, telefone) VALUES(?, ?, ?, ?)");

            pst.setString(1, paciente.getNome());
            pst.setDate(2, paciente.getNascimento());
            pst.setString(3, paciente.getEmail());
            pst.setString(4, paciente.getTelefone());

            pst.executeUpdate();

            pst.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean inserirComAtendimento(int medico, String descricao, double valor, Date data) {
        AtendimentoService service = new AtendimentoService();
        Random gerador = new Random();

        try {
            PreparedStatement pst = conexao.getConexao()
                    .prepareStatement("INSERT INTO paciente (nome, nascimento, email, telefone) VALUES(?, ?, ?, ?)");

            pst.setString(1, paciente.getNome());
            pst.setDate(2, paciente.getNascimento());
            pst.setString(3, paciente.getEmail());
            pst.setString(4, paciente.getTelefone());

            pst.executeUpdate();
            pst.close();

            PreparedStatement pstrs = conexao.getConexao().prepareStatement("SELECT * FROM paciente", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = pstrs.executeQuery();
            
            result.last();

            Atendimento novoAtendimento = new Atendimento();
            novoAtendimento.setCodigo(gerador.nextInt(999));
            novoAtendimento.setPaciente(result.getInt("codigo"));
            novoAtendimento.setDescricao(descricao);
            novoAtendimento.setMedico(medico);
            novoAtendimento.setSituacao("Marcado");
            novoAtendimento.setValor(valor);
            novoAtendimento.setData(data);

            System.out.println(service.inserirAtendimento(novoAtendimento));

            result.close();
            pstrs.close();
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
                    "UPDATE paciente SET nome = ?, nascimento = ?, email = ?, telefone = ? WHERE codigo = ?");

            pst.setString(1, paciente.getNome());
            pst.setDate(2, paciente.getNascimento());
            pst.setString(3, paciente.getEmail());
            pst.setString(4, paciente.getTelefone());
            pst.setInt(5, paciente.getCodigo());

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
                    .prepareStatement("UPDATE atendimento SET paciente = ?, situacao = ? WHERE paciente = ?");

            pst.setInt(1, 0);
            pst.setString(2, "Anulada");
            pst.setInt(3, paciente.getCodigo());

            pst.executeUpdate();

            pst = conexao.getConexao().prepareStatement("DELETE FROM paciente WHERE codigo = ?");

            pst.setInt(1, paciente.getCodigo());

            pst.executeUpdate();

            pst.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Paciente> getListaDePacientes() {
        try {
            PreparedStatement pst = conexao.getConexao().prepareStatement("SELECT * FROM paciente");

            ResultSet result = pst.executeQuery();

            List<Paciente> lista = new ArrayList<Paciente>();

            while (result.next()) {
                Paciente pacienteDaLista = new Paciente();

                pacienteDaLista.setCodigo(result.getInt("codigo"));
                pacienteDaLista.setNome(result.getString("nome"));
                pacienteDaLista.setNascimento(result.getDate("nascimento"));
                pacienteDaLista.setEmail(result.getString("email"));
                pacienteDaLista.setTelefone(result.getString("telefone"));

                lista.add(pacienteDaLista);
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
