/**
 * chatbot.js - Sistema completo para el Chatbot de Papas Brother
 * Incluye persistencia, interfaz responsive y lógica de conversación
 */

document.addEventListener('DOMContentLoaded', () => {
    // Elementos del DOM
    const elements = {
        openBtn: document.getElementById('openChatbotBtn'),
        closeBtn: document.getElementById('closeChatbotBtn'),
        chatBox: document.getElementById('chatbotBox'),
        userInput: document.getElementById('userMessageInput'),
        sendBtn: document.getElementById('sendMessageBtn'),
        chatMessages: document.getElementById('chatMessages')
    };

    // Configuración
    const config = {
        sessionKey: 'pb_chat_session',
        sessionTimeout: 6 * 60 * 60 * 1000, // 6 horas en ms
        typingSpeed: { min: 800, max: 1500 }, // Retraso para simular escritura
        maxMessageLength: 300
    };

    // Estado del chat
    const chatState = {
        isTyping: false,
        context: null,
        pendingFollowUps: []
    };

    // ======================
    // FUNCIONES PRINCIPALES
    // ======================

    function initChatbot() {
        loadSession();
        setupEventListeners();
        displayWelcomeMessage(1500); // Mostrar después de 1.5s
        startSessionWatcher(); // Verificador de sesión
    }

    function setupEventListeners() {
        // Botones de abrir/cerrar
        elements.openBtn.addEventListener('click', openChatInterface);
        elements.closeBtn.addEventListener('click', closeChatInterface);

        // Envío de mensajes
        elements.sendBtn.addEventListener('click', processUserMessage);
        elements.userInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter' && !e.shiftKey) {
                e.preventDefault();
                processUserMessage();
            }
        });

        // Verificador de entrada
        elements.userInput.addEventListener('input', validateInput);
    }

    async function processUserMessage() {
        const message = elements.userInput.value.trim();
        if (!message || chatState.isTyping || message.length > config.maxMessageLength) return;

        // Reset input
        elements.userInput.value = '';
        
        // Mostrar mensaje del usuario
        displayMessage(message, 'user');
        
        // Mostrar estado "escribiendo"
        const typingId = showTypingIndicator();
        chatState.isTyping = true;

        try {
            // Obtener respuesta (simulación o API real)
            const botResponse = await getBotResponse(message);
            
            // Mostrar respuesta después de un delay natural
            await delay(randomInt(config.typingSpeed.min, config.typingSpeed.max));
            displayMessage(botResponse, 'bot', true);

        } catch (error) {
            console.error('Error:', error);
            displayMessage("😕 Ocurrió un error. Por favor intenta nuevamente.", 'bot');
        } finally {
            // Limpiar y actualizar estado
            removeTypingIndicator(typingId);
            chatState.isTyping = false;
        }
    }

    // ======================
    // FUNCIONES DE INTERFAZ
    // ======================

    function openChatInterface() {
        elements.chatBox.classList.add('show');
        elements.openBtn.style.display = 'none';
        setTimeout(() => elements.userInput.focus(), 300);
    }

    function closeChatInterface() {
        elements.chatBox.classList.remove('show');
        setTimeout(() => {
            elements.openBtn.style.display = 'flex';
        }, 300);
    }

    function displayMessage(content, sender, isHtml = false) {
        const messageDiv = document.createElement('div');
        messageDiv.className = `message ${sender}-message`;
        
        if (isHtml) {
            messageDiv.innerHTML = content;
        } else {
            messageDiv.textContent = content;
        }

        elements.chatMessages.appendChild(messageDiv);
        scrollToBottom();
        saveSession();
    }

    function showTypingIndicator() {
        const typingId = `typing-${Date.now()}`;
        const indicator = document.createElement('div');
        indicator.id = typingId;
        indicator.className = 'message bot-message typing-indicator';
        indicator.innerHTML = `
            <div class="typing-dots">
                <span></span><span></span><span></span>
            </div>
        `;
        
        elements.chatMessages.appendChild(indicator);
        scrollToBottom();
        return typingId;
    }

    function removeTypingIndicator(id) {
        const indicator = document.getElementById(id);
        if (indicator) indicator.remove();
    }

    function displayWelcomeMessage(delay = 0) {
        if (elements.chatMessages.children.length > 0) return;
        
        setTimeout(() => {
            const welcomeMsg = `
                ¡Hola! 👋 Soy el asistente de <strong>Papas Brother</strong>.
                <div class="chat-options">
                    <p>Puedes preguntarme sobre:</p>
                    <ul>
                        <li>📋 Menú y precios</li>
                        <li>🕒 Horarios</li>
                        <li>📍 Ubicación</li>
                        <li>🚚 Delivery</li>
                        <li>🎉 Promociones</li>
                    </ul>
                </div>
            `;
            displayMessage(welcomeMsg, 'bot', true);
        }, delay);
    }

    function validateInput() {
        if (elements.userInput.value.length > config.maxMessageLength) {
            elements.userInput.value = elements.userInput.value.slice(0, config.maxMessageLength);
        }
    }

    function scrollToBottom() {
        elements.chatMessages.scrollTop = elements.chatMessages.scrollHeight;
    }

    // ======================
    // PERSISTENCIA DE DATOS
    // ======================

    function saveSession() {
        const sessionData = {
            timestamp: Date.now(),
            messages: elements.chatMessages.innerHTML,
            context: chatState.context
        };
        localStorage.setItem(config.sessionKey, JSON.stringify(sessionData));
    }

    function loadSession() {
        const savedData = localStorage.getItem(config.sessionKey);
        if (!savedData) return;

        try {
            const { timestamp, messages, context } = JSON.parse(savedData);
            
            if (Date.now() - timestamp <= config.sessionTimeout) {
                elements.chatMessages.innerHTML = messages;
                chatState.context = context;
                scrollToBottom();
            } else {
                clearSession();
            }
        } catch (e) {
            console.error('Error loading session:', e);
            clearSession();
        }
    }

    function clearSession() {
        localStorage.removeItem(config.sessionKey);
    }

    function startSessionWatcher() {
        setInterval(() => {
            const session = localStorage.getItem(config.sessionKey);
            if (session && (Date.now() - JSON.parse(session).timestamp > config.sessionTimeout)) {
                clearSession();
                if (elements.chatBox.classList.contains('show')) {
                    displayMessage("Se cerró la sesión por inactividad. Escribe para comenzar de nuevo.", 'bot');
                }
            }
        }, 60 * 60 * 1000); // Verificar cada hora
    }

    // ======================
    // LÓGICA DEL BOT
    // ======================

    async function getBotResponse(userMessage) {
        // En producción, reemplazar con llamada real a tu API:
        // const response = await fetch('/api/chatbot', { method: 'POST', ... });
        
        // Respuesta simulada para desarrollo
        const lowerMsg = userMessage.toLowerCase();
        
        if (containsAny(lowerMsg, ['hola', 'saludos', 'buenos días'])) {
            return "¡Hola! ¿En qué puedo ayudarte hoy? 😊";
        } else if (containsAny(lowerMsg, ['menú', 'carta', 'productos','menu'])) {
            return generateMenuResponse();
        } else if (containsAny(lowerMsg, ['hamburguesa', 'burger'])) {
            return generateBurgersResponse();
        } else if (containsAny(lowerMsg, ['pollo', 'broaster'])) {
            return generateChickenResponse();
        } else if (containsAny(lowerMsg, ['precio', 'costos'])) {
            return "Tenemos opciones desde S/ 12.90. ¿Te interesa saber de algún producto en particular?";
        } else if (containsAny(lowerMsg, ['ubicación', 'dirección', 'donde están'])) {
            return "📍 Estamos en Av. Los Olivos 123, San Martín de Porres. ¿Necesitas <a href='#' target='_blank'>indicaciones en Google Maps</a>?";
        } else if (containsAny(lowerMsg, ['horario', 'horarios', 'abren', 'cierran'])) {
            return "🕒 Horario de atención:<br>Lunes a Viernes: 11:00 am - 10:00 pm<br>Sábados y Domingos: 10:00 am - 11:00 pm";
        } else if (containsAny(lowerMsg, ['delivery', 'domicilio', 'envío'])) {
            return "🚗 Delivery disponible en SMP y distritos cercanos. Costo: S/ 5.00 (GRATIS en pedidos mayores a S/ 35).";
        } else if (containsAny(lowerMsg, ['adiós', 'chao', 'gracias'])) {
            return "¡Gracias por contactarnos! Esperamos verte pronto en Papas Brother. 😊";
        } else {
            const fallbacks = [
                "No estoy seguro de lo que preguntas. ¿Podrías reformularlo?",
                "¿Te refieres a nuestro menú, ubicación u horarios?",
                "Puedes preguntarme sobre hamburguesas, pollos broaster o delivery."
            ];
            return fallbacks[Math.floor(Math.random() * fallbacks.length)];
        }
    }

    // ======================
    // FUNCIONES AUXILIARES
    // ======================

    function containsAny(str, terms) {
        return terms.some(term => str.includes(term.toLowerCase()));
    }

    function delay(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }

    function randomInt(min, max) {
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }

    function generateMenuResponse() {
        return `
            🍽️ <strong>Menú Principal</strong>:
            <div class="menu-categories">
                <div>
                    <strong>🍔 Hamburguesas</strong>
                    <ul>
                        <li>Clásica - S/ 12.90</li>
                        <li>Doble Carne - S/ 16.90</li>
                    </ul>
                </div>
                <div>
                    <strong>🍗 Pollos Broaster</strong>
                    <ul>
                        <li>1/4 Pollo - S/ 10.90</li>
                        <li>Pollo Entero - S/ 32.90</li>
                    </ul>
                </div>
            </div>
            <p>¿Quieres más detalles de alguna categoría?</p>
        `;
    }

    function generateBurgersResponse() {
        return `
            🍔 <strong>Hamburguesas Especiales</strong>:
            <ul>
                <li><strong>Clásica:</strong> Pan artesanal, carne 150g, vegetales frescos - S/ 12.90</li>
                <li><strong>Especial:</strong> Doble carne, queso cheddar, tocino, salsa BBQ - S/ 16.90</li>
                <li><strong>Vegana:</strong> Hamburguesa de lentejas con vegetales - S/ 15.90</li>
                <li><strong>Combo Familiar:</strong> 2 hamburguesas + papas grandes - S/ 28.90</li>
            </ul>
            <p>🚚 <em>Todos los combos incluyen papas y gaseosa</em></p>
        `;
    }

    function generateChickenResponse() {
        return `
            🍗 <strong>Pollos Broaster</strong>:
            <ul>
                <li><strong>1/4 Pollo:</strong> Pechuga o pierna con papas y ensalada - S/ 10.90</li>
                <li><strong>1/2 Pollo:</strong> Media pieza con acompañamientos - S/ 18.90</li>
                <li><strong>Pollo Entero:</strong> Para 2-3 personas - S/ 32.90</li>
                <li><strong>Combo Familiar:</strong> 2 pollos + papas + ensalada + 3 gaseosas - S/ 59.90</li>
            </ul>
            <p>🔥 Todos nuestros pollos son marinados por 24 horas</p>
        `;
    }

    // Inicializar el chatbot
    initChatbot();
});
