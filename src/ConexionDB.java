import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {

    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/orcl";
    private static final String USER = "system";
    private static final String PASSWORD = "Tapiero123";

    public static Connection getConexion() {

        Connection cn = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            cn = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

                System.out.println("Conectado com sucesso! ");
            } catch (Exception e) {

            System.out.println("error de conexion! ");
            e.printStackTrace();
            }
            return cn;
    }
}   
