package com.xyz.leavemanager.service;

import java.io.*;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvParser.Feature;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;

import com.xyz.leavemanager.model.LeaveBalance;
import com.xyz.leavemanager.model.LeaveApply;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CsvUtility{

    @Value("${leave.balance.file}")
    private String path;

    @Value("${leave.applied.file}")
    private String appliedPath;

    public List<LeaveApply> loadAppliedLeaveFile(){
        File file = new File(path);
        List<LeaveApply> appliedLeaves = new ArrayList<>();

        try (InputStream in = new FileInputStream(appliedPath)){
            CsvMapper csvMapper = new CsvMapper();
            csvMapper.enable(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE);
            csvMapper.enable(CsvParser.Feature.EMPTY_STRING_AS_NULL);
            CsvSchema schema = csvMapper.typedSchemaFor(LeaveApply.class)
                    .withHeader()
                    .withStrictHeaders(false)
                    .withColumnReordering(true);
            MappingIterator<LeaveApply> mappingIterator = csvMapper.reader()
                    .forType(LeaveApply.class)
                    .with(schema)
                    .readValues(in);

            mappingIterator.forEachRemaining((t -> {appliedLeaves.add(t);}));
//            appliedLeaves.stream().forEach(e -> System.out.println(e.getEmpId() + ", "+ e.getAppliedLeaves()));
        } catch (IOException e) {
            System.out.println("There is some error in file:");
            e.printStackTrace();
        }
        return appliedLeaves;
    }

    public List<LeaveBalance> loadLeaveBalancesFromFile(){
        List<LeaveBalance> balances = new ArrayList<>();
        File file = new File(path);
        try (InputStream in = new FileInputStream(file)){
            CsvMapper csvMapper = new CsvMapper();
            csvMapper.enable(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE);
            CsvSchema schema = csvMapper.typedSchemaFor(LeaveBalance.class)
                    .withHeader()
                    .withStrictHeaders(false)
                    .withColumnReordering(true);
            MappingIterator<LeaveBalance> mappingIterator = csvMapper.reader()
                    .forType(LeaveBalance.class)
                    .with(schema)
                    .readValues(in);

            mappingIterator.forEachRemaining((t -> {balances.add(t);}));
            System.out.println("Leave balance file data size:" + balances.size());
//            balances.stream().forEach(e -> System.out.println(e.getEmpId() + ", "+ e.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return balances;
    }
}