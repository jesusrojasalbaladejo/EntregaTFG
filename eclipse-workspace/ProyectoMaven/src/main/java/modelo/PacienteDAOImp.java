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

public class PacienteDAOImp implements IPacienteDAO{
	//String url = "D:\\test.db";
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
	public List<Paciente> obtenerPacientes() {
		this.connect();
		List<Paciente> pacientes= new ArrayList();
		ResultSet result = null;
        try {
            PreparedStatement st = connect.prepareStatement("select * from pacientes where borrado=0" );
            result = st.executeQuery();
            while (result.next()) {
            	
            	pacientes.add(new Paciente(result.getString("nombre"), 
            								result.getString("apellidos"), 
            								result.getString("dni"), 
            								result.getString("telefono"),
            								result.getString("ocupacion"),
            								result.getInt("sexo"),
            								result.getInt("manoDominante"),
            								result.getInt("edad")
            								));
 
            	
            	
                System.out.print("DNI: ");
                System.out.println(result.getInt("dni"));

                System.out.print("Nombre: ");
                System.out.println(result.getString("nombre"));

                System.out.print("Apellidos: ");
                System.out.println(result.getString("apellidos"));

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        this.close();
		return pacientes;
	}

	@Override
	public Paciente obtenerPaciente(String id) {
		Paciente p = null;
		 PreparedStatement st;
		 this.connect();
		try {
			st = connect.prepareStatement("Select *  from pacientes where dni= ? and borrado=0");
	         st.setString(1,id);
	         ResultSet result = st.executeQuery();
	            while (result.next()) {
	            	
	            	p = new Paciente(result.getString("nombre"), result.getString("apellidos"), 
	            			result.getString("dni"), result.getString("telefono"), result.getString("ocupacion"), result.getInt("sexo"), result.getInt("manoDominante"), result.getInt("edad")); 
	            	
	            }
	         

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return p;
	}

	@Override
	public void actualizarPaciente(Paciente paciente) {
		this.connect();
		try {
            PreparedStatement st = connect.prepareStatement("update pacientes set nombre=?,apellidos=?,"
            		+ "telefono=?,ocupacion=?,sexo=?,manoDominante=?, edad=? where dni=?");
            
            System.out.println(paciente.getEdad());
            st.setString(1, paciente.getNombre());
            st.setString(2, paciente.getApellidos());
            st.setString(3, paciente.getTelefono());
            st.setString(4, paciente.getOcupación());
            st.setInt(5, paciente.getSexo());
            st.setInt(6, paciente.getManoDominante());
            st.setInt(7, paciente.getEdad());
            st.setString(8, paciente.getDni());
            st.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
		this.close();		
	}

	@Override
	public void eliminarPaciente(Paciente paciente, String fecha) {
		this.connect();
		try {
            PreparedStatement st = connect.prepareStatement("update pacientes set borrado=? where dni=? and borrado=0");
            st.setString(1, fecha);
            st.setString(2, paciente.getDni());
            st.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
		this.close();
		
	}

	@Override
	public void crearPaciente(Paciente paciente) throws SQLException {
		// TODO Auto-generated method stub
		this.connect();
            PreparedStatement st = connect.prepareStatement("insert into pacientes (dni,nombre, apellidos,telefono,ocupacion, sexo, manoDominante) values (?,?,?,?,?,?,?)");
            st.setString(1, paciente.getDni());
            st.setString(2, paciente.getNombre());
            st.setString(3, paciente.getApellidos());
            st.setString(4, paciente.getTelefono());
            st.setString(5, paciente.getOcupación());
            st.setInt(6, paciente.getSexo());
            st.setInt(7, paciente.getManoDominante());
        
            st.execute();
       
		this.close();
	}

	

}
