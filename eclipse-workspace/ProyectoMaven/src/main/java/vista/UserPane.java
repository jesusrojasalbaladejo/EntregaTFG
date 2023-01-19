package vista;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modelo.Consulta;
import modelo.Medicion;
import modelo.Paciente;
import vista.principal.DatosMediciones;
import vista.principal.DatosPaciente;
import vista.principal.ListadoConsultas;
import vista.principal.SelectorUsuario;
import vista.principal.VistasPrincipal;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.border.TitledBorder;

public class UserPane extends JPanel {

	private VistasPrincipal panel1, panel2, panel3, panel4;
	Paciente paciente;

	public UserPane() {
		super();
		setBackground(new Color(255, 255, 255));
		creaPaneles();
		cargaPanelInicial();
		
		
	}
	
	private void cargaDosPaneles() {
		Component[] components = getComponents();

		for (Component component : components) {
		    remove(component);  
		}

		revalidate();
		repaint();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0 };
		setLayout(gridBagLayout);


		panel1.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_panel1 = new GridBagConstraints();
		gbc_panel1.gridx = 0;
		gbc_panel1.gridy = 0;
		gbc_panel1.gridwidth = 1;
		gbc_panel1.gridheight = 1;
		add(panel1, gbc_panel1);

		panel2.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_panel2 = new GridBagConstraints();
		gbc_panel2.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel2.gridx = 1;
		gbc_panel2.gridy = 0;
		gbc_panel2.gridwidth = 8;
		gbc_panel2.gridheight = 1;
		add(panel2, gbc_panel2);		
		
	}

	/**
	 * Crea todos los paneles de datos que pueden formar parte de esta ventana.
	 */
	private void creaPaneles() {
		panel1 = new SelectorUsuario();
		panel2 = new DatosPaciente();
		panel3 = new ListadoConsultas();
		panel4 = new DatosMediciones();
		panel1.setBackground(new Color(255, 255, 255));
		panel2.setBackground(new Color(255, 255, 255));
		panel3.setBackground(new Color(255, 255, 255));
		panel4.setBackground(new Color(255, 255, 255));

	}

	/**
	 * Muestra únicamente el selector de usuario, colocado en el centro de la pantalla
	 */
	private void cargaPanelInicial() {
		Component[] components = getComponents();

		for (Component component : components) {
		    remove(component);  
		}

		revalidate();
		repaint();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[] { 0.0 };
		gridBagLayout.columnWeights = new double[] { 0.0 };
		setLayout(gridBagLayout);
		
		panel1.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_panel1 = new GridBagConstraints();
		gbc_panel1.gridx = 0;
		gbc_panel1.gridy = 0;
		gbc_panel1.gridwidth = 1;
		gbc_panel1.gridheight = 1;
		add(panel1, gbc_panel1);
	}
	
	


	private void cargaCuatroPaneles() {
		Component[] components = getComponents();

		for (Component component : components) {
		    remove(component);  
		}

		revalidate();
		repaint();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0 };
		setLayout(gridBagLayout);


		panel1.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_panel1 = new GridBagConstraints();
		gbc_panel1.gridx = 0;
		gbc_panel1.gridy = 0;
		gbc_panel1.gridwidth = 1;
		gbc_panel1.gridheight = 1;
		add(panel1, gbc_panel1);

		panel2.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_panel2 = new GridBagConstraints();
		gbc_panel2.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel2.gridx = 1;
		gbc_panel2.gridy = 0;
		gbc_panel2.gridwidth = 8;
		gbc_panel2.gridheight = 1;
		add(panel2, gbc_panel2);

		panel3.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_panel3 = new GridBagConstraints();
		gbc_panel3.fill = GridBagConstraints.BOTH;
		gbc_panel3.gridx = 0;
		gbc_panel3.gridy = 1;
		gbc_panel3.gridwidth = 3;
		gbc_panel3.gridheight = 6;
		gbc_panel3.anchor = GridBagConstraints.WEST;
		add(panel3, gbc_panel3);

		panel4.setVisible(false);
		panel4.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_panel4 = new GridBagConstraints();
		gbc_panel4.fill = GridBagConstraints.BOTH;
		gbc_panel4.gridx = 3;
		gbc_panel4.gridy = 1;
		gbc_panel4.gridwidth = 6;
		gbc_panel4.gridheight = 6;
		add(panel4, gbc_panel4);
		
	}

	
	
	void inicializarUsuarios(List<Paciente> pac) {
		((SelectorUsuario) panel1).inicializarUsuarios(pac);
	}
	
	void borraUsuario(List<Paciente> pac) {
		((SelectorUsuario) panel1).inicializarUsuarios(pac);
		mostrarCampos(true);
		limpiar();		
	}

	public void addActionListener(ActionListener al) {
		panel1.addActionListener(al);
		panel2.addActionListener(al);
		panel3.addActionListener(al);
		panel4.addActionListener(al);
	}

	public void mostrarCampos(boolean t) {
		this.paciente = null;
		
		if(t) {
		((SelectorUsuario)panel1).limpiar();
		}
		
		((DatosPaciente) panel2).limpiar();
		
		cargaDosPaneles();
	}

	public void setTextBotonCreacion(boolean creacion) {
		((DatosPaciente) panel2).setTextBotonCreacion(creacion);
	}
	
	public boolean isError() {
	return 		((DatosPaciente) panel2).isError();
	}

	public void seleccionaUsuario(Paciente paciente2) {
		((SelectorUsuario) panel1).seleccionaUsuario(paciente2);
		cargaCuatroPaneles();
	}

	public void actualizaMediciones(int totalMed, int posicion) {
//	int fila= posicion/6;
//	int columna = (posicion%6)+1;

//	if (totalMed==0) {
//		tablaMedidas.setValueAt(false, fila, columna);
//	}else{tablaMedidas.setValueAt(true, fila, columna);}

	}

	public void actualizaDatosPaciente(Paciente paciente) {
		((DatosPaciente) panel2).actualizaDatosPaciente(paciente);
	}

	public void listaConsultas(List<Consulta> consultas) {
		((ListadoConsultas) panel3).listaConsultas(consultas);

	}

	public AbstractButton getAddUserButton() {
		return ((DatosPaciente) panel2).getAddUserButton();
	}

	public Paciente crearPaciente() {
		return ((DatosPaciente) panel2).crearPaciente();
	}

	public void mostrarDatoPaciente(Paciente p) {
		this.paciente = p;
		((DatosPaciente) panel2).mostrarDatoPaciente(p);
		cargaCuatroPaneles();
	}

	public void actializaConsultaNueva(Consulta c) {
		((ListadoConsultas)panel3).setSelectedConsultaActual(c);
		((DatosMediciones) panel4).addNuevaConsulta(c);
		panel4.setVisible(true);
	}

	public void limpiar() {

		cargaPanelInicial();
		panel2.limpiar();
		panel3.limpiar();
		panel4.limpiar();
	}


	public Consulta getConsultaActual() {
		return ((DatosMediciones) panel4).getConsultaActual();
	}

	public void actualizaMedicionesRealizadas(Boolean existeMedicion, int mano, int parteMano) {
		((DatosMediciones) panel4).actualizaMedicionesRealizadas(existeMedicion, mano, parteMano);
	}

	public boolean isEditableMedicion() {
		return 		((DatosMediciones) panel4).isEditableMedicion();
	}


	public void mostraConsulta(List<Consulta> consultasActuales, List<String> med) {
		panel4.setVisible(true);
		((DatosMediciones) panel4).mostrarConsulta(consultasActuales,med);
	}

	public boolean hasMesure(int mano, int parteMano) {
		return 		((DatosMediciones) panel4).hasMesure(mano,parteMano);
	}

	public List<Consulta> getConsultasAComparar() {
		return ((DatosMediciones) panel4).getConsultasAComparar();
	}

	public int getSelectedRowsCount() {
		return 		((ListadoConsultas)panel3).getSelectedRowsCount();
	}

	public void disableBotones(boolean state) {
		((ListadoConsultas)panel3).disableBoton(state);
		
	}


	public void activaBotonesMedida() {
		((ListadoConsultas)panel3).activaBotonesMedida();
		
	}

	public void hideMediciones() {
		((DatosMediciones) panel4).setVisible(false);;
		
	}

	public ListadoConsultas obtieneListadoConsultas() {
		return ((ListadoConsultas)panel3);
	}


}
