package server.server.service.Impl;

import lombok.SneakyThrows;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import server.server.dtos.UserDTO;
import server.server.dtos.request.UserRegistrationRequest;
import server.server.dtos.response.EmailUsernameAvailabilityResponse;
import server.server.enums.Roles;
import server.server.errors.SuccessResponse;
import server.server.exceptions.EmailUsernameAlreadyTakenException;
import server.server.exceptions.InvalidRoleException;
import server.server.exceptions.PibAlreadyTakenException;
import server.server.fileSystem.CustomMultipartFile;
import server.server.fileSystem.service.StorageService;
import server.server.fileSystem.utilities.FolderUtility;
import server.server.fileSystem.utilities.ImageType;
import server.server.models.*;
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
    @Autowired
    StorageService storageService;

    @SneakyThrows
    @Override
    public ResponseEntity<EmailUsernameAvailabilityResponse> checkNewUserData(UserRegistrationRequest userRegistrationRequest) {
        boolean isUsernameAlredyTaken = isUsernameAlreadyTaken(userRegistrationRequest.getUsername());
        boolean isEmailAlredyTaken = isEmailAlredyTaken(userRegistrationRequest.getEmail());

        EmailUsernameAvailabilityResponse response = new EmailUsernameAvailabilityResponse(isUsernameAlredyTaken, isEmailAlredyTaken);

        if(isUsernameAlredyTaken || isEmailAlredyTaken)
            throw new EmailUsernameAlreadyTakenException("Email or username is already in use.", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @SneakyThrows
    @Override
    public ResponseEntity<?> createNewUser(UserRegistrationRequest userRegistrationRequest) {
        ResponseEntity<EmailUsernameAvailabilityResponse> response = checkNewUserData(userRegistrationRequest);

        Roles role = isRoleCorrect(userRegistrationRequest.getRole());
        if(role == null)
            throw new InvalidRoleException("Role is invalid. Choose one of three: User, Seller, Deliverer");

        if(role == Roles.SELLER){
            System.out.println("*************************\n");
            System.out.println("*************************\n");
            System.out.println("*************************\n");
            System.out.println("Prodavac");
            //Ukoliko je u pitanju prodavac
            if(isPibAlreadyExists(userRegistrationRequest.getPib())){
                //Ukoliko uneti pib vec postoji u bazi podataka
                throw new PibAlreadyTakenException("PIB already in use");
                //return new ResponseEntity<>(new PibAlreadyExistsResponse(true), HttpStatus.BAD_REQUEST);
            }
        }

        //Korisnik moze napraviti nalog sa unetim podacima
        User newUser = User.builder()
                .name(userRegistrationRequest.getName())
                .surname(userRegistrationRequest.getSurname())
                .username(userRegistrationRequest.getUsername())
                .password(BCrypt.hashpw(userRegistrationRequest.getPassword(), BCrypt.gensalt()))
                .email(userRegistrationRequest.getEmail())
                .role(roleRepository.findById((long) (role.ordinal() + 1)).get())
                .build();

        //Kreiran nov korisnik
        User createdUser = userRepository.save(newUser);
        if(createdUser == null){
            //Nesto nije kako treba
            throw new IllegalStateException("Cuvanje objekta nije uspeslo");
            //return new ResponseEntity<>("Dodavanje novog korisnika nije uspelo", HttpStatus.BAD_REQUEST);
        }

        //Kreiran user, dodati njegovu fotografiju u fileSystem
        storageService.store(createdUser.getUsername(),
                             new CustomMultipartFile(userRegistrationRequest.getPicture()),
                            ImageType.USER
        );

        //Ukoliko je korisnik uspesno kreiran treba ga dodeliti u role
        if(role == Roles.DELIVERER){
            //Ukoliko je dostavljac
            //Dodavanje dostavljaca
            Deliverer deliverer = Deliverer.builder()
                    .user(createdUser)
                    .latitude(userRegistrationRequest.getLatitude())
                    .longitude(userRegistrationRequest.getLongitude())
                    .build();
            Deliverer createdDeliverer = delivererRepository.save(deliverer);

            if(createdDeliverer == null){
                throw new IllegalStateException("Cuvanje objekta nije uspeslo");
                // return new ResponseEntity<>("Dodavanje dostavljaca nije uspelo", HttpStatus.BAD_REQUEST);
            }

            for (Long licenceId: userRegistrationRequest.getLicenceCategories()) {
                DriversLicenses driversLicense = new DriversLicenses(
                        createdDeliverer.getId(),
                        licenceId
                );

                if(driversLicensesRepository.save(driversLicense) == null)
                    throw new IllegalStateException("Cuvanje objekta nije uspeslo");
                    // return new ResponseEntity<>("Dodavanje vozackih kategorija dostavljaca nije uspelo", HttpStatus.BAD_REQUEST);
            }
        }

        if(role == Roles.SELLER){
            //Dodati u tabelu sellers
            Seller seller = Seller.builder()
                            .user(createdUser)
                            .pib(userRegistrationRequest.getPib())
                            .latitude(userRegistrationRequest.getLatitude())
                            .longitude(userRegistrationRequest.getLongitude())
                            .build();
            sellerRepository.save(seller);

            if(sellerRepository.save(seller) == null){
                //Nesto nije kako treba
                throw new IllegalStateException("Cuvanje objekta nije uspeslo");
                //return new ResponseEntity<>("Dodavanje prodavca nije uspelo", HttpStatus.BAD_REQUEST);
            }
        }


        UserDTO userDTO = UserDTO.builder()
                .name(createdUser.getName())
                .surname(createdUser.getSurname())
                .username(createdUser.getUsername())
                .email(createdUser.getEmail())
                .picture(FolderUtility.convertResourceToByteArray(storageService.loadImageAsResource(
                        createdUser.getUsername(),
                        ImageType.USER
                )))
                .role(createdUser.getRole().getName()).build();

        return new ResponseEntity<>(SuccessResponse.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.name())
                .success(true)
                .message("User added successfully.")
                .data(userDTO).build(), HttpStatus.OK);
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

    private Roles isRoleCorrect(String role){
        if(role == null)
            return null;

        return Roles.valueOf(role.toUpperCase());
    }
}
