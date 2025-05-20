window.addEventListener("DOMContentLoaded", () => {

    const form = document.getElementById("login-form");
    const errorContainer = document.getElementById("error-message");

    if (!form) {
        return;
    }

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const email = document.getElementById("email")?.value.trim();
        const password = document.getElementById("password")?.value;

        console.log("📨 Datos capturados:", { email, password });

        if (!email || !password) {
            errorContainer.innerText = "Por favor completá ambos campos.";
            return;
        }

        try {
            const response = await fetch("https://jpa-1-bo8z.onrender.com/api/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                credentials: "include",
                body: JSON.stringify({ email, password })
            });

            const contentType = response.headers.get("content-type");
            let data;

            if (contentType && contentType.includes("application/json")) {
                data = await response.json();
            } else {
                data = await response.text();
            }

            if (response.ok) {
                console.log("🔑 Login exitoso:", data);

                localStorage.setItem("email", email);

                window.location.href = "./home.html";
            } else {
                console.warn("Auth error:", data);
                errorContainer.innerText = "Email o contraseña incorrectos.";
            }
        } catch (error) {
            console.error("🚨 Request Error:", error);
            errorContainer.innerText = "No se pudo conectar al servidor.";
        }
    });
});
