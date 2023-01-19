package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import modelo.Consulta;
import modelo.EventoConfiguracion;
import modelo.EventoConfiguracionListener;
import modelo.Medicion;
import modelo.Modelo;
import modelo.Paciente;
import modelo.PacienteDAOImp;
import vista.DialogParametros;
import vista.GraphicPane;
import vista.InicializadorListener;
import vista.MedidaGraphicPane;
import vista.Ventana;
import vista.principal.ListadoConsultas;

public class Controlador implements ActionListener, MouseListener, InicializadorListener, TableModelListener,
		EventoConfiguracionListener {

	private Ventana v;
	private Modelo m;
	private Consulta c;
	private GraphicPane lecturagp = null;
	private Paciente paciente;
	private DialogParametros dp;
	private boolean isPlacaConectada;

	public Controlador(Ventana v, Modelo m) {
		this.v = v;
		this.m = m;
		this.v.setListener(this);
		this.m.setListener(this);
		paciente = null;
		c = null;
		this.isPlacaConectada = false;
		m.inicializacion();
		try {
			Thread.sleep(10000);
			v.eliminaPantallaCarga();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void cambiaEstadoPlaca() {
		this.isPlacaConectada = true;
		v.activaBotonesMedida();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if ((e.getSource().getClass().getSimpleName()).equals("JCheckBox")) {
			JCheckBox jc=(JCheckBox) e.getSource();
			MedidaGraphicPane mgp =((MedidaGraphicPane) jc.getParent().getParent().getParent()); 
			if (jc.getText().equals("Comparar todas las medidas")) {
				mgp.includeMediciones(jc.isSelected());
			}else {
				mgp.mostrarPaneles(jc.isSelected());
			}
			
			
		} else {

			String texto;
			JButton source = null;
			if (e.getSource().getClass().getSimpleName().equals("JButton")) {
				source = (JButton) e.getSource();
				texto = source.getText();
			} else {
				JMenuItem mi = (JMenuItem) e.getSource();
				texto = mi.getText();

			}

			switch (texto) {
			case "Nuevo paciente":
				v.setTextBotonCreacion(true);
				v.crearNuevoPaciente(true);
				break;
			case "Salir":
				System.exit(0);
				break;
			case "Realizar Calibración":

				if (!dp.isCalibraciónValida()) {
					dp.getValidacion().setText("El peso de referencia debe ser un número");

				} else {
					System.out.println("Es valida");
					m.enviarCadena("calibra " + dp.getMensajeCalibración());

					dp.dispose();
				}
				break;
			case "Cancelar":
				dp.setVisible(false);
				dp.dispose();
				break;
			case "Calibrar sensor":
				System.out.print("AQUI!!!");

				getDialogParametros(4);

				break;
			case "Iniciar Prueba":
				source.setText("Detener Prueba");
				lecturagp = (GraphicPane) source.getParent().getParent().getParent();
				m.setSeries(lecturagp, this);
				m.enviarCadena("leerSensor");
				break;

			case "Seleccionar":
				this.v.getCardLayout().show(this.v.getContentPane(), "panel test");
				break;
			case "Detener Prueba":
				m.enviarCadena("stop");
				lecturagp = (GraphicPane) source.getParent().getParent().getParent();
				source.setText("Descartar medida");
				lecturagp.normalizar();
				((MedidaGraphicPane) lecturagp.getParent()).calculaMedias();

				break;
			case "Descartar medida":
				lecturagp = (GraphicPane) source.getParent().getParent().getParent();
				lecturagp.clear();
				lecturagp.setValorMaximo("0.0");
				((MedidaGraphicPane) lecturagp.getParent()).calculaMedias();

				source.setText("Iniciar Prueba");

				source.setVisible(true);

				break;
			case "Guardar registros":
				MedidaGraphicPane panelMedidas = (MedidaGraphicPane) source.getParent();
				Boolean existeMedicion = false;
				int numMedida = 0;
				for (GraphicPane gp : panelMedidas.getMediciones()) {
					numMedida++;
					Medicion medicion = new Medicion(panelMedidas.getConsulta().getId(), panelMedidas.getMano(),
							panelMedidas.getParteMano(), numMedida, panelMedidas.getConsulta().getFecha(),
							panelMedidas.getCoherencia());
					
					System.out.println(numMedida+" "+gp.hasValues());
					if (gp.hasValues()) {
						medicion.setValores(gp.getValues());
						medicion.setValoresFiltrados(gp.getValuesFiltrados());
						medicion.setValoresNormalizados(gp.getValuesNormalizados());
						m.addMedicion(medicion);
						existeMedicion = true;
					} else {
						m.removeMedicion(medicion);
					}
				}
				v.actualizaMedicionesRealizadas(existeMedicion, panelMedidas.getMano(), panelMedidas.getParteMano());

				break;

			case ">>":
				v.limpiar();
				JComboBox combo = (JComboBox) source.getParent().getComponent(0);
				if (combo.getSelectedIndex() > 0) { // Cargo los datos del paciente
					v.setTextBotonCreacion(false);
					String cadena = (String) combo.getSelectedItem();
					paciente = m.getPaciente((cadena.split("-"))[0].trim());
					v.MostrarDatosPaciente(paciente);
					List<Consulta> consultas = m.getConsultas(paciente);
					v.listaConsultas(consultas);

				} else { // Crear un nuevo paciente
					v.setTextBotonCreacion(true);
					v.crearNuevoPaciente(true);
				}
				break;
			case "Registrar datos":

				dp.getValidacion().setVisible(false);

				if (!dp.camposRellenos()) {
					dp.getValidacion().setText("Hay elementos que debes rellenar");
					dp.getValidacion().setVisible(true);
				} else {
					Consulta c = m.crearConsulta(paciente, dp.getFlexmax().getText(), dp.getFlexmin().getText(),
							dp.getDesmin().getText(), dp.getDesmax().getText());
					v.actualizaConsultaNueva(c);
					dp.dispose();
				}


				break;
			case "Guardar Paciente":
				if (v.isErrorDataUser()) {
					JOptionPane.showMessageDialog(null, "Hay campos sin rellenar en el formulario de registro",
							"Error en los datos", JOptionPane.ERROR_MESSAGE);
				} else {
					paciente = v.crearPaciente();
					try {
						m.GuardaPaciente(paciente);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese dni en la base de datos");
					}
					InicializaDatos();
					v.seleccionaUsuario(paciente);// paciente.getDni()+" -
													// "+paciente.getApellidos()+","+paciente.getNombre());
					v.setTextBotonCreacion(false);
					}

				break;
			case "Crear nueva consulta":
				v.limpiaPanelesMedidas();

				c = ExisteMedicion();
				if (c == null) {
					getDialogParametros(1);
				} else {
					v.actualizaConsultaNueva(c);
						List<String> med = m.getMedicionesRealizadas(String.valueOf(c.getId()));
						List<Consulta> listaaux = new ArrayList<Consulta>();
						listaaux.add(c);
						v.mostrarConsulta(listaaux, med);
					
				}
				break;
			case "Actualizar Paciente":
				v.actualizaDatosPaciente(paciente);
				m.actualizarPaciente(paciente);
				break;
			case "Eliminar consulta seleccionada":
				getDialogParametros(3);
				break;
			case "Borrar Paciente":
				getDialogParametros(2);

				break;
			case "Borrar Paciente ":
				dp.dispose();
				m.eliminaPaciente(paciente);
				// v.limpiar();

				v.borraUsuario(m.obtenerPacientes());

				paciente = null;


				break;
			case "Borrar Consultas":
				dp.dispose();
				
				JTable tablaConsultas = v.obtieneListadoConsultas().getTable();
				int[] indices = (tablaConsultas.getSelectedRows());
				for (int i : indices) {
					m.borrarConsulta(tablaConsultas.getValueAt(i, 1).toString());
				}
				
				v.listaConsultas(m.getConsultas(paciente));
				v.hideMediciones();

				break;
				
			case "Mostrar consultas seleccionadas":

				System.out.println(
						((ListadoConsultas) source.getParent().getParent()).getTable().getSelectedRows().length);
				v.limpiaPanelesMedidas();
				JTable tabla = ((ListadoConsultas) source.getParent().getParent()).getTable();
				int[] indice = (tabla.getSelectedRows());
				List<Consulta> consultasActuales = new ArrayList<Consulta>();
				String cadenaConsultas = "";
				for (int i : indice) {
					Consulta c = m.obtenerConsulta(Integer.parseInt(tabla.getValueAt(i, 1).toString()));
					consultasActuales.add(c);
					cadenaConsultas += (c.getId() + ",");
				}

				if (cadenaConsultas.length() > 0) {
					cadenaConsultas = cadenaConsultas.substring(0, cadenaConsultas.length() - 1);
					List<String> med = m.getMedicionesRealizadas(cadenaConsultas);
					v.mostrarConsulta(consultasActuales, med);
				}
				break;
			default:
				break;
			}
		}
	}

	private void getDialogParametros(int modo) {
		if (dp == null) {
			dp = DialogParametros.getInstanceDialog(this);
		}
		dp.setModo(modo);
		dp.show();
	}

	public void newValor() {
		lecturagp.setValorMaximo();
		lecturagp.updateChart();
	}

	@Override
	public void InicializaDatos() {

		List<Paciente> pacientes = m.obtenerPacientes();
		v.inicializaCombobox(pacientes);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getButton() == e.BUTTON3 && e.getSource().getClass().getSimpleName().equals("JTabbedPane")) {
			if (((JTabbedPane) e.getSource()).getSelectedIndex() > 0) {
				((JTabbedPane) e.getSource()).remove(((JTabbedPane) e.getSource()).getSelectedIndex());
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int mano, parteMano = 0;

		if (e.getSource().getClass().getSimpleName().equals("JLabel")) {

			int altura = ((JLabel) e.getSource()).getHeight();
			int anchura = ((JLabel) e.getSource()).getWidth();

			int posX = e.getX();
			int posY = e.getY();

			int valores = (posX / (anchura / 6) + posY / (altura / 2) * 6);
			;

			switch (valores) {
			case 0:
				mano = 1;
				parteMano = 1;
				break;
			case 1:
				mano = 1;
				parteMano = 2;
				break;
			case 2:
				mano = 1;
				parteMano = 3;
				break;
			case 3:
				mano = 2;
				parteMano = 1;
				break;
			case 4:
				mano = 2;
				parteMano = 2;
				break;
			case 5:
				mano = 2;
				parteMano = 3;
				break;
			case 6:
				mano = 1;
				parteMano = 4;
				break;
			case 7:
				mano = 1;
				parteMano = 5;

				break;
			case 8:
				mano = 1;
				parteMano = 6;

				break;
			case 9:
				mano = 2;
				parteMano = 4;

				break;
			case 10:
				mano = 2;
				parteMano = 5;
				break;

			default:
				mano = 2;
				parteMano = 6;
				break;
			}

			if (v.hasMesure(mano - 1, parteMano) || v.isEditableMedicion()) {

				Consulta consulta = v.getConsultaActual();
				if (consulta == null || !v.isEditableMedicion()) { // Hay varias medidas para comprobar
					if (v.getConsultaActual() != null) {
						//Consulta consulta = v.getConsultaActual();
						List<Medicion> medidas = m.getMediciones(consulta, mano, parteMano);
						v.showPanelMedidas(mano, parteMano, v.isEditableMedicion());
						v.addMediciones(medidas, mano, parteMano);
					} else {
						List<Consulta> c1 = v.getConsultasAComparar();
						v.showPanelMedidas(mano, parteMano, false);
						for (Consulta consul : c1) {
							List<Medicion> medidas = m.getMediciones(consul, mano, parteMano);
							v.addMediciones(medidas, mano, parteMano);
						}

					}
				} else { // Única consulta y además editable
					System.out.println("ESTOY AQUI!!!");
					//Consulta consulta = v.getConsultaActual();
					List<Medicion> medidas = m.getMediciones(consulta, mano, parteMano);
					v.showPanelMedidas(mano, parteMano, v.isEditableMedicion());
					
					if (medidas.size() > 0) {
						v.addMediciones(medidas, mano, parteMano);
					}
				}

			} else {
				System.out.println("NO TIENE MEDIDAS");

				System.out.println("Mano:" + mano + "Parte Mano:" + parteMano);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	private Consulta ExisteMedicion() {
		return m.existeMedicion(paciente);
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int column = e.getColumn();
		if (column == 0) {
			TableModel model = (TableModel) e.getSource();

			v.hideMediciones();
			v.disableBotones(!(v.getSelectedRowsCount() == 0));

			if (v.getSelectedRowsCount() > 1) {
				System.out.println("Varios " + v.getSelectedRowsCount());
			}

			String columnName = model.getColumnName(column);
			Boolean checked = (Boolean) model.getValueAt(row, column);
			if (checked) {
				System.out.println(columnName + ": " + true);
			} else {
				System.out.println(columnName + ": " + false);
			}
		}

	}

	@Override
	public void conexionEstablecida(EventoConfiguracion ec) {
		cambiaEstadoPlaca();
		v.eliminaPantallaCarga();

	}

}
