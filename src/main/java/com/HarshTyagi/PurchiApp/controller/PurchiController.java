package com.HarshTyagi.PurchiApp.controller;

import com.HarshTyagi.PurchiApp.domain.Purchi;
import com.HarshTyagi.PurchiApp.domain.User;
import com.HarshTyagi.PurchiApp.service.TokenGenerator;
import com.HarshTyagi.PurchiApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/Purchi")
public class PurchiController{
    private final UserService userService;

    private TokenGenerator tokenGenerator;
    @Autowired
    public PurchiController(UserService userService, TokenGenerator tokenGenerator) {
        this.userService = userService;
        this.tokenGenerator = tokenGenerator;
    }



    //http://localhost:8082/Purchi/addPurchi
    @PostMapping("/addPurchi")
    public ResponseEntity addPurchi(HttpServletRequest httpServletRequest, @RequestBody Purchi purchi){
        String email = (String) httpServletRequest.getAttribute("email");

         return new ResponseEntity<>(userService.addPurchi(email, purchi), HttpStatus.OK);
    }
    //http://localhost:8082/Purchi/addUser
    @PostMapping("/addUser")
    public ResponseEntity addUser(@RequestBody User user){


        User retrivedUser = userService.addUser(user);
        if(retrivedUser!=null){
            return new ResponseEntity(tokenGenerator.generateToken(retrivedUser),HttpStatus.OK);
        }
        else {
            return  new ResponseEntity("Failed.......",HttpStatus.EXPECTATION_FAILED);
        }
    }
    // http://localhost:8082/Purchi/totalPurchi
    @GetMapping("/totalPurchi")
    public ResponseEntity<?> getAllPurchi(HttpServletRequest httpServletRequest){
        String email = (String) httpServletRequest.getAttribute("email");
        System.out.println(email);
        return new ResponseEntity<>(userService.getAllPurchi(email),HttpStatus.OK);

    }
    // http://localhost:8082/Purchi/getByTroliHolderName/johndoe@example.com/Jane Smith
    @GetMapping("/getByTroliHolderName/{email}/{troliHolderName}")
    public ResponseEntity<?> getAllPurchiAccordingToTroliHolderName(@PathVariable String email,@PathVariable String troliHolderName){
         return new ResponseEntity<>( userService.getPurchiFromName(email,troliHolderName),HttpStatus.OK);
    }
    // http://localhost:8082/Purchi/totalAmount
    @GetMapping("/totalAmount")
    public ResponseEntity<?> getTotalAmount(HttpServletRequest httpServletRequest){
        String email = (String) httpServletRequest.getAttribute("email");
         return new ResponseEntity<>(userService.getTotalAmount(email),HttpStatus.OK);
    }
    // http://localhost:8082/Purchi/totalWeight
    @GetMapping("/totalWeight")
    public ResponseEntity<?> getTotalWeight(HttpServletRequest httpServletRequest){
        String email = (String)httpServletRequest.getAttribute("email");
        System.out.println(email);
         return new ResponseEntity<>(userService.getTotalWeight(email),HttpStatus.OK);
    }
    // http://localhost:8082/Purchi/totalPurchi/johndoe@example.com/Jhone Doe
    @GetMapping("/totalPurchi/{email}/{purchiHolderName}")
    public  ResponseEntity<?> getTotalPurchiByName(@PathVariable String email, @PathVariable String purchiHolderName){
         return new ResponseEntity<>(userService.getTotalPurchiByName(email,purchiHolderName),HttpStatus.OK);
    }
     // http://localhost:8082/totaltroliholderWeight/Jane Smith
    @GetMapping("/totaltroliholderWeight/{troliHolderName}")
    public  ResponseEntity<?> getTotalWeightForServant( HttpServletRequest httpServletRequest, @PathVariable String troliHolderName){
        String email = (String) httpServletRequest.getAttribute("email");
        String troliHolderName1 = troliHolderName.trim();
         return new ResponseEntity<>(userService.getTotalWeightForServant(email, troliHolderName1),HttpStatus.OK);
    }
   // http://localhost:8082/Purchi/totalAmountBetweenTwoDates/johndoe@example.com/2023-09-12/2023-09-20
    @GetMapping("/totalAmountBetweenTwoDates/{startDate}/{endDate}")
    public ResponseEntity<?> getTotalAmountBetweenSpecificDates(HttpServletRequest httpServletRequest, @PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        String email = (String) httpServletRequest.getAttribute("email");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date startParsedDate = dateFormat.parse(startDate);
        Date endParsedDate = dateFormat.parse(endDate);
        System.out.println(startParsedDate+"-------"+endParsedDate);
        return  new ResponseEntity<>(userService.gettotalAmountForSpecificTimeInterval(email,startParsedDate,endParsedDate), HttpStatus.OK);
    }
    // http://localhost:8082/Purchi/login
    @PostMapping ("/login")
    public ResponseEntity logIn(@RequestBody User user){
        System.out.println(user);
        User retrivedUser = userService.loginUser(user);
        System.out.println(retrivedUser);
        if(retrivedUser!=null){
            System.out.println(tokenGenerator.generateToken(user)
            );
            return new ResponseEntity(tokenGenerator.generateToken(retrivedUser),HttpStatus.OK);
        }
        else {
            return  new ResponseEntity("Failed.......",HttpStatus.EXPECTATION_FAILED);
        }
    }
    @DeleteMapping("/deletePurchi/{id}")
    public ResponseEntity<?> deletePurchi(HttpServletRequest httpServletRequest,@PathVariable String id){
        System.out.println("Hello hi ");
        String email = (String) httpServletRequest.getAttribute("email");
        System.out.println("email" +email);
        System.out.println(id);
        return new ResponseEntity<>(userService.deletePurchi(email,id),HttpStatus.OK);
    }

}
