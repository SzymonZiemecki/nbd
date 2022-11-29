package pl.nbd.model.mapper;

import pl.nbd.model.domain.Client;
import pl.nbd.model.domain.Item;
import pl.nbd.model.domain.Laptop;
import pl.nbd.model.domain.Monitor;
import pl.nbd.model.mgd.ClientMgd;
import pl.nbd.model.mgd.ItemMgd;
import pl.nbd.model.mgd.LaptopMgd;
import pl.nbd.model.mgd.MonitorMgd;
import pl.nbd.model.redis.ItemJson;
import pl.nbd.model.redis.LaptopJson;
import pl.nbd.model.redis.MonitorJson;
import pl.nbd.repository.redis.ItemRedisRepository;

import javax.swing.text.Document;
import java.util.UUID;

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

    public static ItemJson toRedisDocument(Item item){
        if(item instanceof Laptop){
            return laptopToRedisDocument((Laptop) item);
        }
        else if(item instanceof Monitor){
            return monitorToRedisDocument((Monitor) item);
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

    public static Item toDomainModel(ItemJson item){
        if(item instanceof LaptopJson){
            return laptopToDomainModel((LaptopJson) item);
        }
        else if(item instanceof MonitorJson){
            return monitorToDomainModel((MonitorJson) item);
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

    public static ItemJson laptopToRedisDocument(Laptop item){
        if (item.getUniqueId() == null) {
            item.setUniqueId(UUID.randomUUID());
        }
        return LaptopJson.builder()
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

    public static ItemJson monitorToRedisDocument(Monitor item){
        if (item.getUniqueId() == null) {
            item.setUniqueId(UUID.randomUUID());
        }
        return MonitorJson.builder()
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

    public static Item laptopToDomainModel(LaptopJson item){
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

    public static Item monitorToDomainModel(MonitorJson item){
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
