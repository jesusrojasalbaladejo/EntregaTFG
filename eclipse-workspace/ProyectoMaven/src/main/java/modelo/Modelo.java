package modelo;


import uk.me.berndporr.iirj.*;

import java.awt.Container;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;

import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

import controlador.Controlador;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;
import vista.DialogParametros;
import vista.GraphicPane;
import vista.Ventana;

public class Modelo implements SerialPortEventListener{

	int medida_count;
	public static String puertoComunicaciones;
	String puerto;
	XYSeries series;
	private static Modelo sd = null;
	private SerialPort sp;
	StringBuilder message = new StringBuilder();
	double tiempo;
	Controlador c = null;
	private Butterworth butterworth;
	private XYSeries series2;
	private boolean conectada=false;
	public EventoConfiguracionListener ecl;
	
	
	
	public void setSeries(GraphicPane lecturagp, Controlador controlador) {
		this.series = lecturagp.getValues();
		this.series2 = lecturagp.getValuesFiltrados();
		lecturagp.changeSeries(series2);
		this.c=controlador;
		}
	
	public void setSeries(XYSeries serie, XYSeries serie2, Controlador controlador) {
		this.series = serie;
		this.series2 = serie2;
		this.c=controlador;
		}
	
	
    private Modelo() {
    	tiempo=0;
    	puerto = null;    	
    	conectada=false;
    	
    	butterworth = new Butterworth();
		butterworth.lowPass(4,600,15);
    }
    

    public void inicializacion() {
    	for (String pt : SerialPortList.getPortNames()) {
			
    		System.out.println("Puerto: "+ pt);
			if (puerto!=null) {
				break;
			}
			configura(pt);
    		}
    }
    
    public void enviarCadena(String string) {
        try {
        	medida_count = 1;//("stop".equals(string) ) ? 0 : 1;
			sp.writeString(string);
			
        } catch (SerialPortException e) {
			e.printStackTrace();
		}
		
	}

    public static Modelo getInstance() {
		if (sd == null) {
    		sd=new Modelo();
    		puertoComunicaciones=null;
    	}
		return sd;
	}
	
    private void configura(String p1) {
	try {
		System.out.println(p1);
	    sp = new SerialPort(p1);
	   
        sp.openPort();
		
    	sp.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
    	sp.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);

    	sp.addEventListener(this,SerialPort.MASK_RXCHAR);
        for (int i=0;i<3;i++) {
    	sp.writeString("sincronizando");
    	if (puerto!=null) {break;}
    		System.out.println("Enviado...");
    			Thread.sleep(2500);
            }
        
        } catch (SerialPortException ex) {
            System.out.println("There are an error on writing string to port т: " + ex);
    	} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Pues otra cosa!");
		}
}

    @Override
	public void serialEvent(final SerialPortEvent event) {

	if(event.isRXCHAR() && event.getEventValue() > 0){
        try {
            byte buffer[] = sp.readBytes();
            for (byte b: buffer) {
                    if ( (b == '\r' || b == '\n') && message.length() > 0) {
                        final String toProcess = message.toString();
                        SwingUtilities.invokeLater(new Runnable() {

							@Override public void run() {
                                processMessage(toProcess);
                           }

							private void processMessage(String toProcess) {
								//System.out.print(toProcess);
								puerto=event.getPortName();
								
								if (toProcess.trim().compareTo("OK")==0) {
									System.out.println("Sincronizado");
									if (!conectada) {
									ecl.conexionEstablecida(new EventoConfiguracion(this));
									conectada=true;
									}
									
								}else {
									if (toProcess.trim().startsWith("CALIBRADO")) {
									System.out.println(medida_count+" La cadena es: "+ toProcess);}
									else {
								if (medida_count!=0) {
								String separador = Pattern.quote("*");

								String[] valores = toProcess.split(separador);

								//if (Double.parseDouble(valores[1])>0) {

								series.add(		
										new XYDataItem((Double.parseDouble(valores[0])),//*medida_count/1000),
												//Double.parseDouble(toProcess))); 
												Double.parseDouble(valores[1])));
								
								
								double valor = butterworth.filter(Double.parseDouble(valores[1]));
								
								series2.add(
										new XYDataItem((Double.parseDouble(valores[0])),//*medida_count/1000),
												//Double.parseDouble(toProcess))); 
												valor));//butterworth.filter(Double.parseDouble(valores[1]))));
								System.out.println(series.getItemCount() + " ///// " +series2.getItemCount());
								
								medida_count++;
								//System.out.println(Double.parseDouble(valores[1])+" "+valor);
								
								/**QUITAR COMENTARIO!!!**/
								c.newValor(); //Actualizamos el máximo valor de fuerza registrado
								}
								
						    		/*}else {
						    			switch (series.getItemCount()) {
										case 1:
											series.clear();
										case 0:
											series.add(new XYDataItem((Double.parseDouble(valores[0])/1000),//Double.parseDouble(toProcess)));
							        				 Double.parseDouble(valores[1])));	
													medida_count++;
											break;
										default:
											//if(series.getY(series.getItemCount()-1).doubleValue()>0.0) {
											//	series.add(new XYDataItem((Double.parseDouble(valores[0])/1000),//Double.parseDouble(toProcess)));
											//		 Double.parseDouble(valores[1])));																					
											//}
											break;
										}
						    		}*/
								
									}
							}}
                        });
                        
                        message.setLength(0);
                    }
                    else {
            
                        message.append((char)b);
                    }
            }                
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
            System.out.println("serialEvent");
        }
    }
	
}

 	public  List<Paciente> getUsuarios() {
		PacienteDAOImp pac = new PacienteDAOImp();
		List<Paciente> pacientes = pac.obtenerPacientes();
		return pacientes;
	}

	public Paciente getPaciente(String id) {
		return (new PacienteDAOImp()).obtenerPaciente(id);
	}
	
	public void GuardaPaciente(Paciente p) throws SQLException {
		new PacienteDAOImp().crearPaciente(p);
	}
	
	public Consulta crearConsulta(Paciente paciente,String flexMax, String flexMin, String DesvCubMin, String DesvCubMax){
		return new ConsultaDAOImp().crearConsulta(paciente, flexMax, flexMin, DesvCubMin, DesvCubMax);
	}
	
	public List<Consulta> getConsultas(Paciente paciente)
	{
		List<Consulta> resultados = new ConsultaDAOImp().obtenerConsultas(paciente);
		for (Consulta consulta : resultados) {
			consulta.setNumMedicionesRealizadas(new MedicionDAOImp().getNumMediciones(consulta));
		}
		return resultados;
	}


	public void actualizarPaciente(Paciente paciente) {
		new PacienteDAOImp().actualizarPaciente(paciente);
	}



	public List<Medicion> getMediciones(Consulta c) {
		return new MedicionDAOImp().getMediciones(c);
	}
	
	public List<Medicion> getMediciones(Consulta c, int mano, int parteMano) {
		return new MedicionDAOImp().getMediciones(c, mano, parteMano);
	}
	
	public void addMedicion(Medicion med) {
		new MedicionDAOImp().addMedicion(med);
	}
	
	public void removeMedicion(Medicion med) {
		new MedicionDAOImp().removeMedicion(med);
	}

	public List<String> getMedicionesRealizadas(String c) {
		return new MedicionDAOImp().getMedicionesRealizadas(c);
	}

	public Consulta existeMedicion(Paciente p) {
		return new ConsultaDAOImp().existeMedicion(p);
	}

	public Consulta obtenerConsulta(int id) {
		return new ConsultaDAOImp().obtenerConsulta(id);
	}

	public boolean isConected() {
		return conectada;
	}

	public void setListener(EventoConfiguracionListener controlador) {
		this.ecl=controlador;		
	}

	public void eliminaPaciente(Paciente paciente) {
		String fecha = new ConsultaDAOImp().borraConsultasPaciente(paciente.getDni());
		if (fecha!=null) {
				new PacienteDAOImp().eliminarPaciente(paciente,fecha);		
		} else {
			System.out.println("Hubo un error en la eliminación de consultas");
		}
		
	}

	public void borrarConsulta(String string) {
		new ConsultaDAOImp().borrarConsulta(string);
		
	}

	public List<Paciente> obtenerPacientes() {
		return new PacienteDAOImp().obtenerPacientes();
	}
	

}

	


