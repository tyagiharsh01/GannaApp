package com.HarshTyagi.PurchiApp.service;

import com.HarshTyagi.PurchiApp.Repository.PurchiRepo;
import com.HarshTyagi.PurchiApp.Repository.UserRepo;
import com.HarshTyagi.PurchiApp.domain.Purchi;
import com.HarshTyagi.PurchiApp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements  UserService {
    private final UserRepo userRepo;
    private final PurchiRepo purchiRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, PurchiRepo purchiRepo) {
        this.userRepo = userRepo;
        this.purchiRepo = purchiRepo;
    }

    @Override
    public User addUser(User user) {
        if (userRepo.existsById(user.getEmail())) {
            return null;
        } else {
            return userRepo.insert(user);
        }


    }

    @Override
    public Purchi addPurchi(String email, Purchi purchi) {
        purchi.setEmail(email);
        if (userRepo.existsById(email))
            return purchiRepo.insert(purchi);
        else
            return null;
    }


    @Override
    public List<Purchi> getAllPurchi(String email) {
        if (userRepo.existsById(email)) {
            return purchiRepo.findByEmail(email);
        } else {
            return null;
        }
    }

    @Override
    public List<Purchi> getPurchiFromName(String email, String troliHolderName) {

        if (userRepo.existsById(email)) {
            return purchiRepo.findByEmailAndTroliHolderName(email, troliHolderName);
        } else {
            return null;
        }

    }

    @Override
    public double getTotalAmount(String email) {
        double sum = 0;
        Optional<User> optionalUser = userRepo.findById(email);
        if (optionalUser.isPresent()) {
            List<Purchi> purchis = purchiRepo.findByEmail(email);
            sum = purchis.stream().mapToDouble(purchi1 -> purchi1.getRate() * purchi1.getWeight()).sum();
        }
        return sum;
    }

    @Override
    public double gettotalAmountForSpecificTimeInterval(String email, Date startDate, Date endDate) {
        double sum = 0;
        Optional<User> optionalUser = userRepo.findById(email);
        if (optionalUser.isPresent()) {
            User user = userRepo.findById(email).get();
            for (Purchi purchi : purchiRepo.findByEmail(email)) {
                Date purchiDate = purchi.getDate();

                if (purchiDate != null && purchiDate.after(startDate) && purchiDate.before(endDate)) {
                    System.out.println(purchiDate);
                    sum = sum + (purchi.getWeight() * purchi.getRate());
                }
            }
        }
        return sum;
    }


    @Override
    public double getTotalWeight(String email) {
        double sum = 0;
        if (userRepo.existsById(email)) {
            List<Purchi> purchis = purchiRepo.findByEmail(email);
            double result = 0.0;
            for (Purchi purchi : purchis) {
                double weight = purchi.getWeight();
                result += weight;
            }
            sum = result;
        }
        return sum;
    }


    @Override
    public double getTotalWeightForServant(String email, String troliHolderName) {
        double sum = 0;
        Optional<User> optionalUser = userRepo.findById(email);
        if (optionalUser.isPresent()) {
            List<Purchi> purchis = purchiRepo.findByEmailAndTroliHolderName(email, troliHolderName);
            sum = purchis.stream().mapToDouble(purchi -> purchi.getWeight()).sum();

        }
        return sum;
    }

    @Override
    public int getTotalPurchiByName(String email, String purchiHolderName) {
        int count = 0;
        if (userRepo.existsById(email)) {
            List<Purchi> purchis = purchiRepo.findByEmailAndPurchiHolderName(email, purchiHolderName);
            count = (int) purchis.stream().count();
        }
        return count;
    }

    @Override
    public long getTotalPurchi(String email) {
        long count = 0;
        Optional<User> optionalUser = userRepo.findById(email);
        if (optionalUser.isPresent()) {
            List<Purchi> purchis = purchiRepo.findByEmail(email);
            count = purchis.size();
        }
        return count;
    }

    @Override
    public User loginUser(User user) {
        if (userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword()).isPresent())
            return userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword()).get();
        else {
            return null;
        }
    }

    @Override
    public Purchi deletePurchi(String email, String id) {
        Optional<User> optionalUser = userRepo.findById(email);
        if (optionalUser.isPresent()) {
            Purchi purchi = purchiRepo.findById(id).get();
            purchiRepo.deleteById(id);
            return purchi;
        } else {
            return null;
        }
    }

    @Override
    public List<Purchi> PurchiTimePeriodReport(String email, String startDate, String endDate) {
        return null;
    }
}

