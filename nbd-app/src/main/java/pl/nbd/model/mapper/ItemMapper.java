package pl.nbd.model.mapper;

import pl.nbd.model.domain.Client;
import pl.nbd.model.domain.Item;
import pl.nbd.model.domain.Laptop;
import pl.nbd.model.domain.Monitor;
import pl.nbd.model.mgd.ClientMgd;
import pl.nbd.model.mgd.ItemMgd;
import pl.nbd.model.mgd.LaptopMgd;
import pl.nbd.model.mgd.MonitorMgd;

import javax.swing.text.Document;

public class ItemMapper {

    public static ItemMgd toMongoDocument(Item item){
        if(item instanceof Laptop){
            return laptopToMongoDocument((Laptop) item);
        }
        else if(item instanceof Monitor){
            return monitorToMongoDocument((Monitor) item);
        }
        return null;
    }

    public static Item toDomainModel(ItemMgd item){
        if(item instanceof LaptopMgd){
            return laptopToDomainModel((LaptopMgd) item);
        }
        else if(item instanceof MonitorMgd){
            return monitorToDomainModel((MonitorMgd) item);
        }
        return null;
    }

    public static ItemMgd laptopToMongoDocument(Laptop item){
        return LaptopMgd.builder()
                .uniqueId(item.getUniqueId())
                .availableAmount(item.getAvailableAmount())
                .name(item.getName())
                .producer(item.getProducer())
                .description(item.getDescription())
                .price(item.getPrice())
                .cpu(item.getCpu())
                .ramAmount(item.getRamAmount())
                .memoryAmount(item.getMemoryAmount())
                .build();
    }

    public static ItemMgd monitorToMongoDocument(Monitor item){
        return MonitorMgd.builder()
                .uniqueId(item.getUniqueId())
                .availableAmount(item.getAvailableAmount())
                .name(item.getName())
                .producer(item.getProducer())
                .description(item.getDescription())
                .price(item.getPrice())
                .resolution(item.getResolution())
                .panel(item.getPanel())
                .diagonal(item.getDiagonal())
                .build();
    }

    public static Item laptopToDomainModel(LaptopMgd item){
        return Laptop.builder()
                .uniqueId(item.getUniqueId())
                .availableAmount(item.getAvailableAmount())
                .name(item.getName())
                .producer(item.getProducer())
                .description(item.getDescription())
                .price(item.getPrice())
                .cpu(item.getCpu())
                .ramAmount(item.getRamAmount())
                .memoryAmount(item.getMemoryAmount())
                .build();
    }

    public static Item monitorToDomainModel(MonitorMgd item){
        return Monitor.builder()
                .uniqueId(item.getUniqueId())
                .availableAmount(item.getAvailableAmount())
                .name(item.getName())
                .producer(item.getProducer())
                .description(item.getDescription())
                .price(item.getPrice())
                .resolution(item.getResolution())
                .panel(item.getPanel())
                .diagonal(item.getDiagonal())
                .build();
    }

}
