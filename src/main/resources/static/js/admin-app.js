// Điều hướng các Tab không cần load lại trang
function showSection(id, element) {
  document
    .querySelectorAll(".section-panel")
    .forEach((s) => s.classList.remove("active"));
  document.getElementById(id).classList.add("active");

  document
    .querySelectorAll(".nav-link")
    .forEach((l) => l.classList.remove("active"));
  element.classList.add("active");

  if (id === "dashboard") loadStats();
  if (id === "routes") loadRoutes();
  if (id === "buses") loadBuses();
  if (id === "trips") loadTrips();
}

// 1. Tải thống kê
async function loadStats() {
  try {
    // Sửa đường dẫn theo Controller Admin của bạn
    const res = await fetch("/admin/api/admin/stats");
    const data = await res.json();
    document.getElementById("stat-routes").innerText = data.totalRoutes || 0;
    document.getElementById("stat-buses").innerText = data.totalBuses || 0;
  } catch (e) {
    console.error("Lỗi load stats", e);
  }
}

// 2. Tải danh sách Tuyến đường
async function loadRoutes() {
  const res = await fetch("/api/routes");
  const data = await res.json();
  const tbody = document.getElementById("table-routes");
  tbody.innerHTML = data
    .map(
      (r) => `
                <tr>
                    <td class="ps-4">${r.id}</td>
                    <td><span class="badge bg-info text-dark">${r.departure}</span></td>
                    <td><span class="badge bg-success">${r.destination}</span></td>
                    <td>${new Intl.NumberFormat("vi-VN").format(r.price || 0)} đ</td>
                    <td>
                        <button class="btn btn-outline-danger btn-sm" onclick="deleteItem('/api/routes', ${r.id}, loadRoutes)">
                            <i class="bi bi-trash"></i>
                        </button>
                    </td>
                </tr>
            `,
    )
    .join("");
}

// 3. Tải danh sách Xe
async function loadBuses() {
  const res = await fetch("/api/buses");
  const data = await res.json();
  const tbody = document.getElementById("table-buses");
  tbody.innerHTML = data
    .map(
      (b) => `
                <tr>
                    <td class="ps-4 fw-bold">${b.licensePlate || b.id}</td>
                    <td>${b.busType || "Giường nằm"}</td>
                    <td>${b.capacity || 40} Ghế</td>
                    <td>
                        <button class="btn btn-outline-danger btn-sm" onclick="deleteItem('/api/buses', ${b.id}, loadBuses)">
                            <i class="bi bi-trash"></i>
                        </button>
                    </td>
                </tr>
            `,
    )
    .join("");
}

// 4. Tải danh sách Chuyến xe (Trip)
async function loadTrips() {
  const res = await fetch("/api/trips");
  const data = await res.json();
  const tbody = document.getElementById("table-trips");
  tbody.innerHTML = data
    .map(
      (t) => `
                <tr>
                    <td class="ps-4">${t.id}</td>
                    <td>${t.route ? t.route.departure + " ➔ " + t.route.destination : "N/A"}</td>
                    <td>${t.bus ? t.bus.licensePlate : "Chưa gán xe"}</td>
                    <td>${new Date(t.departureTime).toLocaleString("vi-VN")}</td>
                    <td><strong class="text-primary">${t.availableSeats}</strong></td>
                </tr>
            `,
    )
    .join("");
}

// Hàm xóa chung
async function deleteItem(url, id, callback) {
  if (confirm("Bạn có chắc muốn xóa mục này?")) {
    await fetch(`${url}/${id}`, { method: "DELETE" });
    callback();
  }
}

// Khởi tạo lần đầu
window.onload = loadStats;
