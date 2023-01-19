package vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import modelo.Consulta;
import modelo.Medicion;
import modelo.Paciente;
import modelo.PacienteDAOImp;
import vista.principal.ListadoConsultas;

public class Ventana extends JFrame {

	private JMenuBar barraMenu;
	private JMenu menu;
	private JMenuItem item1;
	//private List<GraphicPane> graficas;
	private JComboBox<String> pacientes;
	//private List<JButton> botones;
	private UserPane panelUsuario;
	private static Ventana v = null;
	private CardLayout card;
	private ActionListener actionlistener;

	private List<InicializadorListener> listeners = new ArrayList<InicializadorListener>();
	private JTabbedPane panelTest;

	private MedidaGraphicPane[] paneles;
	private JMenuItem item2;
	private JMenuItem item3;

	private Ventana() {

		super("Sistema medición fuerzas");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Ventana.class.getResource("/Imagenes/icono_app.png")));
		setBackground(Color.white);
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		setVisible(true);
		this.setMaximizedBounds(env.getMaximumWindowBounds());
		setExtendedState(getExtendedState() | this.MAXIMIZED_BOTH);
//		graficas = new ArrayList<GraphicPane>();
//		botones = new ArrayList<JButton>();
		card = new CardLayout();

		getContentPane().setLayout(card);
		AddMenu();
		AddImagenCarga();
		addTestPane();
		setVisible(true);

		this.getCardLayout().first(this.getContentPane());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		paneles = new MedidaGraphicPane[12];
	}
	
	@Override
	public Image getIconImage() {
		Image icono = Toolkit.getDefaultToolkit().
		         getImage(ClassLoader.getSystemResource("imagenes/icono_app.png"));


		   return icono;
}

	private void AddImagenCarga() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setLayout(new GridBagLayout());
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Ventana.class.getResource("/Imagenes/cargando.gif"))));
		panel.add(lblNewLabel);
		
		
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0; // El área de texto empieza en la columna cero.
		constraints.gridy = 0; // El área de texto empieza en la fila cero
		constraints.gridwidth = 1; // El área de texto ocupa dos columnas.
		constraints.gridheight = 1; // El área de texto ocupa 2 filas.
		panel.add (lblNewLabel, constraints);
		getContentPane().add(panel);
	}
	
	public void eliminaPantallaCarga() {
		this.getCardLayout().last(this.getContentPane());
	}

	public void addInitialPane() {
		JPanel panelInicial = new JPanel();
		pacientes = new JComboBox<String>();
		pacientes.setBounds(10, 10, 80, 20);

		panelInicial.add(pacientes);
		pacientes.addItem("Selecciona paciente");
		PacienteDAOImp pac = new PacienteDAOImp();
		List<Paciente> pacientesBD = pac.obtenerPacientes();
		for (int i = 0; i < pacientesBD.size(); i++) {
			pacientes.addItem(pacientesBD.get(i).getApellidos() + "," + pacientesBD.get(i).getNombre());
		}
		JButton botonSeleccionar = new JButton("Seleccionar");
		JButton botonCrear = new JButton("Crear usuario");
//		botones.add(botonSeleccionar);
//		botones.add(botonCrear);
		panelInicial.add(botonSeleccionar);
		panelInicial.add(botonCrear);

		getContentPane().add(panelInicial, "panel inicial");

	}

	public void MostrarDatosPaciente(Paciente p) {
		this.panelUsuario.mostrarDatoPaciente(p);
	}

	public void setListener(ActionListener al) {
		this.actionlistener = al;
		item1.addActionListener(al);
		item2.addActionListener(al);
		item3.addActionListener(al);
//		for (GraphicPane graphicPane : graficas) {
//			graphicPane.addActionListener(al);
//		}

//		for (JButton but : botones) {
//			but.addActionListener(al);
//		}

		panelUsuario.addActionListener(al);
		this.addActualizacionlisteners((InicializadorListener) al);
		System.out.println("Entra");
		System.out.println(listeners.size());
		this.lanzarEvento();

		panelTest.addMouseListener((MouseListener) al);
	}

	public static Ventana getInstance() {
		if (v == null) {
			v = new Ventana();
		}
		return v;
	}

	private void AddMenu() {
		barraMenu = new JMenuBar();
		this.setJMenuBar(barraMenu);
		menu = new JMenu("Opciones");
		barraMenu.add(menu);
		item1 = new JMenuItem("Nuevo paciente");
		item2 = new JMenuItem("Calibrar sensor");
		item3 = new JMenuItem("Salir");

		
		item2.setEnabled(false);
		menu.add(item1);
		menu.add(item2);
		menu.add(item3);
		repaint();

	}

	private void addTestPane() {

		panelTest = new JTabbedPane();
		panelTest.addTab("Datos de usuario", generatePanel("usuario"));
		getContentPane().add(panelTest, "panel test");
		this.repaint();
	}

	private JPanel generatePanel(String tipo) {

		JPanel resultado = new JPanel();

		GridBagLayout gbl_resultado = new GridBagLayout();
		gbl_resultado.rowWeights = new double[] { 1.0, 0.0 };
		gbl_resultado.columnWeights = new double[] { 1.0, 0.0 };
		resultado.setLayout(gbl_resultado);
		panelUsuario = new UserPane();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		// gbc_panel1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		gbc_panel.gridwidth = 2;
		gbc_panel.gridheight = 2;

		resultado.add(panelUsuario, gbc_panel);
		// }

		return resultado;
	}

	public void addActualizacionlisteners(InicializadorListener toAdd) {
		listeners.add(toAdd);
	}

	public void lanzarEvento() {
		for (InicializadorListener obs : listeners) {
			obs.InicializaDatos();
		}
	}

	public CardLayout getCardLayout() {
		return card;
	}

	public void inicializaCombobox(List<Paciente> pac) {
		panelUsuario.inicializarUsuarios(pac);
	}
	
	public void borraUsuario(List<Paciente> pac) {
		panelUsuario.borraUsuario(pac);
	}

	public void seleccionaUsuario(Paciente paciente) {
		panelUsuario.seleccionaUsuario(paciente);// .getDni()+" - "+paciente.getApellidos()+","+paciente.getNombre());
	}

	public void crearNuevoPaciente( boolean t) {
		panelUsuario.mostrarCampos(t);

	}
	
	public void setTextBotonCreacion(boolean creacion) {
		panelUsuario.setTextBotonCreacion(creacion);
	}

	private String getEtiquetaTab(int mano, int parte) {

		String etiqueta = null;

		switch (parte) {
		case 1:
			etiqueta = "Puño ";
			break;
		case 6:
			etiqueta = "Dedo meñique ";
			break;
		case 5:
			etiqueta = "Dedo anular ";
			break;
		case 4:
			etiqueta = "Dedo corazón ";
			break;
		case 3:
			etiqueta = "Dedo índice ";
			break;
		case 2:
			etiqueta = "Pinza lateral ";
			break;
		default:
			break;
		}

		switch (mano) {
		case 1:
			etiqueta += " de mano izquierda";
			break;
		default:
			etiqueta += " de mano derecha";
		}

		return etiqueta;
	}

	public void showPanelMedidas(int mano, int parte, boolean editable) {

		String etiqueta = getEtiquetaTab(mano, parte);
		int valor = parte + (mano - 1) * 5;

		if (paneles[valor] == null) {

			MedidaGraphicPane medidas = new MedidaGraphicPane(getConsultaActual(), mano, parte, editable,actionlistener);
			medidas.setName(String.valueOf(valor));
			paneles[valor] = medidas;
		}
		panelTest.add(paneles[valor], etiqueta);
		panelTest.setSelectedIndex(panelTest.getComponentCount() - 1);

		
	}

	public void limpiaPanelesMedidas() {
		paneles = new MedidaGraphicPane[12];
		for(int i=1;i<panelTest.countComponents();i++) {
			panelTest.remove(i);
			
		}
	}

	public Paciente crearPaciente() {
		return panelUsuario.crearPaciente();
	}

	public boolean isErrorDataUser() {
		return panelUsuario.isError();
	}

	public void actualizaMediciones(int total, int posicion) {
		panelUsuario.actualizaMediciones(total, posicion);
	}

	public void actualizaDatosPaciente(Paciente paciente) {
		panelUsuario.actualizaDatosPaciente(paciente);

	}

	public void listaConsultas(List<Consulta> consultas) {
		panelUsuario.listaConsultas(consultas);

	}

	public void actualizaConsultaNueva(Consulta c) {
		panelUsuario.actializaConsultaNueva(c);

	}

	public void limpiar() {
		panelUsuario.limpiar();
	}

	/*public void mostrarConsulta(Consulta c, List<String> mediciones) {
		panelUsuario.mostraConsulta(c, mediciones);
	}*/

	public Consulta getConsultaActual() {
		return panelUsuario.getConsultaActual();
	}

	public void actualizaMedicionesRealizadas(Boolean existeMedicion, int mano, int parteMano) {
		panelUsuario.actualizaMedicionesRealizadas(existeMedicion, mano, parteMano);

	}

	public boolean isEditableMedicion() {
		return panelUsuario.isEditableMedicion();
	}

	
	public void mostrarConsulta(List<Consulta> consultasActuales, List<String> med) {
		panelUsuario.mostraConsulta(consultasActuales, med);
	}

	public boolean hasMesure(int mano, int parteMano) {
		return panelUsuario.hasMesure(mano, parteMano);

	}

	public void addMediciones(List<Medicion> medidas, int mano, int parteMano) {
		paneles[parteMano + (mano - 1) * 5].addMediciones(medidas);
	}

	public List<Consulta> getConsultasAComparar() {
		return panelUsuario.getConsultasAComparar();
	}

	public int getSelectedRowsCount() {
		return panelUsuario.getSelectedRowsCount();

	}

	public void disableBotones(boolean state) {
		panelUsuario.disableBotones(state);
	}
/*
	public void muestraPaneles() {
		panelUsuario.muestraPaneles();
		
	}*/

	public void activaBotonesMedida() {
		item2.setEnabled(true);
		panelUsuario.activaBotonesMedida();
		
	}

	public void hideMediciones() {
		panelUsuario.hideMediciones();
		
	}

	public ListadoConsultas obtieneListadoConsultas() {
		return panelUsuario.obtieneListadoConsultas();
	}

	

}
