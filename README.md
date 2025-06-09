# 🍟 Aplicativo Web para el Restaurante Papas Brothers

Aplicación web desarrollada para automatizar los procesos operativos del restaurante **Papas Brothers**, especializada en la gestión de pedidos, inventario y ventas.

---

## 📌 Descripción del Proyecto

El proyecto busca transformar digitalmente la operación del restaurante mediante una solución web que permita:

- Tomar pedidos de forma digital
- Gestionar el inventario en tiempo real
- Visualizar el menú por categorías
- Habilitar un panel de administración
- Generar reportes de ventas

---

## 🎯 Objetivos

### 🎯 Objetivo General
Desarrollar un sistema web que automatice los procesos del restaurante Papas Brothers, mejorando la eficiencia operativa y la experiencia del cliente.

### 🔹 Objetivos Específicos
- Implementar un sistema de pedidos en línea
- Diseñar una base de datos centralizada
- Automatizar la gestión del stock e insumos
- Facilitar el registro y autenticación de usuarios
- Generar reportes confiables de ventas y pedidos

---

## 🧩 Módulos del Sistema

- **Clientes**: Registro, login, menú, pedidos, reservas
- **Administrador**: Gestión de productos, stock, reportes
- **Inventario**: Ingreso de productos, alertas de stock bajo
- **Ventas**: Registro automático, reportes en tiempo real

---

## 📦 Tecnologías Utilizadas

- **Frontend**: HTML5, CSS3, JavaScript
- **Backend**: Java (Spring Boot)
- **Base de Datos**: MySQL
- **Diseño UI/UX**: Figma
- **Hosting**: Servidor local o nube

---

## 📋 Requerimientos del Sistema

### ✅ Requerimientos Funcionales

- Inicio de sesión y registro de usuarios
- Visualización y filtrado del menú
- Carrito de compras y edición
- Reservas en línea
- Confirmación y seguimiento de pedidos

### ⚙️ Requerimientos No Funcionales

- Tiempo de respuesta < 200 ms (90% de los casos)
- Compatibilidad con navegadores modernos
- Seguridad basada en roles
- Código legible y comentado
- Diseño responsive

---

## 📂 Estructura del Proyecto

```plaintext
papas-brothers/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── papasbrothers/
│   │   │   │           ├── controller/
│   │   │   │           ├── model/
│   │   │   │           ├── repository/
│   │   │   │           └── service/
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       ├── static/
│   │   │       └── templates/
│   └── pom.xml
├── frontend/
│   ├── index.html
│   ├── css/
│   └── js/
├── docs/
│   └── avance1.pdf
├── database/
│   └── esquema.sql
├── README.md
```

---

## 📊 Documentación Visual

- 🎨 Diseño en Figma
- 🗓️ Diagrama de Gantt

---

## 🔐 Consideraciones Técnicas

- Acceso basado en roles (cliente / administrador)
- Diseño responsive para móviles
- Registro y autenticación de usuarios
- Restricciones de horario y capacidad para reservas
- Código comentado y limpio
- Compatible con navegadores modernos

---
## 🌐 Vistas del Sistema

### 🔑 Vista Login

Formulario simple para iniciar sesión:

- **Usuario:** `Nombre de usuario aquí`
- **Contraseña:** `Contraseña aquí`
- Botón: [Ingresar](#)
- Botón: [Crear usuario](#)

---

### 🏠 Vista Inicio

Diseño principal del sitio con navegación, carrusel de productos, menú destacado y footer informativo.

#### Contenido:
- Navbar con enlaces a Inicio, Menú, Reservaciones y Login
- Carrusel de imágenes (Broaster, Hamburguesas, Salchipapas)
- Menú destacado con tarjetas de productos populares
- Footer con redes, secciones informativas y contacto

---

### 📞 Vista de Contacto

Formulario para sugerencias, consultas o quejas. Incluye campos como nombre, correo, motivo, descripción, y opción de subir archivos. Integrado con validaciones HTML5 y estilo moderno.

#### Características:
- Navbar con menú lateral
- Formulario completo con validación
- Footer con información legal, de contacto y redes

---

### 🍽️ Vista Menú

Listado de productos dividido en categorías:

- Pollos Broaster
- Hamburguesas
- Salchipapas

Cada producto tiene:
- Imagen
- Nombre y precio
- Botón “Pedir ahora”

#### Diseño
- Tarjetas en 4 columnas
- Navbar y menú lateral
- Footer completo

---

### 👥 Vista Nosotros

Sección para presentar la historia de la empresa.

#### Contenido:
- Texto sobre la fundación y propósito de Papas Brothers
- Imagen del local
- Botón para ir al menú

#### Diseño
- Dos columnas (texto + imagen)
- Tipografías modernas y estructura responsive

---

### 📝 Vista de Registro

Formulario de registro de usuario:

- Nombre completo
- Correo electrónico
- Contraseña (mínimo 8 caracteres)
- Validaciones en vivo y HTML5
- Enlace para ir al login

---

### 📧 Contacto

**Correo:** contacto@papabrothers.com  
**Teléfono:** (01) 7137590  
**Ubicación:** San Martín de Porres, Lima - Perú

---

# 🧾 Proyecto: Boleta - Papa Brothers

Este proyecto es una implementación de una **boleta digital** para el restaurante ficticio _Papa Brothers_, que permite mostrar los detalles de una venta (cliente, productos, precios, fecha y hora) y exportar la boleta en formato PDF.

## 🛠️ Tecnologías Utilizadas

- HTML5 + CSS3
- JavaScript (Vanilla JS)
- [html2pdf.js](https://github.com/eKoopmans/html2pdf) para la exportación a PDF
- Spring Boot (Java) para renderizar la vista con datos dinámicos usando Thymeleaf


## 🚀 ¿Cómo funciona?

1. El controlador Java (`BoletaControlador`) genera la fecha, hora y datos de cliente para mostrarlos en la boleta.
2. El archivo HTML (`boleta.html`) presenta los datos estructurados en una tabla y se renderiza con ayuda de Thymeleaf.
3. Un botón en la interfaz permite al usuario **exportar la boleta como PDF**, utilizando la librería `html2pdf.js`.
4. Al cargar la página, se genera un número aleatorio de boleta y se muestran la fecha y hora actuales.


## 📋 Estructura del Proyecto

## 👨‍💻 Autores

Proyecto realizado por el equipo del curso Integrador I – Universidad Tecnológica del Perú (UTP).

---

