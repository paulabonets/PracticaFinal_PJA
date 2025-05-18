// login.js
window.addEventListener("DOMContentLoaded", () => {
    console.log(" Script login.js cargado correctamente");

    const form = document.getElementById("login-form");
    if (form) {
        console.log(" Formulario encontrado");

        form.addEventListener("submit", async (e) => {
            e.preventDefault();
            console.log(" Evento submit capturado correctamente");

            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;

            console.log("📨 Datos capturados:", { email, password });

            try {
                const response = await fetch("http://localhost:8080/api/auth/login", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({ email, password })
                });

                if (response.ok) {
                    const data = await response.json();
                    console.log(" Datos recibidos:", data);

                    localStorage.setItem("token", data.token || "token_placeholder");
                    localStorage.setItem("role", data.role || "USER");
                    localStorage.setItem("email", email);

                    alert(`Bienvenido, ${data.role}`);
                    window.location.href = "index.html";

                } else {
                    const errorMessage = await response.text();
                    console.log(" Error en la autenticación:", response.status, errorMessage);
                    document.getElementById("error-message").innerText = "Usuario o contraseña incorrectos.";
                }
            } catch (error) {
                console.error(" Error al iniciar sesión:", error);
                document.getElementById("error-message").innerText = "Error en el servidor.";
            }
        });
    } else {
        console.error(" El formulario no se encontró en el DOM");
    }
});
