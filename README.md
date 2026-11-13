# DentalCare - Sistema CRUD de Pacientes
Evaluación individual POO S16. Java 17 + JavaFX 17 + MySQL 8 + JDBC + Maven.

## Preparación
1. Instalar JDK 17, IntelliJ IDEA, MySQL Server 8 y MySQL Workbench.
2. Iniciar MySQL Server. Ejecutar `database.sql` en MySQL Workbench.
3. Abrir la carpeta como proyecto Maven en IntelliJ; esperar a que se resuelvan dependencias.
4. Configurar variable de entorno `DB_PASSWORD` con contraseña local de root de MySQL (Run > Edit Configurations > Maven > Environment variables). Si root no tiene contraseña, puede omitirse.
5. Ejecutar desde terminal Maven: `mvn clean javafx:run`.
6. Probar registrar, consultar, actualizar, eliminar. Seleccionar una fila para modificar o eliminar.

Si MySQL usa otro puerto o usuario, cambiar `config/Conexion.java`. La contraseña no debe publicarse en GitHub. Para uso real, utilizar un usuario MySQL con permisos limitados y no datos sensibles reales.

## Estructura
- `model/Paciente.java`: entidad
- `dao/PacienteDAO.java`: SQL y JDBC
- `config/Conexion.java`: conexión a BD
- `controller/PacienteController.java`: interfaz y validación
- `MainApp.java`: arranque JavaFX
- `resources/view/pacientes.fxml`: interfaz visual

## Evidencias para S16
Captura tabla MySQL, aplicación conectada, registrar, consultar, actualizar, eliminar, interfaz, video corto y GitHub. JasperReports y JAR/EXE son puntos extra opcionales.
