package modelo;

import java.util.List;

public interface IMedicionDAO {
	public void addMedicion(Medicion med);
	public int getNumMediciones(Consulta consulta);
	public List<Medicion> getMediciones(Consulta c);
	public List<Medicion> getMediciones(Consulta c, int mano, int parteMano);
	
	public void removeMedicion(Medicion med);
	public List<String> getMedicionesRealizadas(String c);

}
