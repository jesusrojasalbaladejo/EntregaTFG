package modelo;

import java.util.EventListener;

public interface EventoConfiguracionListener extends EventListener{

	void conexionEstablecida(EventoConfiguracion ec);
}
