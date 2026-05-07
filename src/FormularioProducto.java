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
