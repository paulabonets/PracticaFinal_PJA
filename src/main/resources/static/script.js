function agregarPelicula() {
    const nombre = document.getElementById("nombre-pelicula").value;
    const titulo = document.getElementById("titulo-pelicula").value;
    const rating = document.getElementById("rating-pelicula").value;
    const contenedor = document.getElementById("peliculas-recomendadas");

    if (nombre && titulo) {
        const nuevoComentario = document.createElement("div");
        nuevoComentario.classList.add("comment");
        nuevoComentario.innerHTML = `<strong style="color:#ffcc00;">${nombre} recomienda:</strong> ${titulo} (${ "⭐".repeat(rating) })`;
        nuevoComentario.style.padding = "0.5rem";
        nuevoComentario.style.borderBottom = "1px solid #ffcc00";
        contenedor.appendChild(nuevoComentario);
    }
}

document.getElementById("form-plan").addEventListener("submit", function(event) {
    event.preventDefault();

    const nombre = document.getElementById("nombre").value;
    const apellido = document.getElementById("apellido").value;
    const edad = document.getElementById("edad").value;
    const cantidad = document.getElementById("cantidad").value;
    const genero = document.getElementById("genero").value;
    const email = document.getElementById("email").value;

    let preferencias = [];
    if (document.getElementById("prefiere-peliculas").checked) preferencias.push("Películas");
    if (document.getElementById("prefiere-series").checked) preferencias.push("Series");

    if (preferencias.length === 0) {
        alert("Selecciona al menos una preferencia: Películas o Series.");
        return;
    }

    alert(`Gracias ${nombre}, hemos enviado tu plan personalizado de ${cantidad} recomendaciones de ${preferencias.join(" y ")} en el género ${genero} a ${email}.`);
});

function agregarComentario() {
    const nombre = document.getElementById("nombre-comentario").value;
    const comentario = document.getElementById("comentario").value;
    const contenedor = document.getElementById("comentarios-foro");

    if (nombre && comentario) {
        const nuevoComentario = document.createElement("div");
        nuevoComentario.classList.add("comment");
        nuevoComentario.innerHTML = `<strong style="color:#ffcc00;">${nombre}:</strong> ${comentario}`;
        nuevoComentario.style.padding = "0.5rem";
        nuevoComentario.style.borderBottom = "1px solid #ffcc00";
        contenedor.appendChild(nuevoComentario);
    }
}

function enviarCorreo() {
    const correo = document.getElementById("correo").value;
    if (correo) {
        alert("Gracias por contactarnos. Te responderemos pronto a " + correo);
    }
}

function showTab(tabId) {
    document.querySelectorAll('.tab-content').forEach(tab => {
        tab.classList.remove('active');
    });
    document.getElementById(tabId).classList.add('active');
}

function flipCard(card) {
    card.classList.toggle("flipped");

}
async function cargarPeliculasClasicas() {
    try {
        const respuesta = await fetch('http://localhost:8080/api/peliculas');
        const peliculas = await respuesta.json();

        const contenedor = document.getElementById('peliculas-clasicas');
        contenedor.innerHTML = ''; // Limpia antes de volver a cargar

        peliculas.forEach(pelicula => {
            const tarjeta = document.createElement('div');
            tarjeta.classList.add('flip-card');
            tarjeta.innerHTML = `
        <div class="flip-card-inner">
          <div class="flip-card-front">
            <img src="${pelicula.imagenUrl}" alt="${pelicula.titulo}">
          </div>
          <div class="flip-card-back">
            <h3>${pelicula.titulo}</h3>
            <p><strong>Director:</strong> ${pelicula.director}</p>
            <p><strong>Año:</strong> ${pelicula.anio}</p>
            <p>${pelicula.sinopsis}</p>
          </div>
        </div>
      `;
            contenedor.appendChild(tarjeta);
        });
    } catch (error) {
        console.error("Error cargando películas:", error);
    }
}

window.addEventListener('DOMContentLoaded', cargarPeliculasClasicas);
