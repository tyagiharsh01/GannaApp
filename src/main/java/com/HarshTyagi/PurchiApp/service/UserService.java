package com.HarshTyagi.PurchiApp.service;


import com.HarshTyagi.PurchiApp.domain.Purchi;
import com.HarshTyagi.PurchiApp.domain.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserService {
     User addUser(User user);

     Purchi addPurchi(String email, Purchi purchi);

     List<Purchi> getAllPurchi(String email);

     List<Purchi> getPurchiFromName(String email,String troliHolderName);

     double getTotalAmount(String email);

     double gettotalAmountForSpecificTimeInterval(String email,Date startDate, Date endDate);

     double getTotalWeight(String email);

     double getTotalWeightForServant(String email, String troliHolderName);

     int getTotalPurchiByName(String email, String PurchiHoldername);

     long  getTotalPurchi(String email);

     User loginUser(User user);

     Purchi deletePurchi(String email, String id);

     Map<Double,List<Purchi>> PurchiTimePeriodReport(String email, String startDate, String endDate);



}
