package com.papasbrother.servicio;

import com.papasbrother.modelo.Administrador;

public interface AdministradoresService {
    Administrador login(String username, String password);
}
