package pl.nbd.Mapper;

import com.datastax.oss.driver.api.mapper.annotations.*;
import pl.nbd.Dao.ItemDao;

@Mapper
public interface ItemMapper {

    @DaoFactory
    ItemDao itemDao(@DaoKeyspace String keyspace, @DaoTable String table);

    @DaoFactory
    ItemDao itemDao();
}
