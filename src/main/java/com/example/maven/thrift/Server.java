package com.example.maven.thrift;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * @author ZhengHao Lou
 * @date 2020/05/18
 */
public class Server {

    public static void main(String[] args) throws TTransportException {
        TNonblockingServerSocket socket = new TNonblockingServerSocket(9999);
        org.apache.thrift.server.THsHaServer.Args args3 = new THsHaServer.Args(socket);
        PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());

        args3.protocolFactory(new TCompactProtocol.Factory());
        args3.transportFactory(new TFramedTransport.Factory());
        args3.processorFactory(new TProcessorFactory(processor));

        THsHaServer server = new THsHaServer(args3);
        server.serve();
    }

}