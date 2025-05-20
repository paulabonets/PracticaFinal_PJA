window.addEventListener("DOMContentLoaded", async () => {
    const user = await getLoggedUser();
    if (!user) return;

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
            container.appendChild(div);
        });

    } catch (err) {
        console.error("Error fetching reviews:", err);
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