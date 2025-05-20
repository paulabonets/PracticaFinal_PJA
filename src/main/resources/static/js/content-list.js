document.addEventListener("DOMContentLoaded", async () => {
    const user = await getLoggedUser();
    if (!user) return;

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
