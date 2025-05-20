document.addEventListener("DOMContentLoaded", async () => {
    const user = await getLoggedUser();
    if (!user) return;

    if (user.rol === "ADMIN") {
        document.getElementById("wishListNav").remove();
        const path = window.location.pathname;
        const isBooksPage = path.includes("books.html");
        const isMoviesPage = path.includes("movies.html");
        const type = isBooksPage ? "BOOK" : "MOVIE";


        const createBtn = document.getElementById("create-content-btn");
        createBtn.style.display = "inline-block";

        const modal = document.getElementById("create-modal");
        const closeModal = document.getElementById("close-modal");

        createBtn.addEventListener("click", () => modal.style.display = "block");
        closeModal.addEventListener("click", () => modal.style.display = "none");

        window.addEventListener("click", (e) => {
            if (e.target === modal) modal.style.display = "none";
        });

        document.getElementById("create-form").addEventListener("submit", async (e) => {
            e.preventDefault();

            const title = document.getElementById("new-title").value.trim();
            const description = document.getElementById("new-description").value.trim();
            const genre = document.getElementById("new-genre").value.trim();
            const release = document.getElementById("new-release").value;
            const imageFile = document.getElementById("new-image").files[0];

            if (!title || !description || !genre || !release || !imageFile) {
                alert("Por favor complete los campos escenciales del contenido");
                return;
            }

            const readImageAsBase64 = (file) => {
                return new Promise((resolve, reject) => {
                    const reader = new FileReader();
                    reader.onload = () => resolve(reader.result.split(",")[1]);
                    reader.onerror = () => reject("Error reading the image.");
                    reader.readAsDataURL(file);
                });
            };

            try {
                const imageBase64 = await readImageAsBase64(imageFile);

                const payload = {
                    title,
                    description,
                    genre,
                    release,
                    imageBase64
                };

                let endpoint = "";

                if (isBooksPage) {
                    const author = document.getElementById("new-author").value.trim();
                    const isbn = document.getElementById("new-isbn").value.trim();

                    if (!author || !isbn) {
                        alert("Por favor complete los campos del libro");
                        return;
                    }
                    payload.author = author
                    payload.isbn = isbn
                    payload.type = "BOOK"
                    endpoint = "/api/books";
                } else if (isMoviesPage) {
                    const director = document.getElementById("new-director").value.trim();
                    const duration = document.getElementById("new-duration").value.trim();

                    if (!director || !duration) {
                        alert("Por favor complete los campos de la pelicula");
                        return;
                    }
                    payload.director = director;
                    payload.duration = duration;
                    payload.type = "MOVIE"

                    endpoint = "/api/movies";
                }

                const res = await fetch(endpoint, {
                    method: "POST",
                    credentials: "include",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(payload)
                });

                if (!res.ok) throw new Error(await res.text());

                alert(`${isBooksPage ? "Libro" : "Película"} creado correctamente`);
                modal.style.display = "none";
                document.getElementById("create-form").reset();
                fetchAndRenderContent();

            } catch (err) {
                alert("Error al crear: " + err.message);
            }
        });
    }

    const searchBar = document.getElementById("search-bar");
    const isBooks = window.location.pathname.includes("books");
    const type = isBooks ? "BOOK" : "MOVIE";
    let container;
    let emptyMessage;

    if(type === "BOOK") {
        emptyMessage = document.getElementById("books-empty");
        container = document.getElementById("books-container");
    } else {
        emptyMessage = document.getElementById("movies-empty");
        container = document.getElementById("movies-container");
    }

    const fetchAndRenderContent = async (query = "") => {
        try {
            const res = await fetch(`/api/content?type=${type}&title=${query}`);
            const data = await res.json();

            container.innerHTML = "";

            if (data.length === 0) {
                emptyMessage.style.display = 'block';
                return;
            }

            emptyMessage.style.display = 'none';
            data.forEach((item) => {
                const card = document.createElement("div");
                card.classList.add("card");
                card.onclick = () => window.location.href = `/details.html?id=${item.id}`;

                card.innerHTML = `
          <img src="data:image/png;base64,${item.imageBase64}" alt="${item.title}" />
          <div class="card-title">${item.title}</div>
        `;

                container.appendChild(card);
            });
        } catch (err) {
            console.error("Error fetching content", err);
        }
    };

    searchBar.addEventListener("input", (e) => {
        const query = e.target.value.trim();
        fetchAndRenderContent(query);
    });

    fetchAndRenderContent();
});
