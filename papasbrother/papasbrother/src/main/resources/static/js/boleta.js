// Generar número aleatorio para la boleta
function generarNumeroBoleta() {
  const serie = '001';
  const numero = Math.floor(100000 + Math.random() * 900000); // entre 100000 y 999999
  return `Boleta de Venta N° ${serie}-${numero}`;
}

// Obtener fecha y hora local
function obtenerFechaHoraActual() {
  const ahora = new Date();
  const fecha = ahora.toLocaleDateString('es-PE');
  const hora = ahora.toLocaleTimeString('es-PE', { hour: '2-digit', minute: '2-digit' });
  return { fecha, hora };
}

// Cargar datos dinámicos en la boleta
window.onload = function () {
  document.getElementById('num-boleta').innerText = generarNumeroBoleta();

  const { fecha, hora } = obtenerFechaHoraActual();
  document.getElementById('fecha').innerText = fecha;
  document.getElementById('hora').innerText = hora;
};

// Exportar a PDF usando html2pdf
function exportarPDF() {
  const elemento = document.getElementById("boleta");

  const opciones = {
    margin: 0.3,
    filename: 'boleta_papa_brothers.pdf',
    image: { type: 'jpeg', quality: 0.98 },
    html2canvas: { scale: 2 },
    jsPDF: { unit: 'in', format: 'letter', orientation: 'portrait' }
  };

  html2pdf().set(opciones).from(elemento).save();
}
