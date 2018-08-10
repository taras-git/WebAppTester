package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.Properties;

import static javax.mail.Flags.Flag.DELETED;
import static utils.Utils.sleep;

public class EmailReader {

    private static final Logger LOG = LoggerFactory.getLogger(EmailReader.class);

    public static void deleteAllMails() {
        Properties props = setImapProps();

        try {
            Folder inbox = getFolder(props);
            deleteMails(inbox);
        } catch (NoSuchProviderException e) {
            System.out.println(e.toString());
            System.exit(1);
        } catch (MessagingException e) {
            System.out.println(e.toString());
            System.exit(2);
        }
    }

    private static void deleteMails(Folder inbox){
        try {
            Message messages[] = inbox.getMessages();
            try {
                inbox.setFlags(messages, new Flags(DELETED), true);
                LOG.info("Marked DELETE for ALL messages" );
            } catch (Exception e) {
                e.printStackTrace();
            }
            // expunges the folder to remove messages which are marked deleted
            inbox.close(true);
            LOG.info("Messages DELETED ");
        } catch (MessagingException e) {
            System.out.println(e.toString());
        }
    }

    private static Folder getFolder(Properties props) throws MessagingException {
        Store store = getStore(props);

        String login = JsonReader.getUserEmail("app2_driver_mail");
        String password = JsonReader.getUserPassword("app2_driver_mail");
        String imap = JsonReader.getUserImap("app2_driver_mail_imap");
        store.connect(imap, login, password);

        Folder inbox = store.getFolder("Inbox");
        inbox.open(Folder.READ_WRITE);
        return inbox;
    }

    private static Store getStore(Properties props) throws NoSuchProviderException {
        Session session = Session.getDefaultInstance(props, null);
        return session.getStore("imaps");
    }

    private static Properties setImapProps() {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        return props;
    }

    private static boolean emailWithSubjectExists(Folder inbox, String subject){
        try {
            Message messages[] = inbox.getMessages();

            for(Message message:messages) {

                try {
                    if (message.getSubject().equalsIgnoreCase(subject)){
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (MessagingException e) {
            System.out.println(e.toString());
        }

        return false;
    }

    public static boolean getBookingConfirmation() {
        Properties props = setImapProps();

        try {
            Folder inbox = getFolder(props);
            return emailWithSubjectExists(inbox, JsonReader.getConfirmationSubject());

        } catch (NoSuchProviderException e) {
            System.out.println(e.toString());
            System.exit(1);
        } catch (MessagingException e) {
            System.out.println(e.toString());
            System.exit(2);
        }

        return false;
    }

    public static void checkConfirmationEmailReceived(){
        //check for confirmation email every 1 second during 3 minutes
        System.out.println("Waiting for confirmation email > ");
        for(int i = 0; i < 180; i++) {
            if (getBookingConfirmation()){
                LOG.info("CONFIRMATION EMAIL FOUND!!!");
                return;
            }
            sleep(1);
            System.out.println(" . ");
        }
        throw new RuntimeException("CONFIRMATION EMAIL NOT FOUND!!!");
    }

    private static void showMails(Folder inbox, boolean showAll){
        try {
            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message messages[] = showAll ? inbox.getMessages() : inbox.search(ft);
            System.out.println("MAILS: " + messages.length);

            for(Message message:messages) {
                try {
                    getMessageInfo(message);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("No Information");
                }
            }
        } catch (MessagingException e) {
            System.out.println(e.toString());
        }
    }

    private static void showUnreadMails(Folder inbox){
        showMails(inbox, false);
    }

    private static void showAllMails(Folder inbox){
        showMails(inbox, true);
    }

    private static void getMessageInfo(Message message) throws MessagingException, IOException {
        System.out.println("DATE: " + message.getSentDate().toString());
        System.out.println("FROM: " + message.getFrom()[0].toString());
        System.out.println("SUBJECT: " + message.getSubject());
        System.out.println("CONTENT: " + message.getContent().toString());
        System.out.println("******************************************");
    }
}
