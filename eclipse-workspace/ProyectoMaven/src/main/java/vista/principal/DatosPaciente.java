package vista.principal;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import modelo.Paciente;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class DatosPaciente extends VistasPrincipal {

	
	private static final long serialVersionUID = 1L;
	private JButton btnGuardarPaciente;
	private JTextField nombre;
	private JComboBox<String> manodominante,edad,Sexo;
	private JTextField telefono;
	private JTextField ocupacion;
	private JTextField apellidos;
	private JTextField dni;
	private JButton btnBorrarPaciente;

	public DatosPaciente() {
		setBorder(new TitledBorder(null, "Datos del paciente", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		setLayout(gridBagLayout);
		

		JLabel label = new JLabel("Dni");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.fill = GridBagConstraints.VERTICAL;
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		add(label, gbc_label);

		dni = new JTextField(9);
		GridBagConstraints gbc_dni = new GridBagConstraints();
		gbc_dni.insets = new Insets(0, 0, 5, 5);
		gbc_dni.fill = GridBagConstraints.HORIZONTAL;
		gbc_dni.gridx = 0;
		gbc_dni.gridy = 2;
		add(dni, gbc_dni);
		dni.setColumns(12);

		JLabel label_1 = new JLabel("Nombre");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 0;
		add(label_1,gbc_label_1);

		nombre = new JTextField(20);
		GridBagConstraints gbc_nombre = new GridBagConstraints();
		gbc_nombre.insets = new Insets(0, 0, 5, 5);
		gbc_nombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombre.gridx = 2;
		gbc_nombre.gridy = 2;
		add(nombre,gbc_nombre);

		JLabel label_2 = new JLabel("Apellidos");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 4;
		gbc_label_2.gridy = 0;
		add(label_2,gbc_label_2);

		apellidos = new JTextField(20);
		GridBagConstraints gbc_apellidos = new GridBagConstraints();
		gbc_apellidos.insets = new Insets(0, 0, 5, 5);
		gbc_apellidos.fill = GridBagConstraints.HORIZONTAL;
		gbc_apellidos.gridx = 4;
		gbc_apellidos.gridy = 2;
		add(apellidos,gbc_apellidos);

		JLabel lblNewLabel_2 = new JLabel("Ocupación");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 6;
		gbc_lblNewLabel_2.gridy = 0;
		add(lblNewLabel_2,gbc_lblNewLabel_2);

		ocupacion = new JTextField();
		GridBagConstraints gbc_ocupacion = new GridBagConstraints();
		gbc_ocupacion.insets = new Insets(0, 0, 5, 5);
		gbc_ocupacion.fill = GridBagConstraints.HORIZONTAL;
		gbc_ocupacion.gridx = 6;
		gbc_ocupacion.gridy = 2;
		add(ocupacion,gbc_ocupacion);
		ocupacion.setColumns(20);
		
		btnGuardarPaciente = new JButton("Guardar Paciente");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 7;
		gbc_btnNewButton.gridy = 2;
		add(btnGuardarPaciente, gbc_btnNewButton);

		//JButton guardarUsuario = new JButton("Guardar Paciente");
		//panel.add(guardarUsuario);
		// botones.add(guardarUsuario);

		JLabel label_4 = new JLabel("Teléfono");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 0;
		gbc_label_4.gridy = 5;
		add(label_4,gbc_label_4);
		
		btnBorrarPaciente = new JButton("Borrar Paciente");
		//btnBorrarPaciente.setVisible(false);
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 7;
		gbc_btnNewButton_1.gridy = 5;
		add(btnBorrarPaciente, gbc_btnNewButton_1);

		telefono = new JTextField(15);
		GridBagConstraints gbc_telefono = new GridBagConstraints();
		gbc_telefono.insets = new Insets(0, 0, 0, 5);
		gbc_telefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_telefono.gridx = 0;
		gbc_telefono.gridy = 7;
		add(telefono,gbc_telefono);

		JLabel lblNewLabel_1 = new JLabel("Sexo");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 5;
		add(lblNewLabel_1,gbc_lblNewLabel_1);

		Sexo = new JComboBox<String>();
		Sexo.addItem("");
		Sexo.addItem("Hombre");
		Sexo.addItem("Mujer");

		GridBagConstraints gbc_Sexo = new GridBagConstraints();
		gbc_Sexo.insets = new Insets(0, 0, 0, 5);
		gbc_Sexo.gridx = 2;
		gbc_Sexo.gridy = 7;
		add(Sexo,gbc_Sexo);

		JLabel lblNewLabel = new JLabel("Edad");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 4;
		gbc_lblNewLabel.gridy = 5;
		add(lblNewLabel,gbc_lblNewLabel);

		edad = new JComboBox<String>();
		edad.addItem("");

		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.gridx = 4;
		gbc_comboBox.gridy = 7;
		add(edad,gbc_comboBox);

		JLabel lblNewLabel_3 = new JLabel("Mano dominante");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 6;
		gbc_lblNewLabel_3.gridy = 5;
		add(lblNewLabel_3,gbc_lblNewLabel_3);

		manodominante = new JComboBox<String>();
		manodominante.addItem("");
		manodominante.addItem("Izquierda");
		manodominante.addItem("Derecha");
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox_1.gridx = 6;
		gbc_comboBox_1.gridy = 7;
		add(manodominante,gbc_comboBox_1);

		for (int i = 15; i < 101; i++) {
			edad.addItem(String.valueOf(i));
		}

	}

	@Override
	public void addActionListener(ActionListener al) {
		btnGuardarPaciente.addActionListener(al);
		btnBorrarPaciente.addActionListener(al);
	}

	public void inicializarUsuarios(List<String> pac) {
	}

	public JButton getAddUserButton() {
		return btnGuardarPaciente;
	}

	public void mostrarDatoPaciente(Paciente p) {
		
		limpiar();
		this.dni.setText(p.getDni());
		this.nombre.setText(p.getNombre());
		this.apellidos.setText(p.getApellidos());
		this.telefono.setText(p.getTelefono());
		this.ocupacion.setText(p.getOcupación());
		this.Sexo.setSelectedIndex(p.getSexo());	
		this.manodominante.setSelectedIndex(p.getManoDominante());	
		if(p.getEdad()==0) {this.edad.setSelectedIndex(0);}else{this.edad.setSelectedIndex(p.getEdad()-14);}
		this.setVisible(true);
	}
	
	public void actualizaDatosPaciente(Paciente paciente) {
		paciente.setNombre(this.nombre.getText());
		paciente.setApellidos(this.apellidos.getText());
		paciente.setOcupación(this.ocupacion.getText());
		paciente.setTelefono(this.telefono.getText());
		paciente.setEdad((this.edad.getSelectedIndex()==0)?0:Integer.parseInt(this.edad.getSelectedItem().toString()));
		paciente.setSexo(this.Sexo.getSelectedIndex());
		paciente.setManoDominante(this.manodominante.getSelectedIndex());

	}
	
	public Paciente crearPaciente() {
		return new Paciente(	this.nombre.getText(),
								this.apellidos.getText(),
								this.dni.getText(),
								this.telefono.getText(),
								this.ocupacion.getText(),
								this.Sexo.getSelectedIndex(),
								this.manodominante.getSelectedIndex(),
								(this.edad.getSelectedIndex()==0)?0:Integer.parseInt(this.edad.getSelectedItem().toString())
								);
	}

	@Override
	public void limpiar() {
		this.dni.setText("");
		this.nombre.setText("");
		this.apellidos.setText("");
		this.telefono.setText("");
		this.ocupacion.setText("");
		this.Sexo.setSelectedIndex(0);	
		this.manodominante.setSelectedIndex(0);		
		this.edad.setSelectedIndex(0);
	}
	
	public void setTextBotonCreacion(boolean creacion) {
		if (creacion) {
			this.btnGuardarPaciente.setText("Guardar Paciente");
			
		}else {
			this.btnGuardarPaciente.setText("Actualizar Paciente");
		}
		this.btnBorrarPaciente.setVisible(!creacion);
	}
	
	public boolean isError() {
		
		System.out.println(this.Sexo.getSelectedIndex()>0); 
		System.out.println(this.edad.getSelectedIndex()>0);
		System.out.println(this.manodominante.getSelectedIndex()>0);
				System.out.println(this.ocupacion.getText().trim().length()>0);
				System.out.println(this.telefono.getText().trim().length()>0);
				System.out.println(this.dni.getText().trim().length()>0);
				System.out.println(this.nombre.getText().length()>0);
				System.out.println(this.apellidos.getText().length()>0);
		
		return !( this.Sexo.getSelectedIndex()>0 && 
				this.edad.getSelectedIndex()>0 &&
			   this.manodominante.getSelectedIndex()>0 &&
			   this.ocupacion.getText().trim().length()>0 &&
			 this.telefono.getText().trim().length()>0 &&
			 this.dni.getText().trim().length()>0 &&
			 this.nombre.getText().length()>0 &&
			 this.apellidos.getText().length()>0);
		}

	
}
