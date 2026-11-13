package pe.edu.vallegrande.clinica.model;

import java.time.LocalDate;

public class Paciente {
    private int idPaciente;
    private String nombres;
    private String apellidos;
    private String dni;
    private String telefono;
    private String correo;
    private LocalDate fechaRegistro;
    private String observaciones;

    public Paciente(int idPaciente, String nombres, String apellidos, String dni,
                    String telefono, String correo, LocalDate fechaRegistro, String observaciones) {
        this.idPaciente = idPaciente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
        this.observaciones = observaciones;
    }
    public int getIdPaciente() { return idPaciente; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getDni() { return dni; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public String getObservaciones() { return observaciones; }
}
