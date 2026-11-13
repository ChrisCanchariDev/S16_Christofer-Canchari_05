package pe.edu.vallegrande.clinica.dao;

import pe.edu.vallegrande.clinica.config.Conexion;
import pe.edu.vallegrande.clinica.model.Paciente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {
    public List<Paciente> listar() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM pacientes ORDER BY id_paciente DESC";
        try (Connection cn = Conexion.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                pacientes.add(new Paciente(
                    rs.getInt("id_paciente"), rs.getString("nombres"),
                    rs.getString("apellidos"), rs.getString("dni"),
                    rs.getString("telefono"), rs.getString("correo"),
                    rs.getDate("fecha_registro").toLocalDate(), rs.getString("observaciones")
                ));
            }
        }
        return pacientes;
    }

    public void insertar(Paciente p) throws SQLException {
        String sql = "INSERT INTO pacientes (nombres,apellidos,dni,telefono,correo,fecha_registro,observaciones) VALUES (?,?,?,?,?,?,?)";
        try (Connection cn = Conexion.obtenerConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            asignarCampos(ps, p);
            ps.executeUpdate();
        }
    }

    public boolean actualizar(Paciente p) throws SQLException {
        String sql = "UPDATE pacientes SET nombres=?,apellidos=?,dni=?,telefono=?,correo=?,fecha_registro=?,observaciones=? WHERE id_paciente=?";
        try (Connection cn = Conexion.obtenerConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            asignarCampos(ps, p);
            ps.setInt(8, p.getIdPaciente());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        try (Connection cn = Conexion.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM pacientes WHERE id_paciente=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private void asignarCampos(PreparedStatement ps, Paciente p) throws SQLException {
        ps.setString(1, p.getNombres());
        ps.setString(2, p.getApellidos());
        ps.setString(3, p.getDni());
        ps.setString(4, p.getTelefono());
        ps.setString(5, p.getCorreo());
        ps.setDate(6, Date.valueOf(p.getFechaRegistro()));
        ps.setString(7, p.getObservaciones());
    }
}
