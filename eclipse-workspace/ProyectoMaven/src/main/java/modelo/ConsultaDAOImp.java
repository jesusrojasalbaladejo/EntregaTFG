package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsultaDAOImp implements IConsultaDAO {

	Connection connect;

	private void connect() {
		try {

			connect = DriverManager.getConnection("jdbc:sqlite:src/main/resources/tfg.db");
			if (connect != null) {
				System.out.println("Conectado");
			}
		} catch (SQLException ex) {
			System.err.println("No se ha podido conectar a la base de datos\n" + ex.getMessage());
		}
	}

	private void close() {
		try {
			connect.close();
			System.out.println("desconectado");
		} catch (SQLException ex) {
			System.err.println("No se ha podido cerrar la base de datos\n" + ex.getMessage());
		}
	}

	@Override
	public List<Consulta> obtenerConsultas(Paciente p) {
		this.connect();
		List<Consulta> consultas = new ArrayList<Consulta>();
		ResultSet result = null;
		try {
			PreparedStatement st = connect.prepareStatement("select * from Consulta where paciente=? and borrado=0");
			st.setString(1, p.getDni());
			result = st.executeQuery();
			while (result.next()) {

				consultas.add(new Consulta(result.getInt("id"), p, result.getString("fecha"),
						result.getString("flexionMinima"), result.getString("flexionMaxima"), 
						result.getString("DesvCubMin"),
						result.getString("DesvCubMax")));

			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		this.close();
		return consultas;

	}

	@Override
	public Consulta obtenerConsulta(Paciente p, int id) {
		this.connect();
		Consulta c = null;
		ResultSet result = null;
		try {
			PreparedStatement st = connect.prepareStatement("select * from Consulta where paciente=? and id=? and  borrado=0");
			st.setString(1, p.getDni());
			st.setInt(2, id);
			result = st.executeQuery();
			while (result.next()) {

				c = (new Consulta(result.getInt("id"), p, result.getString("fecha"), result.getString("flexionMinima"),
						result.getString("flexionMaxima"), result.getString("DesvCubMin"),
						result.getString("DesvCubMax")));
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		this.close();
		return c;
	}

	public int obtenerUltimaConsulta() {
		this.connect();
		ResultSet result = null;
		int resultado = -1;
		try {
			PreparedStatement st = connect.prepareStatement("select max(id) a from Consulta where borrado=0");
			result = st.executeQuery();
			while (result.next()) {

				resultado = result.getInt("a");

			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		this.close();
		return resultado;
	}

	@Override
	public Consulta crearConsulta(Paciente paciente, String flexMax, String flexMin, String DesvCubMin,
			String DesvCubMax) {
		this.connect();
		String fecha = "";
		try {
			PreparedStatement st = connect.prepareStatement(
					"insert into consulta(paciente, fecha, flexionMinima, flexionMaxima, desvCubMin, desvCubMax) values (?,?,?,?,?,?)");
			st.setString(1, paciente.getDni());
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date(System.currentTimeMillis());
			fecha = formatter.format(date);
			st.setString(2, fecha);
			st.setString(3, flexMin);
			st.setString(4, flexMax);
			st.setString(5, DesvCubMin);
			st.setString(6, DesvCubMax);

			st.execute();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		this.close();
		int resultado = this.obtenerUltimaConsulta();
		if (resultado > -1)
			return new Consulta(resultado, paciente, fecha, (flexMin), (flexMax), (DesvCubMin), (DesvCubMax));
		else
			return null;
	}

	public Consulta existeMedicion(Paciente p) {
		this.connect();
		Consulta consulta = null;
		ResultSet result = null;
		try {
			PreparedStatement st = connect.prepareStatement("select * from Consulta where paciente=? and fecha=? and borrado=0");
			st.setString(1, p.getDni());
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date(System.currentTimeMillis());
			String fecha = formatter.format(date);
			st.setString(2, fecha);			
			result = st.executeQuery();
			while (result.next()) {

				consulta = (new Consulta(result.getInt("id"), p, result.getString("fecha"),
						result.getString("flexionMinima"), result.getString("flexionMaxima"), 
						result.getString("DesvCubMin"),
						result.getString("DesvCubMax")));

			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		this.close();
		return consulta;
	}
	
	
	public Consulta obtenerConsulta(int id) {
		this.connect();
		Consulta c = null;
		ResultSet result = null;
		try {
			PreparedStatement st = connect.prepareStatement("select * from Consulta where id=? and borrado=0");
			st.setInt(1, id);
			result = st.executeQuery();
			while (result.next()) {

				c = (new Consulta(result.getInt("id"), null, result.getString("fecha"), result.getString("flexionMinima"),
						result.getString("flexionMaxima"), result.getString("DesvCubMin"),
						result.getString("DesvCubMax")));
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		this.close();
		return c;
	}

	public void borrarConsulta(String id) {
		this.connect();
		try {
            PreparedStatement st = connect.prepareStatement("update Consulta set borrado=? where id=? and borrado=0");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date(System.currentTimeMillis());
			String fecha = formatter.format(date);
            st.setString(1, fecha);
            st.setString(2, id);
            st.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
		this.close();		
	}

	public String borraConsultasPaciente(String dni) {
		String fecha=null;
		this.connect();
		try {
            PreparedStatement st = connect.prepareStatement("update Consulta set borrado=? where paciente=? and borrado=0");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date(System.currentTimeMillis());
			fecha = formatter.format(date);
            st.setString(1, fecha);
            st.setString(2, dni);
            st.execute();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
		this.close();
		return fecha;
	}
	

}