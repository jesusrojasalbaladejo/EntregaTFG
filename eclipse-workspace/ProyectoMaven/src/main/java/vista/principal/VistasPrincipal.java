package vista.principal;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

public abstract class VistasPrincipal extends JPanel{
	
	private static final long serialVersionUID = 1L;

public abstract void addActionListener(ActionListener al);

public abstract void limpiar();


}
