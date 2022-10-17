package pl.nbd.manager.impl;

import pl.nbd.manager.AddressManager;
import pl.nbd.model.Address;
import pl.nbd.repository.AddressRepository;

import java.util.List;

public class AddressManagerImpl implements AddressManager {

    private AddressRepository addressRepository;

    public AddressManagerImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address addAddress(Address address) {
        return addressRepository.add(address);
    }

    @Override
    public Address updateAddress(Address address) {
        return addressRepository.update(address);
    }

    @Override
    public void deleteAddress(Address address) {
        addressRepository.remove(address);
    }

    @Override
    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address findAddressById(Long id) {
        return addressRepository.findById(id);
    }
}
