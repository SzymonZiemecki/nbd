package pl.nbd.repository.mongo;

import pl.nbd.model.mgd.ItemMgd;
import pl.nbd.repository.AbstractMongoRepository;

public class ItemMgdRepository extends AbstractMongoRepository<ItemMgd> {
    public ItemMgdRepository() {
        super(ItemMgd.class, "items");
    }
}
