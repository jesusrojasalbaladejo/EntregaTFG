#include <EEPROM.h>

String cadenaRecibida = "";
int sensorPin = A6;
float tiempoRecepcion = 0;
float tiempo = 0;
float dato_leido = 0;
float vout, vout2;
float factorcorreccion = 2;


/**
* Función de configuración del programa donde se establecen los baudios empleados en la comunicación, 
* en este caso 115200 baudios para que se manden la mayor cantidad de datos posibles. Además, 
* cada vez que haya datos en el puerto en serie se leerán. 
**/
void setup() {
  Serial.begin(115200);
  Serial.flush();
  EEPROM.get(0, factorcorreccion);
  pinMode(13, OUTPUT);
            digitalWrite(13,LOW);

  while (Serial.available() > 0) Serial.read();
}

/**
* Función encargada de procesar las órdenes enviadas por la aplicación de escritorio. Las órdenes que puede recibir son:
* Sincronizando: Se empleará cuando se inicie la aplicación de escritorio para poder conocer el puerto en el que se encuentra la tarjeta.
* leerSensor: Se mandan los datos recibidos por el sensor a la aplicación de escritorio.
* stop: Detiene el envío de datos a la aplicación.
* calibra: Calcula el factor de corrección empleado por la tarjeta para ajustar los valores obtenidos por el sensor
*/
void loop() {
  //yield();
  if (String(cadenaRecibida).equals(String("sincronizando"))) {
    Serial.println(String("OK"));
    cadenaRecibida = "";
  } else {
    if (String(cadenaRecibida).equals(String("leerSensor"))) {
      leersensor();
    }

    else {
      if (String(cadenaRecibida).equals(String("stop"))) {
        cadenaRecibida = "";
        tiempoRecepcion = 0;
      } else {
        if (String(cadenaRecibida).startsWith(String("calibra"))) {
digitalWrite(13,HIGH);

          String parametros = (cadenaRecibida.substring(8));
          int index = parametros.indexOf(' ');
          if (index != -1) {
            double peso_calibre = (parametros.substring(0, index).toDouble());
            int numMediciones = (parametros.substring(index + 1).toInt());
            calibra(peso_calibre, numMediciones);
          } else {
            calibra(1, 3);
          }
          Serial.println(String("CALIBRADO "+String(EEPROM.get(0, factorcorreccion),3)));
          digitalWrite(13,LOW);

          cadenaRecibida = "";

        }
      }
    }
  }
}


/**
* Función que se encarga de obtener el valor del sensor de fuerza, aplicarle el factor de corrección 
* y enviarlo por el puerto en serie a la aplicación de escritorio.
**/
void leersensor() {
  if (tiempoRecepcion == 0) {
    tiempoRecepcion = millis();
  }
  tiempo = (millis() - tiempoRecepcion) / 1000;
  dato_leido = analogRead(sensorPin);
  vout = (dato_leido * 5.0) / 1023;
  vout = vout * factorcorreccion;
  Serial.println(String(tiempo, 3) + "*" + String(vout, 3));
}


/**
* Evento que ocurrirá cuando se encuentran datos en el puerto en serie. Lee la línea completa y se le asigna a la variable cadenaRecibida.
*/
void serialEvent() {
  if ((Serial.available() > 0)) {
    cadenaRecibida = Serial.readStringUntil('\n');
  }
}

/**
* Función que realiza la calibración del sensor modificando el factor de corrección empleado por el programa. Para ello usaremos un peso de referencia aportado por
* el usuario. Una vez tomada una medida de peso, se ajusta el valor del factor de corrección para ajustar el valor de pesos obtenido al peso de referencia con un
* error del 1%. Además se indicarán el número de veces que se tomarán muestras para obtener la media de factores de corrección. Finalmente, el valor obtenido se guarda en la memoria EEPROM
* PARÁMETROS:
* double peso: Peso de referencia empleado.
* int numeroMedidas: Número de veces que se obtendrá el valor de corrección. Se harán dos mediciones extras para omitir los valores máximos y mínimos.
**/
void calibra(double peso, int numeroMedidas) {

  numeroMedidas = numeroMedidas + 2;
  double lecturas[numeroMedidas], lecturasCorregidas[numeroMedidas];
  double correcciones[numeroMedidas];


  for (int i = 0; i < numeroMedidas; i++) {
    correcciones[i] = 2;
  }

  for (int i = 0; i < numeroMedidas; i++) {

    dato_leido = analogRead(sensorPin);
    lecturas[i] = (dato_leido * 3.3) / 1023;
    lecturasCorregidas[i] = lecturas[i];
    delay(500);
  }

  for (int j = 0; j < numeroMedidas; j++) {
    while (lecturasCorregidas[j] > (peso + 0.001) || (lecturasCorregidas[j]) < (peso - 0.001)) {
      lecturasCorregidas[j] = lecturas[j] * correcciones[j];
      if (lecturasCorregidas[j] > peso)
        correcciones[j] = correcciones[j] - 0.0001;
      else
        correcciones[j] = correcciones[j] + 0.0001;
    }
    //Serial.println(String(lecturas[j],8)+ " "+ String(correcciones[j],8) + " - "+ String(lecturasCorregidas[j],8));
  }


  double total = 0;
  BubbleSortAsc(correcciones, numeroMedidas);
  for (int k = 1; k < numeroMedidas - 1; k++) {
    total = total + correcciones[k];
  }
  factorcorreccion = total / (numeroMedidas - 2);
  EEPROM.put(0, factorcorreccion);
}

/**
* Función que realiza la ordenación de un array de manera ascendente
* PARÁMETROS:
* double* values: Array que será ordenado.
* int length: Número de elementos del array
**/
void BubbleSortAsc(double* array, int length) {
  int i, j, flag = 1;
  double temp;
  for (i = 1; (i <= length) && flag; i++) {
    flag = 0;
    for (j = 0; j < (length - 1); j++) {
      if (array[j + 1] < array[j]) {
        temp = array[j];
        array[j] = array[j + 1];
        array[j + 1] = temp;
        flag = 1;
      }
    }
  }
}
