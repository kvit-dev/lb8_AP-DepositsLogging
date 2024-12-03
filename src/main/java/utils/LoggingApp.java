package utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.*;

public class LoggingApp {
    private static final Logger logger = Logger.getLogger(LoggingApp.class.getName());
    static {
        try {
            logger.setUseParentHandlers(false);

            FileHandler fileHandler = new FileHandler("application.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.SEVERE);
            logger.addHandler(consoleHandler);

            SMTPHandler emailHandler = new SMTPHandler(
                    "smtp.gmail.com",
                    "",
                    "",
                    ""
            );
            emailHandler.setLevel(Level.SEVERE);
            logger.addHandler(emailHandler);
            //logger.removeHandler(emailHandler);

            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Logging error: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public static class SMTPHandler extends Handler {
        private final String host;
        private final String from;
        private final String password;
        private final String to;

        public SMTPHandler(String host, String from, String password, String to) {
            this.host = host;
            this.from = from;
            this.password = password;
            this.to = to;
        }

        @Override
        public void publish(LogRecord record) {
            if (record.getLevel().intValue() >= Level.SEVERE.intValue()) {
                try {
                    sendEmail(record);
                } catch (MessagingException e) {
                    System.err.println("Email could not be sent: " + e.getMessage());
                }
            }
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() throws SecurityException {
        }

        private void sendEmail(LogRecord record) throws MessagingException {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Critical error in the program");
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(record.getMillis()));
            message.setText("Error message: " + record.getMessage() + "\n" +
                    "Date: " + formattedDate);
            Transport.send(message);
            System.out.println("The email has been sent successfully");
        }
    }
}