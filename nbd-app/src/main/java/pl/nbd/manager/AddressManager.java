package pl.nbd.manager;

import pl.nbd.model.Address;
import pl.nbd.repository.AddressRepository;

public class AddressManager {

    private AddressRepository addressRepository;

    public AddressManager(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void addAddress(Address address) {
        addressRepository.add(address);
    }

    public void deleteAddress(Address address) {
        addressRepository.remove(address);
    }

  /*  public Address findAddressById() {

    }*/
}
