document.addEventListener('DOMContentLoaded', function() {
    // 1. Tự động tải dữ liệu khi mở trang
    fetchRoutes();

    // 2. Xử lý sự kiện nhấn nút Lưu
    document.getElementById('btnSaveRoute').addEventListener('click', saveRoute);
});

// Hàm lấy danh sách từ API
async function fetchRoutes() {
    try {
        const response = await fetch('/api/routes');
        const data = await response.json();
        
        const tbody = document.getElementById('tbody-routes');
        tbody.innerHTML = ''; // Xóa trắng bảng trước khi nạp

        if (data.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" class="text-center p-4 text-muted">Chưa có tuyến đường nào.</td></tr>';
            return;
        }

        data.forEach(route => {
            const row = `
                <tr>
                    <td class="ps-4 text-muted">${route.id}</td>
                    <td class="fw-bold text-primary">${route.departure || 'Không xác định'}</td>
                    <td class="fw-bold text-success">${route.destination || 'Không xác định'}</td>
                    <td>${new Intl.NumberFormat('vi-VN').format(route.price)} đ</td>
                    <td class="text-center">
                        <button class="btn btn-outline-danger btn-sm" onclick="deleteRoute(${route.id})">
                            <i class="bi bi-trash"></i> Xóa
                        </button>
                    </td>
                </tr>
            `;
            tbody.innerHTML += row;
        });
    } catch (error) {
        console.error('Lỗi khi tải danh sách tuyến:', error);
        alert('Không thể kết nối đến server!');
    }
}

// Hàm thêm mới tuyến xe
async function saveRoute() {
    const departure = document.getElementById('route-departure').value;
    const destination = document.getElementById('route-destination').value;
    const price = document.getElementById('route-price').value;

    if (!departure || !destination || !price) {
        alert('Vui lòng nhập đầy đủ thông tin!');
        return;
    }

    const newRoute = {
        departure: departure,
        destination: destination,
        price: parseFloat(price)
    };

    try {
        const response = await fetch('/api/routes', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newRoute)
        });

        if (response.ok) {
            // Đóng Modal
            const modalElement = document.getElementById('modalRoute');
            const modal = bootstrap.Modal.getInstance(modalElement);
            modal.hide();

            // Reset Form và tải lại bảng
            document.getElementById('formRoute').reset();
            fetchRoutes();
        } else {
            const errMsg = await response.text();
            alert('Lỗi: ' + errMsg);
        }
    } catch (error) {
        console.error('Lỗi khi lưu tuyến:', error);
    }
}

// Hàm xóa tuyến xe
async function deleteRoute(id) {
    if (confirm('Bạn có chắc chắn muốn xóa tuyến này? Thao tác này không thể hoàn tác.')) {
        try {
            const response = await fetch(`/api/routes/${id}`, { method: 'DELETE' });
            if (response.ok) {
                fetchRoutes(); // Tải lại bảng
            } else {
                alert('Không thể xóa! Tuyến này có thể đang được sử dụng trong một Chuyến xe.');
            }
        } catch (error) {
            console.error('Lỗi khi xóa:', error);
        }
    }
}