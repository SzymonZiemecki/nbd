package pl.nbd.manager;

import pl.nbd.model.Address;

import java.util.List;

public interface AddressManager {
    public Address addAddress(Address address);

    public Address updateAddress(Address address);

    public void deleteAddress(Address address);

    public List<Address> findAllAddresses();

    public Address findAddressById(Long id);
}
