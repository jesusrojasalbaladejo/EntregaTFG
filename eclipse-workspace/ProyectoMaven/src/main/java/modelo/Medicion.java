package modelo;

import java.util.Iterator;

import org.jfree.data.xy.XYSeries;

public class Medicion {

	private int idConsulta;
	private int mano;
	private int parteMano;
	private XYSeries serieTiempos;
	private XYSeries serieValores;
	private XYSeries serieValoresFiltrados;
	private XYSeries serieValoresNormalizados;
	private int numMedida;
	private String Coherencia;
	

	public Medicion(int consulta, int mano, int parteMano, int numMedidas,String fecha, String coherencia) {
		this.idConsulta=consulta;
		this.mano = mano;
		this.parteMano = parteMano;
		this.numMedida=numMedidas;
		this.Coherencia=coherencia;
		
		String nombre="";
		if (numMedidas==5) {
			nombre="Valor medio: "+fecha;
		}else {
			nombre="Medida :"+numMedidas+"_"+fecha;
		}
		
		serieValores=new XYSeries(nombre);
		serieValoresFiltrados=new XYSeries(nombre);
		serieValoresNormalizados=new XYSeries(nombre);
		}
	
	

	public Medicion(int consulta, int mano, int parteMano, int numMedidas, String fecha, 
			String tiempos,String valores, String valoresFiltrados, String valoresNormalizados, String coherencia) {
		this(consulta,mano,parteMano, numMedidas,fecha,coherencia);

		if (numMedidas!=5) {
		String[] valoresSeparados = valores.split(";");
		String[] valoresTiempoSeparados = tiempos.split(";");
		String[] valoresFiltradosSeparados = valoresFiltrados.split(";");
		for (int i=0;i<valoresSeparados.length;i++) {
			serieValores.add(Double.parseDouble(valoresTiempoSeparados[i]),Double.parseDouble(valoresSeparados[i]));
			serieValoresFiltrados.add(Double.parseDouble(valoresTiempoSeparados[i]),Double.parseDouble(valoresFiltradosSeparados[i]));
		}
		}
		String[] valoresNormalizadosSeparados = valoresNormalizados.split(";");
		
		
		
		int posicion=0;
		for (String string : valoresNormalizadosSeparados) {
			serieValoresNormalizados.add(posicion,Double.parseDouble(string));
			posicion++;
		}
	}
	
	
	
	public int getIdConsulta() {
		return idConsulta;
	}

	public void setIdConsulta(int idConsulta) {
		this.idConsulta = idConsulta;
	}

	public int getMano() {
		return mano;
	}

	public void setMano(int mano) {
		this.mano = mano;
	}

	public int getParteMano() {
		return parteMano;
	}

	public void setParteMano(int parteMano) {
		this.parteMano = parteMano;
	}

	public XYSeries getValores() {
		return serieValores;
	}
	
	public void setSerieTiempos(XYSeries serie) {
		this.serieTiempos=serie;
	}

	public void setValores(XYSeries serie) {
		this.serieValores=serie;
	}
	public void setValoresFiltrados(XYSeries serie) {
		this.serieValoresFiltrados=serie;
	}
	public void setValoresNormalizados(XYSeries serie) {
		this.serieValoresNormalizados=serie;
	}

	public int getNumMedida() {
		return numMedida;
	}

	public void setNumMedida(int numMedida) {
		this.numMedida = numMedida;
	}
	
	public String getValoresCadena() {
		return getValorSerieToString(this.serieValores);
	}

	public String getValoresFiltradosCadena() {
		return getValorSerieToString(this.serieValoresFiltrados);
	}

	public String getValoresNormalizadosCadena() {
		return getValorSerieToString(this.serieValoresNormalizados);
	}
	
	public  XYSeries getValoresNormalizados() {
		return this.serieValoresNormalizados;
	}

	public  XYSeries getValoresFiltrados() {
		return this.serieValoresFiltrados;
	}

	
private String getValorSerieToString(XYSeries serie) {
	
	StringBuilder sb = new StringBuilder();
	if(serie.getItemCount()>0) {
	for(int i=0;i<serie.getItemCount();i++) {
		sb.append(serie.getY(i).toString()+";");
	}
	sb.deleteCharAt(sb.length()-1);
	}
	return sb.toString();

}



public String getTiempos() {
	StringBuilder sb = new StringBuilder();
	if(this.getValores().getItemCount()>0) {
	
	for(int i=0;i<this.getValores().getItemCount();i++) {
		sb.append(this.getValores().getX(i).toString()+";");
	}
	sb.deleteCharAt(sb.length()-1);
	}
		
	return sb.toString();
}



public String getCoherencia() {
	return this.Coherencia;
}
	
}
