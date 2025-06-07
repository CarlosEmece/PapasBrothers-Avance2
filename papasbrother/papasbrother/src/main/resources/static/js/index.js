document.addEventListener("click", function() {
    const hamburguesa = document.querySelector(".hamburguesa img");
    const papas = document.querySelector(".papas img");

    let hamburguesaActual = hamburguesa.getAttribute("src");
    let papasActual = papas.getAttribute("src");

    // Si la hamburguesa tiene la imagen original, cámbiala
    if (hamburguesaActual.includes("imagen1.png")) {
        hamburguesa.setAttribute("src", "/img/imagen1vuelta.png");
        papas.setAttribute("src", "/img/imagen2vuelta.png");
        document.querySelector(".hamburguesa").style.animationDirection = "reverse";
        document.querySelector(".papas").style.animationDirection = "reverse";
    } else {
        hamburguesa.setAttribute("src", "/img/imagen1.png");
        papas.setAttribute("src", "/img/imagen2.png");
        document.querySelector(".hamburguesa").style.animationDirection = "normal";
        document.querySelector(".papas").style.animationDirection = "normal";
    }
});

// Función para hacer que las imágenes reaparezcan por el otro lado
function moverElementos() {
    const hamburguesa = document.querySelector(".hamburguesa");
    const papas = document.querySelector(".papas");

    let hamburguesaX = parseInt(getComputedStyle(hamburguesa).left);
    let papasX = parseInt(getComputedStyle(papas).left);
    let pantallaAncho = window.innerWidth;

    // Verificar si las imágenes salieron completamente por la derecha
    if (hamburguesaX >= pantallaAncho) {
        hamburguesa.style.left = "-250px"; // Reaparece por la izquierda
    }
    if (papasX >= pantallaAncho) {
        papas.style.left = "-250px"; // Reaparece por la izquierda
    }

    // Verificar si las imágenes salieron completamente por la izquierda
    if (hamburguesaX <= -250) {
        hamburguesa.style.left = pantallaAncho + "px"; // Reaparece por la derecha
    }
    if (papasX <= -250) {
        papas.style.left = pantallaAncho + "px"; // Reaparece por la derecha
    }

    requestAnimationFrame(moverElementos); // Mantener el movimiento
}

// Iniciar el ciclo de movimiento
moverElementos();


function irAPagina() {
    window.location.href = "paginaprincipal.html"; // Asegúrate de que esta es la URL correcta
}
