package com.example.maven.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;

/**
 * @author ZhengHao Lou
 * @date 2020/05/18
 */
public class Client {

    public static void main(String[] args) throws DataException, TException {
        TFramedTransport tFramedTransport = new TFramedTransport(new TSocket("localhost",9999));
        TProtocol protocol = new TCompactProtocol(tFramedTransport);
        PersonService.Client client = new PersonService.Client(protocol);
        tFramedTransport.open();

        Person person = client.getPersonByName("kyle");
        System.out.println(person);

        Person p = new Person();
        p.setUsername("xiaoming");
        p.setAge(24);
        p.setMarried(false);
        client.savePerson(p);

    }
}