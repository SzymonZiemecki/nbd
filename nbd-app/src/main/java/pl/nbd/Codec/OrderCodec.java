package pl.nbd.Codec;

import com.datastax.oss.driver.api.core.ProtocolVersion;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodecs;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import pl.nbd.model.domain.Address;
import pl.nbd.model.domain.Order;

import java.nio.ByteBuffer;

public class OrderCodec implements TypeCodec<Order> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @NonNull
    @Override
    public GenericType<Order> getJavaType() {
        return GenericType.of(Order.class);
    }

    @NonNull
    @Override
    public DataType getCqlType() {
        return DataTypes.TEXT;
    }

    @Nullable
    @Override
    public ByteBuffer encode(@Nullable Order order, @NonNull ProtocolVersion protocolVersion) {
        String str = null;
        try {
            str = mapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return TypeCodecs.TEXT.encode(str, ProtocolVersion.DEFAULT);
    }

    @Nullable
    @Override
    public Order decode(@Nullable ByteBuffer byteBuffer, @NonNull ProtocolVersion protocolVersion) {
        String str = TypeCodecs.TEXT.decode(byteBuffer, ProtocolVersion.DEFAULT);
        try {
            return mapper.readValue(str, Order.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    @Override
    public String format(@Nullable Order order) {
        try {
            return mapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    @Override
    public Order parse(@Nullable String s) {
        try {
            return mapper.readValue(s, Order.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
