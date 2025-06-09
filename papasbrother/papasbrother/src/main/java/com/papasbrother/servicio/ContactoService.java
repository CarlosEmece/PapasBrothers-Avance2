package com.papasbrother.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.papasbrother.modelo.Contacto;
import com.papasbrother.repositorio.ContactoRepository;

@Service
public class ContactoService {

    @Autowired
    private ContactoRepository contactoRepository;
    
    public Contacto saveContacto(Contacto contacto) {
        return contactoRepository.save(contacto);
    }
    
    public List<Contacto> getAllContactos() {
        return contactoRepository.findAll();
    }
    
    public Contacto getContactoById(Long id) {
        return contactoRepository.findById(id).orElse(null);
    }
    
    public void deleteContacto(Long id) {
        contactoRepository.deleteById(id);
    }
}
