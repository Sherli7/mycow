package cm.sherli.api.mycow.email;

public interface EmailSender {
    void send(String to, String email);
	void sendreset(String email, String buildEmails);
}
