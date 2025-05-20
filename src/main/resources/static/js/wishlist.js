window.addEventListener("DOMContentLoaded", async () => {
    const user = await getLoggedUser();
    if (!user) return;

    if (user.rol === "ADMIN") { window.location.href = "/home.html"; }

    await loadWishlist();
});

async function loadWishlist() {
    try {
        const res = await fetch("http://localhost:8080/api/wishlist", {
            credentials: "include"
        });

        if (!res.ok) throw new Error("Error fetching user wishlist");

        const wishlist = await res.json();
        const container = document.getElementById("wishlist-container");

        if (wishlist.length === 0) {
            document.getElementById("wishlist-empty").style.display = 'block';

            return;
        }

        document.getElementById("wishlist-empty").style.display = 'none';


        wishlist.forEach(item => {
            const card = document.createElement("div");
            card.classList.add("card");

            card.innerHTML = `
                <img src="data:image/jpeg;base64,${item.imageBase64}" alt="${item.title}">
                <h3>${item.title}</h3>
            `;

            card.addEventListener("click", () => {
                window.location.href = `/details.html?id=${item.id}`;
            });

            container.appendChild(card);
        });

    } catch (err) {
        console.error("Error fetching user wishlist:", err);
    }
}
