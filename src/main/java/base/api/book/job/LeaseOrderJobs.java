package base.api.book.job;

import base.api.book.service.LeaseOrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LeaseOrderJobs {

    private final LeaseOrderService leaseOrderService;

    public LeaseOrderJobs(LeaseOrderService leaseOrderService) {
        this.leaseOrderService = leaseOrderService;
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void updateLeaseOrderStatusLateReturn() {
        leaseOrderService.setStatusOnLateReturnOrder();
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void cancelLatePaymentOrders() {
        leaseOrderService.cancelOrderOnLatePayment();
    }


//    @Scheduled(cron = "0 */1 * * * ?")
//    public void chargeLateFees () {leaseOrderService.chargeLateFees();}
}
