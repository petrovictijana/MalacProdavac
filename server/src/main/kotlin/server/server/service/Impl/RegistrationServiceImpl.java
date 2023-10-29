package server.server.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import server.server.dtos.request.UserRegistrationRequest;
import server.server.dtos.response.EmailUsernameAvailabilityResponse;
import server.server.dtos.response.PibAlreadyExistsResponse;
import server.server.enums.Roles;
import server.server.models.Deliverer;
import server.server.models.DriversLicenses;
import server.server.models.Seller;
import server.server.models.User;
import server.server.repository.*;
import server.server.service.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    DriversLicensesRepository driversLicensesRepository;
    @Autowired
    DelivererRepository delivererRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public ResponseEntity<EmailUsernameAvailabilityResponse> checkNewUserData(UserRegistrationRequest userRegistrationRequest) {
        boolean isUsernameAlredyTaken = isUsernameAlreadyTaken(userRegistrationRequest.getUsername());
        boolean isEmailAlredyTaken = isEmailAlredyTaken(userRegistrationRequest.getEmail());

        EmailUsernameAvailabilityResponse response = new EmailUsernameAvailabilityResponse(isUsernameAlredyTaken, isEmailAlredyTaken);

        if(isUsernameAlredyTaken || isEmailAlredyTaken)
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> createNewUser(UserRegistrationRequest userRegistrationRequest) {
        ResponseEntity<EmailUsernameAvailabilityResponse> response = checkNewUserData(userRegistrationRequest);
        if(response.getBody().isEmailTaken() || response.getBody().isUsernameTaken())
            return response;

        if(userRegistrationRequest.getRoleId() == Roles.SELLER.ordinal()){
            //Ukoliko je u pitanju prodavac
            if(isPibAlreadyExists(userRegistrationRequest.getPib())){
                //Ukoliko uneti pib vec postoji u bazi podataka
                return new ResponseEntity<>(new PibAlreadyExistsResponse(true), HttpStatus.BAD_REQUEST);
            }
        }

        //Korisnik moze napraviti nalog sa unetim podacima
        User newUser = User.builder()
                .name(userRegistrationRequest.getName())
                .surname(userRegistrationRequest.getSurname())
                .username(userRegistrationRequest.getUsername())
                .password(BCrypt.hashpw(userRegistrationRequest.getPassword(), BCrypt.gensalt()))
                .email(userRegistrationRequest.getEmail())
                .picture(userRegistrationRequest.getPicture())
                .role(roleRepository.findById(userRegistrationRequest.getRoleId()).get())
                .build();

        //Kreiran nov korisnik
        User createdUser = userRepository.save(newUser);
        if(createdUser == null){
            //Nesto nije kako treba
            return new ResponseEntity<>("Dodavanje novog korisnika nije uspelo", HttpStatus.BAD_REQUEST);
        }

        //Ukoliko je korisnik uspesno kreiran treba ga dodeliti u role
        if(userRegistrationRequest.getRoleId() == Roles.DELIVERY_DRIVER.ordinal()){
            //Ukoliko je dostavljac
            //Dodavanje dostavljaca
            Deliverer deliverer = Deliverer.builder()
                    .user(createdUser)
                    .location(userRegistrationRequest.getLocation())
                    .build();
            Deliverer createdDeliverer = delivererRepository.save(deliverer);

            if(createdDeliverer == null){
                return new ResponseEntity<>("Dodavanje dostavljaca nije uspelo", HttpStatus.BAD_REQUEST);
            }

            for (Long licenceId: userRegistrationRequest.getLicenceCategories()) {
                DriversLicenses driversLicense = new DriversLicenses(
                        createdDeliverer.getUser().getUserId(),
                        licenceId
                );

                if(driversLicensesRepository.save(driversLicense) == null)
                    return new ResponseEntity<>("Dodavanje vozackih kategorija dostavljaca nije uspelo", HttpStatus.BAD_REQUEST);
            }
        }

        if(userRegistrationRequest.getRoleId() == Roles.SELLER.ordinal()){
            //Dodati u tabelu sellers
            Seller seller = Seller.builder()
                            .user(createdUser)
                            .pib(userRegistrationRequest.getPib())
                            .address(userRegistrationRequest.getLocation())
                            .build();
            sellerRepository.save(seller);

            if(sellerRepository.save(seller) == null){
                //Nesto nije kako treba
                return new ResponseEntity<>("Dodavanje prodavca nije uspelo", HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>("Novi korisnik je dodat", HttpStatus.OK);
    }




    private boolean isUsernameAlreadyTaken(String username){
        return userRepository.findByUsername(username) != null ? true : false;
    }
    private boolean isEmailAlredyTaken(String email){
        return userRepository.findByEmail(email) != null ? true : false;
    }
    private boolean isPibAlreadyExists(String pib){
        return sellerRepository.findByPib(pib) != null ? true : false;
    }
}
