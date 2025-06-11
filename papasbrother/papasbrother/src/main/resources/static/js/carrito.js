document.addEventListener('DOMContentLoaded', () => {
    // Se recupera el carrito desde localStorage o se inicializa un arreglo vacío
    let cart = JSON.parse(localStorage.getItem('cart')) || [];

    // Función para actualizar la vista del carrito
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
        
        // Actualiza el total y el conteo de items
        cartTotal.textContent = total.toFixed(2);
        cartCount.textContent = cart.reduce((sum, item) => sum + item.quantity, 0);
        localStorage.setItem('cart', JSON.stringify(cart));
    };

    // Función global para eliminar un item
    window.removeItem = (index, event) => {
        event.stopPropagation();
        cart.splice(index, 1);
        updateCart();
    };

    // Función global para actualizar la cantidad de un item
    window.updateQuantity = (index, newQuantity, event) => {
        event.stopPropagation();
        if (newQuantity > 0) {
            cart[index].quantity = newQuantity;
        } else {
            cart.splice(index, 1);
        }
        updateCart();
    };

    // Maneja la adición del producto al carrito desde los botones "Agregar al carrito"
    const handleAddToCart = (button) => {
        // Se asume que cada producto está contenido en una tarjeta con clases específicas
        const card = button.closest('.promo-card, .prod-card');
        // Se extrae el precio del elemento (puedes ajustar el selector según tu HTML)
        const priceElement = card.querySelector('.discounted-price, .selected-price');
        const price = parseFloat(priceElement.textContent.replace('S/ ', '').trim());
        
        // Crear el objeto producto para el carrito
        const product = {
            id: Date.now(), // O usa un identificador real en caso de disponerlo
            name: card.querySelector('h3').textContent.trim(),
            price: price,
            image: card.querySelector('img').src,
            quantity: 1
        };
        
        // Si ya existe un artículo igual, aumentar la cantidad
        const existingItem = cart.find(item => item.name === product.name && item.price === product.price);
        if (existingItem) {
            existingItem.quantity++;
        } else {
            cart.push(product);
        }
        
        updateCart();
    };

    // Asignar eventos a los botones de agregar al carrito (ajusta los selectores según tu HTML)
    document.querySelectorAll('.btn-promo, .btn-prod').forEach(button => {
        button.addEventListener('click', (e) => {
            e.preventDefault();
            handleAddToCart(button);
        });
    });

    // Función global para procesar el checkout
    window.checkout = (event) => {
        event.stopPropagation();
        if (cart.length === 0) {
            alert('¡Tu carrito está vacío!');
            return;
        }
        
        const confirmacion = confirm(`¿Confirmar pedido por S/ ${document.getElementById('cartTotal').textContent}?`);
        if (confirmacion) {
            // Aquí iría la lógica para enviar el pedido al servidor si lo necesitas.
            // Para este ejemplo se limpia el carrito y se actualiza la vista.
            cart = [];
            updateCart();
            alert('Pedido realizado con éxito');
        }
    };

    // Cerrar el carrito solo cuando se presione el botón de cerrar
    document.querySelector('.cart-dropdown .btn-close').addEventListener('click', () => {
        document.querySelector('.dropdown-menu').classList.remove('show');
    });

    updateCart();
});
