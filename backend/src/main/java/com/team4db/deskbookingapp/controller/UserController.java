package com.team4db.deskbookingapp.controller;

import com.team4db.deskbookingapp.jwt.exception.JWTException;
import com.team4db.deskbookingapp.jwt.exception.TokenRefreshException;
import com.team4db.deskbookingapp.jwt.model.RefreshToken;
import com.team4db.deskbookingapp.jwt.payload.request.LogInRequest;
import com.team4db.deskbookingapp.jwt.payload.request.LogOutRequest;
import com.team4db.deskbookingapp.jwt.payload.request.RegisterRequest;
import com.team4db.deskbookingapp.jwt.payload.request.TokenRefreshRequest;
import com.team4db.deskbookingapp.jwt.payload.response.AuthenticationResponse;
import com.team4db.deskbookingapp.jwt.payload.response.MessageResponse;
import com.team4db.deskbookingapp.jwt.payload.response.RegisterResponse;
import com.team4db.deskbookingapp.jwt.payload.response.TokenRefreshResponse;
import com.team4db.deskbookingapp.jwt.service.RefreshTokenService;
import com.team4db.deskbookingapp.jwt.service.UserDetailsServiceImpl;
import com.team4db.deskbookingapp.jwt.util.JWTUtil;
import com.team4db.deskbookingapp.mapper.BookingMapper;
import com.team4db.deskbookingapp.model.Booking;
import com.team4db.deskbookingapp.model.CancelBooking;
import com.team4db.deskbookingapp.model.Desk;
import com.team4db.deskbookingapp.model.User;
import com.team4db.deskbookingapp.modelDTOs.BookingDTO;
import com.team4db.deskbookingapp.repository.BookingRepository;
import com.team4db.deskbookingapp.repository.DeskRepository;
import com.team4db.deskbookingapp.repository.UserRepository;
import com.team4db.deskbookingapp.service.BookingService;
import com.team4db.deskbookingapp.service.DeskService;
import com.team4db.deskbookingapp.service.EmailSenderService;
import com.team4db.deskbookingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Slf4j
public class UserController {
  private final JWTUtil jwtUtil;
  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final JWTUtil jwtTokenUtil;
  private final UserDetailsServiceImpl userDetailsService;
  private final RefreshTokenService refreshTokenService;
  BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
  private final BookingRepository bookingRepository;
  private final DeskRepository deskRepository;
  private final String BOOKING = "booking";
  private final String UNBOOKING = "unbooking";
  private final EmailSenderService emailSender;
  private final UserService userService;
  private final BookingService bookingService;
  private final DeskService deskService;

  // AUTH ENDPOINTS
  @PostMapping("/login")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody LogInRequest logInRequest)
      throws Exception {

    if (userService.findUserByEmail(logInRequest.getEmail()) == null) {
      log.error("Incorrect username/email or password");
      return ResponseEntity.ok("Incorrect username/email or password");
    }

    if (refreshTokenService.checkUserLogged(logInRequest.getEmail())) {
      log.error("Create auth token -> user already logged in");
      return ResponseEntity.ok("User is already logged in");
    }

    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  logInRequest.getEmail(), logInRequest.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (BadCredentialsException e) {
      //      throw new Exception("Incorrect username or password");
      log.error("Incorrect username/email or password");
      return ResponseEntity.ok("Incorrect username/email or password");
    }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(logInRequest.getEmail());
    final String jwt = jwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(
        new AuthenticationResponse(
            jwt, refreshTokenService.createRefreshToken(logInRequest.getEmail())));
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
    if (userService.findUserByEmail(registerRequest.getEmail()) != null)
      return ResponseEntity.ok(new RegisterResponse(null, "The user already exists."));

    return userService.registerUser(registerRequest);
  }

  @GetMapping(value = "/bps/data/user/{JWT}")
  public User getUserFromJWTPV(@PathVariable String JWT) throws JWTException {
    return getUserFromJWT(JWT);
  }

  @GetMapping(value = "/data/user")
  public User getUserFromJWTQP(@RequestParam String JWT) throws JWTException {
    return getUserFromJWT(JWT);
  }

  public User getUserFromJWT(String JWT) throws JWTException {
    String email = jwtUtil.extractEmail(JWT);

    //noinspection DuplicatedCode
    if (email != null) {
      UserDetails userDetails = this.userDetailsService.loadUserByEmail(email);
      if (jwtUtil.validateToken(JWT, userDetails)) {
        return userService.findUserByEmail(email);
      }
    }
    return null;
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();

    return refreshTokenService
        .findByToken(requestRefreshToken)
        .map(refreshTokenService::verifyExpiration)
        .map(RefreshToken::getUserId)
        .map(
            user -> {
              String token = jwtUtil.generateToken(userDetailsService.loadUserByUsername(user));
              return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
            })
        .orElseThrow(
            () -> {
              log.error("Refresh token is not in database");
              return new TokenRefreshException(
                  requestRefreshToken, "Refresh token is not in database!");
            });
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {

    if (!refreshTokenService.checkUserLogged(logOutRequest.getUserEmail())) {
      return ResponseEntity.ok(new MessageResponse("User must be logged in first!"));
    }

    if (userRepository.findById(logOutRequest.getUserId()).isEmpty()) {
      return ResponseEntity.ok(new MessageResponse("Please enter a valid user id for logout!"));
    }

    refreshTokenService.deleteByUserId(logOutRequest.getUserId());
    return ResponseEntity.ok(new MessageResponse("Log out successful!"));
  }
  //
  // BOOKING ENDPOINTS

  @PostMapping("/book")
  //  @EventListener(ApplicationReadyEvent.class)
  public ResponseEntity<?> bookDesk(@RequestBody BookingDTO booking) {
    if (booking != null) {
      User userToUpdate = userService.findUserByUserID(booking.getUserId());
      if (userToUpdate != null) {
        // todo modify this if you want a different nr of desks per user
        if (userToUpdate.getNrOfBookedDesks() > 5) {
          log.error("bookDesk -> You have reached the limit of booked desks.");
          return ResponseEntity.ok(
              new MessageResponse("You have reached the limit of booked desks."));
        } else {

          Desk desk = deskService.findDeskByDeskId(booking.getDeskId());
          if (desk == null) {
            return ResponseEntity.ok(new MessageResponse("No desk with such id exists !"));
          }
          // if (!desk.isBooked()) {
          //            userToUpdate.getCurrentlyBookedDesks().add(desk);
          if (bookingService.checkExistenceOfBooking(
              booking.getDeskId(), booking.getEndOfBooking())) {
            return ResponseEntity.ok(new MessageResponse("Desk is already booked!"));
          }

          bookingService.makeBooking(booking, desk, userToUpdate);

          String emailBody =
              emailSender.formatMessage(
                  booking, userToUpdate.getLastName(), desk.getMapPosition(), BOOKING);

          // TODO
          emailSender.sendSimpleMail(userToUpdate.getEmail(), "Booking confirmation.", emailBody);

          return ResponseEntity.ok(new MessageResponse("Booking successful!"));
        }
      } else {
        log.error("bookDesk -> No user with such id. Check id.");
        return ResponseEntity.ok(new MessageResponse("No user with such id. Check id."));
      }
    } else {
      log.error("bookDesk -> No booking made. Check request body.");
      return ResponseEntity.ok(new MessageResponse("No booking made. Check request body."));
    }
  }

  @DeleteMapping("/book")
  public ResponseEntity<?> cancelDesk(@RequestBody CancelBooking cancelBooking) {
    Booking booking = bookingService.findBookingsByBookingId(cancelBooking.getBookingId());

    if (booking == null) {
      log.error("Cancel booking -> No booking found!");
      return ResponseEntity.ok(new MessageResponse("No booking found!"));
    }

    User user = userService.findUserByUserID(cancelBooking.getUserId());
    Desk desk = deskService.findDeskByDeskId(booking.getDesk().getDeskId());
    if (booking.getUser().getUserID().equals(cancelBooking.getUserId())) {
      if (user != null) {
        if (desk != null) {
          user.setNrOfBookedDesks(user.getNrOfBookedDesks() - 1);
          user.getUserBookings().remove(booking);
          //          userRepository.save(user);
          //          bookingRepository.delete(booking);
          userService.updateUser(user);
          bookingService.deleteBookingById(booking.getBookingId());
        } else {
          log.error("cancelDesk -> No desk with such ID found");
          return ResponseEntity.ok(new MessageResponse("No desk with such ID found."));
        }
      } else {
        log.error("cancelDesk -> No user with such ID found");
        return ResponseEntity.ok(new MessageResponse("No user with such ID found."));
      }
    } else {
      log.error("cancelDesk -> No booking found. Check booking ID.");
      return ResponseEntity.ok(new MessageResponse("No booking found. Check booking ID."));
    }
    // TODO fix mapper
    BookingDTO newBookingDTO = new BookingDTO();
    newBookingDTO.setBookedAt(booking.getBookedAt());
    newBookingDTO.setEndOfBooking(booking.getEndOfBooking());
    //    newBookingDTO.setStartOfficeHours(booking.getStartOfficeHours());
    //    newBookingDTO.setEndOfficeHours(booking.getEndOfficeHours());
    newBookingDTO.setDeskId(booking.getDesk().getDeskId());
    newBookingDTO.setUserId(booking.getUser().getUserID());
    newBookingDTO.setBookingId(booking.getBookingId());

    String emailBody =
        emailSender.formatMessage(
            newBookingDTO, user.getLastName(), desk.getMapPosition(), UNBOOKING);

    // TODO
    emailSender.sendSimpleMail(user.getEmail(), "Booking cancelled.", emailBody);

    return ResponseEntity.ok(new MessageResponse("Booking deleted successful!"));
  }
}
