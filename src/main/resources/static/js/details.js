let user = null;
let currentContent = null;

window.addEventListener("DOMContentLoaded", async () => {
    user = await getLoggedUser();
    if (!user) return;

    if (user.rol === "ADMIN") {
        document.getElementById("wishListNav").remove();
        document.getElementById("reviews-container").classList.add("extended-reviews-container");
        document.getElementById("create-review-containter").style.display = "none"
        document.getElementById("edit-content-btn").style.display = "inline-block";

        document.getElementById("edit-content-btn").addEventListener("click", () => {
            enterEditMode();
        });
    } else {
        document.getElementById("reviews-container").classList.remove("extended-reviews-container");
        document.getElementById("create-review-containter").style.display = "inline-block"
    }

    const urlParams = new URLSearchParams(window.location.search);
    const contentId = urlParams.get("id");

    if (!contentId) {
        console.error("ID no especificado");
        return;
    }

    await setupWishlist(contentId);
    await loadContentDetails(contentId);
    await loadReviews(contentId);

    setupReviewForm(contentId);
});

async function loadContentDetails(id) {
    try {
        const res = await fetch(`http://localhost:8080/api/content/${id}`, {
            credentials: "include"
        });
        const content = await res.json();
        currentContent = content;

        document.getElementById("title").textContent = content.title;
        document.getElementById("description").textContent = content.description;
        document.getElementById("genre").textContent = content.genre;
        document.getElementById("release").textContent = content.release_date;
        document.getElementById("image").src = "data:image/jpeg;base64," + content.imageBase64;

        const extra = document.getElementById("extra-data");
        extra.innerHTML = "";

        if (content.type === "MOVIE") {
            extra.innerHTML = `
                <p><strong>Director:</strong> ${content.director}</p>
                <p><strong>Duración:</strong> ${content.duration} min</p>
            `;
        } else if (content.type === "BOOK") {
            extra.innerHTML = `
                <p><strong>Autor:</strong> ${content.author}</p>
                <p><strong>ISBN:</strong> ${content.isbn}</p>
            `;
        }

        const wishlistBtn = document.getElementById("wishlist-btn");

        if (user.rol === "ADMIN") {
            const deleteBtn = document.getElementById("delete-content-btn");

            wishlistBtn.style.display = "none"
            deleteBtn.style.display = "inline-block";

            deleteBtn.addEventListener("click", removeContentListener)
        } else {

            wishlistBtn.style.display = "inline-block"
        }

    } catch (err) {
        console.error("Error fetching content:", err);
    }
}

async function loadReviews(contentId) {
    try {
        const res = await fetch(`http://localhost:8080/api/reviews/content/${contentId}`, {
            credentials: "include"
        });

        const reviews = await res.json();
        const container = document.getElementById("reviews-container");
        container.innerHTML = "";

        if (reviews.length === 0) {
            container.innerHTML = "<p>No hay reseñas aún.</p>";
            return;
        }

        reviews.forEach(review => {
            const div = document.createElement("div");
            div.classList.add("review");
            div.innerHTML = `
                <p><strong>${review.userName || "No name"}</strong> | ${review.stars} estrellas</p>
                <p>${review.description}</p>
            `;

            console.log(review.userId);
            console.log(user.id)

            const isAuthor = review.userId === user.id;
            const isAdmin = user.rol === "ADMIN";

            if (isAuthor || isAdmin) {
                const btnContainer = document.createElement("div");
                btnContainer.classList.add("btn-container");

                const deleteBtn = document.createElement("button");
                deleteBtn.textContent = "Eliminar";
                deleteBtn.classList.add("delete-review-btn");

                deleteBtn.addEventListener("click", async () => {
                    if (confirm("¿Estás seguro de que querés eliminar esta reseña?")) {
                        await deleteReview(review.id, contentId);
                    }
                });

                btnContainer.appendChild(deleteBtn);
                div.appendChild(btnContainer);
            }

            container.appendChild(div);
        });

    } catch (err) {
        console.error("Error fetching reviews:", err);
    }
}

async function deleteReview(reviewId, contentId) {
    try {
        const res = await fetch(`http://localhost:8080/api/reviews/${reviewId}`, {
            method: "DELETE",
            credentials: "include"
        });

        if (!res.ok) {
            const error = await res.text();
            throw new Error(error);
        }

        await loadReviews(contentId);
    } catch (err) {
        console.error("Error deleting review:", err);
    }
}

function setupReviewForm(contentId) {
    const form = document.getElementById("review-form");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const stars = parseInt(document.getElementById("stars").value);
        const reviewText = document.getElementById("descriptionReview").value;

        try {
            const res = await fetch("http://localhost:8080/api/reviews", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                credentials: "include",
                body: JSON.stringify({ contentId, stars, reviewText })
            });

            if (!res.ok) {
                const error = await res.text();
                throw new Error(error);
            }

            form.reset();

            await loadReviews(contentId);

        } catch (err) {
            console.error("Error creating review", err);
        }
    });
}

async function setupWishlist(contentId) {
    const wishlistBtn = document.getElementById("wishlist-btn");
    let isInWishlist = false;

    try {
        const res = await fetch("http://localhost:8080/api/wishlist", {
            credentials: "include"
        });

        const wishlist = await res.json();

        console.log()
        console.log(wishlist)
        isInWishlist = wishlist.some(item => item.id == contentId);
        console.log(isInWishlist)
        updateWishlistButton(isInWishlist);

        wishlistBtn.onclick = async () => {
            try {
                const method = isInWishlist ? "DELETE" : "POST";

                const response = await fetch(`http://localhost:8080/api/wishlist/${contentId}`, {
                    method,
                    credentials: "include"
                });

                if (!response.ok) {
                    throw new Error("Error updating wishlist");
                }

                isInWishlist = !isInWishlist;
                updateWishlistButton(isInWishlist);
            } catch (err) {
                console.error("Error updating wishlist:", err);
            }
        };

    } catch (err) {
        console.error("Error fetching wishlist:", err);
        wishlistBtn.style.display = "none";
    }
}

function updateWishlistButton(isInWishlist) {
    const btn = document.getElementById("wishlist-btn");
    btn.textContent = isInWishlist ? "Eliminar de la Wishlist" : "Añadir a la Wishlist";
    btn.classList.add(isInWishlist ? "remove-wishlist" : "add-wishlist")
    btn.classList.remove(isInWishlist ? "add-wishlist" : "remove-wishlist")

}

async function removeContentListener() {
    const confirmDelete = confirm("¿Estás seguro de que quieres eliminar este contenido?");

    if (!confirmDelete) return;

    try {
        const res = await fetch(`http://localhost:8080/api/content/${currentContent.id}`, {
            method: "DELETE",
            credentials: "include"
        });

        if (!res.ok) {
            const errorText = await res.text();
            throw new Error(errorText);
        }

        if (currentContent.type === "MOVIE") {
            window.location.href = "/movies.html";
        } else if (currentContent.type === "BOOK") {
            window.location.href = "/books.html";
        }
    } catch (err) {
        console.error("Error deleting content:", err);
        alert("Error deleting content: " + err.message);
    }
}

function enterEditMode() {
    const titleEl = document.getElementById("title");
    const descriptionEl = document.getElementById("description");
    const genreEl = document.getElementById("genre");
    const releaseEl = document.getElementById("release");
    const extraEl = document.getElementById("extra-data");

    const title = titleEl.textContent;
    const description = descriptionEl.textContent;
    const genre = genreEl.textContent;
    const release = releaseEl.textContent;

    titleEl.innerHTML = `<input type="text" id="edit-title" value="${title} " required>`;
    descriptionEl.innerHTML = `<textarea id="edit-description" required>${description}</textarea>`;
    genreEl.innerHTML = `<input type="text" required id="edit-genre" value="${genre}">`;
    releaseEl.innerHTML = `<input type="date" required id="edit-release" value="${release}">`;

    if (currentContent.type === "MOVIE") {
        extraEl.innerHTML = `
            <p><strong>Director:</strong> <input type="text" required id="edit-director" value="${currentContent.director}"></p>
            <p><strong>Duración:</strong> <input type="number" required id="edit-duration" value="${currentContent.duration}"></p>
        `;
    } else {
        extraEl.innerHTML = `
            <p><strong>Autor:</strong> <input type="text" required id="edit-author" value="${currentContent.author}"></p>
            <p><strong>ISBN:</strong> <input type="text" required id="edit-isbn" value="${currentContent.isbn}"></p>
        `;
    }

    const saveBtn = document.createElement("button");
    saveBtn.classList.add("save-btn");
    saveBtn.textContent = "Guardar cambios";
    saveBtn.style.marginTop = "2rem";
    saveBtn.addEventListener("click", saveChanges);
    extraEl.appendChild(saveBtn);
}

async function saveChanges() {
    const id = new URLSearchParams(window.location.search).get("id");

    const title = document.getElementById("edit-title").value.trim();
    const description = document.getElementById("edit-description").value.trim();
    const genre = document.getElementById("edit-genre").value.trim();
    const release_date = document.getElementById("edit-release").value;


    if (!title || !description || !genre || !release_date) {
        alert("Por favor, completá todos los campos obligatorios.");
        return;
    }

    const updatedData = {
        title,
        description,
        genre,
        release_date,
    };

    if (currentContent.type === "MOVIE") {
        const director = document.getElementById("edit-director").value.trim();
        const duration = document.getElementById("edit-duration").value;

        if (!director || !duration) {
            alert("Por favor, completá todos los campos de la película.");
            return;
        }

        updatedData.director = director;
        updatedData.duration = parseInt(duration);
    } else {
        const author = document.getElementById("edit-author").value.trim();
        const isbn = document.getElementById("edit-isbn").value.trim();

        if (!author || !isbn) {
            alert("Por favor, completá todos los campos del libro.");
            return;
        }

        updatedData.author = author;
        updatedData.isbn = isbn;
    }

    try {
        const res = await fetch(`http://localhost:8080/api/${currentContent.type.toLowerCase()}s/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "include",
            body: JSON.stringify(updatedData)
        });

        if (!res.ok) {
            const errorText = await res.text();
            throw new Error(errorText);
        }

        await loadContentDetails(id);

    } catch (err) {
        console.error("Error al guardar cambios:", err);
        alert("Error al guardar cambios: " + err.message);
    }
}

