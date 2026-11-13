package pe.edu.vallegrande.clinica.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/clinica_dental?useSSL=false&serverTimezone=America/Lima";
    private static final String USUARIO = "root";

    private Conexion() { }

    public static Connection obtenerConexion() throws SQLException {
        String clave = System.getenv("DB_PASSWORD");
        return DriverManager.getConnection(URL, USUARIO, clave == null ? "" : clave);
    }
}
