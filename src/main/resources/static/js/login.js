//const API_URL = "http://localhost:8085";

document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch(`${API_URL}/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });

        if (!response.ok) {
            alert("❌ Login failed: " + response.status);
            return;
        }

        const data = await response.json();
        console.log("Login response:", data);

        if (data.token) {
            // Save token to localStorage
            localStorage.setItem("jwt", data.token);
			localStorage.setItem("username", username);
			//const jwtPayload = parseJwt(token);
			//userId = jwtPayload ? jwtPayload.sub : null;
			//localStorage.setItem("userId",userId);

            alert("✅ Login successful!");
            window.location.href = "dashboard.html";
        } else {
            alert("❌ Login failed. No token in response.");
        }

    } catch (err) {
        console.error("Login error:", err);
        alert("❌ Error: " + err.message);
    }
});
