package vista;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

import javafx.scene.layout.Background;
import modelo.Consulta;
import modelo.Medicion;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class MedidaGraphicPane extends JPanel {

	List<GraphicPane> gp;
	private JTable table;
	private int numeroMediciones;
	private JTextArea valoresCoherencia;
	private JButton guardarRegistros;
	Consulta c;
	private int mano, parteMano;

	/**
	 * @wbp.parser.constructor
	 */
	public MedidaGraphicPane(ActionListener al, boolean isEditable) {
		crearPanelCompleto(al, isEditable);
	}

	private void crearPanelCompleto(ActionListener al, boolean editable) {
		GraphicPane panel = null;
		setBackground(Color.white);
		gp = new ArrayList<>();
		numeroMediciones = 0;
		// if (valores==null)
		{
			for (int i = 0; i < 5; i++) {
				if (i == 4) {
					panel = new GraphicPane("Valores medios");
					panel.setBorder(new TitledBorder(new LineBorder(Color.black), "Registro medios de fuerza"));
					panel.setListener(al);
					
				} else {
					panel = new GraphicPane("Gráfica " + (i + 1));
					panel.setBorder(new TitledBorder(new LineBorder(Color.black), "Registro de fuerza " + (i + 1)));
					panel.setVisible(editable);
					if (!editable) {
						panel.disableButton();
					}else {
						panel.setcheckboxTrue();
					}

				}

				panel.addActionListener(al);

				gp.add(panel);

			}

			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
			gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
			gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
			gridBagLayout.rowWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
			setLayout(gridBagLayout);

			GridBagConstraints gbc_panel_6 = new GridBagConstraints();
			gbc_panel_6.insets = new Insets(0, 0, 5, 5);
			gbc_panel_6.fill = GridBagConstraints.BOTH;
			gbc_panel_6.gridx = 1;
			gbc_panel_6.gridy = 0;
			// add(new JPanel(), gbc_panel_6);
			add(gp.get(0), gbc_panel_6);

			JPanel panel_1 = new JPanel();
			GridBagConstraints gbc_panel_1 = new GridBagConstraints();
			gbc_panel_1.insets = new Insets(0, 0, 5, 0);
			gbc_panel_1.fill = GridBagConstraints.BOTH;
			gbc_panel_1.gridx = 2;
			gbc_panel_1.gridy = 0;
			add(panel_1, gbc_panel_1);
			panel_1.setVisible(editable);
			panel_1.setBackground(Color.white);

			valoresCoherencia = new JTextArea("");
			panel_1.add(valoresCoherencia);

			GridBagConstraints gbc_panel_8 = new GridBagConstraints();
			gbc_panel_8.insets = new Insets(0, 0, 5, 0);
			gbc_panel_8.fill = GridBagConstraints.BOTH;
			gbc_panel_8.gridx = 2;
			gbc_panel_8.gridy = 1;
			// add(new JPanel(), gbc_panel_8);
			add(gp.get(1), gbc_panel_8);

			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 0, 5);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 1;
			gbc_panel.gridy = 2;
			// add(new JPanel(), gbc_panel);
			add(gp.get(2), gbc_panel);

			GridBagConstraints gbc_panel_7 = new GridBagConstraints();
			gbc_panel_7.insets = new Insets(0, 0, 5, 5);
			gbc_panel_7.fill = GridBagConstraints.BOTH;
			gbc_panel_7.gridx = 0;
			gbc_panel_7.gridy = 1;
			// add(new JPanel(), gbc_panel_7);
			add(gp.get(3), gbc_panel_7);

			GridBagConstraints gbc_panel_9 = new GridBagConstraints();
			gbc_panel_9.insets = new Insets(0, 0, 5, 5);
			gbc_panel_9.fill = GridBagConstraints.BOTH;
			gbc_panel_9.gridx = 1;
			gbc_panel_9.gridy = 1;
			add(gp.get(4), gbc_panel_9);

			GridBagConstraints gbc_table = new GridBagConstraints();
			gbc_table.fill = GridBagConstraints.NONE;
			gbc_table.gridx = 2;
			gbc_table.gridy = 2;
			guardarRegistros = new JButton("Guardar registros");
			add(guardarRegistros, gbc_table);
			guardarRegistros.addActionListener(al);
			guardarRegistros.setVisible(editable);
		}
	}

	public MedidaGraphicPane(Consulta c, int mano, int parte, boolean visible, ActionListener al) {
		this(al, visible);
		this.c = c;
		this.mano = mano;
		this.parteMano = parte;
	}

	public void addDatosTablas(int x, int y, String valor) {
		table.setValueAt(valor, x, y);

	}

	public void setListener(ActionListener al) {
		for (GraphicPane graphicPane : gp) {
			graphicPane.addActionListener(al);
		}
	}

	public void calculaMedias() {

		gp.get(4).getValues().clear(); // limpio la gráfica central

		int totalvalores = 0;
		numeroMediciones = 0;

		for (int i = 0; i < 4; i++) {
			if (gp.get(i).hasValues()) {
				numeroMediciones++;
				totalvalores = gp.get(i).getValuesNormalizados().getItemCount();
			}
		}

		switch (numeroMediciones) {
		case 0:
			gp.get(4).getDataset().removeAllSeries();
			break;
		case 1:
			XYSeries valores = null;
			try {
				if (gp.get(0).hasValues()) {
					valores = (XYSeries) gp.get(0).getValuesNormalizados().clone();
				}
				if (gp.get(1).hasValues()) {
					valores = (XYSeries) gp.get(1).getValuesNormalizados().clone();
				}
				if (gp.get(2).hasValues()) {
					valores = (XYSeries) gp.get(2).getValuesNormalizados().clone();
				}
				if (gp.get(3).hasValues()) {
					valores = (XYSeries) gp.get(3).getValuesNormalizados().clone();
				}

				valores.setKey("Valores Medios");
				gp.get(4).setValorNormalizado(valores);
				// }
				this.setCoherencia("");

			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// for (int x = 0;x<valores.getItemCount();x++) {

			break;
		default:
			Double total = 0.0;
			valores = new XYSeries("Valores Medios");
			for (int i = 0; i < totalvalores; i++) {
				total = 0.0;
				for (int j = 0; j < 4; j++) {
					if (gp.get(j).hasValues()) {
						total += gp.get(j).getValuesNormalizados().getY(i).doubleValue();
					}
				}
				System.out.println(total + " " + Double.valueOf(numeroMediciones) + " "
						+ total / Double.valueOf(numeroMediciones));
				valores.add(i, total / Double.valueOf(numeroMediciones));
			}

			StringBuilder msjCoherencia = new StringBuilder();
			msjCoherencia.append("Coherencia\n");
			for (int i = 0; i < 3; i++) {
				for (int j = i + 1; j < 4; j++) {
					if (i != j) {
						if (gp.get(i).hasValues() && gp.get(j).hasValues()) {
							msjCoherencia.append("Gráficas " + (i + 1) + " y " + (j + 1) + " : "
									+ coherencia(gp.get(i).getValuesNormalizados(), gp.get(j).getValuesNormalizados())
									+ "%\n");
						}
					}

				}
			}

			this.setCoherencia(msjCoherencia.toString());
			// gp.get(4).changeSeries(valores);
			gp.get(4).setValorNormalizado(valores);

			break;
		}

	}

	public String coherencia(XYSeries v1, XYSeries v2) {

		int total = 0;
		double division = 0.0;
		for (int i = 0; i < v1.getItemCount(); i++) {
			if (v2.getY(i).doubleValue() > v1.getY(i).doubleValue()) {
				division += (v1.getY(i).doubleValue() / v2.getY(i).doubleValue());
			} else {
				division += (v2.getY(i).doubleValue() / v1.getY(i).doubleValue());

			}
			total++;
		}

		return String.format("%.2f", division * 100 / Double.valueOf(total));
	}

	public int getNumeroMediciones() {
		return numeroMediciones;
	}

	public void setCoherencia(String coherencia) {
		this.valoresCoherencia.setText(coherencia);
	}

	public void borraCoherencia() {
		this.setCoherencia("");
	}

	public Consulta getConsulta() {
		return this.c;
	}

	public List<GraphicPane> getMediciones() {
		return gp;
	}

	public int getMano() {
		return mano;
	}

	public int getParteMano() {
		return parteMano;
	}

	private void cargaValores(List<Medicion> valores) {
		for (Medicion medicion : valores) {
			gp.get(medicion.getNumMedida() - 1).cargaValores(medicion);
		}
	}

	private void cargaValores(HashMap<Consulta, List<Medicion>> mediciones) {
		HashMap<Integer, List<Medicion>> valores = new HashMap<Integer, List<Medicion>>();
		for (Consulta c : mediciones.keySet()) {
			for (Medicion m : mediciones.get(c)) {
				if (valores.get(m.getNumMedida() - 1) == null) {
					valores.put(m.getNumMedida() - 1, new ArrayList<Medicion>());
				}
				valores.get(m.getNumMedida() - 1).add(m);
			}
		}
		for (Integer v : valores.keySet()) {
			gp.get(v).cargaValores(valores.get(v));
		}
	}

	public void addMediciones(List<Medicion> medidas) {
		for (Medicion medicion : medidas) {
			if ((this.c != null) && (medicion.getCoherencia() != null)) {
				this.valoresCoherencia.setText(medicion.getCoherencia().trim());
				this.valoresCoherencia.getParent().setVisible(true);
			}
			gp.get(medicion.getNumMedida() - 1).addMedicion(medicion);
		}
	}

	public void mostrarPaneles(boolean valor) {
		for (int i = 0; i < 4; i++) {
			gp.get(i).setVisible(valor);
		}
	}

	public void includeMediciones(boolean valor) {
		System.out.println(valor);

		if (gp.get(4).getDataset().getSeriesCount()>0) {
		XYSeries serie = gp.get(4).getDataset().getSeries(0);
		gp.get(4).getDataset().removeAllSeries();
		gp.get(4).getDataset().addSeries(serie);

		if (valor) {
			System.out.println("Tengo que meter las gráficas");
			for (int i = 0; i < 4; i++) {
				if (gp.get(i).getSerieNormalizada().getItemCount() > 0) {
					gp.get(4).getDataset().addSeries(gp.get(i).getSerieNormalizada());
				}
			}

		}
		System.out.println(gp.get(4).getDataset().getSeriesCount());
		//mostrarPaneles(!valor);
	}
		}

	public String getCoherencia() {
		return valoresCoherencia.getText();
	}

}
