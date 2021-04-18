import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Consumidor {

    public static void main(String[] args) throws JMSException, NamingException {

        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();
        connection.start();
        System.out.println("Execução iniciada...");

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination fila = (Destination) context.lookup("vendas");
        MessageConsumer consumer = session.createConsumer(fila);

        do{
            System.out.println("Aguardando mensagem...");
            Message message = consumer.receive();
            //message.acknowledge();
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.println("Mensagem recebida: " + text);


        } while(true);

        //connection.close();
        //context.close();
        //System.out.println("Execução finalizada...");
    }
}
