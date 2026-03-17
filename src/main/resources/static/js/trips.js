async function loadTrips() {
    const res = await fetch('/api/trips');
    const trips = await res.json();
    const list = document.getElementById('trip-list');
    
    list.innerHTML = trips.map(t => `
        <tr>
            <td>${t.route.departureLocation} → ${t.route.arrivalLocation}</td>
            <td>${t.bus.busNumber} (${t.bus.busType})</td>
            <td>${t.departureTime.replace('T', ' ')}</td>
            <td class="text-danger fw-bold">${t.pricePerTicket.toLocaleString()}đ</td>
            <td><button class="btn btn-outline-danger btn-sm">Xóa</button></td>
        </tr>
    `).join('');
}