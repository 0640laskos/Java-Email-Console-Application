import java.util.*;


class EmailApplication {
    private ListOfEmails inbox = new ListOfEmails();
    private ListOfEmails archive = new ListOfEmails();
    private ListOfEmails trash = new ListOfEmails();

    public void processInput(String input) {
        String[] tokens = input.split(" ");
        String command = tokens[0];

        switch (command) {
            case "N":
                // New email arrived
                String subject = tokens[1];
                int id = Integer.parseInt(tokens[2]);
                String message = tokens[3];
                long time = Long.parseLong(tokens[4]);
                Email newEmail = new Email(subject, id, message, time);
                inbox.add(newEmail);
                break;

            case "R":
                // Read an email
                int emailId = Integer.parseInt(tokens[1]);
                Email readEmail = inbox.read(emailId);
                if (readEmail != null) {
                    System.out.println("Email id: " + readEmail.getId());
                    System.out.println("Subject: " + readEmail.getSubject());
                    System.out.println("Body: " + readEmail.getMessage());
                    System.out.println("Time received: " + readEmail.getTime());
                    System.out.println("Status: " + readEmail.getStatus());
                } else {
                    System.out.println("No such email.");
                }
                break;

            case "A":
                // Archive an email
                int archiveId = Integer.parseInt(tokens[1]);
                Email archivedEmail = inbox.delete(archiveId);
                if (archivedEmail != null) {
                    archive.add(archivedEmail);
                    System.out.println("Email " + archiveId + " archived.");
                }
                break;

            case "D":
                // Delete an email
                int deleteId = Integer.parseInt(tokens[1]);
                Email deletedEmail = inbox.delete(deleteId);
                if (deletedEmail != null) {
                    trash.add(deletedEmail);
                    System.out.println("Email " + deleteId + " is deleted.");
                }
                break;

            case "S":
                // Show the contents of a folder
                String folder = tokens[1];
                ListOfEmails targetFolder = getFolderByName(folder);
                if (targetFolder != null) {
                    printEmails(targetFolder.getAllEmails());
                }
                break;

            case "U":
                // Show all unread emails in a folder
                String unreadFolder = tokens[1];
                ListOfEmails unreadTargetFolder = getFolderByName(unreadFolder);
                if (unreadTargetFolder != null) {
                    printEmails(unreadTargetFolder.getUnreadEmails());
                }
                break;

            case "C":
                // Clear the contents of a folder
                String clearFolder = tokens[1];
                ListOfEmails clearTargetFolder = getFolderByName(clearFolder);
                if (clearFolder.equals("Trash")) {
                    clearTargetFolder.clear();
                } else if (clearTargetFolder != null) {
                    for (Email email : clearTargetFolder.getAllEmails()) {
                        trash.add(email);
                    }
                    clearTargetFolder.clear();
                }
                break;

            default:
                System.out.println("Invalid command: " + command);
                break;
        }
    }

    private ListOfEmails getFolderByName(String folderName) {
        switch (folderName) {
            case "Inbox":
                return inbox;
            case "Archive":
                return archive;
            case "Trash":
                return trash;
            default:
                System.out.println("Invalid folder name: " + folderName);
                return null;
        }
    }

    private void printEmails(List<Email> emails) {
        System.out.printf("%-5s %-25s %-40s %-15s %-5s%n", "Email", "Subject", "Body", "Time", "Read");
        for (Email email : emails) {
            System.out.printf("%-5d %-25s %-40s %-15d %-5s%n", email.getId(), email.getSubject(), email.getMessage().substring(0, Math.min(40, email.getMessage().length())), email.getTime(), email.getStatus());
        }
    }

    public static void main(String[] args) {
        EmailApplication emailApp = new EmailApplication();
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            emailApp.processInput(input);
        }
    }
}
