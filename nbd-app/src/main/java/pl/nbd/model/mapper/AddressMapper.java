package pl.nbd.model.mapper;

import pl.nbd.model.domain.Address;
import pl.nbd.model.domain.Client;
import pl.nbd.model.mgd.AddressMgd;
import pl.nbd.model.mgd.ClientMgd;

public class AddressMapper {
    public static AddressMgd toMongoDocument(Address address){
        return AddressMgd.builder()
                .uniqueId(address.getUniqueId())
                .city(address.getCity())
                .street(address.getStreet())
                .localNumber(address.getLocalNumber())
                .build();
    }

    public static Address toDomainModel(AddressMgd address){
        return Address.builder()
                .uniqueId(address.getUniqueId())
                .city(address.getCity())
                .street(address.getStreet())
                .localNumber(address.getLocalNumber())
                .build();
    }
}
