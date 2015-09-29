package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Recorrido;
import scala.Array;

public class RecorridoDAO {
	
	private static final String SQL_CONSULTA_RECORRIDOS = "SELECT id_recorrido, tipo, nombre FROM recorrido";
	private static final String SQL_INSERTAR_RECORRIDO = "INSERT INTO recorrido (tipo, nombre, hora_salida, dias_recorrido, lugar_salida, lugar_llegada) VALUES (%d,'%s','%s','%s','%s','%s')";
	
	public List<Recorrido> consultarRecorridos(Connection con)
	{
		List<Recorrido> lsRecorrido = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(SQL_CONSULTA_RECORRIDOS);
			rs = ps.executeQuery();
			while(rs.next())
			{
				Recorrido recorrido = new Recorrido();
				recorrido.setIdRecorrido(rs.getInt("id_recorrido"));
				recorrido.setIdRecorrido(rs.getInt("tipo"));
				recorrido.setNombre(rs.getString("nombre"));
				lsRecorrido.add(recorrido);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Connector.closeStatement(ps);
			Connector.closeResultSet(rs);
		}
		return lsRecorrido;
	}
	
	
	public String insertarRecorrido(Connection con, Recorrido recorrido) throws SQLException
	{
		String mensaje = "OK";
		PreparedStatement ps = null;
		String cadDias = "";
		
		for (String dia : recorrido.diasRecorrido) {
			cadDias += dia + ",";
		}
		
		try {
			ps = con.prepareStatement(String.format(SQL_INSERTAR_RECORRIDO, recorrido.getTipo(), recorrido.getNombre(), recorrido.getHoraSalida(), cadDias, recorrido.getLugarSalida(), recorrido.getLugarLlegada()));
			ps.executeUpdate();
		} catch (SQLException e) {
			mensaje = e.toString();
			e.printStackTrace();
			
		}finally{
			Connector.closeStatement(ps);
			Connector.closeConnection(con);
		}
		return mensaje;
	}
	
}
