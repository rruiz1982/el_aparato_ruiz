package com.elaparato.service;

import com.elaparato.model.Producto;
import com.elaparato.model.Venta;
import com.elaparato.repository.IProductoRepository;
import com.elaparato.repository.IVentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentaService implements IVentaService{

    @Autowired
    private IVentaRepository ventaRepo;

    @Autowired
    private IProductoRepository productoRepository;


    @Override
    public List<Venta> getVentas() {
        return ventaRepo.findAll();
    }

    @Override
    public void saveVenta(Venta vent) {
        List<Producto> productos = vent.getListaProductos();
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            Optional<Producto> managedProducto = productoRepository.findById(producto.getId());
            if (managedProducto.isPresent()) {
                productos.set(i, managedProducto.get());
            } else {
                // Manejar el caso donde el producto no existe, si es necesario
                productoRepository.save(producto);
            }
        }
        vent.setListaProductos(productos);
        ventaRepo.save(vent);
    }

    @Override
    public void deleteVenta(int id) {
        ventaRepo.deleteById(id);
    }

    @Override
    public Venta findVenta(int id) {
       return ventaRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Venta editVenta(Venta vent) {
        Optional<Venta> optionalVenta = ventaRepo.findById(vent.getId_venta());
        if (optionalVenta.isPresent()) {
            Venta venta = optionalVenta.get();
            venta.setFecha(vent.getFecha());
            venta.setListaProductos(vent.getListaProductos());
            return ventaRepo.save(vent);
        } else {
            throw new RuntimeException("Venta not found with id " + vent.getId_venta());
        }


    }

    }
