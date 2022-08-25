package com.xyz.leavemanager.scheduledjobs;

import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;

import com.xyz.leavemanager.model.LeaveBalance;
import com.xyz.leavemanager.model.LeaveApply;
import com.xyz.leavemanager.service.CsvUtility;

@Service
public class LeaveScheduler{
    private static List<LeaveBalance> balances;
    @Autowired
    private CsvUtility csvUtility;

    @Scheduled(fixedRate = 1000)
    public void CalculateLeaveBalance(){
        System.out.println("Inside scheduled job >> ");
        List<LeaveApply> appliedLeaves = csvUtility.loadAppliedLeaveFile();
        for(Iterator<LeaveApply> itr = appliedLeaves.iterator(); itr.hasNext();){
            LeaveApply e = itr.next();
            boolean isRemove = balances.stream()
                .anyMatch(e1 -> {
                    if(e1.getEmpId() == e.getEmpId()) {
                        if(e.getAppliedLeaves() > e1.getAvailableLeaves()){
                            System.out.println(e1.getName() + " is not eligible for the leave.");
                        } else {
                            System.out.println(e1.getName() + " is eligible for the leave.");
                            e1.setLeavesTaken(e1.getLeavesTaken() + e.getAppliedLeaves());
                            e1.setAvailableLeaves(e1.getAvailableLeaves() - e.getAppliedLeaves());
                            csvUtility.updateLeaveBalanceFile(balances);
                        }
                        return true;
                    }
                    return false;
                });
                if(isRemove) {
                    itr.remove();
                    csvUtility.updateAppliedLeaveFile(appliedLeaves);
                }
            }
    }

    @PostConstruct
    public void loadLeaveBalances(){
        System.out.println("Construction completed, load Leave Balance data");
        balances = csvUtility.loadLeaveBalancesFromFile();
    }
}
