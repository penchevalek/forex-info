# Forex info application

Application gets information about currency rates from fixer.io and stores it.

# Endpoints

Information can be retrieved like json or xml. <br/>
One option is to get current currency rates or for some period. <br/>

##### attributes
"requestId" should be unique <br/>
"currency" - at this moment only euro("EUR") is supported <br/>
"period" is measured in hours till now <br/>

* JSON endpoint

#### /forex_info/json_api/current

{ <br/>
&emsp; "requestId": "string", <br/>
&emsp; "timestamp": "integer", <br/>
&emsp; "client": "string", <br/>
&emsp; "currency": "string" <br/>
}

#### /forex_info/json_api/history

{ <br/>
&emsp; "requestId": "string", <br/>
&emsp; "timestamp": "integer", <br/>
&emsp; "client": "string", <br/>
&emsp; "currency": "string", <br/>
&emsp; "period": "integer" <br/>
}

* XML endpoint

#### /forex_info/xml_api/command

&lt;command id="string"&gt; <br/>
&emsp;&emsp;  &lt;get consumer="string"&gt; <br/>
&emsp;&emsp;&emsp; &emsp;    &lt;currency&gt;string&lt;/currency&gt; <br/>
&emsp;&emsp;   &lt;/get&gt; <br/>
&lt;/command&gt; <br/>

#### /forex_info/xml_api/command

&lt;command id="string"&gt; <br/>
&emsp;&emsp; &lt;history &nbsp; consumer="string" &nbsp; currency="string" &nbsp; period="integer" /&gt; <br/>
&lt;/command&gt; <br/>


## Prerequisites

You need to have installed: <br/> <br/>
Java 11 <br/>
Maven <br/>
Postgres <br/>
Redis <br/>
RabbitMQ <br/>

author: <br/>
Aleksandar Penchev