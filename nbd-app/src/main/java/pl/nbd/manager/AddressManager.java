package pl.nbd.manager;

import pl.nbd.model.Address;
import pl.nbd.repository.AddressRepository;

import java.util.List;

public class AddressManager {

    private AddressRepository addressRepository;

    public AddressManager(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address addAddress(Address address) {
        return addressRepository.add(address);
    }

    public Address updateAddress(Address address){
        return addressRepository.update(address);
    }

    public void deleteAddress(Address address) {
        addressRepository.remove(address);
    }

    public List<Address> findAllAddresses(){
        return addressRepository.findAll();
    }

    public Address findAddressById(Long id){
        return addressRepository.findById(id);
    }
}
