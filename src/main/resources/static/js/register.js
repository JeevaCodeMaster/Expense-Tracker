const API_URL = "http://localhost:8085";

document.getElementById("registerForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch(`${API_URL}/users/register`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username, password,role:"USER" })
        });

        if (!response.ok) {
            throw new Error("Registration failed");
        }

        alert("✅ Registration successful! Please login.");
        window.location.href = "login.html";

    } catch (err) {
        console.error(err);
        alert("❌ Error: " + err.message);
    }
});
