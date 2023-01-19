package vista;

import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class DialogParametros extends JDialog{

	private static final long serialVersionUID = 1L;
	private JTextField desmin;
	private JTextField flexmax;
	private JTextField flexmin;
	private JTextField desmax;
	private JLabel validacion;
	private static DialogParametros dialog;
	private JButton btnNewButton;
	private JComboBox numPruebas;
	private JTextField textField,pesoReferencia;
	private JLabel lblNewLabel_5;
	private JButton btnCancelar;
	private JButton btnBorrarPaciente;
	private JButton btnBorrarConsultas;
	
	public static DialogParametros  getInstanceDialog(ActionListener al) {
		if (dialog==null) {
			dialog=new DialogParametros(al);
			dialog.setListener(al);
		}
		return dialog;
	}
	
	
	public void setModo(int modo) {
		limpiar();
		
		Component[] components = this.getContentPane().getComponents();

		for (Component component : components) {
		    remove(component);  
		}
		switch (modo) {
		case 1:
			this.setPantallaRecogidaDatos();
			break;
		case 2:
			this.setPantallaBorradoPaciente();
			break;
		case 3:
			this.setPantallaBorradoConsulta();
			break;
		default:
			this.setPantallaCalibración();
			break;
		}
		
	}


	private DialogParametros(ActionListener al) {
		//super();
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		this.setSize(300,300);
        this.setLocationRelativeTo(null);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        
        
        numPruebas = new JComboBox();
        numPruebas.setMaximumRowCount(10);
        numPruebas.setModel(new DefaultComboBoxModel(new String[] {"5", "6", "7", "8", "9", "10"}));
        
        flexmax = new JTextField();
        flexmin = new JTextField();
        
        desmin = new JTextField();
        desmax = new JTextField();
        
        btnCancelar=new JButton("Cancelar");
        btnNewButton = new JButton("Registrar datos");
        btnBorrarPaciente = new JButton("Borrar Paciente ");
        btnBorrarConsultas = new JButton("Borrar Consultas");
        validacion = new JLabel("");

        pesoReferencia = new JTextField();
        pesoReferencia.setColumns(15);
        
    }

	private void setPantallaBorradoPaciente() {
		this.setTitle("Borrado de paciente");
		JPanel panel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel.getLayout();
        flowLayout.setHgap(10);
        getContentPane().add(panel);
        
        JPanel panel_1 = new JPanel();
        panel_1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
        flowLayout_1.setVgap(0);
        flowLayout_1.setHgap(0);
        getContentPane().add(panel_1);
        
        JLabel lblNewLabel_6 = new JLabel("<html><body><p align=\"center\">Se procederá al borrado del paciente <br> y todas sus consultas </p></body></html>\r\n\r\n");
        lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
        panel_1.add(lblNewLabel_6);
        
        JPanel panel_2 = new JPanel();
        getContentPane().add(panel_2);
        
        JLabel lblNewLabel_7 = new JLabel("¿Está seguro?");
        lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel_2.add(lblNewLabel_7);
        
        JPanel panel_3 = new JPanel();
        getContentPane().add(panel_3);
        
        
        btnCancelar.setVerticalAlignment(SwingConstants.BOTTOM);
        panel_3.add(btnCancelar);
        
        
        btnBorrarPaciente.setVerticalAlignment(SwingConstants.BOTTOM);
        panel_3.add(btnBorrarPaciente);
	}
	
	
	private void setPantallaBorradoConsulta() {
		setTitle("Borrado de consultas");
		JPanel panel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel.getLayout();
        flowLayout.setHgap(10);
        getContentPane().add(panel);
        
        JPanel panel_1 = new JPanel();
        panel_1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
        flowLayout_1.setVgap(0);
        flowLayout_1.setHgap(0);
        getContentPane().add(panel_1);
        
        JLabel lblNewLabel_6 = new JLabel("<html><body><p align=\"center\">Se procederá al borrado <br> de las consultas seleccionadas<br> y todas las medidas realizadas</p></body></html>\r\n\r\n");
        lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
        panel_1.add(lblNewLabel_6);
        
        JPanel panel_2 = new JPanel();
        getContentPane().add(panel_2);
        
        JLabel lblNewLabel_7 = new JLabel("¿Está seguro?");
        lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel_2.add(lblNewLabel_7);
        
        JPanel panel_3 = new JPanel();
        getContentPane().add(panel_3);
        
        
        btnCancelar.setVerticalAlignment(SwingConstants.BOTTOM);
        panel_3.add(btnCancelar);
        
        
        btnBorrarConsultas.setVerticalAlignment(SwingConstants.BOTTOM);
        panel_3.add(btnBorrarConsultas);
	}
	
	private void setPantallaRecogidaDatos() {
		
		this.setTitle("Recogida de datos de la consulta");
        JPanel panel_1 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        flowLayout.setAlignOnBaseline(true);
        getContentPane().add(panel_1);
        
        JLabel lblNewLabel_1 = new JLabel("Flexión máxima");
        panel_1.add(lblNewLabel_1);
        
        panel_1.add(flexmax);
        flexmax.setColumns(10);
        
        JPanel panel_2 = new JPanel();
        FlowLayout flowLayout_3 = (FlowLayout) panel_2.getLayout();
        flowLayout_3.setAlignment(FlowLayout.LEFT);
        getContentPane().add(panel_2);
        
        JLabel lblNewLabel_2 = new JLabel("Flexión mínima");
        panel_2.add(lblNewLabel_2);
        
        
        panel_2.add(flexmin);
        flexmin.setColumns(10);
        
        JPanel panel_3 = new JPanel();
        FlowLayout flowLayout_2 = (FlowLayout) panel_3.getLayout();
        flowLayout_2.setAlignment(FlowLayout.LEADING);
        getContentPane().add(panel_3);
        
        JLabel lblNewLabel_3 = new JLabel("Desviación cubital máxima");
        panel_3.add(lblNewLabel_3);
        
        panel_3.add(desmax);
        desmax.setColumns(10);
        
        JPanel panel = new JPanel();
        FlowLayout flowLayout_1 = (FlowLayout) panel.getLayout();
        flowLayout_1.setAlignment(FlowLayout.LEFT);
        getContentPane().add(panel);
        
        JLabel lblNewLabel = new JLabel("Desviación cubital mínima");
        panel.add(lblNewLabel);
        
        
        panel.add(desmin);
        desmin.setColumns(10);
        
        btnNewButton.setText("Registrar datos");

        validacion.setBackground(Color.RED);
        validacion.setAlignmentX(Component.CENTER_ALIGNMENT);
        getContentPane().add(validacion);
        validacion.setVisible(false);
        btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        getContentPane().add(btnNewButton);
        this.setVisible(true);
	}
	
	private void setPantallaCalibración() {
		setTitle("Calibrar sensor");
		JLabel lblNewLabel_5 = new JLabel("           Número de medidas de calibración           ");
        validacion.setText("");

        JLabel lblNewLabel_4 = new JLabel("Peso de referencia en Kilogramos");
       
        
        JPanel panel_1 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
        flowLayout.setAlignment(FlowLayout.CENTER);
        flowLayout.setAlignOnBaseline(true);
        panel_1.add(lblNewLabel_4);
        panel_1.add(pesoReferencia);
        getContentPane().add(panel_1);
        
        
        JPanel panel_2 = new JPanel();
        FlowLayout flowLayout2 = (FlowLayout) panel_2.getLayout();
        flowLayout2.setAlignment(FlowLayout.CENTER);
        flowLayout2.setAlignOnBaseline(true);
        panel_2.add(lblNewLabel_5);
        panel_2.add(numPruebas);
        getContentPane().add(panel_2);
        
        btnNewButton.setText("Realizar Calibración");
        JPanel panel_3 = new JPanel();
        FlowLayout flowLayout3 = (FlowLayout) panel_3.getLayout();
        flowLayout3.setAlignment(FlowLayout.CENTER);
        flowLayout3.setAlignOnBaseline(true);
        panel_3.add(btnNewButton);
        getContentPane().add(panel_3);
        
        panel_3.add(btnCancelar);

        
        JPanel panel_4 = new JPanel();
        FlowLayout flowLayout4 = (FlowLayout) panel_4.getLayout();
        flowLayout4.setAlignment(FlowLayout.CENTER);
        flowLayout4.setAlignOnBaseline(true);
        panel_4.add(btnNewButton);
        getContentPane().add(panel_4);
        panel_4.add(validacion);
        
        this.setVisible(true);
	}
	
	private void limpiar() {
		this.desmax.setText("");
		this.desmin.setText("");
		this.flexmax.setText("");
		this.flexmin.setText("");
		this.validacion.setText("");
		this.numPruebas.setSelectedIndex(0);

		this.pesoReferencia.setText("");;
	}
	public void setListener(ActionListener al) {
        btnNewButton.addActionListener(al);
        btnCancelar.addActionListener(al);
        btnBorrarPaciente.addActionListener(al);
        btnBorrarConsultas.addActionListener(al);
	}
	
	public boolean camposRellenos() {
		return !desmin.getText().trim().isEmpty() && !desmax.getText().trim().isEmpty() 
				&& !flexmin.getText().trim().isEmpty() && !flexmax.getText().trim().isEmpty();
	}
	
	public JTextField getDesmin() {
		return desmin;
	}

	public void setDesmin(JTextField desmin) {
		this.desmin = desmin;
	}

	public JTextField getFlexmax() {
		return flexmax;
	}

	public void setFlexmax(JTextField flexmax) {
		this.flexmax = flexmax;
	}

	public JTextField getFlexmin() {
		return flexmin;
	}

	public void setFlexmin(JTextField flexmin) {
		this.flexmin = flexmin;
	}

	public JTextField getDesmax() {
		return desmax;
	}

	public void setDesmax(JTextField desmax) {
		this.desmax = desmax;
	}

	public JLabel getValidacion() {
		return validacion;
	}


	public boolean isCalibraciónValida() {
		String valor = pesoReferencia.getText();
		return isValid(valor.replace(",","."));
	}
	
	public static boolean isValid(String valor) {
	    if (valor == null || valor.trim().length()==0) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(valor);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public String getMensajeCalibración() {
		return pesoReferencia.getText().replace(",", ".")+" "+numPruebas.getSelectedItem().toString();
	}

}
