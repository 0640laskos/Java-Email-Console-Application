import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

class ListOfEmails {
    private List<Email> emailList = new LinkedList<>();

    public void add(Email email) {
        emailList.add(email);
    }

    public Email read(int id) {
        for (Email email : emailList) {
            if (email.getId() == id) {
                email.markAsRead();
                return email;
            }
        }
        return null;
    }

    public Email delete(int id) {
        ListIterator<Email> iterator = emailList.listIterator();
        while (iterator.hasNext()) {
            Email email = iterator.next();
            if (email.getId() == id) {
                iterator.remove();
                return email;
            }
        }
        return null;
    }

    public List<Email> getAllEmails() {
        return emailList;
    }

    public List<Email> getUnreadEmails() {
        List<Email> unreadEmails = new LinkedList<>();
        for (Email email : emailList) {
            if (!email.isRead()) {
                unreadEmails.add(email);
            }
        }
        return unreadEmails;
    }

    public void clear() {
        emailList.clear();
    }
}