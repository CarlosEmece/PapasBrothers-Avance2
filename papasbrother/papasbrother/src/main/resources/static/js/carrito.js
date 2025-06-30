document.addEventListener('DOMContentLoaded', () => {
    let cart = JSON.parse(localStorage.getItem('cart')) || [];

    const updateCart = () => {
        const cartItems = document.getElementById('cartItems');
        const cartTotal = document.getElementById('cartTotal');
        const cartCount = document.getElementById('cartCount');

        cartItems.innerHTML = '';
        let total = 0;

        cart.forEach((item, index) => {
            total += item.price * item.quantity;

            const itemHTML = `
                <div class="cart-item d-flex align-items-center mb-2">
                    <img src="${item.image}" alt="${item.name}" 
                         class="me-2" style="width: 50px; height: 50px; object-fit: cover;">
                    <div class="flex-grow-1">
                        <div class="fw-bold">${item.name}</div>
                        <div class="d-flex align-items-center">
                            <button class="btn btn-sm btn-outline-warning" 
                                    onclick="updateQuantity(${index}, ${item.quantity - 1}, event)">-</button>
                            <span class="mx-2">${item.quantity}</span>
                            <button class="btn btn-sm btn-outline-warning" 
                                    onclick="updateQuantity(${index}, ${item.quantity + 1}, event)">+</button>
                            <button class="btn btn-danger btn-sm ms-2" 
                                    onclick="removeItem(${index}, event)">X</button>
                            <span class="ms-2">S/ ${(item.price * item.quantity).toFixed(2)}</span>
                        </div>
                    </div>
                </div>
            `;
            cartItems.innerHTML += itemHTML;
        });

        cartTotal.textContent = total.toFixed(2);
        cartCount.textContent = cart.reduce((sum, item) => sum + item.quantity, 0);
        localStorage.setItem('cart', JSON.stringify(cart));
    };

    window.removeItem = (index, event) => {
        event.stopPropagation();
        cart.splice(index, 1);
        updateCart();
    };

    window.updateQuantity = (index, newQuantity, event) => {
        event.stopPropagation();
        if (newQuantity > 0) {
            cart[index].quantity = newQuantity;
        } else {
            cart.splice(index, 1);
        }
        updateCart();
    };

    const handleAddToCart = (button) => {
        const card = button.closest('.promo-card, .prod-card');
        const priceElement = card.querySelector('.discounted-price, .selected-price');
        const price = parseFloat(priceElement.textContent.replace('S/ ', '').trim());

        const product = {
            id: Date.now(),
            name: card.querySelector('h3').textContent.trim(),
            price: price,
            image: card.querySelector('img').src,
            quantity: 1
        };

        const existingItem = cart.find(item => item.name === product.name && item.price === product.price);
        existingItem ? existingItem.quantity++ : cart.push(product);

        updateCart();
    };

    document.querySelectorAll('.btn-promo, .btn-prod').forEach(button => {
        button.addEventListener('click', (e) => {
            e.preventDefault();
            handleAddToCart(button);
        });
    });



    //Para realizar el pedido del carrito
    window.checkout = (event) => {
        event.stopPropagation();
        console.log("Checkout invocado. isAuthenticated =", isAuthenticated);

        // Verificar si el usuario está autenticado
        if (!isAuthenticated) {
            alert('Ingrese sesión primero, por favor');
            return;
        }

        if (cart.length === 0) {
            alert('¡Tu carrito está vacío!');
            return;
        }

        const total = document.getElementById('cartTotal').textContent;
        const tipoDocumento = document.querySelector('input[name="tipoDocumento"]:checked').value;
        const metodoPago = document.querySelector('input[name="metodoPago"]:checked').value;

        if (metodoPago === 'efectivo') {
            const confirmacion = confirm(`¿Confirmar pedido por S/ ${total} y pagar en efectivo al recibir?`);
            if (confirmacion) {
                if (tipoDocumento === "boleta") {
                    generarPDF();
                } else if (tipoDocumento === "factura") {
                    generarFacturaPDF();
                }
                setTimeout(() => {
                    cart = [];
                    localStorage.setItem('cart', JSON.stringify(cart));
                    updateCart();
                    alert('Pedido realizado con éxito. Paga en efectivo al recibir.');
                }, 1000);
            }
        } else if (metodoPago === 'tarjeta') {
            // Simulación de pago con tarjeta
            mostrarModalTarjeta(total, tipoDocumento);
        }
    };

    // Modal de simulación de pago con tarjeta
    function mostrarModalTarjeta(total, tipoDocumento) {
        // Crear modal si no existe
        let modal = document.getElementById('modalPagoTarjeta');
        if (!modal) {
            modal = document.createElement('div');
            modal.id = 'modalPagoTarjeta';
            modal.className = 'modal fade';
            modal.tabIndex = -1;
            modal.innerHTML = `
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Simulación de Pago con Tarjeta</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <form id="formTarjetaSimulada">
                            <div class="mb-3">
                                <label class="form-label">Número de tarjeta</label>
                                <input type="text" class="form-control" maxlength="16" required pattern="[0-9]{16}">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Nombre en la tarjeta</label>
                                <input type="text" class="form-control" required>
                            </div>
                            <div class="mb-3 d-flex gap-2">
                                <div>
                                    <label class="form-label">Vencimiento</label>
                                    <input type="text" class="form-control" maxlength="5" placeholder="MM/AA" required pattern="[0-9]{2}/[0-9]{2}">
                                </div>
                                <div>
                                    <label class="form-label">CVV</label>
                                    <input type="text" class="form-control" maxlength="3" required pattern="[0-9]{3}">
                                </div>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Monto a pagar</label>
                                <input type="text" class="form-control" value="S/ ${total}" readonly>
                            </div>
                            <button type="submit" class="btn btn-primary w-100">Pagar</button>
                        </form>
                    </div>
                </div>
            </div>`;
            document.body.appendChild(modal);
        }
        // Inicializar modal Bootstrap
        let bsModal = new bootstrap.Modal(modal);
        bsModal.show();

        // Manejar el submit del formulario simulado
        const form = modal.querySelector('#formTarjetaSimulada');
        form.onsubmit = function(e) {
            e.preventDefault();
            bsModal.hide();
            setTimeout(() => {
                if (tipoDocumento === "boleta") {
                    generarPDF();
                } else if (tipoDocumento === "factura") {
                    generarFacturaPDF();
                }
                cart = [];
                localStorage.setItem('cart', JSON.stringify(cart));
                updateCart();
                alert('Pago simulado exitoso. Pedido realizado con éxito.');
            }, 1000);
        };
    }



    ///////////////////////////////////////////////////
    //////PARA LA BOLETA////////
    window.generarPDF = () => {
        // Instanciamos jsPDF
        const doc = new window.jspdf.jsPDF('p', 'mm', 'a4');

        // Datos de cabecera (estos datos pueden venir de variables globales o inyectarse con Thymeleaf)
        const fecha = new Date().toLocaleString();
        const numeroPedido = "PED-" + Math.floor(Math.random() * 1000000);
        const usuario = usuarioLogueado; // Más adelante, inyecta aquí el nombre real del usuario

        // CONFIGURAR ENCABEZADO
        // 1. Nombre de la empresa a la izquierda
        doc.setFontSize(18);
        doc.setTextColor(0, 0, 128); // Color azul oscuro, por ejemplo
        doc.text("Papas Brother", 10, 15);

        // 2. Datos del cliente debajo del nombre de la empresa (parte izquierda)
        doc.setFontSize(12);
        doc.setTextColor(50, 50, 50); // Gris oscuro
        let infoY = 22; // Valor Y para comenzar el bloque de información del cliente
        doc.text(`Cliente: ${usuario}`, 10, infoY);
        doc.text(`Fecha: ${fecha}`, 10, infoY + 7);
        doc.text(`Orden: ${numeroPedido}`, 10, infoY + 14);


        // 3. Ubica el logo de la empresa en la esquina superior derecha
        // Debes tener tu logo en formato Base64. Reemplaza "REEMPLAZA_CON_TU_BASE64" por la cadena real.
        function getBase64ImageFromUrl(url, callback) {
            fetch(url)
                .then(response => response.blob())
                .then(blob => {
                    const reader = new FileReader();
                    reader.onloadend = () => { callback(reader.result); };
                    reader.readAsDataURL(blob);
                })
                .catch(error => console.error(error));
        }

        // Llama a la función con la URL de tu imagen:
        getBase64ImageFromUrl('/img/LogoPapaB.png', (logoData) => {
            // Ahora ya tienes el logoData en Base64 y puedes usarlo:
            const doc = new window.jspdf.jsPDF('p', 'mm', 'a4');
            // ... Código de configuración del PDF
            doc.addImage(logoData, 'PNG', 150, 5, 50, 20);
            // ... Fin del código, doc.save(), etc.
        });

        // Separa visualmente el encabezado con una línea horizontal
        doc.setLineWidth(0.5);
        doc.setDrawColor(200, 200, 200); // color gris claro para la línea
        doc.line(10, 30, 200, 30);

        // Agregar título centrado: Resumen de su pedido
        doc.setFontSize(16);
        doc.setTextColor(0, 0, 0);
        doc.text("Resumen de su pedido", 105, 40, { align: "center" });

        // PREPARAR LA TABLA DE RESUMEN DEL PEDIDO CON jsPDF-AutoTable
        // Primero, preparamos los datos del carrito en una matriz
        let dataForTable = cart.map(item => {
            return [
                item.name,
                String(item.quantity),
                "S/ " + item.price.toFixed(2),
                "S/ " + (item.price * item.quantity).toFixed(2)
            ];
        });
        // Encabezado de la tabla
        let head = [["Producto", "Cantidad", "P. Unitario", "Subtotal"]];

        // Usamos autoTable para crear la tabla. Puedes modificar colores y estilos.
        doc.autoTable({
            startY: 45,
            head: head,
            body: dataForTable,
            theme: 'grid',
            headStyles: { fillColor: [0, 150, 136], textColor: 255, fontSize: 12 },
            styles: { fontSize: 10, cellPadding: 3 }
        });

        // UBICAR EL TOTAL EN EL PIE
        let finalY = doc.lastAutoTable.finalY + 10;
        // Calculamos total
        let total = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);
        doc.setFontSize(14);
        doc.text(`Total: S/ ${total.toFixed(2)}`, 10, finalY);

        // Agrega un mensaje de agradecimiento al pie de la página
        doc.setFontSize(12);
        doc.text("Gracias por su compra", 105, finalY + 10, { align: "center" });

        // Finalmente, se descarga el PDF
        doc.save("boleta-pedido.pdf");
    };



    ///////////////////////////////////////////////////
    //////PARA LA FACTURA////////
    window.generarFacturaPDF = () => {
        const doc = new window.jspdf.jsPDF('p', 'mm', 'a4');

        // Datos de cabecera
        const fecha = new Date().toLocaleString();
        const numeroPedido = "FAC-" + Math.floor(Math.random() * 1000000);
        const usuario = usuarioLogueado;  // Reemplázalo por el nombre real si lo tienes

        // Encabezado Factura: Información de la empresa y el cliente.
        doc.setFontSize(18);
        doc.setTextColor(0, 0, 128);
        doc.text("Papas Brother", 10, 15);

        // Información adicional de la factura (por ejemplo, RUC y dirección)
        doc.setFontSize(10);
        doc.setTextColor(80, 80, 80);
        doc.text("RUC: 12345678901", 10, 22);
        doc.text("San Martin de Porres, Lima", 10, 28);

        // Datos del cliente y de la factura a la derecha
        doc.setFontSize(12);
        doc.setTextColor(50, 50, 50);
        doc.text(`Cliente: ${usuario}`, 120, 22);
        doc.text(`Fecha: ${fecha}`, 120, 28);
        doc.text(`Factura: ${numeroPedido}`, 120, 34);

        // Agregar el logo en la esquina superior derecha
        function getBase64ImageFromUrl(url, callback) {
            fetch(url)
                .then(response => response.blob())
                .then(blob => {
                    const reader = new FileReader();
                    reader.onloadend = () => { callback(reader.result); };
                    reader.readAsDataURL(blob);
                })
                .catch(error => console.error(error));
        }

        // Llama a la función con la URL de tu imagen:
        getBase64ImageFromUrl('/img/LogoPapaB.png', (logoData) => {
            // Ahora ya tienes el logoData en Base64 y puedes usarlo:
            const doc = new window.jspdf.jsPDF('p', 'mm', 'a4');
            // ... Código de configuración del PDF
            doc.addImage(logoData, 'PNG', 150, 5, 50, 20);
            // ... Fin del código, doc.save(), etc.
        });

        // Línea separadora
        doc.setLineWidth(0.5);
        doc.setDrawColor(200, 200, 200);
        doc.line(10, 35, 200, 35);

        // Título centrado: Detalle de la factura
        doc.setFontSize(16);
        doc.setTextColor(0, 0, 0);
        doc.text("Detalle de la factura", 105, 42, { align: "center" });

        // Tabla de detalles (similar a la boleta)
        let dataForTable = cart.map(item => {
            return [
                item.name,
                String(item.quantity),
                "S/ " + item.price.toFixed(2),
                "S/ " + (item.price * item.quantity).toFixed(2)
            ];
        });
        let head = [["Producto", "Cantidad", "P. Unitario", "Subtotal"]];

        doc.autoTable({
            startY: 47,
            head: head,
            body: dataForTable,
            theme: 'grid',
            headStyles: { fillColor: [255, 102, 0], textColor: 255, fontSize: 12 }, // Color diferente para distinguir la factura
            styles: { fontSize: 10, cellPadding: 3 }
        });

        let finalY = doc.lastAutoTable.finalY + 10;
        let total = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);
        doc.setFontSize(14);
        doc.text(`Total: S/ ${total.toFixed(2)}`, 10, finalY);
        doc.setFontSize(12);
        doc.text("Gracias por su compra", 105, finalY + 10, { align: "center" });

        doc.save("factura-pedido.pdf");
    };


function showConfirmModal(total, callback) {
    // Actualiza el monto en el modal
    document.getElementById('modalTotal').textContent = total;
    // Inicializa el modal de Bootstrap
    let confirmModalEl = document.getElementById('confirmOrderModal');
    let confirmModal = new bootstrap.Modal(confirmModalEl);
    confirmModal.show();

    // Asigna el callback al botón de confirmación
    document.getElementById('modalConfirmBtn').onclick = function() {
        confirmModal.hide();
        if (typeof callback === 'function') {
            callback();
        }
    };
}


    // Cerrar carrito solo con botón específico
    document.querySelector('.cart-dropdown .btn-close').addEventListener('click', () => {
        document.querySelector('.dropdown-menu').classList.remove('show');
    });

    updateCart();
});
