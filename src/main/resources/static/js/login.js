// login.js
window.addEventListener("DOMContentLoaded", () => {
    console.log("✅ Script login.js cargado correctamente");

    const form = document.getElementById("login-form");
    const errorContainer = document.getElementById("error-message");

    if (!form) {
        console.error("🚨 El formulario no se encontró en el DOM");
        return;
    }

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        console.log("🔥 Evento submit capturado correctamente");

        const email = document.getElementById("email")?.value.trim();
        const password = document.getElementById("password")?.value;

        console.log("📨 Datos capturados:", { email, password });

        if (!email || !password) {
            errorContainer.innerText = "Por favor completá ambos campos.";
            return;
        }

        try {
            const response = await fetch("http://localhost:8080/api/auth/login", {
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
                console.warn("❌ Error en la autenticación:", data);
                errorContainer.innerText = "Usuario o contraseña incorrectos.";
            }
        } catch (error) {
            console.error("🚨 Error en la solicitud:", error);
            errorContainer.innerText = "No se pudo conectar al servidor.";
        }
    });
});
