package pl.nbd.manager;

import pl.nbd.model.Address;

import java.util.List;

public interface AddressManager {
    Address addAddress(Address address);

    Address updateAddress(Address address);

    void deleteAddress(Address address);

    List<Address> findAllAddresses();

    Address findAddressById(Long id);
}
