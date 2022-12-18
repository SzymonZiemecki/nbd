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

import java.nio.ByteBuffer;

public class AddressCodec implements TypeCodec<Address> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @NonNull
    @Override
    public GenericType<Address> getJavaType() {
        return GenericType.of(Address.class);
    }

    @NonNull
    @Override
    public DataType getCqlType() {
        return DataTypes.ASCII;
    }

    @Nullable
    @Override
    public ByteBuffer encode(@Nullable Address address, @NonNull ProtocolVersion protocolVersion) {
        String str = null;
        try {
            str = mapper.writeValueAsString(address);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return TypeCodecs.ASCII.encode(str, ProtocolVersion.DEFAULT);
    }

    @Nullable
    @Override
    public Address decode(@Nullable ByteBuffer byteBuffer, @NonNull ProtocolVersion protocolVersion) {
        String str = TypeCodecs.ASCII.decode(byteBuffer, ProtocolVersion.DEFAULT);
        try {
            return mapper.readValue(str, Address.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    @Override
    public String format(@Nullable Address address) {
        try {
            return mapper.writeValueAsString(address);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    @Override
    public Address parse(@Nullable String s) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(s, Address.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
