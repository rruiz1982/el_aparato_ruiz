package com.elaparato.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Getter @Setter
@Entity
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "venta_seq")
    @SequenceGenerator(name = "venta_seq", sequenceName = "venta_seq", allocationSize = 1)
    private int id_venta;

    private Date fecha;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "venta_producto",
            joinColumns = @JoinColumn(name = "id_venta"),
            inverseJoinColumns = @JoinColumn(name = "id_producto")
    )
    private List<Producto> listaProductos;

    public Venta(int id_venta, Date fecha, List<Producto> listaProductos) {
        this.id_venta = id_venta;
        this.fecha = fecha;
        this.listaProductos = listaProductos;
    }
}