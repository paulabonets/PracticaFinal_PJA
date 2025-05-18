window.addEventListener("DOMContentLoaded", () => {
    const { email, name, role } = getLoggedUser();

    console.log(`👤 Usuario autenticado: ${email}, Rol: ${role}`);
    crearMenuNavegacion();

    fetchContents();

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

document.querySelector("a#logout").addEventListener("click", async (e) => {
    e.preventDefault();

    try {
        await fetch("http://localhost:8080/api/auth/logout", {
            method: "POST",
            credentials: "include"
        });

        window.location.href = "login.html";
    } catch (error) {
        console.error("Error al cerrar sesión:", error);
    }
});

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

async function getLoggedUser() {
    try {
        const res = await fetch("http://localhost:8080/api/auth/me", {
            method: "GET",
            credentials: "include"
        });

        if (!res.ok) {
            window.location.href = "login.html";

            return;
        }

        return await res.json();

    } catch (error) {
        console.error(error);
        window.location.href = "login.html";
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
