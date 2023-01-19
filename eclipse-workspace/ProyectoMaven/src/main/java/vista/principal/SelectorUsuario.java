package vista.principal;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;
import modelo.Paciente;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;

public class SelectorUsuario extends VistasPrincipal{

	private static final long serialVersionUID = 1L;
	JFrame ventana;
	private JComboBox<String> listaPacientes;
	private JButton boton;
	private List<Paciente> listadoPacientes;
	public SelectorUsuario() {
	setBackground(new Color(255, 255, 255));
		

		/****MARCO****/
		
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0};
		setLayout(gridBagLayout);
		
		
		
		/****COMPONENTES****/
		
		
		listaPacientes = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		gbc_comboBox.gridwidth = 1;
		gbc_comboBox.gridheight = 1;

		add(listaPacientes, gbc_comboBox);
		
		boton= new JButton();
		boton.setText(">>");
		GridBagConstraints gbc_boton = new GridBagConstraints();
		//gbc_boton.fill = GridBagConstraints.HORIZONTAL;
		gbc_boton.gridx = 2;
		gbc_boton.gridy = 1;
		gbc_boton.gridwidth = 1;
		gbc_boton.gridheight = 1;
		add(boton,gbc_boton);
		
		
		
		
	}
	
	@Override
	public void addActionListener(ActionListener al) {
		boton.addActionListener(al);
	}
	
	
	public void inicializarUsuarios(List<Paciente> pac) {
		System.out.println("Actualizando estoy...");
		this.listadoPacientes = pac;
		

		List<String> lista= new ArrayList<String>();
		lista.add("Nuevo Paciente");

		for (Paciente p : listadoPacientes) {
		lista.add(p.getDni()+" - "+p.getApellidos()+","+p.getNombre());	
		}
		
		DefaultComboBoxModel model = new DefaultComboBoxModel(lista.toArray());
		listaPacientes.removeAllItems();
		listaPacientes.setModel(model);
	}

	@Override
	public void limpiar() {
		listaPacientes.setSelectedIndex(0);		
	}

	public void seleccionaUsuario(Paciente paciente) {
		String nombre_paciente=paciente.getDni()+" - "+paciente.getApellidos()+","+paciente.getNombre();
		listadoPacientes.add(paciente);
		listaPacientes.setSelectedIndex(listaPacientes.getItemCount()-1);

	}
	
}
