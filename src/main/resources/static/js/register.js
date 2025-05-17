document.getElementById("register-form").addEventListener("submit", async (e) => {
    e.preventDefault();  // Evitamos que recargue la página

    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const role = document.getElementById("role").value;

    console.log("Datos capturados:", name, email, password, role);

    try {
        const response = await fetch("http://localhost:8080/api/auth/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ name, email, password, rol: role })
        });

        if (response.ok) {
            alert("Usuario registrado correctamente");
            window.location.href = "login.html";
        } else if (response.status === 409) {
            alert("El correo ya está en uso");
        } else {
            alert("Error al registrar el usuario");
        }
    } catch (error) {
        console.error("Error en el registro:", error);
    }
});
