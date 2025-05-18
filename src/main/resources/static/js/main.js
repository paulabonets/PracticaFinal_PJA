document.addEventListener("DOMContentLoaded", () => {
    console.log(" Cargando contenido real de MooKFlix...");

    const API_URL = "http://localhost:8080/api/content";

    async function fetchContent(type) {
        try {
            const response = await fetch(`${API_URL}?type=${type}`);
            if (!response.ok) throw new Error(`Error al obtener ${type}`);
            return await response.json();
        } catch (err) {
            console.error(` ${type} error:`, err);
            return [];
        }
    }

    function renderCards(items, containerId) {
        const container = document.getElementById(containerId);
        container.innerHTML = "";

        items.forEach(item => {
            const card = document.createElement("div");
            card.className = "highlight-card";

            // Parte delantera
            const front = document.createElement("div");
            front.className = "card-front";

            const img = document.createElement("img");
            img.src = item.imageUrl || "img/default.jpg";
            img.alt = item.title;
            img.onerror = () => img.style.display = "none";

            const title = document.createElement("div");
            title.textContent = item.title;

            front.appendChild(img);
            front.appendChild(title);

            // Parte trasera
            const back = document.createElement("div");
            back.className = "card-back";

            back.innerHTML = `
                <h3>${item.title}</h3>
                <p><strong>Género:</strong> ${item.genre || "N/A"}</p>
                <p><strong>Descripción:</strong> ${item.description || "Sin descripción"}</p>
                <p><strong>Fecha:</strong> ${item.release_date ? new Date(item.release_date).toLocaleDateString() : "N/A"}</p>
            `;

            card.appendChild(front);
            card.appendChild(back);

            card.addEventListener("click", () => {
                card.classList.toggle("flipped");
            });

            container.appendChild(card);
        });
    }

    async function cargarContenido() {
        const peliculas = await fetchContent("MOVIE");
        const libros = await fetchContent("BOOK");

        renderCards(peliculas, "movies-section");
        renderCards(libros, "books-section");
    }

    cargarContenido();
    document.getElementById("btn-movieflix").addEventListener("click", (e) => {
        e.preventDefault();
        document.querySelector(".section:nth-of-type(1)").style.display = "block"; // películas
        document.querySelector(".section:nth-of-type(2)").style.display = "none";  // libros
    });

    document.getElementById("btn-bookflix").addEventListener("click", (e) => {
        e.preventDefault();
        document.querySelector(".section:nth-of-type(1)").style.display = "none";
        document.querySelector(".section:nth-of-type(2)").style.display = "block";
    });

    document.getElementById("logo").addEventListener("click", (e) => {
        e.preventDefault();
        document.querySelector(".section:nth-of-type(1)").classList.remove("hidden");
        document.querySelector(".section:nth-of-type(2)").classList.add("hidden");

    });

});
