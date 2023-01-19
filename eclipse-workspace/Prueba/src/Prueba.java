import java.util.ArrayList;
import java.util.List;

import app.Com;
import core.SerialPort;

public class Prueba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SerialPort puerto = new SerialPort();
		ArrayList<String> listaPuertos;
		Com com1;
		
		try {
			listaPuertos = (ArrayList<String>) puerto.getFreeSerialPort();
			System.out.println(listaPuertos.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		System.out.println("Test");
	}

}
