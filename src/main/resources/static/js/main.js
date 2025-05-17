window.addEventListener("DOMContentLoaded", () => {
    console.log("✅ Script main.js cargado correctamente");

    // Comprobación de autenticación
    const email = localStorage.getItem("email");
    const role = localStorage.getItem("role");

    if (!email) {
        console.log("🚫 Usuario no autenticado, redirigiendo a login");
        window.location.href = "./login.html";
        return;
    }

    console.log(`👤 Usuario autenticado: ${email}, Rol: ${role}`);
    crearMenuNavegacion();  // Añadimos el menú

    // Cargar contenidos
    fetchContents();

    // Si es administrador, agregamos botones de gestión
    if (role === "ADMIN") {
        const adminPanel = document.createElement("div");
        adminPanel.classList.add("admin-panel");
        adminPanel.innerHTML = `
            <button onclick="createContent()">Crear Contenido</button>
            <button onclick="updateContent()">Actualizar Contenido</button>
            <button onclick="deleteContent()">Eliminar Contenido</button>
        `;
        document.body.appendChild(adminPanel);
    }
});

// Función para crear el menú de navegación
function crearMenuNavegacion() {
    const nav = document.createElement("nav");
    nav.classList.add("navbar");
    nav.innerHTML = `
        <a href="./index.html">Inicio</a>
        <a href="./register.html">Registrar Usuario</a>
        <a href="#" id="logout-btn">Cerrar Sesión</a>
    `;
    document.body.insertBefore(nav, document.body.firstChild);

    // Evento para cerrar sesión
    document.getElementById("logout-btn").addEventListener("click", () => {
        localStorage.clear();
        window.location.href = "./login.html";
    });
}

async function fetchContents() {
    try {
        const response = await fetch("http://localhost:8080/api/content");
        if (response.ok) {
            const data = await response.json();
            displayContents(data);
        } else {
            console.error("❌ Error al obtener los contenidos:", response.status);
        }
    } catch (error) {
        console.error("🚨 Error fetching contents:", error);
    }
}

function displayContents(contents) {
    const carousel = document.getElementById("content-carousel");
    carousel.innerHTML = "";
    contents.forEach(content => {
        const card = document.createElement("div");
        card.classList.add("card");
        card.innerHTML = `
            <h3>${content.title}</h3>
            <p>${content.description || "Sin descripción"}</p>
        `;
        carousel.appendChild(card);
    });
}
