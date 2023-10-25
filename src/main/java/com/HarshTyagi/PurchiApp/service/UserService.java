package com.HarshTyagi.PurchiApp.service;


import com.HarshTyagi.PurchiApp.domain.Purchi;
import com.HarshTyagi.PurchiApp.domain.User;

import java.util.Date;
import java.util.List;

public interface UserService {
    public User addUser(User user);
    public Purchi addPurchi(String email, Purchi purchi);
    public List<Purchi> getAllPurchi(String email);
    public List<Purchi> getPurchiFromName(String email,String troliHolderName);
    public double getTotalAmount(String email);
    public double gettotalAmountForSpecificTimeInterval(String email,Date startDate, Date endDate);
    public double getTotalWeight(String email);
    public double getTotalWeightForServant(String email, String troliHolderName);
    public int getTotalPurchiByName(String email, String PurchiHoldername);
    public long  getTotalPurchi(String email);
    public User loginUser(User user);
    public Purchi deletePurchi(String email, String id);
    public List<Purchi> PurchiTimePeriodReport(String email,String startDate,String endDate);



}
