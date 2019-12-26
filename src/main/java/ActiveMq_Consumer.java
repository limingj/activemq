import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMq_Consumer {
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
        // 4.1是否开启事务  默认 false
        //4.2签收模式  手动签收：CLIENT_ACKNOWLEDGE  偏消费者  textMessage.acknowledge();
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        //Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
        //（5）获得目的地  队列名
        //（1）点对点 队列 Queue  （2）发布/订阅  Topic
        Queue queue = session.createQueue(QUEUE_NAEM);
        //（6）获得消费者 从哪获得  从哪消费
        MessageConsumer consumer = session.createConsumer(queue);
        //（7）处理消息
        while (true){
                //用户发的是什么消息类型，就要读什么类型
            TextMessage textMessage  = (TextMessage) consumer.receive();
            if(textMessage !=null){
                System.out.println("******"+textMessage.getText());
                textMessage.acknowledge();
            }else
            {
                break;
            }
        }
    }
}
