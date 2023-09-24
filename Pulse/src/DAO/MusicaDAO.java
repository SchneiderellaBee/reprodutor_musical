package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;
import model.Musica;

public class MusicaDAO {

	public static List<Musica> retornaMusicas(int codigoUsuario) {
	    List<Musica> listaMusicas = new ArrayList<>();
	    String sql = "SELECT TITULO, ENDERECO, DURACAO FROM MUSICA WHERE CODIGOUSUARIO = ?";

	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        ps = Conexao.getConnection().prepareStatement(sql);
	        ps.setInt(1, codigoUsuario); // Configura o código do usuário na consulta
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            String titulo = rs.getString("TITULO");
	            String endereco = rs.getString("ENDERECO");
	            float duracao = rs.getFloat("DURACAO");

	            Musica musica = new Musica();
	            musica.setTitulo(titulo);
	            musica.setEndereco(endereco);
	            musica.setDuracao(duracao);

	            listaMusicas.add(musica);
	        }
	    } catch (SQLException e) {
	        System.out.println("Falha na consulta de dados!");
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (ps != null) {
	                ps.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return listaMusicas;
	}

	
	public void cadastraMusica (Musica musica) {
		
		String sql = "INSERT INTO MUSICA (TITULO, ENDERECO, DURACAO, CODIGOUSUARIO) VALUES (?, ?, ?, ?)"; 
		
		PreparedStatement ps = null;
		
		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, musica.getTitulo());
			ps.setString(2, musica.getEndereco());
			ps.setFloat(3, musica.getDuracao());
			ps.setInt(4, musica.getCodigoUsuario());
			
			ps.execute();
			
			if (ps != null) {
                ps.close();
            }
			
		} catch (SQLException e) {
			System.out.println("Falha na inserção de dados!");
		}
	}

	public boolean validaMusica(String endereco, int codigoUsuario) {
	    String sql = "SELECT COUNT(*) FROM MUSICA WHERE ENDERECO = ? AND CODIGOUSUARIO = ?";
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        ps = Conexao.getConnection().prepareStatement(sql);
	        ps.setString(1, endereco);
	        ps.setInt(2, codigoUsuario);

	        rs = ps.executeQuery();

	        if (rs.next()) {
	            int count = rs.getInt(1);
	            return count == 0; 
	        }
	    } catch (SQLException e) {
	        System.out.println("Falha na consulta de dados!");
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (ps != null) {
	                ps.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
	}

	public boolean removeMusica(String titulo, int codigoUsuario) {
        String sql = "DELETE FROM MUSICA WHERE TITULO = ? AND CODIGOUSUARIO = ?";
        PreparedStatement ps = null;

        try {
        	ps = Conexao.getConnection().prepareStatement(sql);
            ps.setString(1, titulo);
            ps.setInt(2, codigoUsuario);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                return true; 
            }
        } catch (SQLException e) {
            System.out.println("Falha ao remover a música!");
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;  
    }

}