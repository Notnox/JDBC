package br.com.devinhouse.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    final static String URL = "jdbc:postgresql://localhost:5432/clinica";
    final static String USER = "postgres";
    final static String PASS = "123";

    private static Connection conexao;

    public boolean iniciarConexao() throws Exception {
        try {
            if (conexao != null && !conexao.isClosed()) {
                return true;
            }
            Class.forName("org.postgresql.Driver");

            conexao = DriverManager.getConnection(URL, USER, PASS);
            conexao.setAutoCommit(false);
            conexao.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (ClassNotFoundException e) {
            throw new Exception("Driver não encontrado!");
        } catch (SQLException e) {
            throw new Exception("Falha: " + e.getMessage());
        }
        return true;
    }

    public boolean iniciarConexaoComParametros(String ip, String banco, String user, String pass) throws Exception {
        try {
            if (conexao != null && !conexao.isClosed()) {
                return true;
            }
            Class.forName("org.postgresql.Driver");

            StringBuilder url = new StringBuilder("jdbc:postgresql://").append(ip).append("/").append(banco);

            conexao = DriverManager.getConnection(url.toString(), user, pass);
            conexao.setAutoCommit(false);
            conexao.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (ClassNotFoundException e) {
            throw new Exception("Driver não encontrado!");
        } catch (SQLException e) {
            throw new Exception("Falha: " + e.getMessage());
        }
        return true;
    }

    public Connection getConexao() {
        return conexao;
    }

    public void encerrarConexao() throws Exception {
        try {
            if (conexao == null || conexao.isClosed()) {
                return;
            }
            conexao.close();
        } catch (SQLException e) {
            throw new Exception("Falha: " + e.getMessage());
        }
    }

    public void confirmaTransacao() throws Exception {
        try {
            if (conexao == null || conexao.isClosed()) {
                return;
            }
            conexao.commit();
        } catch (SQLException e) {
            throw new Exception("Falha: " + e.getMessage());
        }
    }

    public void cancelaTransacao() throws Exception {
        try {
            if (conexao == null || conexao.isClosed()) {
                return;
            }
            conexao.rollback();
        } catch (SQLException e) {
            throw new Exception("Falha: " + e.getMessage());
        }
    }
}
