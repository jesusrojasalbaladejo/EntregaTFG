package vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import modelo.Medicion;
import uk.me.berndporr.iirj.Butterworth;

import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JLabel;

public class GraphicPane extends JPanel {

	private XYSeries series, serieFiltrada, serieNormalizada;

	private JButton botonInicio;
	private JLabel lblmaximo;
	private JFreeChart chart;
	private int posicionMaximo;
	private XYSeriesCollection dataset;

	private JCheckBox cb;
	private JCheckBox cb2;

	/**
	 * TODO
	 * 
	 * @param tipo
	 */

	public void updateChart() {
		this.chart.fireChartChanged();
	}

	public GraphicPane(String tipo) {

		super();
		series = new XYSeries("Valores sin tratar");
		serieFiltrada = new XYSeries("Valores Filtrados " + tipo);
		
		serieNormalizada = new XYSeries("Valores normalizados "+tipo);
		dataset = new XYSeriesCollection();
		//dataset = new XYSeriesCollection(serieFiltrada);

		chart = ChartFactory.createXYLineChart(tipo, "tiempo (s)", "fuerza (kp)", dataset, PlotOrientation.VERTICAL,
				true, true, true);

		// chart.getXYPlot().getRangeAxis().setRange(0, 10);

		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("El valor máximo de fuerza es:");
		panel.add(lblNewLabel);
		//lblNewLabel.setVisible(false);

		lblmaximo = new JLabel("0 Kp");
		panel.add(lblmaximo);
		//lblmaximo.setVisible(false);

		ChartPanel chartpanel = new ChartPanel(chart);
		chartpanel.setDomainZoomable(true);

		this.add(chartpanel, BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		botonInicio = new JButton("Iniciar Prueba");

		buttons.add(botonInicio);

		cb = new JCheckBox("Mostrar todas las gráficas");
		cb.setVisible(false);
		
		cb2 = new JCheckBox("Comparar todas las medidas");
		cb2.setVisible(false);
		
		
		JPanel panelInferior = new JPanel();
		panelInferior.add(buttons);
		panelInferior.add(cb);
		panelInferior.add(cb2);
		
		this.add(panelInferior, BorderLayout.SOUTH);
		
		if (tipo.equals("Valores medios")) {
			buttons.setVisible(false);
			cb.setVisible(true);
			cb2.setVisible(true);
		}

	}

	public XYSeries getValues() {
		return series;
	}
	
	public void setListener(ActionListener al) {
		this.cb.addActionListener(al);
		this.cb2.addActionListener(al);
	}

	
	public XYSeries getValuesNormalizados() {
		return serieNormalizada;
	}

	public void setValues(XYSeries serie) {
		this.series = serie;
	}

	public void changeSeries(XYSeries serie) {
	    DecimalFormat df = new DecimalFormat("0.000");

		this.dataset.removeAllSeries();;
		this.dataset.addSeries(serie);
		lblmaximo.setText(df.format(serie.getMaxY()) + " kgs");
	}

	
	public JFreeChart getGrafica() {
		return chart;
	}

/*	public void setValorMaximo(double d) {
		DecimalFormat df = new DecimalFormat("0.000");
		if (d == this.series.getY(this.series.getItemCount() - 1).doubleValue()) {
			posicionMaximo = this.series.getItemCount() - 1;
		}
		setValorMaximo(df.format(d) + " kgs");

	}*/

	public void setValorMaximo() {
		DecimalFormat df = new DecimalFormat("0.000");
		int posicion = this.serieFiltrada.getItemCount() - 1;
		if (this.serieFiltrada.getY(posicion).doubleValue() == this.serieFiltrada.getMaxY()) {
			posicionMaximo = posicion;
		}

		setValorMaximo(df.format(this.serieFiltrada.getMaxY()) + " kgs");
	}

	public void addActionListener(ActionListener al) {
		this.botonInicio.addActionListener(al);
	}

	public boolean hasValues() {
		return this.serieNormalizada.getItemCount()> 0;
	}
	
	
	
	

	public void normalizar() {

		System.out.println("Posicion máxima " + posicionMaximo);
		System.out.println(this.serieFiltrada.getKey().toString());

		
		
		int posicionizq = posicionMaximo;
		int posicionder = posicionMaximo;

		System.out.println(serieFiltrada.getMaxY()+" "+serieFiltrada.getY(posicionMaximo));

		/**
		 * Se localiza donde se llega a cero por la derecha
		 */
		while (this.serieFiltrada.getY(posicionder).doubleValue() > (this.serieFiltrada.getY(posicionMaximo).doubleValue() / 10)
				&& posicionder < this.serieFiltrada.getItemCount() - 1) {
			// System.out.println("Posicion derecha: " +posicionder+" Valor:
			// "+this.serieFiltrada.getY(posicionder));
			posicionder++;
		}

		/**
		 * Se localiza donde se llega a cero por la izquierda
		 */
		while (serieFiltrada.getY(posicionizq).doubleValue() > (serieFiltrada.getY(posicionMaximo).doubleValue() / 10) && posicionizq > 0) {
			posicionizq--;
		}

		int numElementos = posicionder - posicionizq + 1;

		this.serieNormalizada.clear();

		for(int i=0;i<numElementos;i++) {
			serieNormalizada.add(i*100/numElementos,serieFiltrada.getY(posicionizq+i).doubleValue());
		}
		serieNormalizada.add(100,serieFiltrada.getY(posicionder).doubleValue());
		
		XYSeries auxiliar = new XYSeries(serieNormalizada.getKey().toString());
		double valorPorcentajeAnterior = 0.0;
		double valorPorcentaje = 0.0;
		int numeroValores = 0;
		double totalValores = 0;

		for (int i = 0; i < serieNormalizada.getItemCount(); i++) {
			valorPorcentaje = serieNormalizada.getX(i).doubleValue();

			if (valorPorcentaje == valorPorcentajeAnterior) {
				numeroValores++;
				totalValores += serieNormalizada.getY(i).doubleValue();
			} else {
				auxiliar.add(valorPorcentajeAnterior, totalValores / numeroValores);
				totalValores = serieNormalizada.getY(i).doubleValue();
				numeroValores = 1;
				valorPorcentajeAnterior = valorPorcentaje;
			}
		}
		
		auxiliar.add(valorPorcentaje,serieNormalizada.getY(serieNormalizada.getItemCount()-1));
			

		
		
		serieNormalizada=auxiliar;
		
		changeSeries(serieNormalizada);
		//dataset.addSeries(serieNormalizada);
		//dataset.addSeries(auxiliar);
		
		chart.getXYPlot().getDomainAxis().setLabel("Porcentaje Medición");

		chart.getXYPlot().getDomainAxis().setVerticalTickLabels(false);
		
		chart.getXYPlot().setDataset(dataset);
		
		

	}

	public void normalizar2() {

		System.out.println("Posicion máxima " + posicionMaximo);

		int posicionizq = posicionMaximo;
		int posicionder = posicionMaximo;

		System.out.println("Total=" + series.getItemCount());

		XYSeries serie = new XYSeries("");

		for (int i = 0; i < this.series.getItemCount(); i++) {
			serie.add(this.series.getX(i), this.series.getY(i));
		}

		this.series.clear();

		for (int posicion = 1; posicion < serie.getItemCount(); posicion++) {
			this.series.add(serie.getX(posicion), serie.getY(posicion));
		}
		XYSeriesCollection dataset = new XYSeriesCollection(this.series);
		chart.getXYPlot().setDataset(dataset);

	}

	public void setValorMaximo(String mensaje) {
		System.out.println("EL MENSAJE ES: "+mensaje);
		this.lblmaximo.setText(mensaje);
	}

	public void cargaValores(Medicion medicion) {
		dataset.removeAllSeries();
		dataset.addSeries(medicion.getValores());
		setValorMaximo(String.valueOf(medicion.getValores().getMaxY()) + "kg");
		botonInicio.setVisible(false);
	}

	public void cargaValores(List<Medicion> mediciones) {
		dataset.removeAllSeries();
		for (Medicion m : mediciones) {
			dataset.addSeries(m.getValores());
		}
		botonInicio.setVisible(false);
	}

	public void addMedicion(Medicion medicion) {
		if (dataset==null) {
			dataset=new XYSeriesCollection(medicion.getValoresNormalizados());
			chart.getXYPlot().getDomainAxis().setLabel("Porcentaje Medición");
			this.chart.getXYPlot().setDataset(dataset);
		}else {
			dataset.addSeries(medicion.getValoresNormalizados());	
		}
		this.serieFiltrada=medicion.getValoresFiltrados();
		this.serieNormalizada=medicion.getValoresNormalizados();
		
		System.out.println(medicion.getValores().getMaxY() + " kgs");
		setValorMaximo(medicion.getValoresNormalizados().getMaxY() + " kgs");
		botonInicio.setText("Descartar medida");
	}

	public XYSeries getValuesFiltrados() {
		return this.serieFiltrada;
	}

	public void clear() {
		this.serieFiltrada.clear();
		this.series.clear();
		this.serieNormalizada.clear();
		dataset.removeAllSeries();
		//dataset.addSeries(series);
		//dataset.addSeries(serieFiltrada);
	}
	
	public XYSeriesCollection getDataset() {
		return dataset;
	}

	public XYSeries getSerieNormalizada() {
		return serieNormalizada;
	}

	public void disableButton() {
		botonInicio.setVisible(false);
		
	}

	public void setValorNormalizado(XYSeries serie) {
		try {
			
		DecimalFormat df = new DecimalFormat("0.000");
		this.serieNormalizada = (XYSeries) serie.clone();
		
		this.dataset.removeAllSeries();;
		this.dataset.addSeries(this.serieNormalizada);
		lblmaximo.setText(df.format(serie.getMaxY()) + " kgs");
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setcheckboxTrue() {
		this.cb.setSelected(true);		
	}



}
