package base.api.payment.entity;

public enum PaymentStatus {
    PAYMENT_PENDING, // Mới tạo, chưa được thanh toán
    PROCESSING, // Đã thanh toán, đang chờ xử lý
    SUCCEEDED, // thành công
    CANCELED; // thất bại

    // Check chuyển trạng thái payment
    public boolean canTransitionFrom(PaymentStatus old) {
        if (this.equals(PAYMENT_PENDING)) {
            return false;
        }
        if (this.equals(PROCESSING)) {
            return old.equals(PAYMENT_PENDING);
        }
        if (this.equals(SUCCEEDED)) {
            return old.equals(PAYMENT_PENDING) || old.equals(PROCESSING);
        }
        if (this.equals(CANCELED)) {
            return old.equals(PAYMENT_PENDING) || old.equals(PROCESSING);
        }
        return false;
    }
}
