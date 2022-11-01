package pl.nbd.repository.mongo;

import pl.nbd.model.mgd.OrderMgd;
import pl.nbd.repository.AbstractMongoRepository;

public class OrderMgdRepository extends AbstractMongoRepository<OrderMgd> {
    public OrderMgdRepository() {
        super(OrderMgd.class, "orders");
    }
}
