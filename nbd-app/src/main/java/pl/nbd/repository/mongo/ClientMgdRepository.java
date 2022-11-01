package pl.nbd.repository.mongo;

import pl.nbd.model.mgd.ClientMgd;
import pl.nbd.repository.AbstractMongoRepository;

public class ClientMgdRepository extends AbstractMongoRepository<ClientMgd> {
    public ClientMgdRepository() {
        super(ClientMgd.class, "clients");
    }
}
