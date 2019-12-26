import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMq_Producer {
    public static final String MQ_URL="tcp://192.168.81.129:61616";
    public static final String QUEUE_NAEM="queue0805";
    public static void main(String[] args) throws JMSException {
        //(1)创建ActiveMQConnectionFactory工厂(参数，看源码)
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URL);
        //(2)通过ActiveMQConnectionFactory创建连接Connection
        Connection connection = activeMQConnectionFactory.createConnection();
        //（3）启动连接Connection.start
        connection.start();
        //（4）创建session  两个参数
        //Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4.1是否开启事务  默认 false
           //开启事务  session提交之前提交session.commit();  偏生产者
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
           //4.2签收模式  默认自动签收 Session.AUTO_ACKNOWLEDGE  手动签收：CLIENT_ACKNOWLEDGE
        //Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
        //（5）获得目的地  队列名
        Queue queue = session.createQueue(QUEUE_NAEM);
        //（1）点对点 队列 Queue
             //（2）发布/订阅  Topic
        //（6）获得消息生产者 在哪获得   在哪生产
        MessageProducer producer = session.createProducer(queue);
        //消息持久性   服务器宕机  消息是否还在  默认 持久persistent
        //producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //（7）生产消息
        for (int i = 1; i < 3; i++) {
            //（1）生产者生产消息
            TextMessage textMessage = session.createTextMessage("*******msg"+i);
            //(2)生产者发送消息
            producer.send(textMessage);
        }
        //（8）关闭连接
        producer.close();
        session.commit();
        session.close();
        connection.close();
        System.out.println("********* msg successful");
    }
}
