window.addEventListener("DOMContentLoaded", async () => {
    const user = await getLoggedUser();
    if (!user) return;

    if (user.rol === "ADMIN") { document.getElementById("wishListNav").remove() }

    try {
        const response = await fetch("http://localhost:8080/api/content", {
            credentials: "include"
        });
        const allContent = await response.json();

        const movies = allContent.filter(c => c.type === "MOVIE");
        const books = allContent.filter(c => c.type === "BOOK");

        renderCarousel(movies, "movies-carousel");
        renderCarousel(books, "books-carousel");
    } catch (error) {
        console.error("Error al cargar contenido:", error);
    }
});

function renderCarousel(items, containerId) {
    const container = document.getElementById(containerId);
    container.innerHTML = "";

    items.forEach(item => {
        const div = document.createElement("div");
        div.classList.add("carousel-item");

        const img = document.createElement("img");
        img.src = 'data:image/jpeg;base64,' + item.imageBase64;
        img.alt = item.title;

        img.addEventListener("click", () => {
            window.location.href = `details.html?id=${item.id}`;
        });

        div.appendChild(img);
        container.appendChild(div);
    });
}
