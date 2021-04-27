package com.example.maven.thrift;

import org.apache.thrift.TException;

/**
 * @author ZhengHao Lou
 * @date 2020/05/18
 */
public class PersonServiceImpl implements PersonService.Iface {

    @Override
    public Person getPersonByName(String username) throws DataException, TException {
        Person p = new Person();
        p.setUsername(username);
        p.setAge(28);
        p.setMarried(false);
        return p;
    }

    @Override
    public void savePerson(Person person) throws TException {
        System.out.println(person);
    }

}