package com.HarshTyagi.PurchiApp.service;

import com.HarshTyagi.PurchiApp.Helper.DateHelper;
import com.HarshTyagi.PurchiApp.Repository.PurchiRepo;
import com.HarshTyagi.PurchiApp.Repository.UserRepo;
import com.HarshTyagi.PurchiApp.domain.Purchi;
import com.HarshTyagi.PurchiApp.domain.User;
import org.joda.time.DateTime;
import org.joda.time.Interval;
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
        return userRepo.existsById(user.getEmail()) ? null : userRepo.insert(user);
    }

    @Override
    public Purchi addPurchi(String email, Purchi purchi) {
        if (userRepo.existsById(email)) {
            purchi.setEmail(email);
            return purchiRepo.insert(purchi);
        }
        else
            return null;
    }

    @Override
    public List<Purchi> getAllPurchi(String email) {
        return userRepo.existsById(email) ? purchiRepo.findByEmail(email) : null;
    }

    @Override
    public List<Purchi> getPurchiFromName(String email, String troliHolderName) {
        return userRepo.existsById(email) ? purchiRepo.findByEmailAndTroliHolderName(email, troliHolderName) : null;
    }

    @Override
    public double getTotalAmount(String email) {
        return userRepo.findById(email)
                .map(purchis->purchiRepo.findByEmail(email)
                        .stream().mapToDouble(p->p.getRate()*p.getWeight())
                        .sum()).
                orElse(0.0);
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
    public Map<Double, List<Purchi>> PurchiTimePeriodReport(String email, String startDate, String endDate) {
        Map<Double, List<Purchi>> purchiMap = new HashMap<>();

        // Define the interval using helper method
        Interval interval = DateHelper.defineInterval(startDate, endDate);

        // Fetch all purchis for the given email
        List<Purchi> purchis = getAllPurchi(email);

        // Filter the purchis within the specified interval, or use an empty list if purchis is null
        List<Purchi> filteredPurchis = (purchis != null) ?
                purchis.stream()
                        .filter(purchi -> interval.contains(new DateTime(purchi.getDate())))
                        .collect(Collectors.toList())
                : Collections.emptyList();

        // Calculate the sum of the weights multiplied by rates
        double sum = filteredPurchis.stream()
                .mapToDouble(purchi -> purchi.getWeight() * purchi.getRate())
                .sum();

        // Put the sum and filtered purchis list into the map
        purchiMap.put(sum, filteredPurchis);

        return purchiMap;
    }
}

