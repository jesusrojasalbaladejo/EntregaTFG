package vista.principal;

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Component;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import modelo.Consulta;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.border.EmptyBorder;

public class ListadoConsultas extends VistasPrincipal {

	private JTable table;
	private JButton nuevaMedicion;
	private DefaultTableModel model;
	private JButton mostrarMediciones;
	private JButton eliminarConsultaSeleccionada;
	private static Object[] columnNames = { "Mostrar Medidas","Id consulta", "Fecha", "Número de mediciones" };;
	///HashMap<Integer, Consulta> consultas;

	public ListadoConsultas() {
		setBorder(new TitledBorder(null, "Selecci\u00F3n de consultas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setBackground(new Color(255, 255, 255));

		//consultas=new HashMap<Integer, Consulta>();
		Object[][] data = { {} };
		model = new DefaultTableModel(data, columnNames);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0};
		gridBagLayout.columnWeights = new double[]{1.0};
		setLayout(gridBagLayout);
		

		table = new JTable(model) {

			private static final long serialVersionUID = 1L;

			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 0:
					return Boolean.class;
				case 2:
					return Integer.class;
				case 3:
						return String.class;
				default:
					return Integer.class;

				}
			}
			
			 @Override
			   public boolean isCellEditable(int row, int column) {
			       return column == 0;
			   }
			 @Override
			 public int[] getSelectedRows(){
				 List<Integer> resultados = new ArrayList<>();
				 for (int i=0;i<this.getRowCount();i++) {
					 if ((Boolean)this.getValueAt(i, 0)) {
						 resultados.add(i);
					 }
				 }
				 int[] valores = new int[resultados.size()];
				  for (int i = 0; i < valores.length; i++) {
					  valores[i] = resultados.get(i);
				  }

				  System.out.println("Seleccionados:"+resultados.toString());
				 return valores;
			 }
			 
			 @Override
			public int getSelectedRow() {
				 for (int i=0;i<this.getRowCount();i++) {
					 if ((Boolean)this.getValueAt(i, 0)) {
						 return i;					 }
				 }return (Integer) null;
			}
	};
		
		
		
		
		((DefaultTableModel)table.getModel()).setRowCount(0);

		table.setPreferredScrollableViewportSize(table.getPreferredSize());

		JScrollPane scroll_table = new JScrollPane(table); // add table to scroll panel
		scroll_table.setBorder(new EmptyBorder(60, 0, 0, 0));
		scroll_table.setBounds(5, 10, 300, 150);
		scroll_table.setBackground(new Color(255, 255, 255));
		scroll_table.setVisible(true);
		scroll_table.getViewport().setBackground(new Color(255,255,255));

		GridBagConstraints gbc_scroll_table = new GridBagConstraints();
		gbc_scroll_table.fill = GridBagConstraints.BOTH;
		gbc_scroll_table.insets = new Insets(0, 0, 5, 0);
		gbc_scroll_table.gridx = 0;
		gbc_scroll_table.gridy = 0;
		this.add(scroll_table, gbc_scroll_table);

		JPanel panelBotones = new JPanel();
		panelBotones.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_panelBotones = new GridBagConstraints();
		gbc_panelBotones.insets = new Insets(0, 0, 5, 0);
		gbc_panelBotones.gridx = 0;
		gbc_panelBotones.gridy = 2;
		add(panelBotones, gbc_panelBotones);

		mostrarMediciones = new JButton("Mostrar consultas seleccionadas");
		mostrarMediciones .setAlignmentX(Component.CENTER_ALIGNMENT);
		mostrarMediciones.setEnabled(false);
		panelBotones.add(mostrarMediciones);
		nuevaMedicion = new JButton("Crear nueva consulta");
		nuevaMedicion.setEnabled(false);
		panelBotones.add(nuevaMedicion);

		eliminarConsultaSeleccionada = new JButton("Eliminar consulta seleccionada");
		eliminarConsultaSeleccionada .setEnabled(false);
		panelBotones.add(eliminarConsultaSeleccionada );

	}
/*
	public void inicializarUsuarios(List<String> pac) {
	}

	public void mostrarDatoPaciente(Paciente p) {
	}

	public void vacia() {
	}
*/
	@Override
	public void addActionListener(ActionListener al) {
		this.nuevaMedicion.addActionListener(al);
		this.mostrarMediciones.addActionListener(al);
		this.eliminarConsultaSeleccionada.addActionListener(al);
		this.table.getModel().addTableModelListener((TableModelListener) al);
		
	}

	public void listaConsultas(List<Consulta> consultas) {
//		this.consultas.clear();
		((DefaultTableModel) table.getModel()).setRowCount(0);
///
		for (Consulta consulta : consultas) {
			Object[] datos = { false,consulta.getId(), consulta.getFecha(), consulta.getNumMedicionesRealizadas() };
			((DefaultTableModel) table.getModel()).addRow(datos);
			//this.consultas.put(consulta.getId(), consulta);
		}
		table.repaint();

	}

	@Override
	public void limpiar() {
		((DefaultTableModel) table.getModel()).setRowCount(0);
		mostrarMediciones.setEnabled(false);
	}
	
	public JTable getTable() {
		return table;
	}
	
	//public Consulta getConsulta(int id) {
	//	return this.consultas.get(id);
//	}

	public void setSelectedConsultaActual(Consulta c) {
		int insertados=0;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date(System.currentTimeMillis());
		String fecha = formatter.format(date);
		
		for (int i=0; i<((DefaultTableModel) table.getModel()).getRowCount();i++) {
			if(((String)((DefaultTableModel) table.getModel()).getValueAt(i, 2)).equals(fecha)) {
				((DefaultTableModel) table.getModel()).setValueAt(true, i, 0);
				insertados++;
			}else {
				((DefaultTableModel) table.getModel()).setValueAt(false, i, 0);
			}
		}
		if (insertados==0)
		{
			Object[] datos = { true,c.getId(), c.getFecha(), c.getNumMedicionesRealizadas()};
			((DefaultTableModel) table.getModel()).addRow(datos);	
		}
				
	}

	public int getSelectedRowsCount() {
		
			int total=0;
			for (int i=0;i<((DefaultTableModel) table.getModel()).getRowCount();i++) {
				 if ((Boolean)((DefaultTableModel) table.getModel()).getValueAt(i, 0)) {
					 total++;
					 }
			 }
			return total;
			
			}

	public void disableBoton(boolean state) {
		this.mostrarMediciones.setEnabled(state);
		this.eliminarConsultaSeleccionada.setEnabled(state);

	}
	public void activaBotonesMedida() {
		nuevaMedicion.setEnabled(true);
		
	}

}