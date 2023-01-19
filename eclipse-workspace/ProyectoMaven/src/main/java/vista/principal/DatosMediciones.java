package vista.principal;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import modelo.Consulta;
import vista.Ventana;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Color;

import javax.swing.border.TitledBorder;
import java.awt.CardLayout;

public class DatosMediciones extends VistasPrincipal {

	private static final long serialVersionUID = 1L;
	private JTable tablaMedidas;
	private JTextField flexMax;
	private JTextField flexMin;
	private JTextField desvCubMax;
	private JTextField DesvCubMin;
	private JLabel imagenManos;
	private DefaultTableModel modelMedidas;
	private JPanel panelTextoMedicion;
	private JPanel PanelInvisible;
	
	
	private Consulta c;
	private boolean edicion;
	private List<Consulta> mediciones_comparacion;
	

	public DatosMediciones() {
		setLayout(new CardLayout(0, 0));
		this.add(creaPanel(), "fondo");
	
		this.PanelInvisible=new JPanel();
		PanelInvisible.setVisible(true);
		PanelInvisible.setBackground(new Color(255,255,255));
		this.add(PanelInvisible, "tapa");
	}

	

	@Override
	public void setVisible(boolean aFlag) {
		if(aFlag) {
			((CardLayout)this.getLayout()).show(this, "fondo");
		}else {
			((CardLayout)this.getLayout()).show(this, "tapa");
		}
	}
	
	public JPanel creaPanel() {
		JPanel completo=new JPanel();
		completo.setBorder(new TitledBorder(null, "Datos de medicion", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		completo.setBackground(new Color(255, 255, 255));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 450, 0 };
		gridBagLayout.rowHeights = new int[] { 112, 94, 92, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		completo.setLayout(gridBagLayout);

		panelTextoMedicion = new JPanel();
		panelTextoMedicion.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_panelTextoMedicion = new GridBagConstraints();
		gbc_panelTextoMedicion.fill = GridBagConstraints.BOTH;
		gbc_panelTextoMedicion.insets = new Insets(0, 0, 5, 0);
		gbc_panelTextoMedicion.gridx = 0;
		gbc_panelTextoMedicion.gridy = 0;
		completo.add(panelTextoMedicion, gbc_panelTextoMedicion);
		GridBagLayout gbl_panelTextoMedicion = new GridBagLayout();

		panelTextoMedicion.setLayout(gbl_panelTextoMedicion);

		JLabel lblNewLabel_4 = new JLabel("Flexión máxima");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 0;
		panelTextoMedicion.add(lblNewLabel_4, gbc_lblNewLabel_4);

		flexMax = new JTextField();
		GridBagConstraints gbc_flexMax = new GridBagConstraints();
		gbc_flexMax.insets = new Insets(0, 0, 5, 5);
		gbc_flexMax.gridx = 0;
		gbc_flexMax.gridy = 1;
		panelTextoMedicion.add(flexMax, gbc_flexMax);
		flexMax.setColumns(10);
		flexMax.setEditable(false);

		JLabel lblNewLabel_5 = new JLabel("Flexión mínima");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_5.gridx = 6;
		gbc_lblNewLabel_5.gridy = 0;
		panelTextoMedicion.add(lblNewLabel_5, gbc_lblNewLabel_5);

		flexMin = new JTextField();
		GridBagConstraints gbc_flexMin = new GridBagConstraints();
		gbc_flexMin.insets = new Insets(0, 0, 5, 0);
		gbc_flexMin.gridx = 6;
		gbc_flexMin.gridy = 1;
		panelTextoMedicion.add(flexMin, gbc_flexMin);
		flexMin.setColumns(10);
		flexMin.setEditable(false);
		
		JLabel lblNewLabel_6 = new JLabel("Desviación cubital máxima");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 0;
		gbc_lblNewLabel_6.gridy = 3;
		panelTextoMedicion.add(lblNewLabel_6, gbc_lblNewLabel_6);

		JLabel lblNewLabel = new JLabel("                                  ");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 3;
		panelTextoMedicion.add(lblNewLabel, gbc_lblNewLabel);

		desvCubMax = new JTextField();
		GridBagConstraints gbc_desvCubMax = new GridBagConstraints();
		gbc_desvCubMax.insets = new Insets(0, 0, 0, 5);
		gbc_desvCubMax.gridx = 0;
		gbc_desvCubMax.gridy = 4;
		panelTextoMedicion.add(desvCubMax, gbc_desvCubMax);
		desvCubMax.setColumns(10);
		desvCubMax.setEditable(false);
		JLabel desvCubMin = new JLabel("Desviación cubital mínima");
		GridBagConstraints gbc_desvCubMin = new GridBagConstraints();
		gbc_desvCubMin.insets = new Insets(0, 0, 5, 0);

		gbc_desvCubMin.gridx = 6;
		gbc_desvCubMin.gridy = 3;
		panelTextoMedicion.add(desvCubMin, gbc_desvCubMin);

		DesvCubMin = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();

		gbc_textField_3.gridx = 6;
		gbc_textField_3.gridy = 4;
		panelTextoMedicion.add(DesvCubMin, gbc_textField_3);
		DesvCubMin.setColumns(10);
		DesvCubMin.setEditable(false);
		
		JPanel panelTablaMediciones = new JPanel();
		panelTablaMediciones.setBorder(
				new TitledBorder(null, "Mediciones realizadas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelTablaMediciones.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_panelTablaMediciones = new GridBagConstraints();
		gbc_panelTablaMediciones.fill = GridBagConstraints.BOTH;
		gbc_panelTablaMediciones.insets = new Insets(0, 0, 5, 0);
		gbc_panelTablaMediciones.gridx = 0;
		gbc_panelTablaMediciones.gridy = 1;
		completo.add(panelTablaMediciones, gbc_panelTablaMediciones);

		Object[][] medicionesRealizadas = { { "izquierda" }, { "derecha" } };
		Object[] columnNamesMedidas = { "", "Puño", "Pinza lateral", "Índice", "Corazón", "Anular", "meñique" };
		modelMedidas = new DefaultTableModel(medicionesRealizadas, columnNamesMedidas);
		;

		tablaMedidas = new JTable(modelMedidas) {

			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				default:
					return Boolean.class;

				}
			}
		};

		tablaMedidas.setEnabled(false);
		tablaMedidas.setPreferredScrollableViewportSize(tablaMedidas.getPreferredSize());
		JScrollPane scrollPaneMedidas = new JScrollPane(tablaMedidas);

		panelTablaMediciones.add(scrollPaneMedidas);

		JPanel panelmanos = new JPanel();
		panelmanos.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_panelmanos = new GridBagConstraints();
		gbc_panelmanos.fill = GridBagConstraints.BOTH;
		gbc_panelmanos.gridx = 0;
		gbc_panelmanos.gridy = 2;
		completo.add(panelmanos, gbc_panelmanos);

		Image img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(DatosMediciones.class.getResource("/Imagenes/manos.jpg"))).getImage();

		ImageIcon img2 = new ImageIcon( img.getScaledInstance(600, 400, Image.SCALE_SMOOTH));
		imagenManos = new JLabel(img2);

		panelmanos.add(imagenManos);
		return completo;
		
	}
	@Override
	public void addActionListener(ActionListener al) {
		imagenManos.addMouseListener((MouseListener) al);
	}

	@Override
	public void limpiar() {

		this.flexMax.setText("");
		this.flexMin.setText("");
		this.desvCubMax.setText("");
		this.DesvCubMin.setText("");

		for (int i = 0; i < modelMedidas.getRowCount(); i++) {
			for (int j = 1; j < modelMedidas.getColumnCount(); j++) {
				modelMedidas.setValueAt(false, i, j);
			}
		}
		c=null;
		edicion=false;
		mediciones_comparacion=new ArrayList<Consulta>();
		
	}

	private void ActualizaTablaMediciones(List<String> mediciones) {
		for (String m : mediciones) {
			System.out.println(m);
			String[] valores = m.split("\\*");
			System.out.println(valores[0]);
			System.out.println(valores[1]);
			modelMedidas.setValueAt(true, Integer.parseInt(valores[0]) - 1, Integer.parseInt(valores[1]));
		}
	}

	public Consulta getConsultaActual() {
		return c;
		
	}

	public void actualizaMedicionesRealizadas(Boolean existeMedicion, int mano, int parteMano) {
		modelMedidas.setValueAt(existeMedicion, mano - 1, parteMano);
	}

	public boolean isEditableMedicion() {
		return this.edicion;
	}

	public void mostrarConsulta(List<Consulta> consultasActuales, List<String> med) {
		this.limpiar();
		this.actualizarDatosConsulta(consultasActuales);
		this.ActualizaTablaMediciones(med);
	}

	private void actualizarDatosConsulta(List<Consulta> consultasActuales) {
		
		if (consultasActuales.size() > 1) {
			limpiar();
			this.c = null;
			this.mediciones_comparacion = consultasActuales;
			this.edicion = false;
			panelTextoMedicion.setVisible(false);
		} else {
			Consulta c = consultasActuales.get(0);
			this.addNuevaConsulta(c);
			panelTextoMedicion.setVisible(true);

		}
	}

	public void addNuevaConsulta(Consulta c) {
		this.flexMax.setText(c.getFlexMax());
		this.flexMin.setText(c.getFlexMin());
		this.desvCubMax.setText(c.getDesvCubMax());
		this.DesvCubMin.setText(c.getDesvCubMin());

		this.c = c;
		this.mediciones_comparacion = null;
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date(System.currentTimeMillis());
		String fecha = (formatter.format(date));
		System.out.println(c.getFecha());
		System.out.println(fecha);

		this.edicion = (c.getFecha().contains(fecha));
		System.out.println(fecha);
	}


	public boolean hasMesure(int mano, int parteMano) {
		return (boolean) modelMedidas.getValueAt(mano, parteMano);
	}

	public List<Consulta> getConsultasAComparar() {
		return mediciones_comparacion;
	}
	
	

}
