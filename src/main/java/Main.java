import java.sql.*;

public class Main {

    // JDBC URL, usuario y contraseña de la base de datos
    static final String JDBC_URL = "jdbc:mysql://localhost/Personal";
    static final String JDBC_USER = "root";
    static final String JDBC_PASSWORD = "";

    public static void main(String[] args) {
        try {
            // Establecer la conexión con la base de datos
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // Crear la tabla OficinaEmpleados si no existe
            createTableOficinaEmpleados(connection);

            // Obtener los datos de los empleados y sus departamentos
            ResultSet resultSet = getEmployeeDepartmentData(connection);

            // Insertar datos en la tabla OficinaEmpleados y calcular comisión
            insertAndUpdateOficinaEmpleados(connection, resultSet);
            // Actualizar comisiones según las condiciones dadas
            updateComision(connection);

            // Mostrar la tabla actualizada
            displayOficinaEmpleados(connection);

            // Cerrar la conexión con la base de datos
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para crear la tabla OficinaEmpleados
     * @param connection
     * @throws SQLException
     */
    public static void createTableOficinaEmpleados(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS OficinaEmpleados (" +
                    "NombreEmpleado VARCHAR(25), " +
                    "NombreDepartamento VARCHAR(15), " +
                    "Salario INT, " +
                    "Comision INT)";
            statement.executeUpdate(sql);
        }
    }

    /**
     * Método para obtener los datos de los empleados y sus departamentos
     * @param connection
     * @return
     * @throws SQLException
     */
    public static ResultSet getEmployeeDepartmentData(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT e.Nombre, d.Nombre, e.Salario, d.Dept_no " +
                "FROM Empleado e " +
                "JOIN Departamento d ON e.Dept_no = d.Dept_no";
        return statement.executeQuery(sql);
    }

    /**
     * Método para insertar datos en la tabla OficinaEmpleados y calcular comisión
     * @param connection
     * @param resultSet
     * @throws SQLException
     */
    public static void insertAndUpdateOficinaEmpleados(Connection connection, ResultSet resultSet) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO OficinaEmpleados (NombreEmpleado, NombreDepartamento, Salario, Comision) VALUES (?, ?, ?, ?)"
        )) {
            while (resultSet.next()) {
                String nombreEmpleado = resultSet.getString(1);
                String nombreDepartamento = resultSet.getString(2);
                int salario = resultSet.getInt(3);
                int comision = calcularComision(nombreDepartamento, salario);
                preparedStatement.setString(1, nombreEmpleado);
                preparedStatement.setString(2, nombreDepartamento);
                preparedStatement.setInt(3, salario);
                preparedStatement.setInt(4, comision);
                preparedStatement.executeUpdate();
            }
        }
    }

    /**
     * Método para calcular la comisión según el departamento
     * @param departamento
     * @param salario
     * @return
     */
    public static int calcularComision(String departamento, int salario) {
        switch (departamento) {
            case "CONTABILIDAD":
                return (int) (salario * 0.10);
            case "INVESTIGACION":
                return (int) (salario * 0.20);
            case "VENTAS":
                return (int) (salario * 0.05);
            case "PRODUCCION":
                return (int) (salario * 0.15);
            default:
                return 0;
        }
    }

    /**
     * Método para actualizar la comisión según las condiciones dadas
     * @param connection
     * @throws SQLException
     */
    public static void updateComision(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("UPDATE OficinaEmpleados SET Comision = Comision * 1.1 WHERE Comision < 300");
            statement.executeUpdate("UPDATE OficinaEmpleados SET Comision = Comision * 1.05 WHERE Comision >= 400 AND Comision <= 600");
        }
    }

    /**
     * Método para mostrar la tabla OficinaEmpleados
     * @param connection
     * @throws SQLException
     */
    public static void displayOficinaEmpleados(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM OficinaEmpleados");
            while (resultSet.next()) {
                String nombreEmpleado = resultSet.getString("NombreEmpleado");
                String nombreDepartamento = resultSet.getString("NombreDepartamento");
                int salario = resultSet.getInt("Salario");
                int comision = resultSet.getInt("Comision");
                System.out.printf("Empleado: %s, Departamento: %s, Salario: %d, Comision: %d%n", nombreEmpleado, nombreDepartamento, salario, comision);
            }
        }
    }
}
