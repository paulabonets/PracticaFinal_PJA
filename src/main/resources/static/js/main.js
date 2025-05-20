window.addEventListener("DOMContentLoaded", async () => {
    const user = await getLoggedUser();
    if (!user) return;

    const { email, name, role } = user;

    const logoutLink = document.querySelector("a#logout");

    if (logoutLink) {
        logoutLink.addEventListener("click", async (e) => {
            e.preventDefault();
            try {
                await fetch("https://jpa-1-bo8z.onrender.com/api/auth/logout", {
                    method: "POST",
                    credentials: "include"
                });
                window.location.href = "login.html";
            } catch (error) {
                console.error("Error al cerrar sesión:", error);
            }
        });
    }
});

async function getLoggedUser() {
    try {
        const res = await fetch("https://jpa-1-bo8z.onrender.com/api/auth/me", {
            method: "GET",
            credentials: "include"
        });

        if (!res.ok) {
            window.location.href = "login.html";
            return null;
        }

        return await res.json();
    } catch (error) {
        console.error("Error:", error);
        window.location.href = "login.html";
    }
}
