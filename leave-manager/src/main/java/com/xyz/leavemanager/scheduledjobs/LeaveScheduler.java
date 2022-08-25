package com.xyz.leavemanager.scheduledjobs;

import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

import com.xyz.leavemanager.model.LeaveBalance;
import com.xyz.leavemanager.model.LeaveApply;
import com.xyz.leavemanager.service.CsvUtility;

import java.util.stream.Collectors;

@Service
public class LeaveScheduler{

    private static List<LeaveBalance> balances;

    @Autowired
    private CsvUtility csvUtility;

    @Scheduled(fixedRate = 2000)
    public void CalculateLeaveBalance(){
        System.out.println("Inside scheduled job >> ");
        List<LeaveApply> appliedLeaves = csvUtility.loadAppliedLeaveFile();
        appliedLeaves.stream()
                .forEach(e -> balances.stream()
                        .forEach(e1 -> {
                            if(e1.getEmpId() == e.getEmpId()) {
                                if(e.getAppliedLeaves() > e1.getAvailableLeaves()){
                                    System.out.println(e1.getName() + " is not eligible for the leave.");
                                } else {
                                    System.out.println(e1.getName() + " is eligible for the leave.");
                                }
                            }
                        })
                );
    }

    @PostConstruct
    public void loadLeaveBalances(){
        System.out.println("Construction completed, load Leave Balance data");
        balances = csvUtility.loadLeaveBalancesFromFile();
    }


}
