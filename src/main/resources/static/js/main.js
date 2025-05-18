document.addEventListener("DOMContentLoaded", () => {
    console.log("Cargando contenido real de MooKFlix...");

    const API_URL = "http://localhost:8080/api/content";

    async function fetchContent(type) {
        try {
            const response = await fetch(`${API_URL}?type=${type}`);
            if (!response.ok) throw new Error(`Error al obtener ${type}`);
            return await response.json();
        } catch (err) {
            console.error(`❌ ${type} error:`, err);
            return [];
        }
    }

    function renderCards(items, containerId) {
        const container = document.getElementById(containerId);
        container.innerHTML = ""; // Limpia antes de renderizar
        items.forEach(item => {
            const div = document.createElement("div");
            div.className = "highlight-card";
            div.textContent = item.title || "Sin título";
            container.appendChild(div);
        });
    }

    async function cargarContenido() {
        const peliculas = await fetchContent("MOVIE");
        const libros = await fetchContent("BOOK");

        renderCards(peliculas, "movies-section");
        renderCards(libros, "books-section");
    }

    cargarContenido();
});
