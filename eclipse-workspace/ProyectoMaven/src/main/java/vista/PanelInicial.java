package vista;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.FlowLayout;

public class PanelInicial extends JPanel {
	public PanelInicial() {
		setBackground(Color.WHITE);
		setForeground(Color.WHITE);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		flowLayout.setAlignOnBaseline(true);
		panel.setBounds(0, 0, 100, 100);
		add(panel);
	}
}
