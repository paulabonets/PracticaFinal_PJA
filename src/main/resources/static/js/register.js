document.getElementById("register-form").addEventListener("submit", async (e) => {
    e.preventDefault();

    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("https://jpa-1-bo8z.onrender.com/api/auth/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ name, email, password })
        });

        if (response.ok) {
            console.log("Usuario registrado correctamente");
            window.location.href = "./../home.html";
        } else if (response.status === 409) {
            console.log("El correo ya está en uso");
        } else {
            console.log("Error al registrar el usuario");
        }
    } catch (error) {
        console.error("Error en el registro:", error);
    }
});
