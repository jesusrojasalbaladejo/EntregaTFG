package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicionDAOImp implements IMedicionDAO{

	
	Connection connect;
	
	private void connect(){
		 try {
		     connect = DriverManager.getConnection("jdbc:sqlite:src/main/resources/tfg.db");
		     if (connect!=null) {
		         System.out.println("Conectado");
		     }
		 }catch (SQLException ex) {
		     System.err.println("No se ha podido conectar a la base de datos\n"+ex.getMessage());
		 }
		}

	private void close(){
		        try {
		            connect.close();
		            System.out.println("desconectado");
		        } catch (SQLException ex) {
				     System.err.println("No se ha podido cerrar la base de datos\n"+ex.getMessage());
		        }
		 }
	
	 
	 
	 
	@Override
	public void addMedicion(Medicion med) {
		this.removeMedicion(med);
		this.connect();
		try {
            PreparedStatement st = connect.prepareStatement("insert into MedicionesConsulta(Consulta, Mano, ParteMano,Tiempos,Valores,ValoresFiltrados, ValoresNormalizados,IdMedida,Coherencia) values (?,?,?,?,?,?,?,?,?)");
            st.setInt(1, med.getIdConsulta());
            st.setInt(2,med.getMano());
            st.setInt(3, med.getParteMano());
            st.setString(4, med.getTiempos());
            st.setString(5, med.getValoresCadena());
            st.setString(6, med.getValoresFiltradosCadena());
            st.setString(7, med.getValoresNormalizadosCadena());
            st.setInt(8, med.getNumMedida());
            st.setString(9, med.getCoherencia());
            st.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
		this.close();

		
	}
	public int getNumMediciones(Consulta consulta) {
		int resultado=0;
		PreparedStatement st;
		this.connect();
		try {
			st = connect.prepareStatement("select count(1) from( select Consulta,mano, ParteMano from MedicionesConsulta where Consulta=? group by Consulta, Mano, ParteMano) consulta;");
	         st.setInt(1,consulta.getId());
	         ResultSet result = st.executeQuery();
	            while (result.next()) {
	            	resultado = result.getInt(1);
	            	System.out.println("EL RESULTADO DEL COUNT ES: "+resultado +" para la consulta: "+ consulta.getId());
	            	
	            }
	         

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		return resultado;
	}
	
	
	public List<Medicion> getMediciones(Consulta c) {
		List<Medicion> resultado=new ArrayList<Medicion>();
		PreparedStatement st;
		this.connect();
		try {
			st = connect.prepareStatement("select * from MedicionesConsulta where Consulta=? order by Consulta, Mano, ParteMano,IdMedida;");
	         st.setInt(1,c.getId());
	         ResultSet result = st.executeQuery();
	            while (result.next()) {
	            	Medicion m = new Medicion(c.getId(), result.getInt("Mano"), result.getInt("ParteMano"),result.getInt("IdMedida"),
	            			c.getFecha(),result.getString("Tiempos"),result.getString("Valores"),result.getString("ValoresFiltrados"),
	            			result.getString("ValoresNormalizados"),
	            			result.getString("Coherencia"));
	            	resultado.add(m);
	            }
	         

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}
	@Override
	public List<Medicion> getMediciones(Consulta c, int mano, int parteMano) {


		List<Medicion> resultado=new ArrayList<Medicion>();
		PreparedStatement st;
		this.connect();
		try {
			st = connect.prepareStatement("select * from MedicionesConsulta where Consulta=? and Mano=? and ParteMano=? order by Consulta, Mano, ParteMano,IdMedida;");
			st.setInt(1,c.getId());
			st.setInt(2,mano);
			st.setInt(3,parteMano);
	         ResultSet result = st.executeQuery();
	            while (result.next()) {
	            	Medicion m = new Medicion(c.getId(), result.getInt("Mano"), result.getInt("ParteMano"),result.getInt("IdMedida"),c.getFecha(),
	            			result.getString("Tiempos"),result.getString("Valores"),
	            			result.getString("ValoresFiltrados"),
	            			result.getString("ValoresNormalizados"),
	            			result.getString("Coherencia"));
	            	resultado.add(m);
	            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close();
		return resultado;

	}
	public void removeMedicion(Medicion med) {

		PreparedStatement st;
		this.connect();
		try {
			st = connect.prepareStatement("delete from MedicionesConsulta where Consulta=? and Mano=? and ParteMano=? and IdMedida=?;");
			st.setInt(1,med.getIdConsulta());
			st.setInt(2,med.getMano());
			st.setInt(3,med.getParteMano());
			st.setInt(4, med.getNumMedida());
	        st.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public List<String> getMedicionesRealizadas(String c) {
		List<String> resultado=new ArrayList<String>();
		PreparedStatement st;
		this.connect();
		try {
			String[] idConsultas = c.split(",");
			String consulta="select distinct Mano, ParteMano from MedicionesConsulta where Consulta in (";
			for (String string : idConsultas) {
				consulta=consulta+"?,";
			}
			consulta=consulta.substring(0,consulta.length()-1)+");";
			st = connect.prepareStatement(consulta);
			int parametro=1;
			for (String string : idConsultas) {
				st.setString(parametro, string);
				parametro++;
			}
			 ResultSet result = st.executeQuery();
			 System.out.println(st.toString());
			 System.out.println(c);
			 System.out.println("Resultado Query");
	            while (result.next()) {
	            	resultado.add(result.getInt("Mano")+"*"+result.getInt("ParteMano"));
	            	System.out.println(result.getInt("Mano"));
	            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.close();
		return resultado;

		
	}

	
	
}
