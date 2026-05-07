# CRUD Java + Oracle 19c + Swing GUI (IntelliJ IDEA):.

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/e447dbac-505e-4a1e-8aa6-126f20c544a9" />    

<img width="2558" height="1079" alt="image" src="https://github.com/user-attachments/assets/d2358663-ab09-4559-8ebf-2c624ca1370d" />        

```
## 📌 Descripción General

Proyecto completo desarrollado con:

- Java SE
- Swing GUI
- Oracle Database 19c
- JDBC

Incluye operaciones CRUD completas:

- ✅ Crear
- ✅ Consultar
- ✅ Actualizar
- ✅ Eliminar

La interfaz gráfica contiene:

- 4 botones
- JTable
- JTextField
- Formularios Swing

---

# 🧩 Arquitectura del Proyecto

```text
CRUD_Oracle_Java/
│
├── ConexionDB.java
├── Producto.java
├── ProductoDAO.java
└── FormularioProducto.java
```

---

# 🗄️ 1. Script Oracle 19c

```sql
CREATE TABLE PRODUCTOS (
    ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NOMBRE VARCHAR2(100),
    PRECIO NUMBER(10,2),
    CANTIDAD NUMBER
);

INSERT INTO PRODUCTOS (NOMBRE, PRECIO, CANTIDAD)
VALUES ('Laptop', 3500, 10);

INSERT INTO PRODUCTOS (NOMBRE, PRECIO, CANTIDAD)
VALUES ('Mouse', 80, 50);

INSERT INTO PRODUCTOS (NOMBRE, PRECIO, CANTIDAD)
VALUES ('Teclado', 150, 30);

COMMIT;
```

---

# ☕ 2. Clase de Conexión Oracle

## ConexionDB.java

```java
import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521:XE";

    private static final String USER = "SYSTEM";
    private static final String PASSWORD = "123456";

    public static Connection getConexion() {

        Connection cn = null;

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            cn = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("Conexion Exitosa");

        } catch (Exception e) {

            System.out.println("Error Conexion");
            e.printStackTrace();
        }

        return cn;
    }
}
```

---

# 📦 3. Clase Modelo

## Producto.java

```java
public class Producto {

    private int id;
    private String nombre;
    private double precio;
    private int cantidad;

    public Producto() {
    }

    public Producto(int id,
                    String nombre,
                    double precio,
                    int cantidad) {

        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
```

---

# 🛠️ 4. DAO CRUD

## ProductoDAO.java

```java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // INSERTAR
    public void insertar(Producto p) {

        String sql =
                "INSERT INTO PRODUCTOS " +
                "(NOMBRE, PRECIO, CANTIDAD) " +
                "VALUES (?, ?, ?)";

        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps =
                     cn.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setInt(3, p.getCantidad());

            ps.executeUpdate();

            System.out.println("Registro Insertado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // LISTAR
    public List<Producto> listar() {

        List<Producto> lista =
                new ArrayList<>();

        String sql =
                "SELECT * FROM PRODUCTOS";

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

    // ACTUALIZAR
    public void actualizar(Producto p) {

        String sql =
                "UPDATE PRODUCTOS " +
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

            System.out.println("Registro Actualizado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ELIMINAR
    public void eliminar(int id) {

        String sql =
                "DELETE FROM PRODUCTOS WHERE ID=?";

        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps =
                     cn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("Registro Eliminado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

# 🖥️ 5. Interfaz Gráfica Swing

## FormularioProducto.java

```java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FormularioProducto extends JFrame {

    JTextField txtId;
    JTextField txtNombre;
    JTextField txtPrecio;
    JTextField txtCantidad;

    JButton btnGuardar;
    JButton btnConsultar;
    JButton btnActualizar;
    JButton btnEliminar;

    JTable tabla;
    DefaultTableModel modelo;

    ProductoDAO dao = new ProductoDAO();

    public FormularioProducto() {

        setTitle("CRUD Oracle 19c");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        iniciarComponentes();

        listarDatos();
    }

    private void iniciarComponentes() {

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblId =
                new JLabel("ID");
        lblId.setBounds(20,20,100,25);
        panel.add(lblId);

        txtId = new JTextField();
        txtId.setBounds(120,20,150,25);
        panel.add(txtId);

        JLabel lblNombre =
                new JLabel("Nombre");
        lblNombre.setBounds(20,60,100,25);
        panel.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(120,60,150,25);
        panel.add(txtNombre);

        JLabel lblPrecio =
                new JLabel("Precio");
        lblPrecio.setBounds(20,100,100,25);
        panel.add(lblPrecio);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(120,100,150,25);
        panel.add(txtPrecio);

        JLabel lblCantidad =
                new JLabel("Cantidad");
        lblCantidad.setBounds(20,140,100,25);
        panel.add(lblCantidad);

        txtCantidad = new JTextField();
        txtCantidad.setBounds(120,140,150,25);
        panel.add(txtCantidad);

        // BOTONES

        btnGuardar =
                new JButton("Guardar");
        btnGuardar.setBounds(320,20,120,30);
        panel.add(btnGuardar);

        btnConsultar =
                new JButton("Consultar");
        btnConsultar.setBounds(320,60,120,30);
        panel.add(btnConsultar);

        btnActualizar =
                new JButton("Actualizar");
        btnActualizar.setBounds(320,100,120,30);
        panel.add(btnActualizar);

        btnEliminar =
                new JButton("Eliminar");
        btnEliminar.setBounds(320,140,120,30);
        panel.add(btnEliminar);

        // TABLA

        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Cantidad");

        tabla = new JTable(modelo);

        JScrollPane scroll =
                new JScrollPane(tabla);

        scroll.setBounds(20,220,740,200);

        panel.add(scroll);

        add(panel);

        // EVENTOS

        btnGuardar.addActionListener(e -> guardar());

        btnConsultar.addActionListener(e -> listarDatos());

        btnActualizar.addActionListener(e -> actualizar());

        btnEliminar.addActionListener(e -> eliminar());

        tabla.getSelectionModel()
                .addListSelectionListener(e -> {

            int fila = tabla.getSelectedRow();

            if (fila >= 0) {

                txtId.setText(
                        tabla.getValueAt(fila,0)
                                .toString());

                txtNombre.setText(
                        tabla.getValueAt(fila,1)
                                .toString());

                txtPrecio.setText(
                        tabla.getValueAt(fila,2)
                                .toString());

                txtCantidad.setText(
                        tabla.getValueAt(fila,3)
                                .toString());
            }
        });
    }

    // GUARDAR

    private void guardar() {

        Producto p = new Producto();

        p.setNombre(txtNombre.getText());

        p.setPrecio(
                Double.parseDouble(
                        txtPrecio.getText()));

        p.setCantidad(
                Integer.parseInt(
                        txtCantidad.getText()));

        dao.insertar(p);

        listarDatos();

        limpiar();
    }

    // LISTAR

    private void listarDatos() {

        modelo.setRowCount(0);

        List<Producto> lista =
                dao.listar();

        for (Producto p : lista) {

            modelo.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getPrecio(),
                    p.getCantidad()
            });
        }
    }

    // ACTUALIZAR

    private void actualizar() {

        Producto p = new Producto();

        p.setId(
                Integer.parseInt(
                        txtId.getText()));

        p.setNombre(txtNombre.getText());

        p.setPrecio(
                Double.parseDouble(
                        txtPrecio.getText()));

        p.setCantidad(
                Integer.parseInt(
                        txtCantidad.getText()));

        dao.actualizar(p);

        listarDatos();

        limpiar();
    }

    // ELIMINAR

    private void eliminar() {

        int id =
                Integer.parseInt(
                        txtId.getText());

        dao.eliminar(id);

        listarDatos();

        limpiar();
    }

    // LIMPIAR

    private void limpiar() {

        txtId.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
    }

    // MAIN

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new FormularioProducto()
                    .setVisible(true);
        });
    }
}
```

---

# 🔌 6. Librería JDBC Oracle

## Descargar Driver JDBC

- Oracle JDBC Driver
- ojdbc11.jar

---

# ⚙️ 7. Configuración IntelliJ IDEA

## Agregar Librería JDBC

```text
IntelliJ IDEA
File
→ Project Structure
→ Libraries
→ +
→ Java
→ Seleccionar ojdbc11.jar
```

---

# ▶️ 8. Resultado del Sistema

La aplicación permitirá:

- ✅ Insertar productos
- ✅ Consultar productos
- ✅ Actualizar productos
- ✅ Eliminar productos
- ✅ Mostrar información en JTable
- ✅ Conexión Oracle 19c
- ✅ GUI Swing completa

---

# 🎨 Diseño Sugerido

```text
----------------------------------------
| ID        [__________]               |
| Nombre    [__________]               |
| Precio    [__________]               |
| Cantidad  [__________]               |
|                                      |
| [Guardar] [Consultar]               |
| [Actualizar] [Eliminar]             |
----------------------------------------
|              JTable                 |
----------------------------------------
```
:. . / .
