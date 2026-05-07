import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // INSERTAR
    public void insertar(Producto p) {
        String sql =
                "INSERT INTO PRODUCTOS_Y " +
                        "(NOMBRE, PRECIO, CANTIDAD) " +
                        "VALUES (?,?,?)";

        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps =
                     cn.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setInt(3, p.getCantidad());

            ps.executeUpdate();

            System.out.println("Registro insertado con exito! ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // LISTAR
    public List<Producto> listar() {

        List<Producto> lista =
                new ArrayList<>();

        String sql = "SELECT * FROM PRODUCTOS_Y";

        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps =
                     cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();

                p.setId(rs.getInt("ID"));
                p.setNombre(rs.getString("NOMBRE"));
                p.setPrecio(rs.getDouble("PRECIO"));
                p.setCantidad(rs.getInt("CANTIDAD"));

                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ACTUALIZAR .
    public void actualizar(Producto p) {

        String sql =
                "UPDATE PRODUCTOS_Y " +
                        "SET NOMBRE=?, PRECIO=?, CANTIDAD=? " +
                        "WHERE ID=?";
        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps =
                     cn.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setInt(3, p.getCantidad());
            ps.setInt(4, p.getId());

            ps.executeUpdate();

            System.out.println("Registro actualizado! ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ELIMINAR
    public void eliminar(int id) {

        String sql =
                "DELETE FROM PRODUCTOS_Y WHERE ID=?";

        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps =
                     cn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("Registro eliminado! ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}   

