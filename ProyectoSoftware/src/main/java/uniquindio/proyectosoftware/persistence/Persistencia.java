package uniquindio.proyectosoftware.persistence;



import uniquindio.proyectosoftware.modelo.Pasteleria;


public class Persistencia {


	public static final String RUTA_ARCHIVO_MARKETPLACE_XML = "C://td//pasteleria.xml";
	public static final String RUTA_ARCHIVO_LOG = "C://td//persistencia//log//pasteleria_Log.txt";

	public static final String RUTA_ARCHIVO_MARKETPLACE_XML_RESPALDO = "C://td//pasteleriaRespaldo.xml";



	public static void copiarArchivoRespaldoXml(){
		ArchivoUtil.copiarArchivoRespaldo(RUTA_ARCHIVO_MARKETPLACE_XML,RUTA_ARCHIVO_MARKETPLACE_XML_RESPALDO );
	}

	
//	----------------------LOADS------------------------
	/**
	 * GENERAR REGISTRO
	 * @param mensajeLog
	 * @param nivel
	 * @param accion
	 */
	public static  void guardaRegistroLog(String mensajeLog, int nivel, String accion) {
		ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
	}

	/**
	 * CARGAR RECURSO BINARIO
	 * @return
	 */
	public static Pasteleria cargarRecursoMarketplaceXML() {
		Object object = null;
		Pasteleria hostal = null;
		
		
		try {
			object = ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MARKETPLACE_XML);
			hostal = (Pasteleria) object;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hostal;
	}

	public static  void guardarRecursoMarketplaceXML(Pasteleria hostal) {
		try {
			
			ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MARKETPLACE_XML, hostal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
