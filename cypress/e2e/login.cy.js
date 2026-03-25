describe("Kiểm thử chức năng Đăng nhập - BANVEXE", () => {
  beforeEach(() => {
    // Thay đổi URL này cho đúng với port bạn đang chạy (ví dụ: localhost:8080)
    cy.visit("http://localhost:8080/login");
  });

  it("TC01: Kiểm tra giao diện trang đăng nhập hiển thị đầy đủ", () => {
    cy.get("h2").should("contain", "Đăng nhập");
    cy.get('input[name="username"]').should("be.visible");
    cy.get('input[name="password"]').should("be.visible");
    cy.contains('button', 'Đăng nhập').click()
    cy.get(".btn-google").should("exist");
  });

  it("TC02: Đăng nhập thất bại - Sai thông tin", () => {
    cy.get('input[name="username"]').type("thukyp");
    cy.get('input[name="password"]').type("123456");
    cy.get('button[type="submit"]').click();

    // Kiểm tra thông báo lỗi (dựa trên class .alert của bạn)
    cy.get(".alert")
      .should("be.visible")
      .and("contain", "Tên đăng nhập hoặc mật khẩu không đúng!");
  });

  it("TC03: Đăng nhập thành công", () => {
    // Đảm bảo username và password này đúng với DB của bạn
    cy.get('input[name="username"]').type("user7@gmail");
    cy.get('input[name="password"]').type("user7@gmail");

    cy.get('button[type="submit"]').click();

    // Kiểm tra xem có chuyển hướng về trang chủ (/) không
    cy.url().should("eq", "http://localhost:8080/");
  });

  it("TC04: Kiểm tra tính năng chuyển hướng trang Đăng ký", () => {
    // Click trực tiếp vào thẻ link trong div class link
    cy.get(".link a").click();

    // Đợi một chút để trang kịp load (timeout)
    cy.url({ timeout: 1000 }).should("include", "/register");
  });
});
