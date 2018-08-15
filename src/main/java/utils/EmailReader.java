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

    public static void deleteAllMails() throws MessagingException {
        Properties props = setImapProps();
        Folder inbox = getFolder(props);

        deleteMails(inbox);
    }

    private static void deleteMails(Folder inbox) throws MessagingException {
        Message messages[] = inbox.getMessages();
        inbox.setFlags(messages, new Flags(DELETED), true);
        LOG.info("Marked DELETE for ALL messages" );
        inbox.close(true);
        LOG.info("Messages DELETED ");
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

    private static boolean emailWithSubjectExists(Folder inbox, String subject) throws MessagingException {
        Message messages[] = inbox.getMessages();

        for(Message message:messages)
            if (message.getSubject().equalsIgnoreCase(subject)) return true;

        return false;
    }

    private static boolean emailWithSubjectExists(Folder inbox, String... subjects) throws MessagingException {
        Message messages[] = inbox.getMessages();

        for(Message message:messages)
            for (String subject : subjects)
                if (message.getSubject().equalsIgnoreCase(subject)) return true;

        return false;
    }


    public static boolean getBookingConfirmation() throws MessagingException {
        Properties props = setImapProps();
        Folder inbox = getFolder(props);

        return emailWithSubjectExists(inbox,
                JsonReader.getConfirmationSubjectEN(),
                JsonReader.getConfirmationSubjectDE());
    }

    public static void checkConfirmationEmailReceived() throws MessagingException {
        //check for confirmation email every 1 second during 3 minutes
        LOG.info("Waiting for confirmation email > ");

        for(int i = 0; i < 180; i++) {
            if (getBookingConfirmation()){
                LOG.info("CONFIRMATION EMAIL FOUND!");
                return;
            }
            sleep(1);
        }
        throw new RuntimeException("CONFIRMATION EMAIL NOT FOUND!");
    }

    private static void showMails(Folder inbox, boolean showAll) throws IOException, MessagingException {
        FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
        Message messages[] = showAll ? inbox.getMessages() : inbox.search(ft);
        LOG.info("MAILS: " + messages.length);

        for(Message message:messages) {
            getMessageInfo(message);
        }
    }

    private static void showUnreadMails(Folder inbox) throws IOException, MessagingException {
        showMails(inbox, false);
    }

    private static void showAllMails(Folder inbox) throws IOException, MessagingException {
        showMails(inbox, true);
    }

    private static void getMessageInfo(Message message) throws MessagingException, IOException {
        LOG.info("DATE: " + message.getSentDate().toString());
        LOG.info("FROM: " + message.getFrom()[0].toString());
        LOG.info("SUBJECT: " + message.getSubject());
        LOG.info("CONTENT: " + message.getContent().toString());
        LOG.info("******************************************");
    }
}
