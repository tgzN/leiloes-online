import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {

    public void cadastrarProduto(ProdutosDTO produto) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = conectaDAO.connectDB(); // Use ConexaoDAO.getConnection()
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, produto.getNome());
            pstmt.setInt(2, produto.getValor());
            pstmt.setString(3, produto.getStatus());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            conectaDAO.closeConnection(conn, pstmt); // Feche a conexão
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = conectaDAO.connectDB();
            String sql = "SELECT * FROM produtos";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                listagem.add(produto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        } finally {
            conectaDAO.closeConnection(conn, pstmt, rs); // Feche a conexão
        }
        return listagem;
    }
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = conectaDAO.connectDB();
            String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                listagem.add(produto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + e.getMessage());
        } finally {
            conectaDAO.closeConnection(conn, pstmt, rs); // Feche a conexão
        }
        return listagem;
    }
    
    public void venderProduto(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        // para o commit :flushed:
        try {
            conn = conectaDAO.connectDB();
            String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
        } finally {
            conectaDAO.closeConnection(conn, pstmt);
        }
    }
    
}