package ventanas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import clases.Usuario;
import clases.mejorasPorUsuario;
import clases.Edificios;
import clases.Mejoras;
  



public class ClaseContenedora {
	private static Logger logger = Logger.getLogger("ClaseContenedora");
	public static void main(String[] args) {
	ClaseContenedora cc = new ClaseContenedora();
	//for(Usuario u : cc.sacarUsuarios("Usuario.db")) {
	//	for(Mejoras m : cc.sacarMejoras("Usuario.db")) {
	//		cc.guardarMejorasPersonales("Usuario.db", u.getnUsuario(), m.getNombre());
	//	}
	//}
	cc.actualizarPersonalMejoras("Usuario.db", "admin", "Invierte en acciones con eToro", 0);
	cc.actualizarPersonalMejoras("Usuario.db", "admin", "Se tu propio jefe", 0);
	cc.actualizarPersonalMejoras("Usuario.db", "admin", "Loteria del Estado", 0);
	cc.actualizarPersonalMejoras("Usuario.db", "admin", "Suben las acciones de tu empresa", 0);
	cc.actualizarPersonalMejoras("Usuario.db", "admin", "Ingresaste en la facultad de ADE", 0);
	cc.actualizarPersonalMejoras("Usuario.db", "admin", "La mafia compra tu silencio", 0);
}
	///////////////////////////////////////////////////
	/////////// RECLAMAR LA PUNTUACION ////////////////
	///////////////////////////////////////////////////
	
	public long cargarPuntos(String nomdb){
		try {			
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/"+nomdb);
			Statement stmt = conn.createStatement();
			long dev;
			/*String sql = String.format("SELECT elpunto FROM punto");
			//logger.log(Level.INFO, "Statement: " + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			Statement stmt2 = conn.createStatement();*/
			ResultSet rs = stmt.executeQuery("SELECT * FROM punto WHERE elpunto>0");
			dev=rs.getInt("elpunto");
			rs.close();
			stmt.close();
			conn.close(); 
			System.out.println(dev);
			return dev;
			} catch (SQLException e) {
			System.out.println(e);
			}
			return 1;
	}
	
	public void borraPuntos(String nomdb){
		try {			
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/"+nomdb);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("DELETE FROM punto WHERE elpunto>0");
			rs.close();
			stmt.close();
			conn.close(); 
			} catch (SQLException e) {
			System.out.println(e);
			}
	}
	
	

	////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////// MODIFICACIÓN DE LA APUESTA 2 CON SU EVENTO DE RATON ////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public ArrayList<Usuario> sacarUsuarios(String nombredb){
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/"+nombredb);
			Statement stmt = conn.createStatement();
			ArrayList<Usuario> dev = new ArrayList<>();
			String sql = "SELECT * FROM usuario";
			logger.log(Level.INFO, "Statement: " + sql);
			ResultSet rs = stmt.executeQuery("SELECT * FROM usuario");
			while( rs.next() ) { 
				String nombre = rs.getString("nombre_usuario");
				long dinerototal = rs.getLong("dinero_total");
				int dineroclick = rs.getInt("dinero_click");
				String contraseña = rs.getString("contraseña");
				int dinero_por_segundo = rs.getInt("dinero_por_segundo");
				dev.add( new Usuario( nombre, contraseña, dinerototal, dinero_por_segundo, dineroclick ) );
			}
			rs.close();
			stmt.close();
			conn.close(); 
			return dev;
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
			return null;
		
	}
	public ArrayList<mejorasPorUsuario> sacarMejorasUsuarios(String nombredb){
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/"+nombredb);
			Statement stmt = conn.createStatement();
			ArrayList<mejorasPorUsuario> dev = new ArrayList<>();
			String sql = "SELECT * FROM personmejoras";
			logger.log(Level.INFO, "Statement: " + sql);
			ResultSet rs = stmt.executeQuery("SELECT * FROM personmejoras");
			while( rs.next() ) { 
				String codUsuario = rs.getString("codUsuario");
				String codMejora = rs.getString("codMejora");
				int cantidad = rs.getInt("cantidad");
				
				dev.add( new mejorasPorUsuario( codUsuario, codMejora, cantidad ) );
			}
			rs.close();
			stmt.close();
			conn.close(); 
			return dev;
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
			return null;
		
	}
	
	public ArrayList<Mejoras> sacarMejoras(String nombredb){
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/"+nombredb);
			Statement stmt = conn.createStatement();
			ArrayList<Mejoras> dev = new ArrayList<>();
			String sql = "SELECT * FROM mejoras";
			logger.log(Level.INFO, "Statement: " + sql);
			ResultSet rs = stmt.executeQuery("SELECT * FROM mejoras");
			while( rs.next() ) { 
				String nombreMejoras = rs.getString("nombreMejoras");
				long precioMejoras = rs.getLong("precioMejoras");
				long incdps = rs.getLong("incdps");
				long incdc = rs.getLong("incdc");
				long incdt = rs.getLong("incdt");
				
				dev.add( new Mejoras( nombreMejoras, precioMejoras, incdps, incdc, incdt ) );
			}
			rs.close();
			stmt.close();
			conn.close(); 
			return dev;
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
			return null;
		
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////// FUNCION PARA CAMBIAR LOS ATRIBUTOS DEL USUARIO DE LA BD/////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public void actualizarPartida(String nombredb,String usuario, long a, int b, int c){
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/"+nombredb);
			Statement stmt = conn.createStatement();
			String sql = String.format("UPDATE usuario SET dinero_total = "+ a +", dinero_click= "+ b +", dinero_por_segundo = "+ c +" where nombre_usuario = '"+ usuario+"';");
			logger.log(Level.INFO, "Statement: " + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			Statement stmt2 = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM usuario");
			stmt2.close();
			rs.close();
			conn.close(); 
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
		
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////// FUNCION PARA SACAR EL ARRAYLIST CON LOS USUARIOS DE LA BD //////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public ArrayList<Edificios> sacarEdificios(String nombredb){
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/"+nombredb);
			Statement stmt = conn.createStatement();
			ArrayList<Edificios> dev = new ArrayList<>();
			String sql = "SELECT * FROM edificios";
			logger.log(Level.INFO, "Statement: " + sql);
			ResultSet rs = stmt.executeQuery("SELECT * FROM edificios");
			while( rs.next() ) { 
				String nombre = rs.getString("nombre");
				long precio = rs.getLong("precio");
				long cant = rs.getInt("eCantidad");
				int prod = rs.getInt("eProduccion");

				dev.add( new Edificios( nombre, precio, cant, prod, "") );
			}
			rs.close();
			stmt.close();
			conn.close(); 
			return dev;
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
			return null;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////// FUNCION PARA METER UN USUARIO EN LA BASE DE DATOS //////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public void guardarDBUsuario(String nombredb,int dinero_click, int dinero_segundo, long dinero_total, String usuario, String contraseña){
		try {
			
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/"+nombredb);
			Statement stmt = conn.createStatement();
			String sql = String.format("INSERT INTO usuario VALUES ('%s', %d, %d, '%s', %d)", usuario, dinero_total, dinero_click, contraseña, dinero_segundo);
			logger.log(Level.INFO, "Statement: " + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			Statement stmt2 = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM usuario");
			stmt2.close();
			rs.close();
			conn.close(); 
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
		
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////// FUNCION PARA BORRAR UN USUARIO DE LA BASE DE DATOS /////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public void borrarDBUsuario(String nombredb,String usuario){
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/"+nombredb);
			Statement stmt = conn.createStatement();
			String sql = String.format("DELETE FROM usuario where nombre_usuario = '"+ usuario+"';");
			logger.log(Level.INFO, "Statement: " + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			Statement stmt2 = conn.createStatement();
			ResultSet rs = stmt.executeQuery("DELETE FROM personedif where codigoUsuario = '"+ usuario+"';");
			logger.log(Level.INFO, "Statement: " + "DELETE FROM personedif where codigoUsuario = '"+ usuario+"';");
			stmt2.close();
			rs.close();
			conn.close(); 
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
		
	}
	
	public void guardarMejorasPersonales( String nombredb,String cod1,String cod2){
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/"+nombredb);
            Statement stmt = conn.createStatement();
            String sql = String.format("INSERT INTO personmejoras VALUES ('%s', '%s', %d)", cod1, cod2, 0);
            logger.log(Level.INFO, "Statement: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close(); 
            } catch (SQLException e) {
            System.out.println("No se ha podido cargar el driver de la base de datos");
            }

}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////// FUNCION PARA CAMBIAR LA CONTRASEÑA DEL USUARIO SELECCIONADO EN LA BD ///////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public void cambiarContraseñaBD(String nombredb,String usuario, String contraseña, String nuevaContra){
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/"+nombredb);
			Statement stmt = conn.createStatement();
			String sql = String.format("UPDATE usuario SET contraseña = '"+nuevaContra+"' where nombre_usuario = '"+ usuario+"' AND contraseña= '"+contraseña+"';");
			logger.log(Level.INFO, "Statement: " + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			Statement stmt2 = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM usuario");
			stmt2.close();
			rs.close();
			conn.close(); 
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
		
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////// FUNCION PARA METER UN NUEVO EDIFICIO EN LA BD //////no se usa///////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	/*public void guardarDBEdificio(String nombredb,String nombre, long precio, long cant, int prod, String image ){
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/"+nombredb);
			Statement stmt = conn.createStatement();
			String sql = String.format("INSERT INTO edificios VALUES ('%s', %d, %d, %d, '%s')", nombre, precio, cant, prod, image);
			logger.log(Level.INFO, "Statement: " + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			Statement stmt2 = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM edificios");
			stmt2.close();
			rs.close();
			conn.close(); 
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
		
	}*/

	////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////// FUNCION PARA BORRAR UN EDIFICIO DE LA BD ///////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public void borrarDBEdificio(String nombredb,String nombre){
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/"+nombredb);
			Statement stmt = conn.createStatement();
			String sql = String.format("DELETE FROM edificios where nombre = '"+ nombre+"';");
			logger.log(Level.INFO, "Statement: " + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			Statement stmt2 = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM edificios");
			stmt2.close();
			rs.close();
			conn.close(); 
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
		
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////// FUNCION PARA AÑADIR MEJORAS EN LA BD ///////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public void guardarMejoras( String nombredb,long precio, String nombre, long incremento, String img){
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/"+nombredb);
			Statement stmt = conn.createStatement();
			String sql = String.format("INSERT INTO mejoras VALUES ( %d,'%s', %d, '%s')", precio, nombre, incremento, img);
			logger.log(Level.INFO, "Statement: " + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			Statement stmt2 = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM mejoras");
			stmt2.close();
			rs.close();
			conn.close(); 
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
		
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////// FUNCION PARA BORRAR MEJORA DE LA BD ////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public void borrarMejoras(String nombredb,String nombre){
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/"+nombredb);
			Statement stmt = conn.createStatement();
			String sql = String.format("DELETE FROM mejoras where nombre = '"+ nombre+"';");
			logger.log(Level.INFO, "Statement: " + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			Statement stmt2 = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM mejoras");
			stmt2.close();
			rs.close();
			conn.close(); 
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
	
	}
	public void guardaredificiosPersonales( String nombredb,String cod1, String cod2){
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/"+nombredb);
			Statement stmt = conn.createStatement();
			String sql = String.format("INSERT INTO personedif VALUES ('%s', '%s', %d)", cod1, cod2, 0);
			logger.log(Level.INFO, "Statement: " + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			Statement stmt2 = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM p");
			stmt2.close();
			rs.close();
			conn.close(); 
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
		
	}
	public ArrayList<ArrayList<String>> sacarEdificiosPersonales(String nombredb,String u) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/"+nombredb);
			Statement stmt = conn.createStatement();
			ArrayList<ArrayList<String>> listadelusuario = new ArrayList<>();
			String sql = "SELECT * FROM personedif where codigoUsuario= '"+u+"';";
			logger.log(Level.INFO, "Statement: " + sql);
			ResultSet rs = stmt.executeQuery(sql);
			while( rs.next() ) { 
				ArrayList<String> xd = new ArrayList<>();
				String cd = rs.getString("codigoEdificio");
				String ct = rs.getString("cantidad");
				xd.add(cd);
				xd.add(ct);
				listadelusuario.add(xd);
			}
			
			rs.close();
			stmt.close();
			conn.close(); 
			return listadelusuario;
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
			return null;
	}
	
	public void añadirCantidades(String nombredb,String u, int num) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/Usuario.db");
			ArrayList<ArrayList<String>> listadelusuario = sacarEdificiosPersonales("Usuario.db",u);
			Statement stmt = conn.createStatement();
			String sql = "UPDATE edificios SET eCantidad= "+Integer.parseInt(listadelusuario.get(num).get(1))+" where nombre = '"+listadelusuario.get(num).get(0)+"';";
			logger.log(Level.INFO, "Statement: " + sql);
			ResultSet rs = stmt.executeQuery(sql);
			rs.close();
			stmt.close();
			conn.close();
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
			
	}
	public void actualizarPersonalEdifi(String nombredb,String usuario, int numerito){
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/Usuario.db");
			Statement stmt = conn.createStatement();
			String sql = String.format("UPDATE personedif SET cantidad = "+ numerito +" where codigoUsuario = '"+ usuario+"';");
			logger.log(Level.INFO, "Statement: " + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close(); 
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
		
	}
	public void actualizarPersonalEdifi2(String nombredb,String usuario, String str2, int numerito){
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/Usuario.db");
			Statement stmt = conn.createStatement();
			String sql = String.format("UPDATE personedif SET cantidad = "+ numerito +" where codigoUsuario = '"+ usuario+"' and codigoEdificio = '"+str2+"';");
			logger.log(Level.INFO, "Statement: " + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close(); 
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
		
	}
	
	public void actualizarPersonalMejoras(String nombredb,String usuario, String mejora, int cantidadMejora) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/Usuario.db");
			Statement stmt = conn.createStatement();
			String sql = String.format("UPDATE personmejoras SET cantidad = "+ cantidadMejora +" where codUsuario = '"+ usuario+"' and codMejora = '"+ mejora+"';");
			logger.log(Level.INFO, "Statement: " + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close(); 
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
		
	} 
}
