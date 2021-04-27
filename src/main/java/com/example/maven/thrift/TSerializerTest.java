package com.example.maven.thrift;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.junit.Test;

/**
 * @author ZhengHao Lou
 * @date 2021/01/12
 */
@Slf4j
public class TSerializerTest {

    @Test
    public void test1() throws TException {
        Person person = new Person();
        person.setAge(10);
        person.setUsername("Jim");
        person.setMarried(false);
        TSerializer serializer = new TSerializer(new TJSONProtocol.Factory());
        byte[] bytes = serializer.serialize(person);
        String str = serializer.toString(person);
        log.info(str);

        Person newPerson = new Person();
        TDeserializer deserializer = new TDeserializer(new TJSONProtocol.Factory());
        deserializer.deserialize(newPerson, bytes);
        log.info(newPerson.toString());
    }

}
