import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Produtor {
    private static int numeroMensagens = 2000;

    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();
        connection.start();
        System.out.println("Conexão iniciada...");

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination fila = (Destination) context.lookup("vendas");
        MessageProducer producer = session.createProducer(fila);

        for(int numero = 0; numero < numeroMensagens; numero++){
            String text = String.valueOf(numero+1);
            Message message = session.createTextMessage(text);
            System.out.println("Transmitindo mensagem: " + text);
            producer.send(message);
        }

        connection.close();
        context.close();
        System.out.println("Conexão finalizada...");
    }
}
