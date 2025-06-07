function showSection(sectionId) {
            const sections = document.querySelectorAll('.section');
            sections.forEach(section => section.classList.remove('active')); // Oculta todas las secciones
            document.getElementById(sectionId).classList.add('active'); // Muestra la sección clicada
        }

        function cerrar() {
            alert("Sesión cerrada");
            // Aquí podrías redirigir a una página de inicio de sesión o limpiar datos
            // window.location.href = "login.html";
        }

        // Muestra la primera sección por defecto al cargar la página
        document.addEventListener('DOMContentLoaded', () => {
            showSection('clientes');
        });