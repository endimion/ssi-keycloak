/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.services;

import gr.uaegean.pojo.MinEduAmkaResponse.AmkaResponse;
import gr.uaegean.pojo.MinEduFamilyStatusResponse;
import gr.uaegean.pojo.MinEduResponse;
import gr.uaegean.pojo.MinEduΑacademicResponse.InspectionResult;
import java.util.Optional;
import org.springframework.web.client.HttpClientErrorException;

/**
 *
 * @author nikos
 */
public interface MinEduService {

    public Optional<String> getAccessToken(String type) throws HttpClientErrorException;

    public Optional<String> getAccessTokenByTokenType(String tokenType) throws HttpClientErrorException;

    public Optional<InspectionResult> getInspectioResultByAcademicId(String academicId, String selectedUniversityId, String esmoSessionId) throws HttpClientErrorException;

    public Optional<String> getAcademicIdFromAMKA(String amkaNumber, String selectedUniversityId, String esmoSessionId) throws HttpClientErrorException;

    public Optional<MinEduResponse.InspectionResult> getInspectioResponseByAcademicId(String academicId, String selectedUniversityId, String esmoSessionId) throws HttpClientErrorException;

    public Optional<AmkaResponse> getAmkaFullResponse(String amka, String surname);

    /*
    firstname * 'Ονομα Πολίτη σε ελληνικούς χαρακτήρες
    lastname * Επώνυμο Πολίτη σε ελληνικούς χαρακτήρες
    fathername * Πατρώνυμο Πολίτη σε ελληνικούς χαρακτήρες, ή null σε περίπτωση Πολίτη ΑΓΝΩΣΤΟΥ ΠΑΤΡΟΣ
    mothername * Μητρώνυμο Πολίτη σε ελληνικούς χαρακτήρες
    birthdate * Ημερομηνία Γέννησης Πολίτη, στη μορφή 2 ψηφία ημέρα 2 ψηφία μήνας 4 ψηφία έτος πχ 05121910
    supervisorusername * Το όνομα GDPR χρήστη επόπτείας χρήσης της υπηρεσίας
    supervisorpassword *Ο κωδικός πρόσβασης GDPR χρήστη εποπτείας χρήσης της υπηρεσίας
    servicename * Το όνομα της υπηρεσίας που επιστρέφει δεδομένα πολίτη από το Μητρώο Πολιτών σε ελληνικούς χαρακτήρες , πχ Βεβαίωση Οικογενειακής Κατάστασης
    fuzzy Παράμετρος έξυπνης αναζήτησης που παίρνει τιμή true για ενεργοποίηση και false για απενεργοποίηση, όταν είναι ενεργοποιημένη στις παράμετρους ονόματος, επωνύμου, πατρώνυμου και μητρώνυμου πολίτη δύναται να δοθούν μόν
     */
    public Optional<MinEduFamilyStatusResponse> getFamilyStatusResponse(String firstname, String lastname,
            String fathername, String mothername, String birthdate, String supervisorusername, String supervisorpassword,
            String servicename);

}
