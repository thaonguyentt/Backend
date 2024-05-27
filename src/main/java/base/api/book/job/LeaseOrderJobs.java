package base.api.book.job;

import base.api.book.service.LeaseOrderService;
import base.api.system.security.Identity;
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
        Identity identity = Identity.SYSTEM;
        leaseOrderService.setStatusOnLateReturnOrder(identity);
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void cancelLatePaymentOrders() {
        Identity identity = Identity.SYSTEM;
        leaseOrderService.cancelOrderOnLatePayment(identity);
    }


//    @Scheduled(cron = "0 */1 * * * ?")
//    public void chargeLateFees () {leaseOrderService.chargeLateFees();}
}
