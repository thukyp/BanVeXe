describe('Hệ thống Bán Vé Xe - Kiểm thử Đăng ký', () => {
    
    // Trước mỗi test case, truy cập vào trang đăng ký
    beforeEach(() => {
        // Lưu ý: Thay localhost:8080 bằng port thực tế của Spring Boot
        cy.visit('http://localhost:8080/register'); 
    });

    // 1. Kiểm tra đăng ký thành công (Dùng dữ liệu ngẫu nhiên để tránh trùng)
    it('1. Đăng ký tài khoản mới thành công', () => {
        const randomId = Math.floor(Math.random() * 10000);
        const username = `thuky_${randomId}`;

        cy.get('input[name="fullName"]').type('Phan Thư Kỳ');
        cy.get('input[name="username"]').type(username);
        cy.get('input[name="email"]').type(`${username}@gmail.com`);
        cy.get('input[name="phone"]').type('0987654321');
        cy.get('input[name="password"]').type('12345678');
        
        cy.get('button[type="submit"]').click();

        // Kiểm tra xem có chuyển hướng về trang login không
        cy.url().should('include', '/login');
    });

    // 2. Kiểm tra bỏ trống các trường (HTML5 Validation)
    it('2. Báo lỗi khi để trống họ tên', () => {
        cy.get('button[type="submit"]').click();

        // Kiểm tra xem trình duyệt có ngăn chặn và báo lỗi 'required' không
        cy.get('input[name="fullName"]').then(($el) => {
            const el = $el[0];
            expect(el.validity.valueMissing).to.be.true;
        });
    });

    // 3. Kiểm tra định dạng Email không hợp lệ
    it('3. Báo lỗi khi nhập Email sai định dạng', () => {
        cy.get('input[name="email"]').type('email-khong-hop-le');
        cy.get('button[type="submit"]').click();

        cy.get('input[name="email"]').then(($el) => {
            const el = $el[0];
            expect(el.validity.typeMismatch).to.be.true;
        });
    });


    // 5. Kiểm tra logic phía Server (Ví dụ: Trùng username)
    it('4. Hiển thị thông báo khi tên tài khoản đã tồn tại', () => {
        // Nhập username mà bạn đã đăng ký ở Case 1 hoặc admin
        cy.get('input[name="fullName"]').type('Phan Thư Kỳ');
        cy.get('input[name="username"]').type('admin'); 
        cy.get('input[name="email"]').type('admin@gmail.com');
        cy.get('input[name="phone"]').type('0000000000');
        cy.get('input[name="password"]').type('12345678');

        cy.get('button[type="submit"]').click();

        // Kiểm tra thông báo lỗi từ Thymeleaf trả về (class .alert)
        cy.get('.alert').should('be.visible');
    });
});