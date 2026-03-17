// 1. Tải danh sách xe
async function loadBuses() {
    try {
        const res = await fetch("/api/buses");
        const data = await res.json();
        
        const html = data.map(b => `
            <tr>
                <td class="ps-4">${b.id}</td>
                <td><span class="badge bg-light text-dark border">${b.busNumber || "N/A"}</span></td>
                <td>${b.busType || "Chưa rõ"}</td>
                <td>${b.capacity || 0} ghế</td>
                <td class="text-center">
                    <button class="btn btn-sm btn-outline-primary me-1" 
                            onclick="openEditModal(${b.id}, '${b.busNumber}', '${b.busType}', ${b.capacity})">
                        <i class="bi bi-pencil"></i> Sửa
                    </button>
                    <button class="btn btn-sm btn-outline-danger" onclick="deleteBus(${b.id})">
                        <i class="bi bi-trash"></i> Xóa
                    </button>
                </td>
            </tr>
        `).join("");

        document.getElementById("list-buses").innerHTML = html || '<tr><td colspan="5" class="text-center p-4">Chưa có dữ liệu</td></tr>';
    } catch (e) {
        console.error("Lỗi:", e);
    }
}

// 2. Chuẩn bị Modal khi nhấn "Thêm mới"
function prepareAdd() {
    document.getElementById('modalTitle').innerText = "Thêm phương tiện mới";
    document.getElementById('edit-id').value = "";
    document.getElementById('inp-busNumber').value = "";
    document.getElementById('inp-type').value = "Ghế ngồi";
    document.getElementById('inp-capacity').value = "40";
}

// 3. Mở Modal khi nhấn "Sửa" và đổ dữ liệu
function openEditModal(id, number, type, cap) {
    document.getElementById('modalTitle').innerText = "Cập nhật thông tin xe";
    document.getElementById('edit-id').value = id;
    document.getElementById('inp-busNumber').value = number; 
    document.getElementById('inp-type').value = type;
    document.getElementById('inp-capacity').value = cap;
    
    new bootstrap.Modal(document.getElementById('modalBus')).show();
}

// 4. Lưu thông tin (POST nếu thêm mới, PUT nếu sửa)
async function saveBus() {
    const id = document.getElementById('edit-id').value;
    const data = {
        busNumber: document.getElementById("inp-busNumber").value,
        busType: document.getElementById("inp-type").value,
        capacity: parseInt(document.getElementById("inp-capacity").value)
    };

    if (!data.busNumber) return alert("Vui lòng nhập biển số xe!");

    const method = id ? 'PUT' : 'POST';
    const url = id ? `/api/buses/${id}` : '/api/buses';

    try {
        const res = await fetch(url, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        if (res.ok) {
            bootstrap.Modal.getInstance(document.getElementById('modalBus')).hide();
            loadBuses();
        } else {
            alert("Lỗi hệ thống khi lưu!");
        }
    } catch (e) {
        alert("Không thể kết nối API!");
    }
}

// 5. Xóa xe
async function deleteBus(id) {
    if (confirm("Bạn có chắc chắn muốn xóa xe này?")) {
        await fetch(`/api/buses/${id}`, { method: "DELETE" });
        loadBuses();
    }
}

// Khởi tạo
document.addEventListener("DOMContentLoaded", loadBuses);
document.getElementById("btn-add").addEventListener("click", prepareAdd);
document.getElementById("btn-save").addEventListener("click", saveBus);