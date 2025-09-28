const API_URL = "http://localhost:8085";
const token = localStorage.getItem("jwt");
const userId = parseInt(localStorage.getItem("userId")); // Must be set at login

if (!token || !userId) {
    alert("Not logged in!");
    window.location.href = "login.html";
}

// ---------------- CATEGORY ----------------
async function loadCategories() {
    const res = await fetch(`${API_URL}/category/user/${userId}`, {
        headers: { "Authorization": `Bearer ${token}` }
    });
    const categories = await res.json();

    // Display category list
    const ul = document.getElementById("categoryList");
    ul.innerHTML = "";
    categories.forEach(cat => {
        const li = document.createElement("li");
        li.textContent = cat.name;
        const delBtn = document.createElement("button");
        delBtn.textContent = "Delete";
        delBtn.className = "btn";
        delBtn.onclick = () => deleteCategory(cat.id);
        li.appendChild(delBtn);
        ul.appendChild(li);
    });

    // Populate select boxes
    const select = document.getElementById("categorySelect");
    const filterSelect = document.getElementById("filterCategory");
    select.innerHTML = '<option value="">-- Select Category --</option>';
    filterSelect.innerHTML = '<option value="">-- All Categories --</option>';

    categories.forEach(cat => {
        const opt = document.createElement("option");
        opt.value = cat.id;
        opt.textContent = cat.name;
        select.appendChild(opt);

        const opt2 = document.createElement("option");
        opt2.value = cat.id;
        opt2.textContent = cat.name;
        filterSelect.appendChild(opt2);
    });
}

async function addCategory() {
    const name = document.getElementById("categoryName").value;
    if (!name) return alert("Enter category name");
	

    const res = await fetch(`${API_URL}/category/add-category`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify({
            name: name,
            user: { id: userId },
            parentId: null
        })
    });

    if (res.ok) {
        document.getElementById("categoryName").value = "";
        loadCategories();
    } else {
        const error = await res.text();
        alert("Failed to add category: " + error);
    }
}

async function deleteCategory(id) {
    const res = await fetch(`${API_URL}/category/delete/${id}`, {
        method: "DELETE",
        headers: { "Authorization": `Bearer ${token}` }
    });
    if (res.ok) loadCategories();
    else alert("Failed to delete category");
}

// ---------------- EXPENSE ----------------
document.getElementById("expenseForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const amount = parseFloat(document.getElementById("amount").value);
    const description = document.getElementById("description").value;
    const date = document.getElementById("date").value;
    const categoryId = parseInt(document.getElementById("categorySelect").value);

    if (!categoryId) return alert("Select a category");

    const res = await fetch(`${API_URL}/expense/add-expense`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify({
            amount,
            description,
            date,
            categoryId,
            userId
        })
    });

    if (res.ok) {
        document.getElementById("expenseForm").reset();
        loadExpenses();
    } else {
        const error = await res.text();
        alert("Failed to add expense: " + error);
    }
});

async function loadExpenses(filterCategoryId = "") {
    let url = `${API_URL}/expense/user/${userId}`;
    if (filterCategoryId) url = `${API_URL}/expense/user/${userId}/category/${filterCategoryId}`;

    const res = await fetch(url, {
        headers: { "Authorization": `Bearer ${token}` }
    });
    const expenses = await res.json();

    const tbody = document.getElementById("expenseTable");
    tbody.innerHTML = "";
    expenses.forEach(e => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${e.id}</td>
            <td>${e.amount}</td>
            <td>${e.description}</td>
            <td>${e.date}</td>
            <td>${e.category.name}</td>
            <td><button class="btn" onclick="deleteExpense(${e.id})">Delete</button></td>
        `;
        tbody.appendChild(row);
    });

    loadCharts(expenses);
}

function filterExpenses() {
    const catId = document.getElementById("filterCategory").value;
    loadExpenses(catId);
}

async function deleteExpense(expenseId) {
    const res = await fetch(`${API_URL}/expense/delete/${expenseId}`, {
        method: "DELETE",
        headers: { "Authorization": `Bearer ${token}` }
    });
    if (res.ok) loadExpenses();
    else alert("Failed to delete expense");
}

// ---------------- CHARTS ----------------
function loadCharts(expenses) {
    const monthlyData = {};
    const categoryData = {};

    expenses.forEach(e => {
        const month = e.date.substring(0,7);
        monthlyData[month] = (monthlyData[month] || 0) + e.amount;
        const cat = e.category.name;
        categoryData[cat] = (categoryData[cat] || 0) + e.amount;
    });

    // Line chart: Monthly spend
    new Chart(document.getElementById("monthlyChart"), {
        type: "line",
        data: { labels: Object.keys(monthlyData), datasets: [{ label: "Monthly Spend", data: Object.values(monthlyData) }] }
    });

    // Pie chart: Category breakdown
    new Chart(document.getElementById("categoryChart"), {
        type: "pie",
        data: { labels: Object.keys(categoryData), datasets: [{ label: "Category Breakdown", data: Object.values(categoryData) }] }
    });
}

// ---------------- INIT ----------------
loadCategories();
loadExpenses();
