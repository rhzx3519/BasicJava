#!/usr/bin/env bash

FILE="quote.txt"

function get_json_value()
{
  local json=$1
  local key=$2

  local value=$(echo "${json}" | awk -F"[,:}]" '{for(i=1;i<=NF;i++){if($i~/'${key}'\042/){print $(i+1)}}}' | tr -d '"' | sed -n $key)
  echo ${value}
}

function parse_json(){
    echo $1 | sed 's/.*'$2':\([^,}]*\).*/\1/'
}

function foo {
    for((;;))
    do
        echo "{"data":{"askPrice":39.25,"latestPrice":39.25,"symbol":"01347","amount":7.33403512E8,"bidSize":9000,"type":"quote","bidPrice":39.2,"volume":18550158,"high":41.35,"preClose":37.4,"low":37.7,"marketStatus":"交易中","latestTime":"11-30 14:02:34","mi":{"p":39.25,"a":39.545,"t":1606716120000,"v":32000,"h":39.25,"l":39.2},"askSize":1000,"open":37.95,"timestamp":1606716154621},"timestamp":1606716154928}
{"data":{"askPrice":1.95,"symbol":"C38U.SI","bidSize":882300,"type":"quote","askSize":1989700,"bidPrice":1.94,"timestamp":1606716154656},"timestamp":1606716154996}"
    done
}

function handle {
#    jsonStr={"data":{"askPrice":1.95,"latestPrice":39.25,"symbol":"C38U.SI","bidSize":882300,"type":"quote","askSize":1989700,"bidPrice":1.94,"timestamp":1606716154656},"timestamp":1606716154996}
    jsonStr=$1
    latestPrice=$(parse_json $jsonStr symbol)
    echo $latestPrice
}

function func_start {
#    $(/data0/kafka/bin/kafka-console-consumer.sh --bootstrap-server 124.250.34.59:19092 --topic quote) | awk '{print $0}'
#    echo $quotes
#    foo | awk '{print $0}' | sed | parse_json $1 latestPrice
}

func_start