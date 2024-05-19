package tn.esprit.interfaces;

public interface CService <Certificate> {
    void add(Certificate certificate);

    void update(Certificate certificate);

    java.util.List<Certificate> getAllCertificate();

    void delete(int certificateId);

    Certificate getOneCertificate(int reservationId);
}