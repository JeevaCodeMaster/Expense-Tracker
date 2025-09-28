const API_URL = "http://localhost:8085";

function saveAuth(token) {
    localStorage.setItem("jwt", token);
 

    sessionStorage.setItem("jwt", token);
    
}

function getToken() {
    return localStorage.getItem("jwt") || sessionStorage.getItem("jwt");
}



function isLoggedIn() {
	console.log("yes ")
    return !!getToken();
}
function parseJwt(token) {
    try {
        const base64Url = getToken.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(c =>
            '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
        ).join(''));
        return JSON.parse(jsonPayload);
    } catch (e) {
        return null;
    }
}
function logout() {
    const token = getToken();
    fetch(`${API_URL}/auth/logout`, {
        method: "GET",
        headers: { "Authorization": `Bearer ${token}` }
    }).finally(() => {
        localStorage.clear();
        sessionStorage.clear();
        window.location.href = "login.html";
    });
}
