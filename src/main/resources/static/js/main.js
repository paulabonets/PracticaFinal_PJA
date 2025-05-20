window.addEventListener("DOMContentLoaded", async () => {
    const user = await getLoggedUser();
    if (!user) return;

    const { email, name, role } = user;

    const logoutLink = document.querySelector("a#logout");

    if (logoutLink) {
        logoutLink.addEventListener("click", async (e) => {
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
    }
});

async function getLoggedUser() {
    try {
        const res = await fetch("http://localhost:8080/api/auth/me", {
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
