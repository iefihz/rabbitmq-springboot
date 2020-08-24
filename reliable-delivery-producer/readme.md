RabbitMQ整合SpringBoot实现可靠性投递--生产者

整体流程：

1. 这里以Order为例，创建Order类，这个类必须包含messageId属性，这个属性作为
mq发送记录的id，需要全局唯一，可以通过继承基础类BaseMessage（包含messageId）。

2. 生产者端：
请求-->controller组装完order(生成messageId)-->OrderService中持久化order、
持久化BrokerMessageLog(状态为发送中)、投递order-->生产者收到broker的ack后，
将log状态改为发送成功-->任务监听已过期且状态为发送中的log，超过3次的，设为发
送失败，反之重发

3. 消费者端：不同消息，创建不同消费者（不同方法）去操作，具体看代码

4. 消息幂等性：消费者收到消息时，先把log的id存到一个表中，作为主键，如果有异常，并
异常为org.springframework.dao.DuplicateKeyException，说明消息重复了，直接ack即可，
如果不是这个异常，则需要其他补偿机制。例如：重新投递之类、人工处理等等

5. 步骤1中Order类创建的3种方式：
        
        i.直接在Order类中包含messageId属性(监听任务需要改，log持久化代码不能复用)，实例：OrderController
        ii.通过继承（推荐，消费者改动少，监听任务不需要改，log持久化的代码可以复用）,实例：UserController
        iii.通过包含（消费者改动少，监听任务不需要改，log持久化的代码可以复用）,实例：BookController