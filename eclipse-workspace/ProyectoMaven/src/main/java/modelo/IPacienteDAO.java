package modelo;

import java.sql.SQLException;
import java.util.List;

public interface IPacienteDAO {

	public List<Paciente> obtenerPacientes();
	public Paciente obtenerPaciente(String id);
	public void actualizarPaciente(Paciente paciente);
	public void crearPaciente(Paciente paciente) throws SQLException;
	public void eliminarPaciente(Paciente paciente, String fecha);
}
