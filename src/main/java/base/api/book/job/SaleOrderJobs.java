package base.api.book.job;

import base.api.book.service.LeaseOrderService;
import base.api.book.service.SaleOrderService;
import base.api.system.security.Identity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SaleOrderJobs {

    private final SaleOrderService saleOrderService;


    public SaleOrderJobs(SaleOrderService saleOrderService) {
        this.saleOrderService = saleOrderService;
    }



    @Scheduled(cron = "0 */1 * * * ?")
    public void cancelLatePaymentOrders() {
        Identity identity = Identity.SYSTEM;
        saleOrderService.cancelOrderOnLatePayment(identity);
        saleOrderService.cancelOrderOnLatePayment(identity);
    }




//    @Scheduled(cron = "0 */1 * * * ?")
//    public void chargeLateFees () {leaseOrderService.chargeLateFees();}
}
