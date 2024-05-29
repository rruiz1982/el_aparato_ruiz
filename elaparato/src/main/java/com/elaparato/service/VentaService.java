package com.elaparato.service;

import com.elaparato.model.Producto;
import com.elaparato.model.Venta;
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


    @Override
    public List<Venta> getVentas() {
        return ventaRepo.findAll();
    }

    @Override
    public void saveVenta(Venta vent) {
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
