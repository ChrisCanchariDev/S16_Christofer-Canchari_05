package pe.edu.vallegrande.clinica.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pe.edu.vallegrande.clinica.dao.PacienteDAO;
import pe.edu.vallegrande.clinica.model.Paciente;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class PacienteController {
    @FXML private TextField txtNombres, txtApellidos, txtDni, txtTelefono, txtCorreo;
    @FXML private TextArea txtObservaciones;
    @FXML private DatePicker fechaRegistro;
    @FXML private TableView<Paciente> tablaPacientes;
    @FXML private TableColumn<Paciente, Integer> colId;
    @FXML private TableColumn<Paciente, String> colNombres, colApellidos, colDni, colTelefono, colCorreo, colObservaciones;
    @FXML private TableColumn<Paciente, LocalDate> colFecha;
    @FXML private Label lblEstado;
    private final PacienteDAO dao = new PacienteDAO();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idPaciente"));
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaRegistro"));
        colObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        tablaPacientes.getSelectionModel().selectedItemProperty().addListener((obs, anterior, actual) -> {
            if (actual != null) {
                txtNombres.setText(actual.getNombres());
                txtApellidos.setText(actual.getApellidos());
                txtDni.setText(actual.getDni());
                txtTelefono.setText(actual.getTelefono());
                txtCorreo.setText(actual.getCorreo());
                fechaRegistro.setValue(actual.getFechaRegistro());
                txtObservaciones.setText(actual.getObservaciones());
            }
        });
        limpiar();
        cargar();
    }

    private Paciente leerFormulario(int id) {
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String dni = txtDni.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String observaciones = txtObservaciones.getText().trim();
        if (nombres.isEmpty() || apellidos.isEmpty() || telefono.isEmpty() || fechaRegistro.getValue() == null) {
            throw new IllegalArgumentException("Nombres, apellidos, teléfono y fecha son obligatorios.");
        }
        if (!dni.matches("\\d{8}")) throw new IllegalArgumentException("El DNI debe tener 8 dígitos.");
        if (!telefono.matches("\\d{7,15}")) throw new IllegalArgumentException("El teléfono debe tener entre 7 y 15 dígitos.");
        if (!correo.isEmpty() && !correo.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException("El correo no tiene formato válido.");
        }
        if (nombres.length() > 80 || apellidos.length() > 80 || correo.length() > 120 || observaciones.length() > 255) {
            throw new IllegalArgumentException("Uno de los campos excede la longitud permitida.");
        }
        return new Paciente(id, nombres, apellidos, dni, telefono, correo, fechaRegistro.getValue(), observaciones);
    }

    @FXML private void registrar() {
        try {
            dao.insertar(leerFormulario(0));
            cargar();
            limpiar();
            aviso(Alert.AlertType.INFORMATION, "Paciente registrado correctamente.");
        } catch (IllegalArgumentException | SQLException e) { error(e); }
    }
    @FXML private void actualizar() {
        Paciente seleccionado = tablaPacientes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) { aviso(Alert.AlertType.WARNING, "Selecciona un paciente de la tabla."); return; }
        try {
            if (dao.actualizar(leerFormulario(seleccionado.getIdPaciente()))) {
                cargar(); limpiar(); aviso(Alert.AlertType.INFORMATION, "Paciente actualizado.");
            } else aviso(Alert.AlertType.WARNING, "El registro ya no existe.");
        } catch (IllegalArgumentException | SQLException e) { error(e); }
    }
    @FXML private void eliminar() {
        Paciente seleccionado = tablaPacientes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) { aviso(Alert.AlertType.WARNING, "Selecciona un paciente de la tabla."); return; }
        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION, "¿Eliminar al paciente seleccionado?", ButtonType.YES, ButtonType.NO);
        confirmar.setHeaderText("Confirmar eliminación");
        Optional<ButtonType> respuesta = confirmar.showAndWait();
        if (respuesta.isEmpty() || respuesta.get() != ButtonType.YES) return;
        try {
            if (dao.eliminar(seleccionado.getIdPaciente())) {
                cargar(); limpiar(); aviso(Alert.AlertType.INFORMATION, "Paciente eliminado.");
            } else aviso(Alert.AlertType.WARNING, "El registro ya no existe.");
        } catch (SQLException e) { error(e); }
    }
    @FXML private void cargar() {
        try {
            var datos = dao.listar();
            tablaPacientes.setItems(FXCollections.observableArrayList(datos));
            lblEstado.setText("Conectado a MySQL | Pacientes: " + datos.size());
        } catch (SQLException e) {
            lblEstado.setText("Sin conexión a MySQL");
            error(e);
        }
    }
    @FXML private void limpiar() {
        tablaPacientes.getSelectionModel().clearSelection();
        txtNombres.clear(); txtApellidos.clear(); txtDni.clear(); txtTelefono.clear(); txtCorreo.clear();
        fechaRegistro.setValue(LocalDate.now());
        txtObservaciones.clear();
    }
    private void error(Exception e) {
        String mensaje = e.getMessage();
        if (e instanceof SQLException sql && sql.getErrorCode() == 1062) mensaje = "Este DNI ya está registrado.";
        aviso(Alert.AlertType.ERROR, mensaje);
    }
    private void aviso(Alert.AlertType tipo, String mensaje) {
        Alert a = new Alert(tipo, mensaje, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
