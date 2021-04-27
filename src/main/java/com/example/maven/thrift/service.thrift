//名称空间，用来隔离代码，防止数据类型定义中名字冲突
namespace java netty_thrift

//引入其他thrift文件
//include "test.thrift"

/**
数据类型定义，通过typedef将thrift中的类型和java中的类型对应起来，然后直接使用java中的数据类型，方便理解
下面就是thrift中使用的基本数据类型
*/
typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean
typedef string String

//定义常量
//const int CONSTANT_A = 12345

/**
定义结构
1.每个属性都有唯一一个正整数标识符
2.每个属性都可以标记为optional和required
3.每个结构体都可以包含其他结构体
4.每个属性都有默认值
5.
*/
struct Person{
    1: optional String username
    2: optional int age
    3: optional boolean married
}


//异常定义
exception DataException{
    1: optional String message
    2: optional String callStack
    3: optional String date
}

//服务定义
com.example.maven.service PersonService{

    Person getPersonByName(1: required String username) throws (1: DataException dataException),

    void savePerson(1: required Person person)
}



enum TweetType {//仅仅是作为一个例子，后面生成代码时没有用到

TWEET,         // 编译器默认从1开始赋值
RETWEET = 2,  // 可以赋予某个常量某个整数
DM = 0xa,     //允许常量是十六进制整数
REPLY         // 末尾没有逗号
}