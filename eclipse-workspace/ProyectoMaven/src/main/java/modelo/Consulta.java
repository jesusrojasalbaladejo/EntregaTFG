package modelo;


public class Consulta {

	private int id;
	private String fecha;
	private Paciente paciente;
	private String flexMin, flexMax,DesvCubMin,DesvCubMax;
	private int numMedicionesRealizadas;
	

	public Consulta(int id, Paciente p, String fecha, String flexMin, String flexMax,String DesvCubMin,String DesvCubMax) {
		this.id = id; 
		this.paciente = p;
		this.fecha = fecha;
		this.flexMin = flexMin;
		this.flexMax=flexMax;
		this.DesvCubMin=DesvCubMin;
		this.DesvCubMax=DesvCubMax;
		this.setNumMedicionesRealizadas(0);
	}

	public String getFlexMin() {
		return flexMin;
	}


	public void setFlexMin(String flexMin) {
		this.flexMin = flexMin;
	}


	public String getFlexMax() {
		return flexMax;
	}


	public void setFlexMax(String flexMax) {
		this.flexMax = flexMax;
	}


	public String getDesvCubMin() {
		return DesvCubMin;
	}


	public void setDesvCubMin(String desvCubMin) {
		DesvCubMin = desvCubMin;
	}


	public String getDesvCubMax() {
		return DesvCubMax;
	}


	public void setDesvCubMax(String desvCubMax) {
		DesvCubMax = desvCubMax;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	@Override
	public String toString() {
		return this.id + " " + this.fecha;
	}

	public int getNumMedicionesRealizadas() {
		return numMedicionesRealizadas;
	}

	public void setNumMedicionesRealizadas(int numMedicionesRealizadas) {
		this.numMedicionesRealizadas = numMedicionesRealizadas;
	}
	
	public void addMedicion() {
		this.numMedicionesRealizadas++;
	};
	
	public void removeMedicion() {
		this.numMedicionesRealizadas--;
	}

}
